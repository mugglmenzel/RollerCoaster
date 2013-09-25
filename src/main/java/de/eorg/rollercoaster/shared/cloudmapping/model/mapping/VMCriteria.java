package de.eorg.rollercoaster.shared.cloudmapping.model.mapping;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
 
@PersistenceCapable
public class VMCriteria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1040858534748868856L;
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
	private boolean age;
	@Persistent
	private String userID;
	 
	public VMCriteria(String key, boolean initialLicenceCosts, boolean hourlyLicenceCosts, boolean popularity, boolean age, String user)
	{
		this.key = key;
		this.initialLicenceCosts = initialLicenceCosts;
		this.hourlyLicenceCosts = hourlyLicenceCosts;
		this.popularity = popularity;
		this.age = age;
		this.userID = user;
	}
	
	public VMCriteria(){}
	
	public void setData (boolean initialLicenceCosts, boolean hourlyLicenceCosts, boolean popularity, boolean age)
	{
		this.initialLicenceCosts = initialLicenceCosts;
		this.hourlyLicenceCosts = hourlyLicenceCosts;
		this.popularity = popularity;
		this.age = age;
	}
	
	public boolean getInitialLicenceCosts(){
		return initialLicenceCosts;
	}


	public boolean isHourlyLicenceCosts() {
		return hourlyLicenceCosts;
	}

	public boolean isPopularity() {
		return popularity;
	}

	public boolean isAge() {
		return age;
	}

	public String getUserID() {
		return userID;
	}
	
}
