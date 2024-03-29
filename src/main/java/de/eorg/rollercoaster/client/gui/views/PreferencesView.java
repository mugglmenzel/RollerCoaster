package de.eorg.rollercoaster.client.gui.views;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Slider;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

import de.eorg.rollercoaster.client.RollerCoaster;
import de.eorg.rollercoaster.client.services.RollerCoasterService;
import de.eorg.rollercoaster.client.services.RollerCoasterServiceAsync;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.Preferences;

public class PreferencesView extends AbstractView {

	final Slider s1 = new Slider(" ");
	final Slider s2 = new Slider(" ");
	final Slider s3 = new Slider(" ");
	final Slider s4 = new Slider(" ");
	final Slider s5 = new Slider(" ");
	private RollerCoasterServiceAsync rollerCoasterService = GWT
			.create(RollerCoasterService.class);
	
	private Logger logger = Logger.getLogger("CriteriaView.class.getName()");

	public PreferencesView(EView backView, EView nextView) {
		super(true, false, "back", "next", backView, nextView);

		
		
		getHeading().setContents("Preferences");
		getInstructions()
				.setContents(
						"Please define Preferences comparing evaluation criteria in pairwise comparisons in matters of importance.");

		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(10);
		
		if(RollerCoaster.loginInfo.getMember()!=null){
		rollerCoasterService.loadPreferences(RollerCoaster.loginInfo.getMember().getSocialId(),new AsyncCallback<Preferences>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Preferences result) {
				s1.setValue(result.getVMImage());
				s2.setValue(result.getQuality());
				s3.setValue(result.getLatency());
				s4.setValue(result.getPerformance());
				s5.setValue(result.getCost());
				//SC.say(new Integer(result.getVMImage()).toString());
				
			}});

		}
		
		s1.setVertical(false);
		s1.setNumValues(5);
		s1.setLength(400);
		s1.setMinValueLabel("Cheap VM Image");
		s1.setMaxValueLabel("Quality VM Image");
		s1.setMaxValue(5);
		s1.setMinValue(1);
		vp.add(s1);

		s2.setVertical(false);
		s2.setNumValues(5);
		s2.setLength(400);
		s2.setMinValueLabel("Cheap Service");
		s2.setMaxValueLabel("Quality Service");
		s2.setMaxValue(5);
		s2.setMinValue(1);
		vp.add(s2);

		s3.setVertical(false);
		s3.setNumValues(5);
		s3.setLength(400);
		s3.setMinValueLabel("Latency Service");
		s3.setMaxValueLabel("Cheap Service");
		s3.setMaxValue(5);
		s3.setMinValue(1);
		vp.add(s3);

		s4.setVertical(false);
		s4.setNumValues(5);
		s4.setLength(400);
		s4.setMinValueLabel("Performance");
		s4.setMaxValueLabel("Uptime");
		s4.setMaxValue(5);
		s4.setMinValue(1);
		vp.add(s4);

		s5.setVertical(false);
		s5.setNumValues(5);
		s5.setLength(400);
		s5.setMinValueLabel("Initial Cost");
		s5.setMaxValueLabel("Hourly Cost");
		s5.setMaxValue(5);
		s5.setMinValue(1);
		vp.add(s5);

		IButton save = new IButton("Save & Next");
		save.setTitle("Save & Next");
		save.setLeft(500);
		save.setLayoutAlign(Alignment.RIGHT);
		save.setVisible(true);
		save.setAutoFit(true);
		save.setIcon("/images/arrow_right.png");
		save.setIconOrientation("right");
//		save.moveTo(300, 10);
//		save.setVisible(true);
//		save.setAutoFit(true);
//		save.setTitle("Save");
//		vp.add(save);
		 
		// Alles auf in Layout packen
		getContent().addMember(vp);
//		getContent().addMember(save);
		navigation.addChild(save);

		save.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				//TODO: remove
				 logger.log(Level.INFO, "Save button clicked");
				String member = "default";
				if(RollerCoaster.loginInfo.getMember()!=null)
					member = RollerCoaster.loginInfo.getMember().getSocialId();
				rollerCoasterService.savePreference((int) s1.getValue(),(int) s2.getValue(), (int) s3.getValue(),(int) s4.getValue(), (int) s5.getValue(),member,new AsyncCallback<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						
					}

					@Override
					public void onSuccess(Void result) {
						nextTab(-1);
					}});
				
			}
		});
		
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

}
