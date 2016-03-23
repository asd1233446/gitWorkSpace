package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class UserFavorite implements Serializable {
    /**
     * 总数
     */
    private int total;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 影片列表
     */
    private java.util.List<com.jessieray.api.model.MovieInfo> movies;



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


    public void setMovies(java.util.List<com.jessieray.api.model.MovieInfo> movies) {
        this.movies = movies;
    }

    public java.util.List<com.jessieray.api.model.MovieInfo> getMovies() {
        return movies;
    }


}