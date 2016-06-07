package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.AddArticleComment;
import com.jessieray.api.model.savePicture;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class DeleteArticleRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 删除动态
     * 
     * @param articleid 
     *          动态id
     */
    public static void findDeleteArticle(java.lang.String articleid,ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/deleteArticle.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("articleid", RequestUtils.object2String(articleid));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<savePicture>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }





}