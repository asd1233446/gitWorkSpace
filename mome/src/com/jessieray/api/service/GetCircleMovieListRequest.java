package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.GetCircleMovieList;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class GetCircleMovieListRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 获取动态详情评论
     * 
     * @param userid 
     *           用户id
     * @param movie_type 
     *           电影类型
     * @param pageSize 
     *           页数量
     * @param pageNo 
     *           页索引
     */
    public static void findGetCircleMovieList(java.lang.String userid, java.lang.String movie_type, java.lang.Integer pageSize, java.lang.Integer pageNo, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/getCircleMovieList.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("movie_type", RequestUtils.object2String(movie_type));
        params.put("pageSize", RequestUtils.object2String(pageSize));
        params.put("pageNo", RequestUtils.object2String(pageNo));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<GetCircleMovieList>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}