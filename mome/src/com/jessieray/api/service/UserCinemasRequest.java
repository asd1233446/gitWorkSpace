package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.UserCinemas;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class UserCinemasRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 获取他得影院
     * 
     * @param userid 
     *           用户ID
     * @param pageNo 
     *           页数
     * @param pageSize 
     *           页条数
     */
    public static void findUserCinemas(java.lang.String userid, java.lang.Integer pageNo, java.lang.Integer pageSize, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/UserCinemas.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("pageNo", RequestUtils.object2String(pageNo));
        params.put("pageSize", RequestUtils.object2String(pageSize));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<UserCinemas>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}