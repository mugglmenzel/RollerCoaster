package de.eorg.rollercoaster.client.gui.views;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BkgndRepeat;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;

import de.eorg.rollercoaster.client.RollerCoaster;

public abstract class AbstractView implements IView {
	private Layout layout = new VLayout();

	private Label heading = new Label();

	private Label instructions = new Label();

	private Layout content = new VLayout();

	public Canvas navigation = new Canvas();

	private Label postit = new Label();

	private Label postitHeader = new Label();

	/**
	 * 
	 */
	public AbstractView(boolean showBackButton, boolean showNextButton,
			String backLabel, String nextLabel, EView backView, EView nextView) {
		super();
		setLayout(new VLayout());
		getLayout().setWidth100();
		getLayout().setHeight100();

		HLayout tabContentLayout = new HLayout();
		tabContentLayout.setWidth100();
		tabContentLayout.setAlign(VerticalAlignment.TOP);

		VLayout contentLayout = new VLayout();
		contentLayout.setWidth(600);
		contentLayout.setMaxWidth(600);
		contentLayout.setAutoHeight();
		VLayout postitLayout = new VLayout();
		postitLayout.setWidth(400);
		postitLayout.setHeight100();
		postitLayout.setSnapTo("TL");
		postitLayout.setAlign(Alignment.LEFT);

		tabContentLayout.addMember(contentLayout);
		tabContentLayout.addMember(postitLayout);

		getHeading().setStyleName("heading");
		getHeading().setHeight(30);
		getHeading().setMargin(10);
		getInstructions().setStyleName("instructions");
		getInstructions().setHeight(15);
		getInstructions().setMargin(10);
		createNavigation(showBackButton, showNextButton, backLabel, nextLabel,
				backView, nextView);
		getNavigation().setMargin(10);

		VLayout help = new VLayout();
		help.setBackgroundImage("/images/post-it.png");
		help.setBackgroundRepeat(BkgndRepeat.NO_REPEAT);
		help.setWidth(150);
		help.setHeight(167);
		help.setLayoutAlign(VerticalAlignment.TOP);

		getPostitHeader().setValign(VerticalAlignment.TOP);
		getPostitHeader().setAutoHeight();
		getPostitHeader().setMaxWidth(300);
		getPostitHeader().setStyleName("postitHeader");
		getPostitHeader().setContents("Help");
		getPostitHeader().setIcon("/images/help.png");

		getPostit().setValign(VerticalAlignment.TOP);
		getPostit().setAutoHeight();
		getPostit().setMaxWidth(300);
		getPostit().setStyleName("postit");

		help.addMember(getPostitHeader());
		help.addMember(getPostit());

		contentLayout.addMember(getHeading());
		contentLayout.addMember(getInstructions());
		contentLayout.addMember(getContent());
		contentLayout.addMember(getNavigation());

		postitLayout.addMember(help);

		getLayout().addMember(tabContentLayout);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.eorganization.hoopla.client.smartView.IView#getLayout()
	 */
	public Layout getLayout() {
		return layout;
	}

	/**
	 * @param layout
	 *            the layout to set
	 */
	private void setLayout(Layout layout) {
		this.layout = layout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.eorganization.hoopla.client.smartView.IView#setHeading(com.smartgwt
	 * .client .widgets.Label)
	 */
	public void setHeading(Label heading) {
		this.heading = heading;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.eorganization.hoopla.client.smartView.IView#getHeading()
	 */
	public Label getHeading() {
		return heading;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.eorganization.hoopla.client.smartView.IView#setInstructions(com.smartgwt
	 * .client .widgets.Label)
	 */
	public void setInstructions(Label instructions) {
		this.instructions = instructions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.eorganization.hoopla.client.smartView.IView#getInstructions()
	 */
	public Label getInstructions() {
		return instructions;
	}

	/**
	 * @param navigation
	 *            the navigation to set
	 */
	private void setNavigation(Canvas navigation) {
		this.navigation = navigation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.eorganization.hoopla.client.smartView.IView#getNavigation()
	 */
	public Canvas getNavigation() {
		return navigation;
	}

	private void createNavigation(boolean showBackButton,
			boolean showNextButton, String backLabel, String nextLabel,
			final EView backView, final EView nextView) {

		IButton back = new IButton();
		back.setTitle(backLabel);
		back.setLayoutAlign(Alignment.LEFT);
		back.setDisabled(!showBackButton);
		back.setVisible(showBackButton);
		back.setAutoFit(true);
		if (showBackButton)
			back.setIcon("/images/arrow_left.png");
		back.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				previousTab(-1);
			}
		});

		IButton saveAndNext = new IButton();
		saveAndNext.setTitle(nextLabel);
		saveAndNext.setLeft(500);
		saveAndNext.setLayoutAlign(Alignment.RIGHT);
		saveAndNext.setDisabled(!showNextButton);
		saveAndNext.setVisible(showNextButton);
		saveAndNext.setAutoFit(true);
		if (showNextButton)
			saveAndNext.setIcon("/images/arrow_right.png");
		saveAndNext.setIconOrientation("right");
		saveAndNext.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				nextTab(-1);
			}
		});

		Canvas navigation = new Canvas();
		navigation.setWidth(500);
		navigation.setHeight(saveAndNext.getHeight());
		navigation.addChild(back);
		if (showNextButton)
			navigation.addChild(saveAndNext);

		setNavigation(navigation);
	}

	/**
	 * @param content
	 *            the content to set
	 */
	private void setContent(Layout content) {
		this.content = content;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.eorganization.hoopla.client.smartView.IView#getContent()
	 */
	public Layout getContent() {
		return content;
	}

	/**
	 * @param postit
	 *            the postit to set
	 */
	private void setPostit(Label postit) {
		this.postit = postit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.eorganization.hoopla.client.smartView.IView#getPostit()
	 */
	public Label getPostit() {
		return postit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.eorganization.hoopla.client.smartView.IView#setPostitHeader(com.smartgwt
	 * .client .widgets.Label)
	 */
	public void setPostitHeader(Label postitHeader) {
		this.postitHeader = postitHeader;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.eorganization.hoopla.client.smartView.IView#getPostitHeader()
	 */
	public Label getPostitHeader() {
		return postitHeader;
	}

	public void nextTab(final int nextNo) {
		RollerCoaster.getViewsTabSet().selectTab(
				nextNo != -1 ? nextNo : RollerCoaster.getViewsTabSet()
						.getSelectedTabNumber() + 1);
		RollerCoaster.getViewsTabSet().getSelectedTab()
				.fireEvent(new TabSelectedEvent(null));
	}

	public void previousTab(final int prevNo) {
		RollerCoaster.getViewsTabSet().selectTab(
				prevNo != -1 ? prevNo : RollerCoaster.getViewsTabSet()
						.getSelectedTabNumber() - 1);
		RollerCoaster.getViewsTabSet().getSelectedTab()
				.fireEvent(new TabSelectedEvent(null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.eorganization.hoopla.client.smartView.IView#update()
	 */
	public abstract void refresh();

	public void show() {
		getLayout().bringToFront();
	}

}
