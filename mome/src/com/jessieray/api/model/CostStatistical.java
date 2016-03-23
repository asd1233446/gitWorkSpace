package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class CostStatistical implements Serializable {
    /**
     * 动态详情信息
     */
    private java.util.List<com.jessieray.api.model.TypeInfo> types;



    public void setTypes(java.util.List<com.jessieray.api.model.TypeInfo> types) {
        this.types = types;
    }

    public java.util.List<com.jessieray.api.model.TypeInfo> getTypes() {
        return types;
    }


}