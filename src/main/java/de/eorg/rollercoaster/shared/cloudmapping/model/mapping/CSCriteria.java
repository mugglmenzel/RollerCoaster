package de.eorg.rollercoaster.shared.cloudmapping.model.mapping;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
  
@PersistenceCapable
public class CSCriteria implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8266591640365320460L;
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String key;
//    private Long id;
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
	
	public CSCriteria(String key, boolean cpu, boolean ram, boolean uptime, boolean popularity, boolean initialLicenceCosts, boolean hourlyLicenceCosts, boolean maxLatency, boolean avgLatency, String user )
	{
		this.key = key;
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
	
	public CSCriteria(){}
	
	public void setData(boolean cpu, boolean ram, boolean uptime, boolean popularity, boolean initialLicenceCosts, boolean hourlyLicenceCosts, boolean maxLatency, boolean avgLatency)
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
	}

	public boolean isInitialLicenceCosts() {
		return initialLicenceCosts;
	}

	public boolean isHourlyLicenceCosts() {
		return hourlyLicenceCosts;
	}

	public boolean isPopularity() {
		return popularity;
	}

	public boolean isMaxLatency() {
		return maxLatency;
	}

	public boolean isAvgLatency() {
		return avgLatency;
	}

	public boolean isCpu() {
		return cpu;
	}

	public boolean isRam() {
		return ram;
	}

	public boolean isUptime() {
		return uptime;
	}

	public String getUserID() {
		return userID;
	}

}
