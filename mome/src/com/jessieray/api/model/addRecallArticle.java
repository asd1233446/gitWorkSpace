package com.jessieray.api.model;

import java.io.Serializable;

public class addRecallArticle implements Serializable{
    /**
     * 回忆录id
     * */
	private String recallid;
    /**
     * 评分
     * */
	private String mark;
	
	 /**
     * 影评
     * */
	private String brief;

	public String getRecallid() {
		return recallid;
	}

	public void setRecallid(String recallid) {
		this.recallid = recallid;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}
	
	
	
}
