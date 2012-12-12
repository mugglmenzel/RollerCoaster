/**
 * 
 */
package de.eorg.rollercoaster.shared.cloudmapping.model.mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mugglmenzel
 * 
 */
public abstract class Appliance implements IAttributedItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5718934871710445135L;

	/**
	 * @uml.property name="name"
	 */
	private String name;

	/**
	 * @uml.property name="attributes"
	 * @uml.associationEnd multiplicity="(0 -1)" elementType=
	 *                     "de.eorg.cumulusgenius.shared.cloudmapping.model.mapping.Attribute"
	 */
	private List<Attribute<?>> attributes = new ArrayList<Attribute<?>>();

	public Appliance(String name) {
		super();
		this.name = name;
	}

	public Appliance(String name, List<Attribute<?>> attributes) {
		super();
		this.name = name;
		this.attributes = attributes;
	}

	public void setAttributes(List<Attribute<?>> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @param name
	 *            the name to set
	 * @uml.property name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 * @uml.property name="name"
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the attributes
	 */
	@Override
	public List<Attribute<?>> getAttributes() {
		return attributes;
	}

	@Override
	public Attribute<?> getAttribute(IEAttribute attributeName) {
		for (Attribute<?> a : getAttributes())
			if (a.getName().equals(attributeName))
				return a;
		return null;
	}

	@Override
	public int compareTo(IAttributedItem o) {
		return ((o.getAttributes() != null && o.getAttributes() != null) ? getAttributes()
				.size() - o.getAttributes().size()
				: (getAttributes() != null ? 1 : -1));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Appliance)) {
			return false;
		}
		Appliance other = (Appliance) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
