package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.AddArticleComment;
import com.jessieray.api.model.PlatformList;
import com.jessieray.api.model.savePicture;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class PlatformListRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 网络及电视观影平台列表
     * 
     * @param type 观影平台类型，2为网络观影平台 ，3为电视平台
     */
    public static void findPlatformList(java.lang.String type,ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/platformList.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("type", RequestUtils.object2String(type));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<PlatformList>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }





}