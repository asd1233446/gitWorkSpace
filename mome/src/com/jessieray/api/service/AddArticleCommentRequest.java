package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.AddArticleComment;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class AddArticleCommentRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 发表评论
     * 
     * @param userid 
     *           用户ID
     * @param articleid 
     *           动态id
     * @param brief 
     *           评论内容
     */
    public static void findAddArticleComment(java.lang.String userid, java.lang.String articleid, java.lang.String brief, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/addArticleComment.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("articleid", RequestUtils.object2String(articleid));
        params.put("brief", RequestUtils.object2String(brief));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<AddArticleComment>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }





}