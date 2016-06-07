package com.jessieray.api.service;

import java.lang.reflect.Type;

import com.jessieray.api.model.AddMovieFavor;
import com.jessieray.api.model.addRecallArticle;
import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.RequestUtils;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;

public class addRecallArticleRequest {

	public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {
	}.getType();

	/**
	 * 添加影评
	 * 
	 * @param recallid
	 *            回忆录id
	 * @param mark
	 *            评分
	 * @param brief
	 *            短评
	 */
	public static void findaddRecallArticle(String userid, java.lang.String recallid,String movieId,
			java.lang.String mark, String brief, ResponseCallback response) {
		String url = RequestProxy.getRequest().getRequestUrl()
				+ "/addRecallArticle.shtml";
		java.util.Map<String, String> params = new java.util.HashMap<String, String>();
		params.put("userid", RequestUtils.object2String(userid));
		params.put("recallid", RequestUtils.object2String(recallid));
		params.put("mark", RequestUtils.object2String(mark));
		params.put("brief", RequestUtils.object2String(brief));
		params.put("movieid", RequestUtils.object2String(movieId));
		resultType = new com.google.gson.reflect.TypeToken<ResponseResult<addRecallArticle>>() {
		}.getType();
		RequestProxy.getRequest().doRequest(url, Request.Method.POST, params,
				resultType, response);
	}

}
