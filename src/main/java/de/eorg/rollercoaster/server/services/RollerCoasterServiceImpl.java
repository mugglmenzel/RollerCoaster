package de.eorg.rollercoaster.server.services;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.eorg.rollercoaster.client.gui.views.CriteriaView;
import de.eorg.rollercoaster.client.services.RollerCoasterService;
import de.eorg.rollercoaster.server.db.dao.PMF;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.CSCriteria;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.Preferences;
import de.eorg.rollercoaster.shared.cloudmapping.model.mapping.VMCriteria;


public class RollerCoasterServiceImpl extends RemoteServiceServlet implements
RollerCoasterService {
	private Logger log = Logger.getLogger(RollerCoasterServiceImpl.class.getName());
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

		String cKey = CSCriteria.class.getSimpleName()+"."+user;

		CSCriteria c = null;

		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			try{
				c = pm.getObjectById(CSCriteria.class, cKey);
				c.setData(cpu,ram,uptime,popularity,initialLicenceCosts,hourlyLicenceCosts,maxLatency,avgLatency);
			}
			catch (Exception e) {
				c =	new CSCriteria(cKey, cpu,ram,uptime,popularity,initialLicenceCosts,hourlyLicenceCosts,maxLatency,avgLatency,user);
			}
			pm.makePersistent(c);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		//		PersistenceManager pm = PMF.get().getPersistenceManager();
		//		CSCriteria c = new CSCriteria(cpu,ram,uptime,popularity,initialLicenceCosts,hourlyLicenceCosts,maxLatency,avgLatency,user);
		//		try{
		//			pm.makePersistent(c);
		//		}finally
		//		{
		//			pm.close();
		//		}

	}

	public CSCriteria loadCSCriteria(String key) {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();

		String k = CSCriteria.class.getSimpleName()+"."+key;

		Transaction tx = pm.currentTransaction();
		CSCriteria c = null;

		try {
			tx.begin();
			c = pm.getObjectById(CSCriteria.class, k);
			tx.commit();
		}
		catch (Exception e) {

		}
		finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		} 

		return c;

		//		PersistenceManager pm = PMF.get().getPersistenceManager();
		//		Query query = pm.newQuery(CSCriteria.class);
		//		query.setFilter("userID=='"+key+"'");
		//		List<CSCriteria> result = (List<CSCriteria>) query.execute();
		//
		//		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public void saveVMCriteria(boolean initialLicenceCosts,
			boolean hourlyLicenceCosts, boolean popularity, boolean age, String user) {

		//TODO: Delete PrintOut
		log.info("Saving VMCriteria...");
		
		PersistenceManager pm = PMF.get().getPersistenceManager();

		String vKey = VMCriteria.class.getSimpleName()+"."+user;

		VMCriteria v = null;

		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			try{
				v = pm.getObjectById(VMCriteria.class, vKey);
				v.setData(initialLicenceCosts, hourlyLicenceCosts, popularity, age);
			}
			catch (Exception e) {
				v =	new VMCriteria(vKey, initialLicenceCosts,hourlyLicenceCosts,popularity,age,user);
			}
			pm.makePersistent(v);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		//	PersistenceManager pm = PMF.get().getPersistenceManager();
		//	VMCriteria v = new VMCriteria(initialLicenceCosts,hourlyLicenceCosts,popularity,age,user);
		//	try{
		//		pm.makePersistent(v);
		//	}finally
		//	{
		//		pm.close();
		//	}

	}
	public VMCriteria loadVMCriteria(String key) {

		PersistenceManager pm = PMF.get().getPersistenceManager();

		String k = VMCriteria.class.getSimpleName()+"."+key;

		Transaction tx = pm.currentTransaction();
		VMCriteria v = null;

		try {
			tx.begin();
			v = pm.getObjectById(VMCriteria.class, k);
			tx.commit();
		}
		catch (Exception e) {

		}
		finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		} 
		//TODO: Delete PrintOut
		log.info("Loaded VMCriteria: "+v.getInitialLicenceCosts());
		return v;
	}

	//	PersistenceManager pm = PMF.get().getPersistenceManager();
	//	Query query = pm.newQuery(VMCriteria.class);
	//	query.setFilter("userID=='"+key+"'");
	//	List<VMCriteria> result = (List<VMCriteria>) query.execute();
	//	
	//	return result.size() > 0 ? result.get(0) : null;
	//}

}
