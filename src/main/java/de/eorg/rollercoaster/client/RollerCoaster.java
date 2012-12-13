package de.eorg.rollercoaster.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.TabSet;

import de.eorg.rollercoaster.client.gui.TopLayout;
import de.eorg.rollercoaster.client.gui.canvas.LoginWindow;
import de.eorg.rollercoaster.client.gui.views.ComponentsView;
import de.eorg.rollercoaster.client.gui.views.CriteriaView;
import de.eorg.rollercoaster.client.gui.views.PreferencesView;
import de.eorg.rollercoaster.client.gui.views.RecommendationView;
import de.eorg.rollercoaster.client.gui.views.RequirementsView;
import de.eorg.rollercoaster.client.gui.views.WelcomeView;
import de.eorg.rollercoaster.client.gui.views.handlers.ViewHandler;
import de.eorg.rollercoaster.client.services.LoginService;
import de.eorg.rollercoaster.client.services.LoginServiceAsync;
import de.eorg.rollercoaster.client.services.RollerCoasterService;
import de.eorg.rollercoaster.client.services.RollerCoasterServiceAsync;
import de.eorg.rollercoaster.shared.model.LoginInfo;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RollerCoaster implements EntryPoint {

	public static LoginServiceAsync loginService = GWT
			.create(LoginService.class);

	public static RollerCoasterServiceAsync rollerCoasterService = GWT
			.create(RollerCoasterService.class);

	public static LoginInfo loginInfo;

	private Anchor signOutLink = new Anchor("Sign Out");

	public static TabSet tabs = new TabSet();

	ImgButton componentsButton = new ImgButton();
	ImgButton requirementsButton = new ImgButton();
	ImgButton criteriaButton = new ImgButton();
	ImgButton preferencesButton = new ImgButton();
	ImgButton recommendationButton = new ImgButton();

	// liste bauen mit indizes
	{

	}
	public static WelcomeView welcomeView = new WelcomeView();
	public static ComponentsView componentsView = new ComponentsView();
	public static RequirementsView requirementsView = new RequirementsView();
	public static CriteriaView criteriaView = new CriteriaView();
	public static PreferencesView preferencesView = new PreferencesView();
	public static RecommendationView recommendationView = new RecommendationView();

	public static Layout hPan = new HLayout();
	public static Layout vPan = new VLayout();

	@Override
	public void onModuleLoad() {

		// Check login status using login service.
		loginService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {
					public void onFailure(Throwable error) {
					}

					public void onSuccess(LoginInfo result) {
						DOM.setStyleAttribute(RootPanel.get("loading")
								.getElement(), "display", "none");
						
						loginInfo = result;
						
						if (loginInfo.isLoggedIn()) {
							createMasterLayout();
						} else {
							loadLogin();
						}

					}
				});

	}

	private void loadLogin() {
		Layout masterLayout = new VLayout();
		masterLayout.addMember(new TopLayout(loginInfo));

		VLayout welcomeInfo = new VLayout(10);
		welcomeInfo.setDefaultLayoutAlign(Alignment.CENTER);
		welcomeInfo.setAlign(VerticalAlignment.TOP);
		welcomeInfo.setHeight100();
		welcomeInfo.setWidth100();
		welcomeInfo.setBackgroundColor("#ffffff");

		Label welcomeLabel = new Label(
				"<h1 style=\"font-size: 40pt\">Welcome!</h1><p style=\"font-size: 20pt\"><span style=\"font-weight: bolder;\">Hoopla</span> <span style=\"font-style: italic; font-weight: bolder;\">n.</span> is  ... .</p><p style=\"font-size: 20pt\">Feel free to play Hoopla and <a href=\"http://myownthemepark.com/prices-table/\">contact us for a premium account</a>. Simply sign in with a social loginInfo account (Facebook, Twitter, Google+).</p>");
		welcomeLabel.setWidth(600);
		welcomeLabel.setStyleName("welcome");

		Label loginAnchor = new Label(
				"<span style=\"font-size: 35pt; cursor: pointer; text-decoration: underline;\">Login</span>");
		loginAnchor.setAutoFit(true);
		loginAnchor.setWrap(false);
		loginAnchor.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				new LoginWindow(loginInfo.getLoginUrl()).show();
			}
		});

		welcomeInfo.addMember(welcomeLabel);
		welcomeInfo.addMember(loginAnchor);

		masterLayout.addMember(welcomeInfo);

		masterLayout.setWidth100();
		masterLayout.setHeight100();
		masterLayout.setMaxHeight(700);
		masterLayout.draw();
	}

	private void createMasterLayout() {
		Layout masterLayout = new VLayout();

		componentsButton.setWidth("150px");
		componentsButton.setHeight("50px");
		componentsButton.setSrc("bild1.png");
		componentsButton.setTitle("Components");
		componentsButton.setShowTitle(true);

		requirementsButton.setWidth("150px");
		requirementsButton.setLeft("150px");
		requirementsButton.setHeight("50px");
		requirementsButton.setSrc("bild2.png");
		requirementsButton.setTitle("Requirements");
		requirementsButton.setShowTitle(true);

		criteriaButton.setWidth("150px");
		criteriaButton.setLeft("300px");
		criteriaButton.setHeight("50px");
		criteriaButton.setSrc("bild2.png");
		criteriaButton.setTitle("Criteria");
		criteriaButton.setShowTitle(true);

		preferencesButton.setWidth("150px");
		preferencesButton.setLeft("450px");
		preferencesButton.setHeight("50px");
		preferencesButton.setSrc("bild2.png");
		preferencesButton.setTitle("Preferences");
		preferencesButton.setShowTitle(true);

		recommendationButton.setSrc("bild3.png");
		recommendationButton.setLeft("600px");
		recommendationButton.setTitle("Recommendation");
		recommendationButton.setWidth("150px");
		recommendationButton.setHeight("50px");
		recommendationButton.setShowTitle(true);

		hPan.setTop("50px");
		hPan.addChild(componentsButton);
		hPan.addChild(requirementsButton);
		hPan.addChild(criteriaButton);
		hPan.addChild(preferencesButton);
		hPan.addChild(recommendationButton);

		VLayout vLay = new VLayout();
		vLay.setTop("100px");
		vLay.addChild(componentsView.getLayout());
		vLay.addChild(requirementsView.getLayout());
		vLay.addChild(criteriaView.getLayout());
		vLay.addChild(preferencesView.getLayout());
		vLay.addChild(recommendationView.getLayout());

		requirementsView.getLayout().setVisible(false);
		criteriaView.getLayout().setVisible(false);
		preferencesView.getLayout().setVisible(false);
		recommendationView.getLayout().setVisible(false);

		componentsButton.addClickHandler(new ViewHandler(componentsView));
		requirementsButton.addClickHandler(new ViewHandler(requirementsView));
		criteriaButton.addClickHandler(new ViewHandler(criteriaView));
		preferencesButton.addClickHandler(new ViewHandler(preferencesView));
		recommendationButton
				.addClickHandler(new ViewHandler(recommendationView));

		// Set up sign out hyperlink.
		signOutLink.setHref(loginInfo.getLogoutUrl());

		masterLayout.addMember(new TopLayout(new de.eorg.rollercoaster.shared.model.LoginInfo()));
		masterLayout.addMember(signOutLink);
		masterLayout.addMember(hPan);
		masterLayout.addMember(vLay);
		masterLayout.setWidth100();
		masterLayout.setHeight100();
		masterLayout.setMaxHeight(700);
		masterLayout.draw();
	}

	public static TabSet getTabs() {
		return tabs;
	}

	public static void setTabs(TabSet tabs) {
		RollerCoaster.tabs = tabs;
	}
}
