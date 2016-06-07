package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class AddRecall implements Serializable {
  private String recallid;
  private String samescene;//同场次人数
  private String sceneid;//场次id
public String getRecallid() {
	return recallid;
}

public void setRecallid(String recallid) {
	this.recallid = recallid;
}

public String getSamescene() {
	return samescene;
}

public void setSamescene(String samescene) {
	this.samescene = samescene;
}

public String getSceneid() {
	return sceneid;
}

public void setSceneid(String sceneid) {
	this.sceneid = sceneid;
}

  
  

}