package com.jessieray.api.model;

import java.io.Serializable;
import java.util.List;

public class Moviesearch implements Serializable {

	/**
	 * 请求条数
	 * */
	private String count;
	/**
	 * 条数索引
	 * */
	private String start;
	/**
	 * 总共条数
	 * */
	private String total;

	/**
	 * 电影结果
	 * */
	private List<DouMovieInfo> subjects;
	
	
	

	public String getCount() {
		return count;
	}




	public void setCount(String count) {
		this.count = count;
	}




	public String getStart() {
		return start;
	}




	public void setStart(String start) {
		this.start = start;
	}




	public String getTotal() {
		return total;
	}




	public void setTotal(String total) {
		this.total = total;
	}




	public List<DouMovieInfo> getSubjects() {
		return subjects;
	}




	public void setSubjects(List<DouMovieInfo> subjects) {
		this.subjects = subjects;
	}





}
