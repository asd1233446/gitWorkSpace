package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.AddArticleComment;
import com.jessieray.api.model.GetMovieUserArticleByKeyWord;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class getMovieByKeywordRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 发表评论
     * 
     * @param userid 
     *           用户ID
     * @param keyword 
     *           关键字
     
     */
    public static void findgetMovieByKeyword(java.lang.String userid, java.lang.String keyword, int pageNo,int pageSize, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/getMovieByKeyword.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("keyword", RequestUtils.object2String(keyword));
        params.put("pageSize", RequestUtils.object2String(pageSize));
        params.put("pageNo", RequestUtils.object2String(pageNo));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<GetMovieUserArticleByKeyWord>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }





}