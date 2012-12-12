package de.eorg.rollercoaster.experiments;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.LogManager;

import de.eorg.rollercoaster.shared.cloudmapping.logic.ahp.AnalyticHierarchyProcess;
import de.eorg.rollercoaster.shared.cloudmapping.model.AMI;
import de.eorg.rollercoaster.shared.cloudmapping.model.EC2Resource;
import de.eorg.rollercoaster.shared.cloudmapping.model.ahp.configuration.Alternative;
import de.eorg.rollercoaster.shared.cloudmapping.model.ahp.configuration.Criterion;
import de.eorg.rollercoaster.shared.cloudmapping.model.ahp.configuration.CriterionType;
import de.eorg.rollercoaster.shared.cloudmapping.model.ahp.configuration.Goal;
import de.eorg.rollercoaster.shared.cloudmapping.model.ahp.configuration.GoalType;
import de.eorg.rollercoaster.shared.cloudmapping.model.ahp.values.Evaluation;
import de.eorg.rollercoaster.shared.cloudmapping.model.ahp.values.EvaluationResult;
import de.eorg.rollercoaster.shared.cloudmapping.model.jama.Matrix;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.ApplianceAlternative;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.ApplianceDecision;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.Attribute;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.CombinationAlternative;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.CombinationTotalValue;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.CombinationValue;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.CombinedDecision;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.Component;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.ComputeDecision;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.ComputeServiceAlternative;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.EApplianceAttribute;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.ECombinedValueAttribute;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.EComputeServiceAttribute;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.Provider;

public class ExperimentMultiComponent {

	private static final Integer NUM_PROVIDERS = 3;

	private static final Integer NUM_COMPONENTS = 3;

	private static List<Provider> providers = new ArrayList<Provider>();

	static {
		for (int i = 1; i <= NUM_PROVIDERS; i++)
			providers.add(new Provider("provider-" + i));
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		FileWriter fw = new FileWriter("out.txt");
		BufferedWriter out = new BufferedWriter(fw);
		out.write("Component,Number of AMIs,Number of Services,AMIs Model Creation (ms),AMIs Evaluation (ms),Services Model Creation (ms),Services Evaluation (ms),Combination Model Creation (ms),Combination Evaluation (ms),Total (ms)\n");
		out.close();

		System.out.println("Parameters: providers=" + NUM_PROVIDERS
				+ ", components=" + NUM_COMPONENTS);

		List<Component> components = new ArrayList<Component>();
		for (int i = 1; i <= NUM_COMPONENTS; i++)
			components.add(new Component("compo_" + i));
		for (Component c : components)
			c.setConnectedComponents(new HashSet<Component>(components));

		Map<Component, CombinationTotalValue> componentSolutions = new HashMap<Component, CombinationTotalValue>();
		// future work:
		// Map<Component, SortedSet<CombinationTotalValue>> componentSolutions =
		// new HashMap<Component, SortedSet<CombinationTotalValue>>();
		for (int i = 5; i <= 45; i = i + 5)
			for (int j = 5; j <= 45; j = j + 5) {
				System.out.println("---------------------------");
				for (Component c : components) {
					System.out.println("Running Component=" + c.getName()
							+ ", AMIs=" + i + ", Services=" + j);
					// computeSolutions(c, i, j);
					componentSolutions.put(c,
							computeSolutions(c, i, j, componentSolutions));
				}
				System.out.println("best solution: " + componentSolutions);
				System.out.println("---------------------------");
				// got all results, now clear it:
				componentSolutions = new HashMap<Component, CombinationTotalValue>();
				// future work:
				// computeFormation(componentSolutions);
			}

	}

	private static void computeFormation(
			Map<Component, SortedSet<CombinationTotalValue>> componentSolutions) {

		for (Component c : componentSolutions.keySet()) {
			System.out
					.println("here we find a global overall solution that considers internet and network costs over all dependent components spanning the large solution space");
		}
	}

