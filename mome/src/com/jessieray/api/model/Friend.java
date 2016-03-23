/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jessieray.api.model;

import java.io.Serializable;

/**
 *
 * @deprecated关系列表
 */
public class Friend implements Serializable{
	/***
	 * 关系类型
	 * 
	 */
	private String relationtype;
	
	public String getRelationtype() {
		return relationtype;
	}

	public void setRelationtype(String relationtype) {
		this.relationtype = relationtype;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/***
	 * 昵称
	 * 
	 */
	private String nickname;
	
	
	/***
	 * 用户id
	 * 
	 */
	private String userid;
	
	/***
	 * 头像地址
	 * 
	 */
	private String avatar;
	
    
   
}
