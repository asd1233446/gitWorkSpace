package com.jessieray.api.model;

import java.io.Serializable;

public class BindLogin implements Serializable{
	
private UserInfo userinfo;

public UserInfo getUserinfo() {
	return userinfo;
}

public void setUserinfo(UserInfo userinfo) {
	this.userinfo = userinfo;
}



}
