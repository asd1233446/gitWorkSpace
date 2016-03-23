package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.SendPersonelMessage;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class SendPersonelMessageRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 发送消息
     * 
     * @param userid 
     *           用户ID
     * @param toid 
     *           接收人用户ID
     * @param brief 
     *           消息内容
     */
    public static void findSendPersonelMessage(java.lang.String userid, java.lang.String toid, java.lang.String brief, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/SendPersonelMessage.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("toid", RequestUtils.object2String(toid));
        params.put("brief", RequestUtils.object2String(brief));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<SendPersonelMessage>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}