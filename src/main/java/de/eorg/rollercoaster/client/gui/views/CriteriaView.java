package de.eorg.rollercoaster.client.gui.views;



import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;

import de.eorg.rollercoaster.client.RollerCoaster;
import de.eorg.rollercoaster.client.services.RollerCoasterService;
import de.eorg.rollercoaster.client.services.RollerCoasterServiceAsync;
import de.eorg.rollercoaster.server.services.RollerCoasterServiceImpl;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.CSCriteria;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.VMCriteria;


public class CriteriaView extends AbstractView {
	
	public static final TreeNode[] vMImageData = new TreeNode[] {
			new vMImageTreeNode("100", "1", "CheapestVMImage", true),
			new vMImageTreeNode("200", "100", "Initial License Costs", false),
			new vMImageTreeNode("300", "100", "Hourly Licence Costs", true),
			new vMImageTreeNode("400", "1", "Best Quality VM Image", true),
			new vMImageTreeNode("500", "400", "Popularity", false),
			new vMImageTreeNode("600", "400", "Age", false) };

	public static final TreeNode[] cloudServiceData= new TreeNode[] {
		new cloudServiceTreeNode("100", "1", "Best Quality Service", false),
		new cloudServiceTreeNode("110", "100", "Performance", false),
		new cloudServiceTreeNode("111", "110", "CPU", false),
		new cloudServiceTreeNode("112", "110", "RAM", false),
		new cloudServiceTreeNode("120", "100", "Uptime", false),
		new cloudServiceTreeNode("130", "100", "Popularity", false),
		new cloudServiceTreeNode("200", "1", "Cheapest Service", false),
		new cloudServiceTreeNode("210", "200", "Initial License Costs",
				false),
		new cloudServiceTreeNode("220", "200", "Hourly License Costs",
				false),
		new cloudServiceTreeNode("300", "1", "Best Latency Service", false),
		new cloudServiceTreeNode("310", "300", "Max. Latency", false),
		new cloudServiceTreeNode("320", "300", "Avg. Latency", false) 
		};;
	
	private Logger logger = Logger.getLogger("CriteriaView.class.getName()");
	
	private RollerCoasterServiceAsync rollerCoasterService = GWT
			.create(RollerCoasterService.class);

