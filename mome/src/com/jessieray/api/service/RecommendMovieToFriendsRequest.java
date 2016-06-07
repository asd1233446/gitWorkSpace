package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.AddArticleComment;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class RecommendMovieToFriendsRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     *推荐影片给好友
     * 
     * @param userid 
     *           用户ID
     * @param movieid 
     *           电影id
     * @param ids 
     *           多个好友用','隔开 ，不可为空
     */
    public static void findRecommendMovie(java.lang.String userid, java.lang.String movieid, java.lang.String ids, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/recommendMovieToFriends.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("movieid", RequestUtils.object2String(movieid));
        params.put("ids", RequestUtils.object2String(ids));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<?>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }





}