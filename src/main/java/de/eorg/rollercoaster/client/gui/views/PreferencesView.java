package de.eorg.rollercoaster.client.gui.views;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.Slider;

public class PreferencesView extends AbstractView {

	final Slider s1 = new Slider(" ");
	final Slider s2 = new Slider(" ");
	final Slider s3 = new Slider(" ");
	final Slider s4 = new Slider(" ");
	final Slider s5 = new Slider(" ");

	public PreferencesView(EView backView, EView nextView) {
		super(true, true, "back", "next", backView, nextView);

		getHeading().setContents("Preferences");
		getInstructions()
				.setContents(
						"Please define Preferences comparing evaluation criteria in pairwise comparisons in matters of importance.");

		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(10);

		s1.setVertical(false);
		s1.setNumValues(5);
		s1.setLength(800);
		s1.setMinValueLabel("Cheap VM Image");
		s1.setMaxValueLabel("Quality VM Image");
		s1.setMaxValue(5);
		s1.setMinValue(1);
		vp.add(s1);

		s2.setVertical(false);
		s2.setNumValues(5);
		s2.setLength(800);
		s2.setMinValueLabel("Cheap Service");
		s2.setMaxValueLabel("Quality Service");
		s2.setMaxValue(5);
		s2.setMinValue(1);
		vp.add(s2);

		s3.setVertical(false);
		s3.setNumValues(5);
		s3.setLength(800);
		s3.setMinValueLabel("Latency Service");
		s3.setMaxValueLabel("Cheap Service");
		s3.setMaxValue(5);
		s3.setMinValue(1);
		vp.add(s3);

		s4.setVertical(false);
		s4.setNumValues(5);
		s4.setLength(800);
		s4.setMinValueLabel("Performance");
		s4.setMaxValueLabel("Uptime");
		s4.setMaxValue(5);
		s4.setMinValue(1);
		vp.add(s4);

		s5.setVertical(false);
		s5.setNumValues(5);
		s5.setLength(800);
		s5.setMinValueLabel("Initial Cost");
		s5.setMaxValueLabel("Hourly Cost");
		s5.setMaxValue(5);
		s5.setMinValue(1);
		vp.add(s5);

		// Alles auf in Layout packen
		getContent().addMember(vp);

	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

}
