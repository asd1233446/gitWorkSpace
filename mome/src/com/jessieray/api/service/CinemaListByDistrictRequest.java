package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.CinemaListByDistrict;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class CinemaListByDistrictRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 添加观影回忆录，影院列表
     * 
     * @param district 
     *           区
     * @param city 
     *           城市
     */
    public static void findCinemaListByDistrict(java.lang.String district, java.lang.String city, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/CinemaListByDistrict.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("district", RequestUtils.object2String(district));
        params.put("city", RequestUtils.object2String(city));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<CinemaListByDistrict>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}