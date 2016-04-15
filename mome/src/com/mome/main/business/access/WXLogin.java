package com.mome.main.business.access;

import android.content.Context;
import android.widget.Toast;

import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXLogin {
	
	/**
	 * 应用appid
	 */
	private IWXAPI api;
	
	/**
	 * 应用注册到微信
	 */
	
	private Context context;
	
	private static WXLogin wxlogin;
	
	private WXLogin(Context context){
		this.context=context;
		regToWx();
	}
	
	
	
	public static WXLogin getInstance(Context context){
		if(wxlogin==null){
			wxlogin=new WXLogin(context);
		}
		return wxlogin;
	}
	
	private void regToWx() {
		api = WXAPIFactory.createWXAPI(context, AppConfig.WEBCHAT_APP_KEY, true);
		if(!api.isWXAppInstalled())
			Tools.toastShow("未安装微信");
		else
		api.registerApp(AppConfig.WEBCHAT_APP_KEY);
	}
	
	/**
	 * 微信登录授权请求
	 */
	public void WXLoginRequest() {
		final SendAuth.Req req = new SendAuth.Req();
		req.scope = "snsapi_userinfo";
		req.state = "wechat_sdk_demo_test";
		api.sendReq(req);
		
	}
	
}
