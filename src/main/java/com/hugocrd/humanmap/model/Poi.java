package com.hugocrd.humanmap.model;

import java.math.BigDecimal;

public class Poi {

	private String description;
	private Loc loc;

	public Poi() {}
	
	public Poi(String description, BigDecimal lat, BigDecimal lng) {
		this.description = description;
		this.loc = new Loc(lat, lng);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Loc getLoc() {
		return loc;
	}
	public void setLoc(Loc loc) {
		this.loc = loc;
	}
}
