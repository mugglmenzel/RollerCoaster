package de.eorg.rollercoaster.client.gui.views;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

import de.eorg.rollercoaster.client.RollerCoaster;

public class WelcomeView extends AbstractView {

	public WelcomeView(EView nextView) {
		super(false, true, "", "start", EView.WELCOME_VIEW, nextView);

		getHeading().setContents("Welcome to the Roller Coaster!");

		IButton singleComponent = new IButton(
				"<span style=\"font-size: 14pt\">Start Simple Web App in the Cloud</span>",
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						nextTab(-1);
					}
				});
		singleComponent.setWidth(500);
		singleComponent.setHeight(50);
		Label singleComponentDescription = new Label(
				"Find a VM Image and Comput Service for your Web Application with One-Click Deployment.");
		singleComponentDescription.setAutoHeight();

		IButton multiComponent = new IButton(
				"<span style=\"font-size: 14pt\">Start Complex Web App in the Cloud</span>",
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						nextTab(-1);
					}
				});
		multiComponent.setDisabled(true);
		multiComponent.setWidth(500);
		multiComponent.setHeight(50);
		Label multiComponentDescription = new Label("Coming Soon.");
		multiComponentDescription.setAutoHeight();

		getContent().addMember(singleComponent);
		getContent().addMember(singleComponentDescription);
		getContent().addMember(new Label(" "));
		getContent().addMember(multiComponent);
		getContent().addMember(multiComponentDescription);

		VerticalPanel loginPanel = new VerticalPanel();
		loginPanel.setWidth("500px");
		loginPanel.setHeight("400px");
		loginPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		DOM.setElementAttribute(loginPanel.getElement(), "id", "loginPanel");

		Label log = new Label("Sign In");
		log.setWidth("400px");
		log.setHeight("100px");
		log.setAlign(Alignment.CENTER);
		DOM.setElementAttribute(log.getElement(), "id", "log");

		Label loginLabel = new Label(
				"Please sign in to your Google Account to access Cumulus Genius.");
		loginLabel.setHeight("200px");
		loginLabel.setWidth("400px");
		loginLabel.setAlign(Alignment.CENTER);
		DOM.setElementAttribute(loginLabel.getElement(), "id", "loginLabel");

		ImgButton img = new ImgButton();
		img.setShowFocused(false);
		img.setSrc("google-sign-in.png");
		img.setWidth("200px");
		img.setHeight("37px");
		img.setAlign(Alignment.CENTER);
		img.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.Location.assign(RollerCoaster.loginInfo.getLoginUrl());
			}
		});

		loginPanel.add(log);
		loginPanel.add(loginLabel);
		loginPanel.add(img);
		// getContent().addMember(loginPanel);

	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}
}