	private static CombinationTotalValue computeSolutions(Component c,
			int numAMIs, int numServices,
			Map<Component, CombinationTotalValue> componentSolutions)
			throws Exception {

		// control Logging output with file
		LogManager.getLogManager().readConfiguration(
				new FileInputStream(new File("logging.properties")));

		long startTimeAMIModel = new Date().getTime();
		List<AMI> amis = new ArrayList<AMI>();
		for (int i = 0; i < numAMIs; i++) {
			AMI ami = new AMI("ami-" + i);
			ami.getAttributes().add(
					new Attribute<Double>(EApplianceAttribute.COSTPERHOUR,
							new Double((Math.random() * 0.4 + 0.1))));
			ami.getAttributes().add(
					new Attribute<Double>(EApplianceAttribute.POPULARITY,
							new Double(Math.random())));
			amis.add(ami);

		}

		// System.out.println("Generated AMIs: " + amis);

		ApplianceDecision ad = new ApplianceDecision();
		ad.setName("AMI Decision");

		Goal bestAppliance = new Goal("Best Appliance");
		bestAppliance.setGoalType(GoalType.POSITIVE);
		Criterion appliancePopularity = new Criterion("Appliance Popularity");
		appliancePopularity.setType(CriterionType.QUANTITATIVE);
		bestAppliance.addChild(appliancePopularity);

		Goal cheapestAppliance = new Goal("Cheapest Appliance");
		cheapestAppliance.setGoalType(GoalType.NEGATIVE);
		Criterion applianceCosts = new Criterion("Appliance Costs");
		applianceCosts.setType(CriterionType.QUANTITATIVE);
		cheapestAppliance.addChild(applianceCosts);

		ad.addGoal(bestAppliance);
		ad.addGoal(cheapestAppliance);

		List<ApplianceAlternative> applianceAlternatives = new ArrayList<ApplianceAlternative>();
		for (AMI a : amis) {
			ApplianceAlternative aa = new ApplianceAlternative(a,
					"alternative-" + a.getName());
			applianceAlternatives.add(aa);
			ad.addAlternative(aa);
		}

		// System.out.println("AMI Decision: " + ad);

		List<Evaluation> amiEvaluations = new ArrayList<Evaluation>();
		Evaluation evBest = new Evaluation();
		evBest.getEvaluations().add(
				createAMIMatrix(applianceAlternatives,
						EApplianceAttribute.POPULARITY));
		Evaluation evCheapest = new Evaluation();
		evCheapest.getEvaluations().add(
				createAMIMatrix(applianceAlternatives,
						EApplianceAttribute.COSTPERHOUR));
		amiEvaluations.add(evBest);
		amiEvaluations.add(evCheapest);

		long endTimeAMIModel = new Date().getTime();
		System.out.println("AMI Model Creation took "
				+ (endTimeAMIModel - startTimeAMIModel) + " ms");

		long startTimeAMIEval = new Date().getTime();
		AnalyticHierarchyProcess ahpAMI = new AnalyticHierarchyProcess(ad);
		EvaluationResult amiEvalResult = ahpAMI.evaluateFull(amiEvaluations);
		long endTimeAMIEval = new Date().getTime();
		System.out.println("AMI Evaluation took "
				+ (endTimeAMIEval - startTimeAMIEval) + " ms");

		long startTimeServiceModel = new Date().getTime();
		List<EC2Resource> services = new ArrayList<EC2Resource>();
		for (int i = 0; i < numServices; i++) {
			EC2Resource ec2 = new EC2Resource("ec2-" + i);
			ec2.getAttributes().add(
					new Attribute<Double>(EComputeServiceAttribute.COSTPERHOUR,
							new Double((Math.random() * 0.39 + 0.01))));
			ec2.getAttributes().add(
					new Attribute<Double>(
							EComputeServiceAttribute.CPUBENCHMARK, new Double(
									Math.random() * 1000)));
			ec2.getAttributes().add(
					new Attribute<Double>(
							EComputeServiceAttribute.RAMBENCHMARK, new Double(
									Math.random() * 1000)));
			ec2.getAttributes().add(
					new Attribute<Double>(
							EComputeServiceAttribute.DISKBENCHMARK, new Double(
									Math.random() * 1000)));
			ec2.getAttributes().add(
					new Attribute<Double>(EComputeServiceAttribute.MAXLATENCY,
							new Double(Math.random() * 450 + 50)));
			ec2.getAttributes().add(
					new Attribute<Double>(EComputeServiceAttribute.AVGLATENCY,
							new Double(Math.random() * 490 + 10)));
			ec2.getAttributes().add(
					new Attribute<Double>(
							EComputeServiceAttribute.SERVICEPOPULARITY,
							new Double(Math.random())));
			ec2.getAttributes().add(
					new Attribute<Double>(EComputeServiceAttribute.UPTIME,
							new Double(Math.random() * 0.1 + 0.9)));

			ec2.setProvider(providers.get(new Random().nextInt(NUM_PROVIDERS)));

			services.add(ec2);
		}

		ComputeDecision sd = new ComputeDecision();
		sd.setName("Service Decision");

		Goal bestService = new Goal("Best Service");
		bestService.setGoalType(GoalType.POSITIVE);
		Criterion servicePopularity = new Criterion("Service Popularity");
		servicePopularity.setType(CriterionType.QUANTITATIVE);
		Criterion serviceCPU = new Criterion("Service CPU");
		serviceCPU.setType(CriterionType.BENCHMARK);
		Criterion serviceRAM = new Criterion("Service RAM");
		serviceRAM.setType(CriterionType.BENCHMARK);
		Criterion serviceDisk = new Criterion("Service Disk");
		serviceDisk.setType(CriterionType.BENCHMARK);
		Criterion serviceUptime = new Criterion("Service Uptime");
		serviceUptime.setType(CriterionType.QUANTITATIVE);

		bestService.addChild(servicePopularity);
		bestService.addChild(serviceCPU);
		bestService.addChild(serviceRAM);
		bestService.addChild(serviceDisk);
		bestService.addChild(serviceUptime);

		Goal cheapestService = new Goal("Cheapest Service");
		cheapestService.setGoalType(GoalType.NEGATIVE);
		Criterion serviceCosts = new Criterion("Service Costs");
		serviceCosts.setType(CriterionType.QUANTITATIVE);
		cheapestService.addChild(serviceCosts);

		Goal latencyService = new Goal("Low Latency Service");
		latencyService.setGoalType(GoalType.NEGATIVE);
		Criterion serviceMaxLatency = new Criterion("Service Max Latency");
		serviceMaxLatency.setType(CriterionType.BENCHMARK);
		Criterion serviceAvgLatency = new Criterion("Service Avg Latency");
		serviceAvgLatency.setType(CriterionType.BENCHMARK);
		latencyService.addChild(serviceMaxLatency);
		latencyService.addChild(serviceAvgLatency);

		sd.addGoal(bestService);
		sd.addGoal(cheapestService);
		sd.addGoal(latencyService);

		List<ComputeServiceAlternative> ec2Alternatives = new ArrayList<ComputeServiceAlternative>();
		for (EC2Resource e : services) {
			ComputeServiceAlternative ea = new ComputeServiceAlternative(e,
					"alternative-" + e.getName());
			ec2Alternatives.add(ea);
			sd.addAlternative(ea);
		}

		List<Evaluation> serviceEvaluations = new ArrayList<Evaluation>();

		Evaluation evServicePopularity = new Evaluation();
		evServicePopularity.getEvaluations().add(
				createServiceMatrix(ec2Alternatives,
						EComputeServiceAttribute.SERVICEPOPULARITY));
		evServicePopularity.getEvaluations().add(
				createServiceMatrix(ec2Alternatives,
						EComputeServiceAttribute.CPUBENCHMARK));
		evServicePopularity.getEvaluations().add(
				createServiceMatrix(ec2Alternatives,
						EComputeServiceAttribute.RAMBENCHMARK));
		evServicePopularity.getEvaluations().add(
				createServiceMatrix(ec2Alternatives,
						EComputeServiceAttribute.DISKBENCHMARK));
		Evaluation evServiceCheapest = new Evaluation();
		evServiceCheapest.getEvaluations().add(
				createServiceMatrix(ec2Alternatives,
						EComputeServiceAttribute.COSTPERHOUR));
		Evaluation evServiceLatency = new Evaluation();
		evServiceLatency.getEvaluations().add(
				createServiceMatrix(ec2Alternatives,
						EComputeServiceAttribute.MAXLATENCY));
		evServiceLatency.getEvaluations().add(
				createServiceMatrix(ec2Alternatives,
						EComputeServiceAttribute.AVGLATENCY));
		serviceEvaluations.add(evServicePopularity);
		serviceEvaluations.add(evServiceCheapest);
		serviceEvaluations.add(evServiceLatency);

		long endTimeServiceModel = new Date().getTime();
		System.out.println("Service Model Creation took "
				+ (endTimeServiceModel - startTimeServiceModel) + " ms");

		long startTimeServiceEval = new Date().getTime();
		AnalyticHierarchyProcess ahpService = new AnalyticHierarchyProcess(sd);
		EvaluationResult serviceEvalResult = ahpService
				.evaluateFull(serviceEvaluations);
		long endTimeServiceEval = new Date().getTime();
		System.out.println("Service Evaluation took "
				+ (endTimeServiceEval - startTimeServiceEval) + " ms");

		long startTimeCombined = new Date().getTime();
		List<CombinationValue> combinations = new ArrayList<CombinationValue>();
		for (ApplianceAlternative aa : applianceAlternatives)
			for (ComputeServiceAlternative csa : ec2Alternatives)
				combinations.add(new CombinationValue(aa.getAppl(), csa
						.getComputeService(), amiEvalResult
						.getResultMultiplicativeIndexMap().get(aa),
						serviceEvalResult.getResultMultiplicativeIndexMap()
								.get(csa)));

		CombinedDecision cd = new CombinedDecision();
		cd.setName("Combined Decision");

		Goal cheapestCombination = new Goal("Cheapest Combination");
		cheapestCombination.setGoalType(GoalType.POSITIVE);
		Criterion combinedCosts = new Criterion("Combined Costs");
		combinedCosts.setType(CriterionType.QUANTITATIVE);
		cheapestCombination.addChild(combinedCosts);
		cd.addGoal(cheapestCombination);

		List<CombinationAlternative> combiAlternatives = new ArrayList<CombinationAlternative>();
		List<CombinationValue> combs = combinations;
		for (int i = 0; i < combs.size(); i++) {
			combs.get(i)
					.getAttributes()
					.add(new Attribute<Double>(
							ECombinedValueAttribute.NETWORK_COST_RECIEVE,
							100 + Math.random() * 100));
			combs.get(i)
					.getAttributes()
					.add(new Attribute<Double>(
							ECombinedValueAttribute.NETWORK_COST_SEND,
							100 + Math.random() * 100));
			combs.get(i)
					.getAttributes()
					.add(new Attribute<Double>(
							ECombinedValueAttribute.INTERNET_COST_RECIEVE,
							500 + Math.random() * 100));
			combs.get(i)
					.getAttributes()
					.add(new Attribute<Double>(
							ECombinedValueAttribute.INTERNET_COST_SEND,
							500 + Math.random() * 100));
			CombinationAlternative ca = new CombinationAlternative(
					"combi_" + i, combs.get(i));

			combiAlternatives.add(ca);
			cd.addAlternative(ca);
		}

		Evaluation combinedEvaluation = new Evaluation();
		combinedEvaluation.getEvaluations().add(
				createCombinedMatrix(c, combiAlternatives, componentSolutions));
		List<Evaluation> combinedEvaluations = new ArrayList<Evaluation>();
		combinedEvaluations.add(combinedEvaluation);

		long endTimeCombined = new Date().getTime();
		System.out.println("Combination Model took "
				+ (endTimeCombined - startTimeCombined) + " ms (for "
				+ combinations.size() + " combinations)");
		long startTimeCombinedEval = new Date().getTime();
		AnalyticHierarchyProcess ahpCombined = new AnalyticHierarchyProcess(cd);
		EvaluationResult combinedEvalResult = ahpCombined
				.evaluateFull(combinedEvaluations);

		SortedSet<CombinationTotalValue> combinationResults = new TreeSet<CombinationTotalValue>();
		for (Alternative a : combinedEvalResult
				.getResultMultiplicativeIndexMap().keySet()) {
			Double value = (((CombinationAlternative) a).getCombination()
					.getApplianceValue() + ((CombinationAlternative) a)
					.getCombination().getServiceValue())
					/ combinedEvalResult.getResultMultiplicativeIndexMap().get(
							a);
			combinationResults.add(new CombinationTotalValue(
					((CombinationAlternative) a).getCombination(), value));
		}
		long endTimeCombinedEval = new Date().getTime();
		System.out.println("Combination Eval took "
				+ (endTimeCombinedEval - startTimeCombinedEval) + " ms");

		System.out.println("Worst Combination: " + combinationResults.first());
		System.out.println("Best Combination: " + combinationResults.last());

		FileWriter fw = new FileWriter("out.txt", true);
		BufferedWriter out = new BufferedWriter(fw);
		out.write(""
				+ c.getName()
				+ ","
				+ numAMIs
				+ ","
				+ numServices
				+ ","
				+ (endTimeAMIModel - startTimeAMIModel)
				+ ","
				+ (endTimeAMIEval - startTimeAMIEval)
				+ ","
				+ (endTimeServiceModel - startTimeServiceModel)
				+ ","
				+ (endTimeServiceEval - startTimeServiceEval)
				+ ","
				+ (endTimeCombined - startTimeCombined)
				+ ","
				+ (endTimeCombinedEval - startTimeCombinedEval)
				+ ","
				+ ((endTimeAMIModel - startTimeAMIModel)
						+ (endTimeAMIEval - startTimeAMIEval)
						+ (endTimeServiceModel - startTimeServiceModel)
						+ (endTimeServiceEval - startTimeServiceEval)
						+ (endTimeCombined - startTimeCombined) + (endTimeCombinedEval - startTimeCombinedEval))
				+ "\n");
		out.close();

		return combinationResults.last();
	}

