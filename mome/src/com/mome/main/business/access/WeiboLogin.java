package com.mome.main.business.access;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.mome.main.business.access.weibo.AsyncWeiboRunner;
import com.mome.main.business.access.weibo.RequestListener;
import com.mome.main.core.datacache.DataSaveManager;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.WeiboParameters;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;

public class WeiboLogin {
	private final static String cacheKey = "weibo_token";
	/**
	 * 请求微博客户端的requestCode
	 */
	public final static int AUTH_REQUEST_CODE = 12373;
	/**
	 * SSO登录对象
	 */
	private SsoHandler ssoHandler;
	
	/**
	 * 最终登录成功后的消息，会发送到登录页面
	 */
	private Handler handler;
	
	private final static int TOKEN_SUCCESS = 0;
	private final static int FAILURE = TOKEN_SUCCESS + 1;
	private final static int RE_GET_TOKEN = FAILURE + 1;
	
	/**
	 * 第三方用户名
	 */
	private String userName;
	/**
	 * 自己内容的消息处理，
	 */
	private Handler loginHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == FAILURE){
				loginFail();
			} else if(msg.what == TOKEN_SUCCESS){
				TokenCache tokenCache = (TokenCache)msg.obj;
				getUserInfo(tokenCache.getToken(), tokenCache.getId(), false);
			} else if(msg.what == RE_GET_TOKEN){
				WeiboAuth weiboAuth = new WeiboAuth(AppConfig.mainActivity, AppConfig.WEIBO_APP_KEY, AppConfig.WEIBO_REDIRECT_URL, AppConfig.WEIBO_SCOPE);
				weiboAuth.authorize(new AuthListener(), WeiboAuth.OBTAIN_AUTH_CODE);
			}
		}
	};
	
	/**
	 * 用微博账号登录
	 */
	public void login(Handler handler){
		this.handler = handler;
		boolean isValid = false;
		Object obj = DataSaveManager.getInstance().readObject(cacheKey);
		if(obj != null){
			TokenCache tokenCache = (TokenCache)obj;
			if(tokenCache.isValid()){
				getUserInfo(tokenCache.getToken(), tokenCache.getId(), true);
				isValid = true;
			}
		}
		
		if(!isValid){
			WeiboAuth weiboAuth = new WeiboAuth(AppConfig.mainActivity, AppConfig.WEIBO_APP_KEY, AppConfig.WEIBO_REDIRECT_URL, AppConfig.WEIBO_SCOPE);
			ssoHandler = new SsoHandler(AppConfig.mainActivity, weiboAuth);
			ssoHandler.authorize(AUTH_REQUEST_CODE, new AuthListener(), null);
		}
	}
	/**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    private class AuthListener implements WeiboAuthListener {
        
        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
        	Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
            	TokenCache tokenCache = new TokenCache();
				tokenCache.setId(mAccessToken.getUid());
				tokenCache.setToken(mAccessToken.getToken());
				tokenCache.setExpiresIn(mAccessToken.getExpiresTime());
				tokenCache.setLoginTime(System.currentTimeMillis());
				DataSaveManager.getInstance().saveObject(cacheKey, tokenCache);
				
				Message message = new Message();
				message.what = TOKEN_SUCCESS;
				message.obj = tokenCache;
				loginHandler.sendMessage(message);
            } else {
                String code = values.getString("code");
                if (!TextUtils.isEmpty(code)) {
                	fetchTokenAsync(code);
                }else{
                	loginHandler.sendEmptyMessage(FAILURE);
                }
            }
        }

        @Override
        public void onCancel() {
            
        }

        @Override
        public void onWeiboException(WeiboException e) {
        	loginHandler.sendEmptyMessage(FAILURE);
        }
    }
    /**
     * 异步获取 Token。
     * 
     * @param authCode  授权 Code，该 Code 是一次性的，只能被获取一次 Token
     * @param appSecret 应用程序的 APP_SECRET，请务必妥善保管好自己的 APP_SECRET，
     *                  不要直接暴露在程序中，此处仅作为一个DEMO来演示。
     */
    public void fetchTokenAsync(String authCode) {
        WeiboParameters requestParams = new WeiboParameters();
        requestParams.add(WBConstants.AUTH_PARAMS_CLIENT_ID,     AppConfig.WEIBO_APP_KEY);
        requestParams.add(WBConstants.AUTH_PARAMS_CLIENT_SECRET, AppConfig.WEIBO_APP_SECRET);
        requestParams.add(WBConstants.AUTH_PARAMS_GRANT_TYPE,    "authorization_code");
        requestParams.add(WBConstants.AUTH_PARAMS_CODE,          authCode);
        requestParams.add(WBConstants.AUTH_PARAMS_REDIRECT_URL,  AppConfig.WEIBO_REDIRECT_URL);
    
        /**
         * 请注意：
         * {@link RequestListener} 对应的回调是运行在后台线程中的，
         * 因此，需要使用 Handler 来配合更新 UI。
         */
        AsyncWeiboRunner.request("https://open.weibo.cn/oauth2/access_token", requestParams, "POST", new RequestListener() {
            @Override
            public void onComplete(String response) {
                // 获取 Token 成功
                Oauth2AccessToken token = Oauth2AccessToken.parseAccessToken(response);
                if (token != null && token.isSessionValid()) {
                	TokenCache tokenCache = new TokenCache();
    				tokenCache.setId(token.getUid());
    				tokenCache.setToken(token.getToken());
    				tokenCache.setExpiresIn(token.getExpiresTime());
    				tokenCache.setLoginTime(System.currentTimeMillis());
    				DataSaveManager.getInstance().saveObject(cacheKey, tokenCache);
    				
    				Message message = new Message();
    				message.what = TOKEN_SUCCESS;
    				message.obj = tokenCache;
    				loginHandler.sendMessage(message);
                } else {
                	loginHandler.sendEmptyMessage(FAILURE);
                }
            }
    
            @Override
            public void onComplete4binary(ByteArrayOutputStream responseOS) {
            	loginHandler.sendEmptyMessage(FAILURE);
            }
    
            @Override
            public void onIOException(IOException e) {
            	loginHandler.sendEmptyMessage(FAILURE);
            }
    
            @Override
            public void onError(WeiboException e) {
            	loginHandler.sendEmptyMessage(FAILURE);
            }
        });
    }
    private void getUserInfo(String token, String uid, final boolean isReToken){
    	WeiboParameters requestParams = new WeiboParameters();
        requestParams.add("access_token", token);
        requestParams.add("uid", uid);
        Tools.toastShow("正在登录");
        AsyncWeiboRunner.request("https://api.weibo.com/2/users/show.json", requestParams, "GET", new RequestListener(){
			@Override
			public void onComplete(String response) {
				try{
					JSONObject json = new JSONObject(response);
					userName = json.optString("screen_name");
//					SinaUserInfoHttpService.bind(json.optString("screen_name"), json.optString("id"), WeiboLogin.this);
				}catch(Exception e){
					if(isReToken){
						loginHandler.sendEmptyMessage(RE_GET_TOKEN);
					}else{
						loginHandler.sendEmptyMessage(FAILURE);
					}
				}
			}

			@Override
			public void onComplete4binary(
					ByteArrayOutputStream responseOS) {
				if(isReToken){
					loginHandler.sendEmptyMessage(RE_GET_TOKEN);
				}else{
					loginHandler.sendEmptyMessage(FAILURE);
				}
			}

			@Override
			public void onIOException(IOException e) {
				if(isReToken){
					loginHandler.sendEmptyMessage(RE_GET_TOKEN);
				}else{
					loginHandler.sendEmptyMessage(FAILURE);
				}
			}

			@Override
			public void onError(WeiboException e) {
				if(isReToken){
					loginHandler.sendEmptyMessage(RE_GET_TOKEN);
				}else{
					loginHandler.sendEmptyMessage(FAILURE);
				}
			}
        	
        });
    }
	/**
	 * 代理ssoHandler的authorizeCallBack方法
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	public void authorizeCallBack(int requestCode, int resultCode, Intent data){
		if(ssoHandler != null){
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}
	
	private void loginFail(){
		Tools.toastShow("登录失败");
	}
	
	
	private static WeiboLogin weiboLogin;
	public static WeiboLogin getInstance(){
		if(weiboLogin == null){
			weiboLogin = new WeiboLogin();
		}
		return weiboLogin;
	}

	public static void clearToken(){
		TokenCache tokenCache = new TokenCache();
		tokenCache.setId("");
		tokenCache.setToken("");
		tokenCache.setExpiresIn(0);
		tokenCache.setLoginTime(0);
		DataSaveManager.getInstance().saveObject(cacheKey, tokenCache);
	}
}
