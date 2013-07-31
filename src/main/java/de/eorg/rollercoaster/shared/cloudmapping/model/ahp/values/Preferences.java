package de.eorg.rollercoaster.shared.cloudmapping.model.ahp.values;

import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

public class Preferences {
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
	@PrimaryKey
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
		return user;
	}

 
}
