package de.eorg.rollercoaster.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.eorg.rollercoaster.client.services.RollerCoasterService;
import de.eorg.rollercoaster.client.services.RollerCoasterServiceAsync;
import de.eorg.rollercoaster.client.services.LoginInfo;
import de.eorg.rollercoaster.client.services.LoginService;
import de.eorg.rollercoaster.client.services.LoginServiceAsync;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class _CumulusGenius_ implements EntryPoint {

	
	private VerticalPanel mainPanel = new VerticalPanel();

	/*
	private final TabSet tabSet = new TabSet();
	
	Tab welcome = new Tab("Welcome");
	Tab newDecisionTab = new Tab("New Decision");
	Tab ComponentsTab = new Tab("Components");
	Tab RequirementsTab = new Tab("Requirements");
	Tab CriteriaTab = new Tab("Criteria");
	Tab PreferencesTab = new Tab("Preferences");
	Tab RecommentdationTab = new Tab("Recommendation");
	
	*/

	public static LoginServiceAsync loginService = GWT
			.create(LoginService.class);

	public static RollerCoasterServiceAsync CumulusGeniusService = GWT
			.create(RollerCoasterService.class);
	private LoginInfo loginInfo = null;
	
	private VerticalPanel loginPanel = new VerticalPanel();
	
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access Cumulus Genius.");
	
	private Anchor signInLink = new Anchor("Sign In");
	
	private Anchor signOutLink = new Anchor("Sign Out");

	
	@Override
	public void onModuleLoad() {
		
		
		createMasterLayout();
		/*
		// Check login status using login service.
		loginService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {
					public void onFailure(Throwable error) {
					}

					public void onSuccess(LoginInfo result) {
						loginInfo = result;
						if (loginInfo.isLoggedIn()) {
							createMasterLayout();
						} else {
							loadLogin();
						}

					}
				});

*/
	}

	private void loadLogin() {
		// Assemble login panel.
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("cumulus").add(loginPanel);

	}

	private void createMasterLayout() {
		/*
		// Set up sign out hyperlink.
		// signOutLink.setHref(loginInfo.getLogoutUrl());

		
		final WelcomeView welcomeView = new WelcomeView();
		welcome.setPane(welcomeView.getContent());
		tabSet.addTab(welcome);

		final NewDecisionView newDecisionView = new NewDecisionView();
		newDecisionTab.setPane(newDecisionView.getContent());
		tabSet.addTab(newDecisionTab);
		
	
		tabSet.setWidth100();
		tabSet.setHeight100();
		
		// Create the view
		// mainPanel.add(signOutLink);
		mainPanel.add(tabSet);
		RootPanel.get("cumulus").add(mainPanel);
		tabSet.redraw();
		
		*/
		
		
		/*
		TabLayoutPanel p = new TabLayoutPanel(1.5, Unit.EM);
		p.setAnimationDuration(1000);
		p.getElement().getStyle().setMarginBottom(10.0, Unit.PX);
		
		
		p.add(new HTML("this content"), "this");
		p.add(new HTML("that content"), "that");
		p.add(new HTML("the other content"), "the other");
		
		p.selectTab(0);
		
	 	//mainPanel.add(p);
	 	RootPanel.get("cumulus").add(p);
		*/
		
		
		
		/*
		final TabSet topTabSet = new TabSet();  
        topTabSet.setTabBarPosition(Side.TOP);  
        topTabSet.setWidth(400);  
        topTabSet.setHeight(200);  
  
        Tab tTab1 = new Tab("Blue");
  
        Tab tTab2 = new Tab("Green");  
          
        topTabSet.addTab(tTab1);  
        topTabSet.addTab(tTab2);  
  
        final TabSet leftTabSet = new TabSet();  
        leftTabSet.setTabBarPosition(Side.LEFT);  
        leftTabSet.setWidth(400);  
        leftTabSet.setHeight(200);  
  
        Tab lTab1 = new Tab();  
        Tab lTab2 = new Tab();  
       
  
        leftTabSet.addTab(lTab1);  
        leftTabSet.addTab(lTab2);  
  
        HLayout buttons = new HLayout();  
        buttons.setMembersMargin(15);  
  
        IButton blueButton = new IButton("Select Blue");  
        blueButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
                topTabSet.selectTab(0);  
                leftTabSet.selectTab(0);  
            }  
        });  
  
        IButton greeButton = new IButton("Select Green");  
        greeButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
                topTabSet.selectTab(1);  
                leftTabSet.selectTab(1);  
            }  
        });  
          
        buttons.addMember(greeButton);  
        buttons.addMember(blueButton);  
  
        VLayout vLayout = new VLayout();  
        vLayout.setMembersMargin(15);  
        vLayout.addMember(topTabSet);  
        vLayout.addMember(buttons);  
        vLayout.addMember(leftTabSet);  
        vLayout.setHeight("auto");  
  
        //vLayout.draw();  
		
        mainPanel.add(vLayout);
	 	RootPanel.get("cumulus").add(mainPanel);
		*/
		 
		
		
			TabPanel tabs = new TabPanel();
			tabs.add(new HTML("1 ist gut"), "<div id=\"first\">Basic Panels</div>", true);
		    tabs.add(new HTML("2"), "Dock Panel");
		    tabs.add(new HTML("3"), "Tables");
		    tabs.setWidth("100%");
		    tabs.selectTab(0);
		    
		    
		    

		    RootPanel.get("cumulus").add(tabs);
		
		
		
	}
}
