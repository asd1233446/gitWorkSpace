package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class GetGoodUsersByArticleId implements Serializable {
    /**
     * 点赞总数
     */
    private int goods;

    /**
     * 用户列表
     */
    private java.util.List<com.jessieray.api.model.UserInfo> rows;



    public void setGoods(int goods) {
        this.goods = goods;
    }

    public int getGoods() {
        return goods;
    }


    public void setRows(java.util.List<com.jessieray.api.model.UserInfo> rows) {
        this.rows = rows;
    }

    public java.util.List<com.jessieray.api.model.UserInfo> getRows() {
        return rows;
    }


}