	private static Matrix createAMIMatrix(List<ApplianceAlternative> alt,
			EApplianceAttribute attr) {
		double[][] critEv = new double[alt.size()][alt.size()];
		double c;

		for (int a = 0; a < alt.size(); a++) {
			c = (Double) alt.get(a).getAppl().getAttribute(attr).getValue();
			for (int b = 0; b < alt.size(); b++) {

				critEv[a][b] = c
						/ (Double) alt.get(b).getAppl().getAttribute(attr)
								.getValue();
				// System.out.println("[" + critEv[a][b] + "]");
			}
			// System.out.println("\n");
		}
		Matrix mat = new Matrix(critEv);

		return mat;
	}

	private static Matrix createServiceMatrix(
			List<ComputeServiceAlternative> alt, EComputeServiceAttribute attr) {
		double[][] critEv = new double[alt.size()][alt.size()];
		double c;

		for (int a = 0; a < alt.size(); a++) {
			c = (Double) alt.get(a).getComputeService().getAttribute(attr)
					.getValue();
			for (int b = 0; b < alt.size(); b++) {

				critEv[a][b] = c
						/ (Double) alt.get(b).getComputeService()
								.getAttribute(attr).getValue();
				// System.out.println("[" + critEv[a][b] + "]");
			}
			// System.out.println("\n");
		}
		Matrix mat = new Matrix(critEv);

		return mat;
	}

