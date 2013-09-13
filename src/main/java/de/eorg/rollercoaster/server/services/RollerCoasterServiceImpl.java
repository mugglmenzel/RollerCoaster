package de.eorg.rollercoaster.server.services;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.eorg.rollercoaster.client.services.RollerCoasterService;
import de.eorg.rollercoaster.server.db.dao.PMF;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.CSCriteria;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.Preferences;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.VMCriteria;


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
		Query query = pm.newQuery(Preferences.class);
		query.setFilter("userID=='"+key+"'");
		List<Preferences> result = (List<Preferences>) query.execute();
		
		return result.size() > 0 ? result.get(0) : null;
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



@Override
public void saveCSCriteria(boolean cpu, boolean ram, boolean uptime,
		boolean popularity, boolean initialLicenceCosts,
		boolean hourlyLicenceCosts, boolean maxLatency, boolean avgLatency,
		String user) {
	PersistenceManager pm = PMF.get().getPersistenceManager();
	CSCriteria c = new CSCriteria(cpu,ram,uptime,popularity,initialLicenceCosts,hourlyLicenceCosts,maxLatency,avgLatency,user);
	try{
		pm.makePersistent(c);
	}finally
	{
		pm.close();
	}
	
}

@Override
public void saveVMCriteria(boolean initialLicenceCosts,
		boolean hourlyLicenceCosts, boolean popularity, boolean age, String user) {
	PersistenceManager pm = PMF.get().getPersistenceManager();
	VMCriteria v = new VMCriteria(initialLicenceCosts,hourlyLicenceCosts,popularity,age,user);
	try{
		pm.makePersistent(v);
	}finally
	{
		pm.close();
	}
	
}
}
