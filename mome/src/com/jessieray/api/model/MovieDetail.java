package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class MovieDetail implements Serializable {
    /**
     * 影片ID
     */
    private int movieid;

    /**
     * 影片名称
     */
    private String title;

    /**
     * 简介
     */
    private String summary;

    /**
     * 收藏的人数
     */
    private int favors;

    /**
     * 海报链接
     */
    private String imagesrc;

    /**
     * 详情介绍页地址
     */
    private String site;

    /**
     * 关注的人数
     */
    private int attentions;

    /**
     * 观看过的好友数
     */
    private int lookedfriends;

    /**
     * 评论数
     */
    private int articles;

    /**
     * 影片类型
     */
    private String type;

    /**
     * 评分
     */
    private int mark;

    /**
     * 影片时长
     */
    private int duration;

    /**
     * 是否有观影记录
     */
    private boolean wasrecall;

    /**
     * 是否收藏过此影片
     */
    private boolean myfavorite;



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


    public void setSummary(String summary) {
         if (summary == null) {
            return;
         }
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }


    public void setFavors(int favors) {
        this.favors = favors;
    }

    public int getFavors() {
        return favors;
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


    public void setSite(String site) {
         if (site == null) {
            return;
         }
        this.site = site;
    }

    public String getSite() {
        return site;
    }


    public void setAttentions(int attentions) {
        this.attentions = attentions;
    }

    public int getAttentions() {
        return attentions;
    }


    public void setLookedfriends(int lookedfriends) {
        this.lookedfriends = lookedfriends;
    }

    public int getLookedfriends() {
        return lookedfriends;
    }


    public void setArticles(int articles) {
        this.articles = articles;
    }

    public int getArticles() {
        return articles;
    }


    public void setType(String type) {
         if (type == null) {
            return;
         }
        this.type = type;
    }

    public String getType() {
        return type;
    }


    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getMark() {
        return mark;
    }


    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }


    public void setWasrecall(boolean wasrecall) {
        this.wasrecall = wasrecall;
    }

    public boolean getWasrecall() {
        return wasrecall;
    }


    public void setMyfavorite(boolean myfavorite) {
        this.myfavorite = myfavorite;
    }

    public boolean getMyfavorite() {
        return myfavorite;
    }


}