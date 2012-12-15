package de.eorg.rollercoaster.client.gui.views.handlers;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

import de.eorg.rollercoaster.client.RollerCoaster;
import de.eorg.rollercoaster.client.gui.views.EView;

public class ViewHandler implements ClickHandler {

	EView av = EView.COMPONENTS_VIEW;

	public ViewHandler(EView av) {
		this.av = av;
	}

	@Override
	public void onClick(ClickEvent event) {
		RollerCoaster.getViewsMap().get(av).show();
	}

}
