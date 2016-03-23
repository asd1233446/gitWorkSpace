package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class GetMovieUserArticleByKeyWord implements Serializable {
    /**
     * 动态详情信息
     */
    private com.jessieray.api.model.UserInfo users;



    public void setUsers(com.jessieray.api.model.UserInfo users) {
        this.users = users;
    }

    public com.jessieray.api.model.UserInfo getUsers() {
        return users;
    }


}