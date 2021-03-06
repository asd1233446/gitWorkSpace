package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.GetGoodUsersByArticleId;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class GetGoodUsersByArticleIdRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 动态点赞用户列表
     * 
     * @param articleid 
     *           动态表id
     */
    public static void findGetGoodUsersByArticleId(java.lang.String articleid, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/getGoodUsersByArticleId.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("articleid", RequestUtils.object2String(articleid));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<GetGoodUsersByArticleId>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}