 package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.Me;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class MeRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 个人信息
     * 
     * @param userid 
     *           用户ID
     */
    public static void findMe(java.lang.String userid, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/me.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Me>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}