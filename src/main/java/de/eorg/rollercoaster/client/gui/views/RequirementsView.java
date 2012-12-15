package de.eorg.rollercoaster.client.gui.views;

import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VStack;

import de.eorg.rollercoaster.client.datasource.CSRequirementsXmlDS;
import de.eorg.rollercoaster.client.datasource.VMRequirementsXmlDS;

public class RequirementsView extends AbstractView {

	public RequirementsView(EView backView, EView nextView) {
		super(true, true, "back", "next", backView, nextView);

		getHeading().setContents("Requirements");
		getInstructions()
				.setContents(
						"Please define Requirements which have to be met by your System");

		// Überschrift setzen
		Label vMconstraints = new Label(
				"<h2>Virtual Machine Image Constraints</h2>");

		// erstes Grid erstellen
		Layout vmLayout = new VStack();

		final ListGrid vMGrid = new ListGrid();
		vMGrid.setWidth(500);
		vMGrid.setHeight(150);
		vMGrid.setCellHeight(22);
		vMGrid.setDataSource(VMRequirementsXmlDS.getInstance());

		final ListGridField nameTypeField = new ListGridField("name", "Name");
		final ListGridField interconnectionsField = new ListGridField(
				"constraint", "Constraint");
		final ListGridField valueFiel = new ListGridField("value", "Value");
		final ListGridField softwareField = new ListGridField("metric",
				"Metric");

		vMGrid.setAutoFetchData(true);
		vMGrid.setCanEdit(true);
		vMGrid.setModalEditing(true);
		vMGrid.setEditEvent(ListGridEditEvent.CLICK);
		vMGrid.setListEndEditAction(RowEndEditAction.NEXT);
		vMGrid.setAutoSaveEdits(false);
		vmLayout.addMember(vMGrid);

		// Buttons zum editieren der Tabelle
		IButton addComponentButton = new IButton("Add Component");
		addComponentButton.setTop(250);
		addComponentButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				vMGrid.startEditingNew();
			}
		});
		vmLayout.addMember(addComponentButton);

		IButton saveButton = new IButton("Save");
		saveButton.setTop(250);
		saveButton.setLeft(110);
		saveButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				vMGrid.saveAllEdits();
			}
		});
		vmLayout.addMember(saveButton);

		IButton deleteButton = new IButton("Delete Component");
		deleteButton.setTop(250);
		deleteButton.setLeft(220);
		deleteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				vMGrid.removeSelectedData();
			}
		});
		vmLayout.addMember(deleteButton);

		// Überschrift setzen
		Label cSconstraints = new Label("<h2>Cloud Service Constraints</h2>");

		// zweites Grid erstellen
		Layout csLayout = new VStack();
		final ListGrid cSGrid = new ListGrid();
		cSGrid.setWidth(500);
		cSGrid.setHeight(150);
		cSGrid.setCellHeight(22);
		cSGrid.setDataSource(CSRequirementsXmlDS.getInstance());

		final ListGridField cS_nameTypeField = new ListGridField("name", "Name");
		final ListGridField cs_interconnectionsField = new ListGridField(
				"constraint", "Constraint");
		final ListGridField cS_valueFiel = new ListGridField("value", "Value");
		final ListGridField cS_softwareField = new ListGridField("metric",
				"Metric");

		cSGrid.setAutoFetchData(true);
		cSGrid.setCanEdit(true);
		cSGrid.setModalEditing(true);
		cSGrid.setEditEvent(ListGridEditEvent.CLICK);
		cSGrid.setListEndEditAction(RowEndEditAction.NEXT);
		cSGrid.setAutoSaveEdits(false);
		csLayout.addMember(cSGrid);

		// Buttons zum editieren der Tabelle
		IButton cS_addComponentButton = new IButton("Add Component");
		cS_addComponentButton.setTop(250);
		cS_addComponentButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cSGrid.startEditingNew();
			}
		});
		csLayout.addMember(cS_addComponentButton);

		IButton cS_saveButton = new IButton("Save");
		cS_saveButton.setTop(250);
		cS_saveButton.setLeft(110);
		cS_saveButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cSGrid.saveAllEdits();
			}
		});
		csLayout.addMember(cS_saveButton);

		IButton cS_deleteButton = new IButton("Delete Component");
		cS_deleteButton.setTop(250);
		cS_deleteButton.setLeft(220);
		cS_deleteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cSGrid.removeSelectedData();
			}
		});
		csLayout.addMember(cS_deleteButton);

		getContent().addMember(vMconstraints);
		getContent().addMember(vmLayout);

		getContent().addMember(cSconstraints);
		getContent().addMember(csLayout);
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}
}
