package com.hugocrd.humanmap.model;

import java.math.BigDecimal;

public class Box {
	
	private BigDecimal south;
	private BigDecimal west;
	private BigDecimal north;
	private BigDecimal east;
	 
	public BigDecimal getSouth() {
		return south;
	}
	public void setSouth(BigDecimal south) {
		this.south = south;
	}
	public BigDecimal getWest() {
		return west;
	}
	public void setWest(BigDecimal west) {
		this.west = west;
	}
	public BigDecimal getNorth() {
		return north;
	}
	public void setNorth(BigDecimal north) {
		this.north = north;
	}
	public BigDecimal getEast() {
		return east;
	}
	public void setEast(BigDecimal east) {
		this.east = east;
	}
	
	public String toString(){
		return "[["+south+","+west+"],["+north+","+east+"]]";
	}
	 
	 
}
