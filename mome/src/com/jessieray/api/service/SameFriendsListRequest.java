package com.jessieray.api.service;

import java.lang.reflect.Type;

import com.jessieray.api.model.AddMovieFavor;
import com.jessieray.api.model.Friend;
import com.jessieray.api.model.SameFriendsList;
import com.jessieray.api.model.addRecallArticle;
import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.RequestProxy;
import com.jessieray.api.request.base.RequestUtils;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseResult;

public class SameFriendsListRequest {

	public static Type resultType = new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {
	}.getType();

	/**
	 * 同场次用户列表 sameSceneList 观看此片的好友 sameMovieFriends 收藏过此篇的人sameFavorMovieusers
	 * 
	 * @param movieid
	 * 
	 * @param userid
	 * 
	 * @param pageNo
	 * 
	 * @param pageSize
	 * 
	 */
	public static void findFriendList(java.util.Map<String, String> params,
			String trade, ResponseCallback response) {

		String url = RequestProxy.getRequest().getRequestUrl() + trade;

		resultType = new com.google.gson.reflect.TypeToken<ResponseResult<SameFriendsList>>() {
		}.getType();
		RequestProxy.getRequest().doRequest(url, Request.Method.GET, params,
				resultType, response);
	}

}
