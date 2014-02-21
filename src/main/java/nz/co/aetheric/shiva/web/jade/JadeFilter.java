package nz.co.aetheric.shiva.web.jade;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.ClasspathTemplateLoader;
import de.neuland.jade4j.template.JadeTemplate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This filter provides jade templating functionality to a web application with as
 * little pain as possible for the developer.
 * <p>Author: <a href="http://gplus.to/tzrlk">Peter Cummuskey</a></p>
 */
public class JadeFilter implements Filter {
	private static final Logger LOG = LoggerFactory.getLogger(JadeFilter.class);

	public static final String[] PREFIX_LIST = new String[] {
			"WEB-INF",
			"META-INF/resources",
			"META-INF/resources/WEB-INF"
	};

	protected JadeConfiguration config;

	protected Map<String, JadeModelProvider> providers;

	public JadeFilter() {
		providers = new LinkedHashMap<>();
	}

	/**
	 * Sets up the filter with information from the FilterConfig instance.
	 * @param filterConfig The configuration settings from the web(-fragment).xml used to initialise this.
	 * @throws ServletException
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		config = new JadeConfiguration();

		String caching = filterConfig.getInitParameter("caching");
		if (StringUtils.isNotBlank(caching)) {
			config.setCaching("true".equalsIgnoreCase(caching));
		}

		String prettyPrint = filterConfig.getInitParameter("prettyPrint");
		if (StringUtils.isNotBlank(prettyPrint)) {
			config.setPrettyPrint("true".equalsIgnoreCase(prettyPrint));
		}

		config.setTemplateLoader(new ClasspathTemplateLoader());

	}

	/**
	 * The primary hook method for the filter chain.
	 * @param request The http request.
	 * @param response The http response.
	 * @param chain The filter chain this is a part of.
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
			doFilter((HttpServletRequest) request, (HttpServletResponse) response);
		}

		chain.doFilter(request, response);
	}

	/**
	 * The more specific <strong>Http</strong>ServletXxx implementation of
	 * {@link #doFilter(ServletRequest, ServletResponse, FilterChain)}.
	 * @param request The http request.
	 * @param response The http response.
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		final String uri = request.getRequestURI();
		if (LOG.isDebugEnabled()) {
			LOG.debug("Checking '{}'.", uri);
		}

		final String templateName = uri.substring(request.getContextPath().length());
		final JadeTemplate template = this.getTemplate(templateName);

		if (template == null) {
			return;
		}

		final JadeModelProvider provider = providers.get(templateName);
		final Map<String, Object> model = provider != null
				? provider.getTemplateModel()
				: Collections.<String, Object>emptyMap();

		final String rendered = config.renderTemplate(template, model);
		response.getWriter().append(rendered);
	}

	protected JadeTemplate getTemplate(String templateName) {
		for (String uriPrefix : PREFIX_LIST) {
			try {
				return config.getTemplate(uriPrefix + templateName);
			} catch (IOException ex) {
				LOG.trace("Unable to load template:", ex);
			}
		}

		LOG.warn("Unable to find template in classpath: {}", templateName);
		return null;
	}

	/**
	 * Hook method for safely taking down this given filter instance.
	 * Not actually necessary in this implementation.
	 */
	@Override
	public void destroy() {
		/* Nothing necessary here. */
	}

	/**
	 * Adds a provider to the reference hash.
	 * @param provider The provider to add.
	 */
	public void addProvider(JadeModelProvider provider) {
		providers.put(provider.getTemplateName(), provider);
	}

	/**
	 * Removes a provider from the reference hash.
	 * @param provider The provider to remove.
	 */
	public void removeProvider(JadeModelProvider provider) {
		providers.remove(provider.getTemplateName());
	}

}
