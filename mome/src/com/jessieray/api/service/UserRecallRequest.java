package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.UserRecall;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class UserRecallRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 电影回忆录
     * 
     * @param userid 
     *           用户ID
     */
    public static void findUserRecall(java.lang.String userid,String year,int pageNo ,int pageSize,  ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/UserRecall.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("year", RequestUtils.object2String(year));
        params.put("pageNo", RequestUtils.object2String(pageNo));
        params.put("pageSize", RequestUtils.object2String(pageSize));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<UserRecall>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}