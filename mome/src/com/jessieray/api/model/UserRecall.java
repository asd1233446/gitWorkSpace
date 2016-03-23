package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class UserRecall implements Serializable {
    /**
     * 观影回忆录总数
     */
    private int total;

    /**
     * 观影回忆录列表
     */
    private java.util.List<com.jessieray.api.model.MemoirsInfo> recalls;



    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }


    public void setRecalls(java.util.List<com.jessieray.api.model.MemoirsInfo> recalls) {
        this.recalls = recalls;
    }

    public java.util.List<com.jessieray.api.model.MemoirsInfo> getRecalls() {
        return recalls;
    }


}