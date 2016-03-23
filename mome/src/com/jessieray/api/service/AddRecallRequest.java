package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.AddRecall;


import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class AddRecallRequest {
    
    public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

    
    /**
     * 添加观影回忆录
     * 
     * @param userid 
     *           用户ID
     * @param movieid 
     *           电影id
     * @param type 
     *           回忆录类型
     * @param title 
     *           电影名称
     * @param date 
     *           观影日期
     * @param mark 
     *           评分
     * @param theater 
     *           剧场名
     * @param cinemaid 
     *           影院ID
     * @param seat 
     *           座位
     * @param brief 
     *           短评
     * @param starttime 
     *           开始时间
     * @param ticketphoto 
     *           电影票图片地址
     * @param price 
     *           票价
     */
    public static void findAddRecall(java.lang.String userid, java.lang.String movieid, java.lang.Integer type, java.lang.String title, java.lang.String date, java.lang.Float mark, java.lang.String theater, java.lang.String cinemaid, java.lang.String seat, java.lang.String brief, java.lang.String starttime, java.lang.String ticketphoto, java.lang.Integer price, ResponseCallback response) {
        String url = RequestProxy.getRequest().getRequestUrl() + "/addRecall.shtml";
        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
        params.put("userid", RequestUtils.object2String(userid));
        params.put("movieid", RequestUtils.object2String(movieid));
        params.put("type", RequestUtils.object2String(type));
        params.put("title", RequestUtils.object2String(title));
        params.put("date", RequestUtils.object2String(date));
        params.put("mark", RequestUtils.object2String(mark));
        params.put("theater", RequestUtils.object2String(theater));
        params.put("cinemaid", RequestUtils.object2String(cinemaid));
        params.put("seat", RequestUtils.object2String(seat));
        params.put("brief", RequestUtils.object2String(brief));
        params.put("starttime", RequestUtils.object2String(starttime));
        params.put("ticketphoto", RequestUtils.object2String(ticketphoto));
        params.put("price", RequestUtils.object2String(price));
        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<AddRecall>>() {}.getType();
        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
    }




}