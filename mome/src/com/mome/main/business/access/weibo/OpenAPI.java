package com.mome.main.business.access.weibo;

import android.content.Context;
import android.util.Log;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;

public class OpenAPI extends AbsOpenAPI {
	 private static final String API_BASE_URL = API_SERVER + "/users";
	 private static final String REVOKE_OAUTH_URL="https://api.weibo.com/oauth2/revokeoauth2";
	 private static final String API_FRIENDS_URL=API_SERVER + "/friendships/friends";
	 
	public OpenAPI(Context context, String appKey, Oauth2AccessToken accessToken) {
		super(context, appKey, accessToken);
		// TODO Auto-generated constructor stub
	}
	
	/**
     * 根据用户ID获取用户信息。
     * 
     * @param uid      需要查询的用户ID
     * @param listener 异步请求回调接口
     */
    public void show(long uid, RequestListener listener) {
        WeiboParameters params = new WeiboParameters(mAppKey);
        params.put("uid", uid);
        requestAsync(API_BASE_URL + "/show.json", params, HTTPMETHOD_GET, listener);
    }
    
    
    /**
     *获取用户的双向关注列表，即互粉列表
     * https://api.weibo.com/2/friendships/friends/bilateral.json
     * @param uid      需要查询的用户ID
     * @param count 单页返回的记录条数，默认为50。
     * @param page 返回结果的页码，默认为1
     * @param sort 排序类型，0：按关注时间最近排序，默认为0。
     * @param listener 异步请求回调接口
     */
    public void getFriends(String uid,String count,String page,String sort, RequestListener listener) {
        WeiboParameters params = new WeiboParameters(mAppKey);
        params.put("uid", uid);
        params.put("count", count);
        params.put("page", page);
        params.put("sort", sort);
        Log.e("===========", "=========="+API_FRIENDS_URL + "/bilateral.json");
        requestAsync(API_FRIENDS_URL + "/bilateral.json", params, HTTPMETHOD_GET, listener);
    }
    
    public void logout(RequestListener listener) {
        requestAsync(REVOKE_OAUTH_URL, new WeiboParameters(mAppKey), HTTPMETHOD_POST, listener);
    }
}
