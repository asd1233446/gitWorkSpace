package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.GetArticleByUserId;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class GetArticleByUserIdRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 获取动态
     * 
     * @param userid 
     *           用户ID
     * @param article_type 
     *           动态类型，参数类型
     * @param pageNo 
     *           分页页码
     * @param pageSize 
     *           每页显示数量
     */
    public static void findGetArticleByUserId(java.lang.String userid, java.lang.String article_type, java.lang.Integer pageNo, java.lang.Integer pageSize, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/getArticleByUserId.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("article_type", RequestUtils.object2String(article_type));
        params.put("pageNo", RequestUtils.object2String(pageNo));
        params.put("pageSize", RequestUtils.object2String(pageSize));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<GetArticleByUserId>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}