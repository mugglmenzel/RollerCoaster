/**
 * 
 */
package de.eorg.rollercoaster.client.gui.canvas;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import de.eorg.rollercoaster.client.gui.handlers.MemberUpdatedHandler;
import de.eorg.rollercoaster.shared.model.Member;

/**
 * @author mugglmenzel
 * 
 */
public class IntroductionWindow extends Window {

	private Member member;

	private MemberUpdatedHandler handler;

	/**
	 * 
	 */
	public IntroductionWindow(final Member member, MemberUpdatedHandler handler) {
		this.setMember(member);
		this.setHandler(handler);

		setTitle("Introduction");
		setIsModal(true);
		setAutoCenter(true);
		setAutoSize(true);
		setDismissOnOutsideClick(true);
		setDismissOnEscape(true);
		setShowMinimizeButton(false);
		addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClientEvent event) {
				destroy();
			}
		});

		VLayout windowLayout = new VLayout();
		Img intro = new Img("/images/welcome_info.png", 676, 532);
		intro.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(
					com.smartgwt.client.widgets.events.ClickEvent event) {
				destroy();
			}
		});
		DynamicForm form = new DynamicForm();
		final CheckboxItem checkboxItem = new CheckboxItem("showAgain",
				"Do not show this introduction again!");
		checkboxItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				getMember().setShowWelcomeInfo(
						!checkboxItem.getValueAsBoolean());
				//TODO: member to loginservice
				/*CrawlerServiceAsync crawler = GWT.create(CrawlerService.class);
				crawler.updateMember(getMember(), new AsyncCallback<Member>() {

					@Override
					public void onSuccess(Member result) {
						getHandler().updated(result);
					}

					@Override
					public void onFailure(Throwable caught) {
					}
				});*/
			}
		});
		form.setFields(checkboxItem);

		windowLayout.addMember(intro);
		windowLayout.addMember(form);

		addItem(windowLayout);
	}

	/**
	 * @return the member
	 */
	public Member getMember() {
		return member;
	}

	/**
	 * @param member
	 *            the member to set
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * @return the handler
	 */
	public MemberUpdatedHandler getHandler() {
		return handler;
	}

	/**
	 * @param handler
	 *            the handler to set
	 */
	public void setHandler(MemberUpdatedHandler handler) {
		this.handler = handler;
	}

}
