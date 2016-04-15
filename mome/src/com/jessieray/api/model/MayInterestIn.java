package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class MayInterestIn implements Serializable {
    /**
     * 同类型爱好用户
     */
    private java.util.List<com.jessieray.api.model.UserInfo> same_hobby;

    /**
     * 常去同一影院
     */
    private java.util.List<com.jessieray.api.model.UserInfo> same_cinema;

    /**
     * 通场次观影用户
     */
    private java.util.List<com.jessieray.api.model.UserInfo> same_movie;



    public void setSame_hobby(java.util.List<com.jessieray.api.model.UserInfo> same_hobby) {
        this.same_hobby = same_hobby;
    }

    public java.util.List<com.jessieray.api.model.UserInfo> getSame_hobby() {
        return same_hobby;
    }


    public void setSame_cinema(java.util.List<com.jessieray.api.model.UserInfo> same_cinema) {
        this.same_cinema = same_cinema;
    }

    public java.util.List<com.jessieray.api.model.UserInfo> getSame_cinema() {
        return same_cinema;
    }


    public void setSame_movie(java.util.List<com.jessieray.api.model.UserInfo> same_movie) {
        this.same_movie = same_movie;
    }

    public java.util.List<com.jessieray.api.model.UserInfo> getSame_movie() {
        return same_movie;
    }


}