	public CriteriaView(EView backView, EView nextView) {
		super(true, false, "back", "next", backView,
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
		
		
		//Einstellungen der letzten Sitzung laden
		if(RollerCoaster.loginInfo.getMember()!=null){

			rollerCoasterService.loadVMCriteria(RollerCoaster.loginInfo.getMember().getSocialId(),new AsyncCallback<VMCriteria>(){

				@Override
				public void onFailure(Throwable caught) {
					
				}

				@Override
				public void onSuccess(VMCriteria result) {
					
					if (result.getInitialLicenceCosts())
						vMImageTreeGrid.selectRecord(vMImageData[1]);
					
					if (result.isHourlyLicenceCosts())
						vMImageTreeGrid.selectRecord(vMImageData[2]);
					
					if (result.isPopularity())
						vMImageTreeGrid.selectRecord(vMImageData[4]);
					
					if (result.isAge())
						vMImageTreeGrid.selectRecord(vMImageData[5]);
					
				    logger.log(Level.INFO, "Loaded VMCriteria: "+result.getInitialLicenceCosts());
				}});

			}
		
		IButton save = new IButton("Save & Next");
		save.setTitle("Save & Next");
		save.setLeft(500);
		save.setLayoutAlign(Alignment.RIGHT);
		save.setVisible(true);
		save.setAutoFit(true);
		save.setIcon("/images/arrow_right.png");
		save.setIconOrientation("right");

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
		
		//Einstellungen der letzten Sitzung laden	    
		if(RollerCoaster.loginInfo.getMember()!=null){

			rollerCoasterService.loadCSCriteria(RollerCoaster.loginInfo.getMember().getSocialId(),new AsyncCallback<CSCriteria>(){

				@Override
				public void onFailure(Throwable caught) {
					
				}

				@Override
				public void onSuccess(CSCriteria result) {
					
					if (result.isCpu())
						cloudServiceTreeGrid.selectRecord(cloudServiceData[2]);
					
					if (result.isRam())
						cloudServiceTreeGrid.selectRecord(cloudServiceData[3]);
					
					if (result.isUptime())
						cloudServiceTreeGrid.selectRecord(cloudServiceData[4]);
					
					if (result.isPopularity())
						cloudServiceTreeGrid.selectRecord(cloudServiceData[5]);
					
					if (result.isInitialLicenceCosts())
						cloudServiceTreeGrid.selectRecord(cloudServiceData[7]);
					
					if (result.isHourlyLicenceCosts())
						cloudServiceTreeGrid.selectRecord(cloudServiceData[8]);
					
					if (result.isMaxLatency())
						cloudServiceTreeGrid.selectRecord(cloudServiceData[10]);
					
					if (result.isAvgLatency())
						cloudServiceTreeGrid.selectRecord(cloudServiceData[11]);
					
				}});

			}

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
		navigation.addChild(save);
//		layout_two.addMember(save);

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

		HLayout horizontal_layout = new HLayout();
		horizontal_layout.addMember(layout_one);
		horizontal_layout.addMember(layout_two);
		// Alles in Layout packen
		getContent().addMember(horizontal_layout);
		
		save.addClickHandler(new ClickHandler() {
			boolean iniLicCosts = false;
			boolean hourLicCosts = false;
			boolean pop = false;
			boolean age = false;
			boolean cpu = false;
			boolean ram = false;
			boolean uptime = false;
			boolean popularity = false;
			boolean csIniLicCosts = false;
			boolean csHourlyLicCosts = false;
			boolean maxLatency = false;
			boolean avgLatency= false;

			public void onClick(ClickEvent event) {

				//SC.say(vMImageTreeGrid.getSelectedPaths());
				//String[] selection = vMImageTreeGrid.getSelectedPaths().split("/");
				
				MatchResult matcher = RegExp.compile("Initial License Costs").exec(vMImageTreeGrid.getSelectedPaths());
				if(matcher != null)
					iniLicCosts=true;
				else
					iniLicCosts = false;
				matcher = RegExp.compile("Hourly").exec(vMImageTreeGrid.getSelectedPaths());
				if(matcher != null)
					hourLicCosts=true;
				else
					hourLicCosts=false;
				matcher = RegExp.compile("Popularity").exec(vMImageTreeGrid.getSelectedPaths());
				if(matcher != null)
					pop=true;
				else
					pop = false;
				matcher = RegExp.compile("Age").exec(vMImageTreeGrid.getSelectedPaths());
				if(matcher != null)
					age=true;
				else
					age=false;
				
				// CSTree Grid
				
				matcher = RegExp.compile("CPU").exec(cloudServiceTreeGrid.getSelectedPaths());
				if(matcher != null)
					cpu=true;
				else
					cpu = false;
				matcher = RegExp.compile("RAM").exec(cloudServiceTreeGrid.getSelectedPaths());
				if(matcher != null)
					ram=true;
				else
					ram=false;
				matcher = RegExp.compile("Popularity").exec(cloudServiceTreeGrid.getSelectedPaths());
				if(matcher != null)
					popularity=true;
				else
					popularity = false;
				matcher = RegExp.compile("Uptime").exec(cloudServiceTreeGrid.getSelectedPaths());
				if(matcher != null)
					uptime=true;
				else
					uptime=false;
				matcher = RegExp.compile("Initial").exec(cloudServiceTreeGrid.getSelectedPaths());
				if(matcher != null)
					csIniLicCosts=true;
				else
					csIniLicCosts = false;
				matcher = RegExp.compile("Hourly").exec(cloudServiceTreeGrid.getSelectedPaths());
				if(matcher != null)
					csHourlyLicCosts=true;
				else
					csHourlyLicCosts=false;
				matcher = RegExp.compile("Avg").exec(cloudServiceTreeGrid.getSelectedPaths());
				if(matcher != null)
					avgLatency=true;
				else
					avgLatency=false;
				matcher = RegExp.compile("Max").exec(cloudServiceTreeGrid.getSelectedPaths());
				if(matcher != null)
					maxLatency=true;
				else
					maxLatency=false;
				

//				for(int i = 0; i < selection.length;i++)
//				{
//					SC.say(selection[i]);
//					
//					
//					if (selection[i].equals("Initial License Costs"))
//						iniLicCosts = true;
//					if (selection[i].equals("Hourly Licence Costs"))
//						hourLicCosts = true;
//					if (selection[i].equals("Popularity"))
//						pop = true;
//					if (selection[i].equals("Age"))
//						age = true;
//				}
				String member = "default";
				if(RollerCoaster.loginInfo.getMember()!=null)
					member = RollerCoaster.loginInfo.getMember().getSocialId();
				rollerCoasterService.saveVMCriteria(iniLicCosts,hourLicCosts,pop,age,member,new AsyncCallback<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Void result) {
						
						
					}});
				
				rollerCoasterService.saveCSCriteria(cpu,ram,uptime,popularity,csIniLicCosts,csHourlyLicCosts,maxLatency,avgLatency,member,new AsyncCallback<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						
					}});
				nextTab(-1);
			}
		});
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
