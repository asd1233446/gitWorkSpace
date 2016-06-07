package com.jessieray.api.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.jessieray.api.model.AddArticleComment;
import com.jessieray.api.model.BindLogin;
import com.jessieray.api.model.UserInfo;

import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.request.base.RequestUtils;

/**
 * This file is auto generated, do not modify it by hand
 * 
 */

public class BindThridpartRequest {

	public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {
	}.getType();

	/**
	 * 
	 * type:第三方类型，int，1：微博，2：微信，3：豆瓣，不能为空 
	 * thirdid：此平台ID，String，不能为空；
	 * username：此平台用户名，String，可以为空；
	 * nickname：此平台昵称，String，可以为空；
	 * avatar：此平台头像地址,String,可以为空
	 */
	public static void findBindThridpart(java.lang.String type,
			java.lang.String thirdid, java.lang.String username,String nickname,String avatar,
			ResponseCallback response) {
		String url = RequestProxy.getRequest().getRequestUrl()
				+ "/bindThridpart.shtml";
		java.util.Map<String, String> params = new java.util.HashMap<String, String>();
		params.put("type", RequestUtils.object2String(type));
		params.put("thirdid", RequestUtils.object2String(thirdid));
		params.put("username", RequestUtils.object2String(username));
		params.put("nickname", RequestUtils.object2String(nickname));
		params.put("avatar", RequestUtils.object2String(avatar));
		resultType = new com.google.gson.reflect.TypeToken<ResponseResult<BindLogin>>() {
		}.getType();
		RequestProxy.getRequest().doRequest(url, Request.Method.POST, params,
				resultType, response);
	}

}