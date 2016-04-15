package com.jessieray.api.model;

import java.io.Serializable;

public class Year implements Serializable{

	
	/**
	 * 年回忆录总数
	 * */
	private String yeartotal;
	private String year;
	public String getYeartotal() {
		return yeartotal;
	}
	public void setYeartotal(String yeartotal) {
		this.yeartotal = yeartotal;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	
}
