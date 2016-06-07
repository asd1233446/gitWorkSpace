package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.AddArticleComment;
import com.jessieray.api.model.GetMovieUserArticleByKeyWord;
import com.jessieray.api.model.getDouBanToMovie;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class GetDouBanToMovieRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
  
    public static void findGetDouBanToMovie(java.lang.String id,ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/getDouBanToMovie.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("id", RequestUtils.object2String(id));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<getDouBanToMovie>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }





}