package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class MarkStatistical implements Serializable {
    /**
     * 平均分
     */
    private float average;

    /**
     * 好评影片数
     */
    private int good;

    /**
     * 一般影片数
     */
    private int common;

    /**
     * 较差影片数
     */
    private int bad;

    /**
     * 最差影片数
     */
    private int worst;



    public void setAverage(float average) {
        this.average = average;
    }

    public float getAverage() {
        return average;
    }


    public void setGood(int good) {
        this.good = good;
    }

    public int getGood() {
        return good;
    }


    public void setCommon(int common) {
        this.common = common;
    }

    public int getCommon() {
        return common;
    }


    public void setBad(int bad) {
        this.bad = bad;
    }

    public int getBad() {
        return bad;
    }


    public void setWorst(int worst) {
        this.worst = worst;
    }

    public int getWorst() {
        return worst;
    }


}