	private static Matrix createCombinedMatrix(Component comp,
			List<CombinationAlternative> alt,
			Map<Component, CombinationTotalValue> componentSolutions) {
		double[][] critEv = new double[alt.size()][alt.size()];
		double c = 1D;

		for (int a = 0; a < alt.size(); a++) {
			for (Component connectedComp : comp.getConnectedComponents())
				if (componentSolutions.get(connectedComp) != null
						&& componentSolutions
								.get(connectedComp)
								.getService()
								.getProvider()
								.equals(alt.get(a).getCombination()
										.getService().getProvider()))
					c = (Double) alt
							.get(a)
							.getCombination()
							.getAttribute(
									ECombinedValueAttribute.NETWORK_COST_RECIEVE)
							.getValue()
							+ (Double) alt
									.get(a)
									.getCombination()
									.getAttribute(
											ECombinedValueAttribute.NETWORK_COST_SEND)
									.getValue();
				else
					c = (Double) alt
							.get(a)
							.getCombination()
							.getAttribute(
									ECombinedValueAttribute.INTERNET_COST_RECIEVE)
							.getValue()
							+ (Double) alt
									.get(a)
									.getCombination()
									.getAttribute(
											ECombinedValueAttribute.INTERNET_COST_SEND)
									.getValue();
			for (int b = 0; b < alt.size(); b++) {

				double d = (Double) alt
						.get(b)
						.getCombination()
						.getAttribute(
								ECombinedValueAttribute.INTERNET_COST_RECIEVE)
						.getValue()
						+ (Double) alt
								.get(b)
								.getCombination()
								.getAttribute(
										ECombinedValueAttribute.INTERNET_COST_SEND)
								.getValue()
						+ (Double) alt
								.get(b)
								.getCombination()
								.getAttribute(
										ECombinedValueAttribute.NETWORK_COST_RECIEVE)
								.getValue()
						+ (Double) alt
								.get(b)
								.getCombination()
								.getAttribute(
										ECombinedValueAttribute.NETWORK_COST_SEND)
								.getValue();
				critEv[a][b] = c / d;
				// System.out.println("[" + critEv[a][b] + "]");
			}
			// System.out.println("\n");
		}
		Matrix mat = new Matrix(critEv);

		return mat;
	}

}
