package com.mome.main.business.access.weibo;

import android.content.Context;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;

public class OpenAPI extends AbsOpenAPI {
	 private static final String API_BASE_URL = API_SERVER + "/users";
	 private static final String REVOKE_OAUTH_URL="https://api.weibo.com/oauth2/revokeoauth2";
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
    
    public void logout(RequestListener listener) {
        requestAsync(REVOKE_OAUTH_URL, new WeiboParameters(mAppKey), HTTPMETHOD_POST, listener);
    }
}
