package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.AddArticleComment;
import com.jessieray.api.model.RelationListByUserId;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class RelationListByUserIdRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 查询好友
     * 
     * @param userid 
     *           用户ID
     * @param type 
     *           好友类型id
     * @param pageNo 
     *           页码
     *           @param pageSize
     *           每页显示条数
     */
    public static void findRelationListByUserId(java.lang.String userid,java.lang.Integer type, java.lang.Integer pageNo, java.lang.Integer pageSize, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/relationListByUserId.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("type", RequestUtils.object2String(type));
        params.put("pageNo", RequestUtils.object2String(pageNo));
        params.put("pageSize", RequestUtils.object2String(pageSize));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<RelationListByUserId>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }





}