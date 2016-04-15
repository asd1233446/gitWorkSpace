package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.CinemaListByCity;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class CinemaListByCityRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 添加观影回忆录，影院列表
     * 
     * @param district 
     *           区
     * @param city 
     *           城市
     */
    public static void findCinemaListByCity(java.lang.String userid, java.lang.String city, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/CinemaListByCity.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("city", RequestUtils.object2String(city));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<CinemaListByCity>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}