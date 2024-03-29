package de.eorg.rollercoaster.shared.cloudmapping.model.mapping;

/**
 * @author  mugglmenzel
 */
public interface IEAttribute {

	/**
	 * @param name  the name to set
	 * @uml.property  name="name"
	 */
	public abstract void setName(String name);

	/**
	 * @return  the name
	 * @uml.property  name="name"
	 */
	public abstract String getName();

}