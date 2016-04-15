package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class DistrictsInfo implements Serializable {
    /**
     * 本区影院数
     */
    private int total;

    /**
     * 区名称
     */
    private String district;

    /**
     * 城市名称
     */
    private String city;

    private java.util.List<com.jessieray.api.model.CinemaInfo> cinemas;

    public java.util.List<com.jessieray.api.model.CinemaInfo> getCinemas() {
		return cinemas;
	}

	public void setCinemas(
			java.util.List<com.jessieray.api.model.CinemaInfo> cinemas) {
		this.cinemas = cinemas;
	}

	public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }


    public void setDistrict(String district) {
         if (district == null) {
            return;
         }
        this.district = district;
    }

    public String getDistrict() {
        return district;
    }


    public void setCity(String city) {
         if (city == null) {
            return;
         }
        this.city = city;
    }

    public String getCity() {
        return city;
    }


}