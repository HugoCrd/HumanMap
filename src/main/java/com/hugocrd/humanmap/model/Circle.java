package com.hugocrd.humanmap.model;

import java.math.BigDecimal;

public class Circle {

	private BigDecimal lat;
	private BigDecimal lng;
	private BigDecimal radius;

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
	public BigDecimal getRadius() {
		return radius;
	}
	public void setRadius(BigDecimal radius) {
		this.radius = radius;
	}
	
	public String toString(){
		return "[["+lat+","+lng+"],"+radius+"]";
	}
}
