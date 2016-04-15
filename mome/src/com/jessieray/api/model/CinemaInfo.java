package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class CinemaInfo implements Serializable {
    /**
     * 影院id
     */
    private String cinemaid;

    /**
     * 影院名称
     */
    private String title;

    /**
     * 影院地址
     */
    private String address;

    /**
     * 区名称
     */
    private String district;

    /**
     * 城市名称
     */
    private String city;
    
    public void setCinemaid(String cinemaid) {
         if (cinemaid == null) {
            return;
         }
        this.cinemaid = cinemaid;
    }

    public String getCinemaid() {
        return cinemaid;
    }


    public void setTitle(String title) {
         if (title == null) {
            return;
         }
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public void setAddress(String address) {
         if (address == null) {
            return;
         }
        this.address = address;
    }

    public String getAddress() {
        return address;
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