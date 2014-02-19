package nz.co.aetheric.shiva.web.jade;

import de.neuland.jade4j.model.JadeModel;

import java.util.Map;

/**
 * Defines the requirements for injectable {@link JadeModel} provider classes.
 * <p>Author: <a href="http://gplus.to/tzrlk">Peter Cummuskey</a></p>
 */
public interface JadeModelProvider {

	/**
	 * This method is used to discriminate between templates.
	 * @return The name of the template this model refers to.
	 */
	public String getTemplateName();

	/**
	 * Provides a hook to generate an object model for the given template.
	 * @return The model required for this particular template.
	 */
	public Map<String, Object> getTemplateModel();

}
