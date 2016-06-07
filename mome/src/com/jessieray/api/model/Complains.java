package com.jessieray.api.model;

import java.io.Serializable;

public class Complains implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String complainid;
	/**
	 * 处理状态
	 * */
	private String status;
	/**
	 * 处理内容
	 * */
	private String brief;
	
	/**
	 * 回复
	 * */
	private String feedback;

	public String getComplainid() {
		return complainid;
	}

	public void setComplainid(String complainid) {
		this.complainid = complainid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	
	
	

}
