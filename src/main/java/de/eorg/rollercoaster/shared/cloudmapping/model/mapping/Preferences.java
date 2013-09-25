package de.eorg.rollercoaster.shared.cloudmapping.model.mapping;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Preferences implements Serializable {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String key;
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

	public Preferences(String key, int VMImage, int Quality, int Latency, int Performance, int Cost, String user){

		this.key = key;
		this.VMImage = VMImage;
		this.Quality = Quality;
		this.Latency = Latency;
		this.Performance = Performance;
		this.Cost = Cost;
		this.userID = user;
	}

	public Preferences(){}

	public void setData(int VMImage, int Quality, int Latency, int Performance, int Cost){

		this.VMImage = VMImage;
		this.Quality = Quality;
		this.Latency = Latency;
		this.Performance = Performance;
		this.Cost = Cost;
	}

	public String getUser() {
		return userID;
	}

	public int getVMImage(){
		return VMImage;
	}
	public int getQuality(){
		return Quality;
	}
	public int getLatency(){
		return Latency;
	}
	public int getPerformance(){
		return Performance;
	}
	public int getCost(){
		return Cost;
	}



}
