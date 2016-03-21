package com.mome.main.business.access;

import com.mome.main.core.utils.AppConfig;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXLogin {
	
	/**
	 * 应用appid
	 */
	public static final String APP_ID = "wxd930ea5d5a258f4f";
	private IWXAPI api;
	
	/**
	 * 应用注册到微信
	 */
	private void regToWx() {
		api = WXAPIFactory.createWXAPI(AppConfig.context, APP_ID, true);
		api.registerApp(APP_ID);
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
