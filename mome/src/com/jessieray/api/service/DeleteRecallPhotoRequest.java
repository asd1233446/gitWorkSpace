package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.AddArticleComment;
import com.jessieray.api.model.savePicture;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class DeleteRecallPhotoRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 删除观影图片
     * 
     * @param photoid 
     *           图片ID
     */
    public static void findDeleteRecallPhoto(java.lang.String photoid,ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/deleteRecallPhoto.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("photoid", RequestUtils.object2String(photoid));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<savePicture>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }





}