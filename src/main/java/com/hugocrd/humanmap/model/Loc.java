package com.hugocrd.humanmap.model;

import java.math.BigDecimal;

public class Loc {
	private BigDecimal lat;
	private BigDecimal lng;
	
	public Loc() {}
	
	public Loc(BigDecimal lat, BigDecimal lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}
	
	public BigDecimal getLat() {
		return lat;
	}
	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}
	public BigDecimal getLng() {
		return lng;
	}
	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}
	
	public String toString(){
		return "["+lat+","+lng+"]";
	}
}
