package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class TypeInfo implements Serializable {
    /**
     * 影片类型
     */
    private String movietype;

    /**
     * 本类型影片平均分
     */
    private float average;

    /**
     * 本类型影片观影次数
     */
    private int total;



    public void setMovietype(String movietype) {
         if (movietype == null) {
            return;
         }
        this.movietype = movietype;
    }

    public String getMovietype() {
        return movietype;
    }


    public void setAverage(float average) {
        this.average = average;
    }

    public float getAverage() {
        return average;
    }


    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }


}