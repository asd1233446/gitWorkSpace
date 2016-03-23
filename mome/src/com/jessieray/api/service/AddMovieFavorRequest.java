package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.AddMovieFavor;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class AddMovieFavorRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 收藏影片
     * 
     * @param userid 
     *           用户ID
     * @param movieid 
     *           电影id
     */
    public static void findAddMovieFavor(java.lang.String userid, java.lang.String movieid, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/addMovieFavor.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("movieid", RequestUtils.object2String(movieid));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<AddMovieFavor>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}