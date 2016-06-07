package com.mome.main.business.access;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.jessieray.api.model.UserInfo;
import com.mome.main.business.access.weibo.OpenAPI;
import com.mome.main.core.datacache.DataSaveManager;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.utils.Utility;

public class WeiboLogin {
	private AuthInfo mAuthInfo;
	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能 */
	private Oauth2AccessToken mAccessToken;
	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
	private SsoHandler mSsoHandler;

	private static WeiboLogin sinaLogin;
	
	private ResultListener listener;

	private Context context;
	private final static int TOKEN_SUCCESS = 0;
	private final static int FAILURE = TOKEN_SUCCESS + 1;
	private TokenCache tokenCache;
	
	private Map<String, String> parmes=new HashMap<String, String>();
	//授权入口;0 查询用户信息，1获取好友列表
	private int type=-1;
	public WeiboLogin(Context context) {
		this.context = context;
	}


	public void  query(int type,Map<String, String> parmes,ResultListener listener){
	this.type=type;
	this.parmes=parmes;
	this.listener=listener;
	auth();
	}
	
	
	private void auth(){
		mAuthInfo = new AuthInfo(context, AppConfig.WEIBO_APP_KEY,
				AppConfig.WEIBO_REDIRECT_URL, AppConfig.WEIBO_SCOPE);
		mSsoHandler = new SsoHandler((Activity) context, mAuthInfo);
		mSsoHandler.authorize(new AuthListener());
			}
	
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			 if (msg.what == TOKEN_SUCCESS) {
				 Log.e("type", "type"+type);
				 switch (type) {
				case 0:
					//查询用户信息
					getUserInfo();
					break;
				case 1:
					//好友列表；
					getFriends(parmes);
					break;
				default:
					break;
				}
				
			}
		}
	};

//获得用户信息
	private void getUserInfo() {
		OpenAPI api = new OpenAPI(context, AppConfig.WEIBO_APP_KEY,
				mAccessToken);
		api.show(Long.valueOf(tokenCache.getId()), ResultListener);
	}
	//新浪微博好友列表
	public void getFriends(Map parmes){
		OpenAPI api = new OpenAPI(context, AppConfig.WEIBO_APP_KEY,
				mAccessToken);
		api.getFriends(tokenCache.getId(), (String)parmes.get("count"), (String) parmes.get("page"), "0", ResultListener);
	}
	
	public void LogOut(){
		OpenAPI api = new OpenAPI(context, AppConfig.WEIBO_APP_KEY,
				mAccessToken);
		api.logout(ResultListener);
	}

	/**
	 * 微博 OpenAPI 回调接口。
	 */
	private RequestListener ResultListener = new RequestListener() {
		@Override
		public void onComplete(String response) {
			Log.e("====用户查询查询==", response);
			listener.sucess(response);
		}

		@Override
		public void onWeiboException(WeiboException e) {
			e.printStackTrace();
			listener.error("查询失败");
		}
	};


	public SsoHandler getSsoHandler() {
		return mSsoHandler;
	}

	/**
	 * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 */
	class AuthListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			// 从 Bundle 中解析 Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			// 从这里获取用户输入的 电话号码信息
			String phoneNum = mAccessToken.getPhoneNum();
			if (mAccessToken.isSessionValid()) {
				// 保存 Token 到 SharedPreferences
				tokenCache = new TokenCache();
				tokenCache.setId(mAccessToken.getUid());
				tokenCache.setToken(mAccessToken.getToken());
				tokenCache.setExpiresIn(mAccessToken.getExpiresTime());
				tokenCache.setLoginTime(System.currentTimeMillis());
//				DataSaveManager.getInstance().saveObject(cacheKey, tokenCache);

				handler.sendEmptyMessage(TOKEN_SUCCESS);
				Log.e("================","====授权成功==");
				


			} else {
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
				String code = values.getString("code");
				Log.e("授权失败", code);

				listener.error("登陆失败");

			}
		}

		@Override
		public void onCancel() {
			Log.e("================","====授权取消==");

		}

		@Override
		public void onWeiboException(WeiboException e) {
			e.printStackTrace();
		
		}
	}

	/***
	 * 分享
	 */
	/** 微博分享的接口实例 */
	private IWeiboShareAPI mWeiboShareAPI;


	public IWeiboShareAPI registerToSina() {
		// 创建微博 SDK 接口实例		
		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context,
				AppConfig.WEIBO_APP_KEY);
		  mWeiboShareAPI.registerApp();
		return mWeiboShareAPI;
	}

	public void sendMedia(String title, String description, Bitmap ThumbImage,
			String url, String defaultText) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
		WebpageObject mediaObject = new WebpageObject();
		mediaObject.identify = Utility.generateGUID();
		mediaObject.title = title;
		mediaObject.description = description;
		// 设置 Bitmap 类型的图片到视频对象里 设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
		mediaObject.setThumbImage(ThumbImage);
		mediaObject.actionUrl = url;
		mediaObject.defaultText = defaultText;
		weiboMessage.mediaObject = mediaObject;
		send(weiboMessage);
	}

	public void sendText(String shareText) {
		// 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
		if (!TextUtils.isEmpty(shareText)) {
			Log.e("进入了发送", shareText);
			TextObject textObject = new TextObject();
			textObject.text = shareText;
			weiboMessage.textObject = textObject;
			send(weiboMessage);
		}
	}

	public void sendImage(Bitmap shareImage) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
		if (shareImage != null) {
			// 设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
			ImageObject imageObject = new ImageObject();
			imageObject.setImageObject(shareImage);
			weiboMessage.imageObject = imageObject;
			send(weiboMessage);
		}
	}

	private void send(WeiboMultiMessage weiboMessage) {
		  // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
//        String token="";
//        if(tokenCache!=null){
//        	token=tokenCache.getToken();
//        }
        mWeiboShareAPI.sendRequest((Activity) context, request, mAuthInfo, "", new AuthListener());
    
	}
}
