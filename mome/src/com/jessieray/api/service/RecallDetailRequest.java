package com.jessieray.api.service;

import java.lang.reflect.Type;

import com.jessieray.api.model.GetCircleMovieList;
import com.jessieray.api.model.RecallDetail;
import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.RequestUtils;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;

public class RecallDetailRequest {
	 public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {}.getType();

	    
	    /**
	     * 获取回忆录详情
	     * 
	     * @param recallid 
	     *           用户id
	     */
	    public static void findRecallDetail(java.lang.String recallid, ResponseCallback response) {
	        String url = RequestProxy.getRequest().getRequestUrl() + "/RecallDetail.shtml";
	        java.util.Map<String, String> params = new java.util.HashMap<String, String>();
	        params.put("recallid", RequestUtils.object2String(recallid));
	        resultType = new com.google.gson.reflect.TypeToken<ResponseResult<RecallDetail>>() {}.getType();
	        RequestProxy.getRequest().doRequest(url, Request.Method.GET, params, resultType, response);
	    }
}
