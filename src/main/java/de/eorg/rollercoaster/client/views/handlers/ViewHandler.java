package de.eorg.rollercoaster.client.views.handlers;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

import de.eorg.rollercoaster.client.RollerCoaster;
import de.eorg.rollercoaster.client.views.AbstractView;


public class ViewHandler implements ClickHandler {
	
	AbstractView av = RollerCoaster.componentsView;
	
	public ViewHandler (AbstractView av){
	this.av= av;
	}
	
	

	@Override
	public void onClick(ClickEvent event) {
				
		RollerCoaster.componentsView.getLayout().setVisible(false);
		RollerCoaster.requirementsView.getLayout().setVisible(false);
		RollerCoaster.criteriaView.getLayout().setVisible(false);
		RollerCoaster.preferencesView.getLayout().setVisible(false);
		RollerCoaster.recommendationView.getLayout().setVisible(false);
		
		//if (av == CumulusGenius.componentsView)
		//{
		//	CumulusGenius.componentsView.componentInterconnections[0] = "Test";
		//}
		
		av.getLayout().setVisible(true);
		

		
		
	}

}
