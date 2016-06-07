package com.jessieray.api.model;

import java.io.Serializable;
import java.util.List;

public class savePicture implements Serializable{
	/*
	 * 图片名字
	 * **/
	private String name;
	/*
	 * 图片地址
	 * **/
	private String path;
	
	 private List<String> fault;
	   private List<Success> success;
	public List<String> getFault() {
		return fault;
	}
	public void setFault(List<String> fault) {
		this.fault = fault;
	}
	
	   
	public List<Success> getSuccess() {
		return success;
	}
	public void setSuccess(List<Success> success) {
		this.success = success;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public class Success implements Serializable{
		private String name;
		private String id;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		
		
	}
}
