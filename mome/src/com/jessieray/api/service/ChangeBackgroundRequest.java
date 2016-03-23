package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.ChangeBackground;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class ChangeBackgroundRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 修改背景图片
     * 
     * @param userid 
     *           用户ID
     * @param backgroundimg 
     *           图片地址
     */
    public static void findChangeBackground(java.lang.String userid, java.lang.String backgroundimg, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/ChangeBackground.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("backgroundimg", RequestUtils.object2String(backgroundimg));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<ChangeBackground>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}