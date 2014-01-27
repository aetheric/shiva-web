package nz.co.aetheric.shiva.web.jade;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.FileTemplateLoader;
import de.neuland.jade4j.template.JadeTemplate;
import de.neuland.jade4j.template.TemplateLoader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * TODO: What is the purpose of this class?
 * <p>Author: <a href="http://gplus.to/tzrlk">Peter Cummuskey</a></p>
 */
public class JadeFilter implements Filter {
	private static final Logger LOG = LoggerFactory.getLogger(JadeFilter.class);

	/** TODO */
	private JadeConfiguration config;
	private TemplateLoader loader;

	/**
	 * TODO
	 * @param filterConfig TODO
	 * @throws ServletException
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = new JadeConfiguration();

		String caching = filterConfig.getInitParameter("caching");
		if (StringUtils.isNotBlank(caching)) {
			config.setCaching("true".equalsIgnoreCase(caching));
		}

		String prettyPrint = filterConfig.getInitParameter("prettyPrint");
		if (StringUtils.isNotBlank(prettyPrint)) {
			config.setPrettyPrint("true".equalsIgnoreCase(prettyPrint));
		}

		String templatePath = filterConfig.getInitParameter("templatePath");
		loader = new FileTemplateLoader(
				StringUtils.isNotBlank(templatePath) ? templatePath : "/",
				"UTF-8"
		);

		//etc.

	}

	/**
	 * TODO
	 * @param request TODO
	 * @param response TODO
	 * @param chain TODO
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
	 * TODO
	 * @param request TODO
	 * @param response TODO
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String uri = request.getRequestURI();
		LOG.debug("Checking '%s'.", uri);

		String templateName = uri.substring(request.getContextPath().length());
		JadeTemplate template = config.getTemplate(templateName);

		// TODO: Some method of adding context to templates.

		String rendered = config.renderTemplate(template, Collections.<String, Object>emptyMap());
		response.getWriter().append(rendered);
	}

	/**
	 * TODO
	 */
	@Override
	public void destroy() {
		/* Nothing necessary here. */
	}

}