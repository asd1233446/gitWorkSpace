package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class GetArticleByUserId implements Serializable {
    /**
     * 总数
     */
    private int total;

    /**
     * 动态类型
     */
    private int article_type;

    private java.util.List<com.jessieray.api.model.DynamicInfo> rows;



    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }


    public void setArticle_type(int article_type) {
        this.article_type = article_type;
    }

    public int getArticle_type() {
        return article_type;
    }


    public void setRows(java.util.List<com.jessieray.api.model.DynamicInfo> rows) {
        this.rows = rows;
    }

    public java.util.List<com.jessieray.api.model.DynamicInfo> getRows() {
        return rows;
    }


}