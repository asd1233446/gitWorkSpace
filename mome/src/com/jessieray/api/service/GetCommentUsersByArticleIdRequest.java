package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.GetCommentUsersByArticleId;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class GetCommentUsersByArticleIdRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 动态评论用户列表
     * 
     * @param articleid 
     *           动态表id
     */
    public static void findGetCommentUsersByArticleId(java.lang.String articleid, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/getCommentUsersByArticleId.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("articleid", RequestUtils.object2String(articleid));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<GetCommentUsersByArticleId>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}