package de.eorg.rollercoaster.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import de.eorg.rollercoaster.client.gui.canvas.LoginWindow;
import de.eorg.rollercoaster.client.gui.canvas.RegisterWindow;
import de.eorg.rollercoaster.client.gui.canvas.TopLayout;
import de.eorg.rollercoaster.client.gui.canvas.WelcomeWindow;
import de.eorg.rollercoaster.client.gui.views.ComponentsView;
import de.eorg.rollercoaster.client.gui.views.CriteriaView;
import de.eorg.rollercoaster.client.gui.views.EView;
import de.eorg.rollercoaster.client.gui.views.IView;
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
import de.eorg.rollercoaster.shared.model.Member;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RollerCoaster implements EntryPoint {

	private LoginServiceAsync loginService = GWT.create(LoginService.class);

	private RollerCoasterServiceAsync rollerCoasterService = GWT
			.create(RollerCoasterService.class);

	public static LoginInfo loginInfo;

	public static Map<EView, IView> viewsMap = new HashMap<EView, IView>();

	public static TabSet viewsTabSet = new TabSet();

	@Override
	public void onModuleLoad() {

		// Check login status using login service.
		loginService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {
					public void onFailure(Throwable error) {
						SC.say("Error during login! "
								+ error.getLocalizedMessage());
					}

					public void onSuccess(LoginInfo result) {
						DOM.setStyleAttribute(RootPanel.get("loading")
								.getElement(), "display", "none");

						loginInfo = result;

						if (!loginInfo.isLoggedIn()) {
							new WelcomeWindow(loginInfo).show();
							if (getMember() != null) {
								new RegisterWindow(getMember()).show();
							}
						} else {
							/*
							 * if (getMember() != null &&
							 * getMember().isShowWelcomeInfo()) new
							 * IntroductionWindow(getMember(), new
							 * MemberUpdatedHandler() {
							 * 
							 * @Override public void updated(Member member) {
							 * loginInfo.setMember(member); } }).show();
							 */
						}

						createMasterLayout();

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
				"<h1 style=\"font-size: 30pt\">Welcome!</h1><p style=\"font-size: 20pt\"><span style=\"font-weight: bolder;\">Rollercoaster</span> <span style=\"font-style: italic; font-weight: bolder;\">n.</span> is  ... .</p><p style=\"font-size: 20pt\">Feel free to ride the Rollercoaster and <a href=\"http://myownthemepark.com/prices-table/\">contact us for a premium account</a>. Simply sign in with a social loginInfo account (Facebook, Twitter, Google+).</p>");
		welcomeLabel.setWidth(600);
		welcomeLabel.setStyleName("welcome");

		Label loginAnchor = new Label(
				"<span style=\"font-size: 25pt; cursor: pointer; text-decoration: underline;\">Login</span>");
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

		ImgButton componentsButton = new ImgButton();
		ImgButton requirementsButton = new ImgButton();
		ImgButton criteriaButton = new ImgButton();
		ImgButton preferencesButton = new ImgButton();
		ImgButton recommendationButton = new ImgButton();

		componentsButton
				.addClickHandler(new ViewHandler(EView.COMPONENTS_VIEW));
		requirementsButton.addClickHandler(new ViewHandler(
				EView.REQUIREMENTS_VIEW));
		criteriaButton.addClickHandler(new ViewHandler(EView.CRITERIA_VIEW));
		preferencesButton.addClickHandler(new ViewHandler(
				EView.PREFERENCES_VIEW));
		recommendationButton.addClickHandler(new ViewHandler(
				EView.RECOMMENDATION_VIEW));

		componentsButton.setWidth("150px");
		componentsButton.setHeight("50px");
		componentsButton.setSrc("bild1.png");
		componentsButton.setTitle("Components");
		componentsButton.setShowTitle(true);

		requirementsButton.setWidth("150px");
		// requirementsButton.setLeft("150px");
		requirementsButton.setHeight("50px");
		requirementsButton.setSrc("bild2.png");
		requirementsButton.setTitle("Requirements");
		requirementsButton.setShowTitle(true);

		criteriaButton.setWidth("150px");
		// criteriaButton.setLeft("300px");
		criteriaButton.setHeight("50px");
		criteriaButton.setSrc("bild2.png");
		criteriaButton.setTitle("Criteria");
		criteriaButton.setShowTitle(true);

		preferencesButton.setWidth("150px");
		// preferencesButton.setLeft("450px");
		preferencesButton.setHeight("50px");
		preferencesButton.setSrc("bild2.png");
		preferencesButton.setTitle("Preferences");
		preferencesButton.setShowTitle(true);

		recommendationButton.setSrc("bild3.png");
		// recommendationButton.setLeft("600px");
		recommendationButton.setTitle("Recommendation");
		recommendationButton.setWidth("150px");
		recommendationButton.setHeight("50px");
		recommendationButton.setShowTitle(true);

		Layout processButtonsStack = new HStack();
		processButtonsStack.addMember(componentsButton);
		processButtonsStack.addMember(requirementsButton);
		processButtonsStack.addMember(criteriaButton);
		processButtonsStack.addMember(preferencesButton);
		processButtonsStack.addMember(recommendationButton);

		getViewsMap().put(EView.WELCOME_VIEW,
				new WelcomeView(EView.REQUIREMENTS_VIEW));
		getViewsMap()
				.put(EView.COMPONENTS_VIEW,
						new ComponentsView(EView.WELCOME_VIEW,
								EView.REQUIREMENTS_VIEW));
		getViewsMap()
				.put(EView.REQUIREMENTS_VIEW,
						new RequirementsView(EView.COMPONENTS_VIEW,
								EView.CRITERIA_VIEW));
		getViewsMap().put(
				EView.CRITERIA_VIEW,
				new CriteriaView(EView.REQUIREMENTS_VIEW,
						EView.PREFERENCES_VIEW));
		getViewsMap().put(
				EView.PREFERENCES_VIEW,
				new PreferencesView(EView.CRITERIA_VIEW,
						EView.RECOMMENDATION_VIEW));
		getViewsMap().put(EView.RECOMMENDATION_VIEW,
				new RecommendationView(EView.PREFERENCES_VIEW));

		viewsTabSet.setWidth100();
		viewsTabSet.setHeight100();
		for (IView view : getViewsMap().values()) {
			Tab newTab = new Tab(view.getHeading().getContents());
			newTab.setPane(view.getLayout());
			viewsTabSet.addTab(newTab);
		}

		Layout mainLayout = new VStack(20);
		mainLayout.setBackgroundColor("white");
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		// mainLayout.addChild(processButtonsStack);
		mainLayout.addChild(viewsTabSet);

		masterLayout.addMember(new TopLayout(loginInfo));
		masterLayout.addMember(mainLayout);
		masterLayout.setWidth100();
		masterLayout.setHeight100();
		masterLayout.setMaxHeight(700);
		masterLayout.draw();
	}

	/**
	 * @return the viewsMap
	 */
	public static Map<EView, IView> getViewsMap() {
		return viewsMap;
	}

	private Member getMember() {
		return loginInfo.getMember();
	}

	/**
	 * @return the viewsTabSet
	 */
	public static TabSet getViewsTabSet() {
		return viewsTabSet;
	}

}
