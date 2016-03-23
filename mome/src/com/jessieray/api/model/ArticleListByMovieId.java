package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class ArticleListByMovieId implements Serializable {
    /**
     * 总数
     */
    private int total;

    private java.util.List<com.jessieray.api.model.DynamicInfo> articles;



    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }


    public void setArticles(java.util.List<com.jessieray.api.model.DynamicInfo> articles) {
        this.articles = articles;
    }

    public java.util.List<com.jessieray.api.model.DynamicInfo> getArticles() {
        return articles;
    }


}