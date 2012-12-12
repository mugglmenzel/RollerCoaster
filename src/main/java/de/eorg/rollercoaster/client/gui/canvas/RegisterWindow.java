/**
 * 
 */
package de.eorg.rollercoaster.client.gui.canvas;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ImageStyle;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import de.eorg.rollercoaster.client.gui.util.ImageUtil;
import de.eorg.rollercoaster.shared.model.Member;

/**
 * @author mugglmenzel
 * 
 */
public class RegisterWindow extends Window {

	private Member member;

	private DynamicForm form = new DynamicForm();

	private TextItem emailItem = new TextItem("email", "Email");

	private TextItem firstNameItem = new TextItem("firstName", "First Name");

	private TextItem lastNameItem = new TextItem("lastName", "Last Name");

	private TextItem AWSSecretItem = new TextItem("awsSecret", "AWS Secret Key");

	private TextItem AWSAccessItem = new TextItem("awsAccess", "AWS Access Key");

	private IButton saveButton = new IButton("Save");

	/**
	 * 
	 */
	public RegisterWindow(Member member) {
		this.member = member;
		addItem(getWindowLayout());
	}

	public Layout getWindowLayout() {
		setWidth(500);
		setHeight(500);
		setTitle("Register");
		setShowMinimizeButton(false);
		setIsModal(true);
		setShowModalMask(true);
		setAutoCenter(true);
		setDismissOnOutsideClick(true);
		setShowShadow(true);
		setShadowOffset(0);
		setShadowSoftness(10);

		addCloseClickHandler(new CloseClickHandler() {
			public void onCloseClick(CloseClientEvent event) {
				destroy();
			}
		});

		Img profileImg = new Img(member.getProfilePic(), 100,
				ImageUtil.getScaledImageHeight(member.getProfilePic(), 100));
		profileImg.setImageType(ImageStyle.STRETCH);

		HeaderItem header = new HeaderItem();
		header.setDefaultValue("Registration");
		emailItem.setValue(member.getEmail());
		emailItem.setRequired(true);
		firstNameItem.setValue(member.getFirstname());
		firstNameItem.setRequired(true);
		lastNameItem.setValue(member.getLastname());
		lastNameItem.setRequired(true);
		AWSSecretItem.setValue(member.getAWSSecretKey());
		AWSAccessItem.setValue(member.getAWSAccessKey());

		form.setFields(header, emailItem, firstNameItem, lastNameItem,
				AWSSecretItem, AWSAccessItem);
		form.setAutoFocus(true);

		HLayout buttons = new HLayout();
		buttons.setMembersMargin(15);
		buttons.setAlign(Alignment.CENTER);

		IButton cancelButton = new IButton("Cancel");
		cancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				destroy();
				// com.google.gwt.user.client.Window.Location.assign(GWT.getHostPageBaseURL());
			}
		});
		saveButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (form.validate()) {
					member.setEmail(emailItem.getValueAsString());
					member.setFirstname(firstNameItem.getValueAsString());
					member.setLastname(lastNameItem.getValueAsString());
					member.setAWSSecretKey(AWSSecretItem.getValueAsString());
					member.setAWSAccessKey(AWSAccessItem.getValueAsString());

					//TODO: member to loginservice
					/*CrawlerServiceAsync crawlerService = GWT
							.create(CrawlerService.class);
					crawlerService.registerMember(member,
							new AsyncCallback<Member>() {

								@Override
								public void onSuccess(Member result) {
									if (member != null) {
										destroy();
										com.google.gwt.user.client.Window.Location
												.assign(GWT
														.getHostPageBaseURL());
									} else
										SC.warn("Email address already in use!");
								}

								@Override
								public void onFailure(Throwable caught) {
									SC.warn("Something went wrong!");
								}
							});*/
				}
			}
		});

		buttons.addMember(saveButton);
		buttons.addMember(cancelButton);

		VLayout windowLayout = new VLayout();
		windowLayout.setMargin(10);
		windowLayout.setMembersMargin(15);
		windowLayout.addMember(profileImg);
		windowLayout.addMember(form);
		windowLayout.addMember(buttons);

		return windowLayout;
	}
}
