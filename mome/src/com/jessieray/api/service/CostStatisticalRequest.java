package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.CostStatistical;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class CostStatisticalRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 评分统计
     * 
     * @param userid 
     *           用户ID
     * @param date 
     *           年
     * @param type 
     *           回忆录类型
     */
    public static void findCostStatistical(java.lang.String userid, java.lang.String date, java.lang.Integer type, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/costStatistical.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("date", RequestUtils.object2String(date));
        params.put("type", RequestUtils.object2String(type));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<CostStatistical>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}