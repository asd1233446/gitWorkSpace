package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class UserRecall implements Serializable {

    /**
     * 观影回忆录列表
     */
    
    private java.util.List<com.jessieray.api.model.MemoirsInfo> recalls;
     
    
    private  java.util.List<com.jessieray.api.model.Year> yearcounts;
    
    


    public java.util.List<com.jessieray.api.model.Year> getYearcounts() {
		return yearcounts;
	}

	public void setYearcounts(
			java.util.List<com.jessieray.api.model.Year> yearcounts) {
		this.yearcounts = yearcounts;
	}
    public void setRecalls(java.util.List<com.jessieray.api.model.MemoirsInfo> recalls) {
        this.recalls = recalls;
    }

    public java.util.List<com.jessieray.api.model.MemoirsInfo> getRecalls() {
        return recalls;
    }


}