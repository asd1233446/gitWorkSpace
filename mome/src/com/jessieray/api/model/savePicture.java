package com.jessieray.api.model;

import java.io.Serializable;

public class savePicture implements Serializable{
	/*
	 * 图片名字
	 * **/
	private String name;
	/*
	 * 图片地址
	 * **/
	private String path;
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
}
