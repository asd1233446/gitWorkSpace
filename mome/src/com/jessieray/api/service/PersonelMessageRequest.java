package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.PersonelMessage;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class PersonelMessageRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 获取消息列表
     * 
     * @param userid 
     *           用户ID
     * @param Lastid 
     *           消息ID
     */
    public static void findPersonelMessage(java.lang.String userid, java.lang.String Lastid, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/PersonelMessage.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("Lastid", RequestUtils.object2String(Lastid));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<PersonelMessage>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}