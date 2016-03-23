package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
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

public class GetMovieUserArticleByKeyWordRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 获取搜索结果
     * 
     * @param keyWord 
     *           关键词
     * @param pageSize 
     *           结果数量
     */
    public static void findGetMovieUserArticleByKeyWord(java.lang.String keyWord, java.lang.Integer pageSize, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/getMovieUserArticleByKeyWord.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("keyWord", RequestUtils.object2String(keyWord));
        params.put("pageSize", RequestUtils.object2String(pageSize));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<GetMovieUserArticleByKeyWord>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}