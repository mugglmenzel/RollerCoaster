package de.eorg.rollercoaster.client.gui.views;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.Layout;

public interface IView {
	/**
	 * @return the layout
	 */
	public Layout getLayout();

	/**
	 * @return the heading
	 */
	public Label getHeading();

	/**
	 * @return the instructions
	 */
	public Label getInstructions();

	/**
	 * @return the navigation
	 */
	public Canvas getNavigation();

	/**
	 * @return the content
	 */
	public Layout getContent();

	/**
	 * @return the postit
	 */
	public Label getPostit();

	/**
	 * @return the postitHeader
	 */
	public Label getPostitHeader();

	/**
	 * Method executed to refresh view.
	 */
	public void refresh();
	
	/**
	 * Method executed to show view.
	 */
	public void show();
	
}