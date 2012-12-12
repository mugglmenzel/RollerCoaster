/**
 * 
 */
package de.eorg.rollercoaster.shared.cloudmapping.model.mapping;

import java.io.Serializable;
import java.util.List;

/**
 * @author mugglmenzel
 *
 */
public interface IAttributedItem extends Cloneable, Serializable, Comparable<IAttributedItem> {

	public Attribute<?> getAttribute(IEAttribute attributeName);
	
	public List<Attribute<?>> getAttributes();
	
}
