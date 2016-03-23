package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class ArticleDetail implements Serializable {
    /**
     * 动态详情信息
     */
    private com.jessieray.api.model.DynamicInfo info;



    public void setInfo(com.jessieray.api.model.DynamicInfo info) {
        this.info = info;
    }

    public com.jessieray.api.model.DynamicInfo getInfo() {
        return info;
    }


}