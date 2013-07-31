package de.eorg.rollercoaster.shared.cloudmapping.model.mapping;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Preferences {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
	@Persistent
	private int VMImage;
	@Persistent
	private int Quality;
	@Persistent
	private int Latency;
	@Persistent
	private int Performance;
	@Persistent
	private int Cost;
	@Persistent
	private String userID;
	
	public Preferences(int VMImage, int Quality, int Latency, int Performance, int Cost, String user){
		
	this.VMImage = VMImage;
	this.Quality = Quality;
	this.Latency = Latency;
	this.Performance = Performance;
	this.Cost = Cost;
	this.userID = user;
	}

	public String getUser() {
		return userID;
	}
	
	public int getVMImage(){
		return VMImage;
	}
	

 
}
