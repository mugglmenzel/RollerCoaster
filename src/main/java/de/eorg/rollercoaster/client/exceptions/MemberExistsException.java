/**
 * 
 */
package de.eorg.rollercoaster.client.exceptions;

/**
 * @author mugglmenzel
 * 
 */
public class MemberExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -385467798081987879L;

	/**
	 * 
	 */
	public MemberExistsException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MemberExistsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public MemberExistsException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public MemberExistsException(Throwable arg0) {
		super(arg0);
	}

}
