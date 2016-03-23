package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.GetCommentsByArticleId;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class GetCommentsByArticleIdRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 获取动态详情评论
     * 
     * @param userid 
     *           用户ID
     * @param articleid 
     *           动态id
     * @param pageSize 
     *           页面评论数量
     * @param pageNo 
     *           页索引
     */
    public static void findGetCommentsByArticleId(java.lang.String userid, java.lang.String articleid, java.lang.Integer pageSize, java.lang.Integer pageNo, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/getCommentsByArticleId.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("articleid", RequestUtils.object2String(articleid));
        params.put("pageSize", RequestUtils.object2String(pageSize));
        params.put("pageNo", RequestUtils.object2String(pageNo));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<GetCommentsByArticleId>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}