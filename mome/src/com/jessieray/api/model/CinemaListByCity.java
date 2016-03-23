package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class CinemaListByCity implements Serializable {
    /**
     * 观影回忆录列表
     */
    private java.util.List<com.jessieray.api.model.CinemaInfo> mycinemas;

    /**
     * 观影回忆录列表
     */
    private java.util.List<com.jessieray.api.model.DistrictsInfo> districts;



    public void setMycinemas(java.util.List<com.jessieray.api.model.CinemaInfo> mycinemas) {
        this.mycinemas = mycinemas;
    }

    public java.util.List<com.jessieray.api.model.CinemaInfo> getMycinemas() {
        return mycinemas;
    }


    public void setDistricts(java.util.List<com.jessieray.api.model.DistrictsInfo> districts) {
        this.districts = districts;
    }

    public java.util.List<com.jessieray.api.model.DistrictsInfo> getDistricts() {
        return districts;
    }


}