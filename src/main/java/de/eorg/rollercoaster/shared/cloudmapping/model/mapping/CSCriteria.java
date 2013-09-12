package de.eorg.rollercoaster.shared.cloudmapping.model.mapping;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class CSCriteria {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
	@Persistent
	private boolean initialLicenceCosts;
	@Persistent
	private boolean hourlyLicenceCosts;
	@Persistent
	private boolean popularity;
	@Persistent
	private boolean maxLatency;
	@Persistent
	private boolean avgLatency;
	@Persistent
	private boolean cpu;
	@Persistent
	private boolean ram;
	@Persistent
	private boolean uptime;
	@Persistent
	private String userID;
	
	
	
	public CSCriteria(boolean cpu, boolean ram, boolean uptime, boolean popularity, boolean initialLicenceCosts, boolean hourlyLicenceCosts, boolean maxLatency, boolean avgLatency, String user )
	{
		this.initialLicenceCosts = initialLicenceCosts;
		this.hourlyLicenceCosts = hourlyLicenceCosts;
		this.popularity = popularity;
		this.ram = ram;
		this.initialLicenceCosts = initialLicenceCosts;
		this.hourlyLicenceCosts = hourlyLicenceCosts;
		this.uptime = uptime;
		this.cpu = cpu;
		this.maxLatency = maxLatency;
		this.avgLatency = avgLatency;
		this.userID = user;
	}

}
