package de.eorg.rollercoaster.client.gui.views;

import com.google.gwt.user.client.ui.Label;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VStack;

import de.eorg.rollercoaster.client.datasource.ComponentsXmlDS;

public class ComponentsView extends AbstractView {

	/**
	 * final ListGridField componentTypeField = new
	 * ListGridField("componentType", "Component Type"); final ListGridField
	 * softwareField = new ListGridField("software", "Software"); final
	 * ListGridField interconnectionsField = new ListGridField(
	 * "interconnections", "Interconnections");
	 */

	public final ListGrid componentsGrid = new ListGrid();

	public ComponentsView(EView nextView) {
		super(false, true,
			null, "next", EView.COMPONENTS_VIEW, nextView);

		getHeading().setContents("Components");
		getInstructions()
				.setContents(
						"Please define Requirements which have to be met by your System");
		Layout layout = new VStack();

		componentsGrid.setWidth(500);
		componentsGrid.setHeight(224);
		componentsGrid.setCellHeight(22);
		componentsGrid.setDataSource(ComponentsXmlDS.getInstance());

		componentsGrid.setAutoFetchData(true);
		componentsGrid.setCanEdit(true);
		componentsGrid.setModalEditing(true);
		componentsGrid.setEditEvent(ListGridEditEvent.CLICK);
		componentsGrid.setListEndEditAction(RowEndEditAction.NEXT);
		componentsGrid.setAutoSaveEdits(false);
		layout.addMember(componentsGrid);

		IButton addComponentButton = new IButton("Add Component");
		addComponentButton.setTop(250);
		addComponentButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				componentsGrid.startEditingNew();

			}
		});
		layout.addMember(addComponentButton);

		Label testLabel = new Label();
		ListGridField[] componentsField1 = componentsGrid.getAllFields();
		if (componentsField1.length == 0)
			testLabel.setText("Das Feld ist leer.");

		getContent().addMember(layout);
		getContent().addMember(testLabel);
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

}
