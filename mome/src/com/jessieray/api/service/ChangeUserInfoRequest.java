package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.ChangeUserInfo;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class ChangeUserInfoRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 更新用户信息
     * 
     * @param userid 
     *           用户ID
     * @param sex 
     *           性别
     * @param nickname 
     *           昵称
     * @param avatar 
     *           头像地址
     * @param signature 
     *           签名
     * @param location 
     *           地址
     */
    public static void findChangeUserInfo(java.lang.String userid, java.lang.String sex, java.lang.String nickname, java.lang.String avatar, java.lang.String signature, java.lang.String location, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/ChangeUserInfo.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("sex", RequestUtils.object2String(sex));
        params.put("nickname", RequestUtils.object2String(nickname));
        params.put("avatar", RequestUtils.object2String(avatar));
        params.put("signature", RequestUtils.object2String(signature));
        params.put("location", RequestUtils.object2String(location));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<ChangeUserInfo>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.POST, params, resultType, response);
    }




}