package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class CinemaListByDistrict implements Serializable {
    /**
     * 观影回忆录列表
     */
    private java.util.List<com.jessieray.api.model.CinemaInfo> mycinemas;



    public void setMycinemas(java.util.List<com.jessieray.api.model.CinemaInfo> mycinemas) {
        this.mycinemas = mycinemas;
    }

    public java.util.List<com.jessieray.api.model.CinemaInfo> getMycinemas() {
        return mycinemas;
    }


}