package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class GetCommentsByArticleId implements Serializable {
    /**
     * 信息数量
     */
    private int total;

    /**
     * 动态详情信息
     */
    private java.util.List<com.jessieray.api.model.DynamicInfo> rows;



    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }


    public void setRows(java.util.List<com.jessieray.api.model.DynamicInfo> rows) {
        this.rows = rows;
    }

    public java.util.List<com.jessieray.api.model.DynamicInfo> getRows() {
        return rows;
    }


}