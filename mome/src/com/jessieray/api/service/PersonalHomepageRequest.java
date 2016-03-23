package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.PersonalHomepage;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class PersonalHomepageRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 个人主页
     * 
     * @param userid 
     *           用户ID
     * @param myuserid 
     *           访问者的用户ID
     */
    public static void findPersonalHomepage(java.lang.String userid, java.lang.String myuserid, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/personalHomepage.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("myuserid", RequestUtils.object2String(myuserid));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<PersonalHomepage>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}