package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.Me;
import com.jessieray.api.model.Message;
import com.jessieray.api.model.MessageInfo;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class MessageInfoRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();



    /**
     * 消息
     * 
     * @param userid 
     *           用户ID
     */
    public static void findMeaage(java.util.Map<String, String> params,String trad,ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + trad;
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Message>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }

    /**
     * 消息
     * 
     * @param userid 
     *           用户ID
     */
    public static void sendMeaage(java.util.Map<String, String> params,String trad,ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + trad;
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Message>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.POST, params, resultType, response);
    }

}