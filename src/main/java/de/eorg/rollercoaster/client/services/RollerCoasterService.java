package de.eorg.rollercoaster.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.Preferences;

@RemoteServiceRelativePath("rollercoaster")
public interface RollerCoasterService extends RemoteService {
/*
	public UserDecision storeUserDecision(UserDecision newUserDecision);

	public List<Decision> getDecisions();

	public Decision getDecision(Long id);

	public void createComponent(String name, String software,
			String interconnection);

	public Component getComponent(Long id);

	// public EvaluationResult evaluate(Decision decision, List<Evaluation>
	// eval, int precision) throws Exception;
*/
	
	public void savePreference(int VMImage, int Quality, int Latency, int Performance, int Cost, String user);
	public Preferences loadPreferences(String key);
}
