package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.AddArticleComment;
import com.jessieray.api.model.Addttention;
import com.jessieray.api.model.Cancelttention;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class CancelttentionRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 添加关注
     * 
     * @param userid 
     *           用户ID
     * @param relatedid 
     *           被关注的id
     
     */
    public static void findCancelAddttention(java.lang.String userid,java.lang.String relatedid, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/cancelttention.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("relatedid", RequestUtils.object2String(relatedid));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Cancelttention>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }





}