package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class Me implements Serializable {
    /**
     * 动态详情信息
     */
    private com.jessieray.api.model.UserInfo userinfo;



    public void setUserinfo(com.jessieray.api.model.UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public com.jessieray.api.model.UserInfo getUserinfo() {
        return userinfo;
    }


}