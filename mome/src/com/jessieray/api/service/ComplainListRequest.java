package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.AddArticleComment;
import com.jessieray.api.model.ComplainList;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class ComplainListRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 反馈列表
     * 
     */
    public static void findComplainList(java.lang.String userid,ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/complainList.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<ComplainList>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.POST, params, resultType, response);
    }





}