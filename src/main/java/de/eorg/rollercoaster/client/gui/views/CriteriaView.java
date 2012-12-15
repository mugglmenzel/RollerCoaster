package de.eorg.rollercoaster.client.gui.views;

import com.google.gwt.user.client.ui.HTML;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;

public class CriteriaView extends AbstractView {
	public static final TreeNode[] vMImageData = new TreeNode[] {
			new vMImageTreeNode("100", "1", "CheapestVMImage", true),
			new vMImageTreeNode("200", "100", "Initial License Costs", false),
			new vMImageTreeNode("300", "100", "Hourly Licence Costs", true),
			new vMImageTreeNode("400", "1", "Best Quality VM Image", true),
			new vMImageTreeNode("500", "400", "Popularity", false),
			new vMImageTreeNode("600", "400", "Age", false) };

	public static final TreeNode[] cloudServiceData = new TreeNode[] {
			new cloudServiceTreeNode("100", "1", "Best Quality Service", true),
			new cloudServiceTreeNode("110", "100", "Performance", false),
			new cloudServiceTreeNode("111", "110", "CPU", true),
			new cloudServiceTreeNode("112", "110", "RAM", true),
			new cloudServiceTreeNode("120", "100", "Uptime", false),
			new cloudServiceTreeNode("130", "100", "Popularity", false),
			new cloudServiceTreeNode("200", "1", "Cheapest Service", false),
			new cloudServiceTreeNode("210", "200", "Initial License Costs",
					false),
			new cloudServiceTreeNode("220", "200", "Hourly License Costs",
					false),
			new cloudServiceTreeNode("300", "1", "Best Latency Service", false),
			new cloudServiceTreeNode("310", "300", "Max. Lateny", false),
			new cloudServiceTreeNode("320", "300", "Avg. Latency", false) };

	public CriteriaView(EView backView, EView nextView) {
		super(true, true, "back", "next", backView,
				nextView);
		getHeading().setContents("Criteria");
		getInstructions()
				.setContents(
						"Please define Criteria on which alternatives will be evaluated");

		// first tree
		final Tree vmImageTree = new Tree();
		vmImageTree.setModelType(TreeModelType.PARENT);
		vmImageTree.setRootValue(1);
		vmImageTree.setNameProperty("Name");
		vmImageTree.setIdField("Id");
		vmImageTree.setParentIdField("ReportsTo");
		vmImageTree.setOpenProperty("isOpen");
		vmImageTree.setData(vMImageData);

		final TreeGrid vMImageTreeGrid = new TreeGrid();
		vMImageTreeGrid.setWidth(200);
		vMImageTreeGrid.setHeight(170);
		vMImageTreeGrid.setShowOpenIcons(false);
		vMImageTreeGrid.setShowDropIcons(false);
		vMImageTreeGrid.setClosedIconSuffix("");
		vMImageTreeGrid.setData(vmImageTree);
		vMImageTreeGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		vMImageTreeGrid.setShowSelectedStyle(false);
		vMImageTreeGrid.setShowPartialSelection(true);
		vMImageTreeGrid.setCascadeSelection(true);

		vMImageTreeGrid.addDrawHandler(new DrawHandler() {
			public void onDraw(DrawEvent event) {
				vMImageTreeGrid.getTree().openAll();
			}
		});

		VLayout layout_one = new VLayout();
		Label h_1 = new Label("<h3>Virtual Machine Image Criteria</h3>");
		h_1.setWidth("200px");

		layout_one.addMember(h_1);
		layout_one.addMember(vMImageTreeGrid);

		// second tree
		final Tree cloudServiceTree = new Tree();
		cloudServiceTree.setModelType(TreeModelType.PARENT);
		cloudServiceTree.setRootValue(1);
		cloudServiceTree.setNameProperty("Name");
		cloudServiceTree.setIdField("Id");
		cloudServiceTree.setParentIdField("ReportsTo");
		cloudServiceTree.setOpenProperty("isOpen");
		cloudServiceTree.setData(cloudServiceData);

		final TreeGrid cloudServiceTreeGrid = new TreeGrid();
		cloudServiceTreeGrid.setWidth(200);
		cloudServiceTreeGrid.setHeight(330);
		cloudServiceTreeGrid.setShowOpenIcons(false);
		cloudServiceTreeGrid.setShowDropIcons(false);
		cloudServiceTreeGrid.setClosedIconSuffix("");
		cloudServiceTreeGrid.setData(cloudServiceTree);
		cloudServiceTreeGrid
				.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		cloudServiceTreeGrid.setShowSelectedStyle(false);
		cloudServiceTreeGrid.setShowPartialSelection(true);
		cloudServiceTreeGrid.setCascadeSelection(true);

		cloudServiceTreeGrid.addDrawHandler(new DrawHandler() {
			public void onDraw(DrawEvent event) {
				cloudServiceTreeGrid.getTree().openAll();
			}
		});

		VLayout layout_two = new VLayout();
		Label h_2 = new Label("<h3>Cloud Service Criteria</h3>");
		h_2.setWidth("200px");

		layout_two.addMember(h_2);
		layout_two.addMember(cloudServiceTreeGrid);

		/**
		 * nextButton.addClickHandler(new ClickHandler() { public void
		 * onClick(ClickEvent event){
		 * dao.createVMCriteria(vmImageTree.getAttributeAsBoolean
		 * ("Initial License Costs"),
		 * vmImageTree.getAttributeAsBoolean("Hourly Licence Costs"),
		 * vmImageTree.getAttributeAsBoolean("Popularity"),
		 * vmImageTree.getAttributeAsBoolean("Age"));
		 * dao.createCSCriteria(cloudServiceTree.getAttributeAsBoolean("CPU"),
		 * cloudServiceTree.getAttributeAsBoolean("RAM"),
		 * cloudServiceTree.getAttributeAsBoolean("Uptime"),
		 * cloudServiceTree.getAttributeAsBoolean("Popularity"),
		 * cloudServiceTree.getAttributeAsBoolean("Initial License Costs"),
		 * cloudServiceTree.getAttributeAsBoolean("Hourly Licence Costs"),
		 * cloudServiceTree.getAttributeAsBoolean("Max. Lateny"),
		 * cloudServiceTree.getAttributeAsBoolean("Avg. Lateny")); } });
		 */

		// Alles in Layout packen
		getContent().addMember(layout_one);
		getContent().addMember(layout_two);
	}

	public static class vMImageTreeNode extends TreeNode {
		public vMImageTreeNode(String id, String reportsTo, String name,
				boolean isOpen) {
			setAttribute("Id", id);
			setAttribute("ReportsTo", reportsTo);
			setAttribute("Name", name);
			setAttribute("isOpen", isOpen);
		}
	}

	public static class cloudServiceTreeNode extends TreeNode {
		public cloudServiceTreeNode(String id, String reportsTo, String name,
				boolean isOpen) {
			setAttribute("Id", id);
			setAttribute("ReportsTo", reportsTo);
			setAttribute("Name", name);
			setAttribute("isOpen", isOpen);
		}
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}
