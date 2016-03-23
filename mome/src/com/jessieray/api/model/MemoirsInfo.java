package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class MemoirsInfo implements Serializable {
    /**
     * 开始时间
     */
    private String startime;

    /**
     * 电影海报地址
     */
    private String imageSrc;

    /**
     * 剧场
     */
    private String theater;

    /**
     * 我的评分
     */
    private float mark;

    /**
     * 电影票地址
     */
    private String ticketphoto;

    /**
     * 观影日期
     */
    private String date;

    /**
     * 观影回忆录ID
     */
    private int recallid;

    /**
     * 电影名称
     */
    private String title;

    /**
     * 电影时长
     */
    private int duration;

    /**
     * 票价
     */
    private float price;

    /**
     * 座位
     */
    private String seat;

    /**
     * 影片类型
     */
    private String movietype;

    /**
     * 影院名称
     */
    private String cinema;

    /**
     * 影片ID
     */
    private String movieid;



    public void setStartime(String startime) {
         if (startime == null) {
            return;
         }
        this.startime = startime;
    }

    public String getStartime() {
        return startime;
    }


    public void setImageSrc(String imageSrc) {
         if (imageSrc == null) {
            return;
         }
        this.imageSrc = imageSrc;
    }

    public String getImageSrc() {
        return imageSrc;
    }


    public void setTheater(String theater) {
         if (theater == null) {
            return;
         }
        this.theater = theater;
    }

    public String getTheater() {
        return theater;
    }


    public void setMark(float mark) {
        this.mark = mark;
    }

    public float getMark() {
        return mark;
    }


    public void setTicketphoto(String ticketphoto) {
         if (ticketphoto == null) {
            return;
         }
        this.ticketphoto = ticketphoto;
    }

    public String getTicketphoto() {
        return ticketphoto;
    }


    public void setDate(String date) {
         if (date == null) {
            return;
         }
        this.date = date;
    }

    public String getDate() {
        return date;
    }


    public void setRecallid(int recallid) {
        this.recallid = recallid;
    }

    public int getRecallid() {
        return recallid;
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


    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }


    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }


    public void setSeat(String seat) {
         if (seat == null) {
            return;
         }
        this.seat = seat;
    }

    public String getSeat() {
        return seat;
    }


    public void setMovietype(String movietype) {
         if (movietype == null) {
            return;
         }
        this.movietype = movietype;
    }

    public String getMovietype() {
        return movietype;
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


    public void setMovieid(String movieid) {
         if (movieid == null) {
            return;
         }
        this.movieid = movieid;
    }

    public String getMovieid() {
        return movieid;
    }


}