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

public class addRecallFriendsRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 添加观影同伴
     * 
     * @param recallid 
     *           回忆录id
     * @param friends 
     *           好友的id
     
     */
    public static void findaddRecallFriends(java.lang.String recallid,java.lang.String friends, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/addRecallFriends.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("recallid", RequestUtils.object2String(recallid));
        params.put("friends", RequestUtils.object2String(friends));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<?>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.POST, params, resultType, response);
    }





}