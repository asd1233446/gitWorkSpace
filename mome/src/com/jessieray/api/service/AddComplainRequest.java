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

public class AddComplainRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 提交反馈意见
     * 
     */
    public static void findAddComplain(java.lang.String userid, java.lang.Integer type, java.lang.String brief,java.lang.String qq,java.lang.String phone,java.lang.String cell, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/addComplain.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("type", RequestUtils.object2String(type));
        params.put("brief", RequestUtils.object2String(brief));
        params.put("qq", RequestUtils.object2String(qq));
        params.put("phone", RequestUtils.object2String(phone));
        params.put("cell", RequestUtils.object2String(cell));

        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<?>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.POST, params, resultType, response);
    }





}