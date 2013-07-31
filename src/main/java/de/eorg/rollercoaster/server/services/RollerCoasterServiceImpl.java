package de.eorg.rollercoaster.server.services;

import javax.jdo.PersistenceManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.eorg.rollercoaster.client.services.RollerCoasterService;
import de.eorg.rollercoaster.server.db.dao.PMF;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.Preferences;


public class RollerCoasterServiceImpl extends RemoteServiceServlet implements
RollerCoasterService {

	@Override
	public void savePreference(int VMImage, int Quality, int Latency, int Performance, int Cost, String user) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Preferences p = new Preferences(VMImage,Quality,Latency,Performance,Cost,user);
		try{
			pm.makePersistent(p);
		}finally
		{
			pm.close();
		}
	}
	
	public Preferences loadPreferences(String key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Preferences p = pm.getObjectById(Preferences.class, key);
		
		return p;
	}
/*	
	Objectify ofy = ObjectifyService.begin();
	CumulusDAO dao = new CumulusDAO();


	@Override
	public List<Decision> getDecisions() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Decision getDecision(Long id) {
		return  ofy.get(Decision.class, id);
		
	}



	@Override
	public UserDecision storeUserDecision(UserDecision newUserDecision) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void createComponent(String name, String software,
			String interconnection) {
		dao.createComponent(name, software, interconnection);
		
	}



	@Override
	public Component getComponent(Long id) {
		Component result = dao.getComponent(id);
		return result;
	}
*/
}
