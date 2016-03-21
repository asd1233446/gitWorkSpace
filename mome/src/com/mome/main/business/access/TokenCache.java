package com.mome.main.business.access;

import java.io.Serializable;

public class TokenCache implements Serializable{

	private static final long serialVersionUID = -5078764570474711902L;

	private String id;
	private String token;
	private long expiresIn;
	private long loginTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getExpiresIn() {
		return expiresIn-(System.currentTimeMillis()-loginTime)/1000;
	}
	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}
	public long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}
	public boolean isValid(){
		if((System.currentTimeMillis()-loginTime)/1000 < expiresIn){
			return true;
		}
		return false;
	}
}
