package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class UserCinemas implements Serializable {
    /**
     * 总数
     */
    private int total;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 最近动态
     */
    private java.util.List<com.jessieray.api.model.CinemaInfo> cinemas;



    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }


    public void setNickname(String nickname) {
         if (nickname == null) {
            return;
         }
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }


    public void setCinemas(java.util.List<com.jessieray.api.model.CinemaInfo> cinemas) {
        this.cinemas = cinemas;
    }

    public java.util.List<com.jessieray.api.model.CinemaInfo> getCinemas() {
        return cinemas;
    }


}