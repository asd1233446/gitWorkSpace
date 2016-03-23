package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.ArticleListByMovieId;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class ArticleListByMovieIdRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 获取电影详情页动态列表
     * 
     * @param movieid 
     *           电影ID
     * @param ordertype 
     *           排序类型
     * @param pageNo 
     *           分页页码
     * @param pageSize 
     *           每页显示数量
     */
    public static void findArticleListByMovieId(java.lang.String movieid, java.lang.Integer ordertype, java.lang.Integer pageNo, java.lang.Integer pageSize, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/ArticleListByMovieId.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("movieid", RequestUtils.object2String(movieid));
        params.put("ordertype", RequestUtils.object2String(ordertype));
        params.put("pageNo", RequestUtils.object2String(pageNo));
        params.put("pageSize", RequestUtils.object2String(pageSize));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<ArticleListByMovieId>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}