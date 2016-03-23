package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class GetCommentUsersByArticleId implements Serializable {
    /**
     * 评论总数
     */
    private int comments;

    /**
     * 用户列表
     */
    private java.util.List<com.jessieray.api.model.UserInfo> rows;



    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getComments() {
        return comments;
    }


    public void setRows(java.util.List<com.jessieray.api.model.UserInfo> rows) {
        this.rows = rows;
    }

    public java.util.List<com.jessieray.api.model.UserInfo> getRows() {
        return rows;
    }


}