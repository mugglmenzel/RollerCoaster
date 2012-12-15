/**
 * 
 */
package de.eorg.rollercoaster.client.gui.validators;

import com.smartgwt.client.widgets.form.validator.CustomValidator;

/**
 * @author mugglmenzel
 * 
 */
public class MemberExistsValidator extends CustomValidator {

	private boolean exists = true;

	/**
	 * @param exists
	 */
	public MemberExistsValidator(boolean exists) {
		super();
		this.setExists(exists);
		setErrorMessage("Member already exists.");
	}

	@Override
	protected boolean condition(Object value) {
		return !exists();
	}
	
	

	/**
	 * @return the exists
	 */
	public boolean exists() {
		return exists;
	}

	/**
	 * @param exists
	 *            the exists to set
	 */
	public void setExists(boolean exists) {
		this.exists = exists;
	}

}
