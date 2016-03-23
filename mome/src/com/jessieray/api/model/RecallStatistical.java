package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class RecallStatistical implements Serializable {
    /**
     * 总数量
     */
    private int total;

    /**
     * 影院观影数
     */
    private int cinematotal;

    /**
     * 网络观影数
     */
    private int internettotal;

    /**
     * 电视观影数
     */
    private int tvtotal;

    /**
     * 最常去影院名称
     */
    private String favorcinema;

    /**
     * 最常去影院次数
     */
    private int favoercinematotal;

    /**
     * 最常网络观影方式
     */
    private String favorinternet;

    /**
     * 最常网络观影方式次数
     */
    private int favorinternettotal;



    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }


    public void setCinematotal(int cinematotal) {
        this.cinematotal = cinematotal;
    }

    public int getCinematotal() {
        return cinematotal;
    }


    public void setInternettotal(int internettotal) {
        this.internettotal = internettotal;
    }

    public int getInternettotal() {
        return internettotal;
    }


    public void setTvtotal(int tvtotal) {
        this.tvtotal = tvtotal;
    }

    public int getTvtotal() {
        return tvtotal;
    }


    public void setFavorcinema(String favorcinema) {
         if (favorcinema == null) {
            return;
         }
        this.favorcinema = favorcinema;
    }

    public String getFavorcinema() {
        return favorcinema;
    }


    public void setFavoercinematotal(int favoercinematotal) {
        this.favoercinematotal = favoercinematotal;
    }

    public int getFavoercinematotal() {
        return favoercinematotal;
    }


    public void setFavorinternet(String favorinternet) {
         if (favorinternet == null) {
            return;
         }
        this.favorinternet = favorinternet;
    }

    public String getFavorinternet() {
        return favorinternet;
    }


    public void setFavorinternettotal(int favorinternettotal) {
        this.favorinternettotal = favorinternettotal;
    }

    public int getFavorinternettotal() {
        return favorinternettotal;
    }


}