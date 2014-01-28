package nz.co.aetheric.shiva.web.cache;

import net.jawr.web.resource.bundle.handler.ResourceBundlesHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * TODO: What is the purpose of this class?
 * <p>Author: <a href="http://gplus.to/tzrlk">Peter Cummuskey</a></p>
 */
public class CacheServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(CacheServlet.class);

	public static final String CACHE_MIME = "text/cache-manifest";
	public static final String CACHE_HEAD = "CACHE MANIFEST";
	public static final String CACHE_SEC_CACHE = "CACHE:";
	public static final String CACHE_SEC_NET = "NETWORK:";
	public static final String CACHE_SEC_FALL = "FALLBACK:";

	protected ResourceBundlesHandler jshandler;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		ServletContext context = super.getServletContext();
		//jshandler = (ResourceBundlesHandler) context.getAttribute(ResourceBundlesHandler.JS_CONTEXT_ATTRIBUTE);
	}

	/**
	 * TODO
	 * @param req The HTTP request.
	 * @param resp The HTTP response.
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType(CACHE_MIME);

		PrintWriter writer = resp.getWriter();

		writer.println(CACHE_HEAD);
		// TODO: Last modified.

		writer.println(CACHE_SEC_CACHE);
		// TODO: Add JAWR bundles, etc.

		writer.println(CACHE_SEC_NET);
		// TODO: API urls
		writer.println("*");

		writer.println(CACHE_SEC_FALL);
		//TODO: Fallback urls.

		writer.flush();
	}
}
