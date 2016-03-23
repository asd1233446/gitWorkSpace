package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.RecallStatistical;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class RecallStatisticalRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 回忆录统计
     * 
     * @param userid 
     *           用户ID
     * @param date 
     *           年
     */
    public static void findRecallStatistical(java.lang.String userid, java.lang.String date, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/recallStatistical.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("date", RequestUtils.object2String(date));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<RecallStatistical>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}