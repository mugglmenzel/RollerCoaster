package de.eorg.rollercoaster.shared.cloudmapping.model.mapping;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class VMCriteria {

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
	private boolean age;
	@Persistent
	private String userID;
	
	
	public VMCriteria(boolean initialLicenceCosts, boolean hourlyLicenceCosts, boolean popularity, boolean age, String user)
	{
		this.initialLicenceCosts = initialLicenceCosts;
		this.hourlyLicenceCosts = hourlyLicenceCosts;
		this.popularity = popularity;
		this.age = age;
		this.userID = user;
	}
	
	public boolean getInitialLicenceCosts(){
		return initialLicenceCosts;
	}
	
}
