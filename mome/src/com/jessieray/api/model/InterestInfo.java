package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class InterestInfo implements Serializable {
    /**
     * 影片类型
     */
    private String movietype;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 影院名称
     */
    private String cinema;

    /**
     * 电影名称
     */
    private String title;

    /**
     * 用户id
     */
    private String userid;



    public void setMovietype(String movietype) {
         if (movietype == null) {
            return;
         }
        this.movietype = movietype;
    }

    public String getMovietype() {
        return movietype;
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


    public void setAvatar(String avatar) {
         if (avatar == null) {
            return;
         }
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }


    public void setCinema(String cinema) {
         if (cinema == null) {
            return;
         }
        this.cinema = cinema;
    }

    public String getCinema() {
        return cinema;
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


    public void setUserid(String userid) {
         if (userid == null) {
            return;
         }
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }


}