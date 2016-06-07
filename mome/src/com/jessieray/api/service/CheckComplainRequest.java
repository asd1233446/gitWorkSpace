package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.AddArticleComment;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class CheckComplainRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 反馈回复
     * 
     */
    public static void findAddComplain(java.lang.String complainid,ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/checkComplain.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("complainid", RequestUtils.object2String(complainid));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<?>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.POST, params, resultType, response);
    }





}