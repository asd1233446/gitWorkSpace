package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class MovieInfo implements Serializable {
    /**
     * 电影id
     */
    private int movieid;

    /**
     * 电影标题
     */
    private String title;

    /**
     * 电影海报
     */
    private String imagesrc;

    /**
     * 评分
     */
    private float mark;

    /**
     * 是否添加了回忆录
     */
    private boolean wasrecall;

    /**
     * 关注数
     */
    private int attentions;

    /**
     * 我的评分
     */
    private float myscore;

    /**
     * 看过的好友数
     */
    private int lookedfriends;

    /**
     * 收藏人数
     */
    private int favoers;



    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public int getMovieid() {
        return movieid;
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


    public void setImagesrc(String imagesrc) {
         if (imagesrc == null) {
            return;
         }
        this.imagesrc = imagesrc;
    }

    public String getImagesrc() {
        return imagesrc;
    }


    public void setMark(float mark) {
        this.mark = mark;
    }

    public float getMark() {
        return mark;
    }


    public void setWasrecall(boolean wasrecall) {
        this.wasrecall = wasrecall;
    }

    public boolean getWasrecall() {
        return wasrecall;
    }


    public void setAttentions(int attentions) {
        this.attentions = attentions;
    }

    public int getAttentions() {
        return attentions;
    }


    public void setMyscore(float myscore) {
        this.myscore = myscore;
    }

    public float getMyscore() {
        return myscore;
    }


    public void setLookedfriends(int lookedfriends) {
        this.lookedfriends = lookedfriends;
    }

    public int getLookedfriends() {
        return lookedfriends;
    }


    public void setFavoers(int favoers) {
        this.favoers = favoers;
    }

    public int getFavoers() {
        return favoers;
    }


}