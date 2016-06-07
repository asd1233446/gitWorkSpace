package com.mome.main.business.access;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;

import com.mome.main.R;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
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
	
	public void sendText(String text){
		WXTextObject textObj = new WXTextObject();
		textObj.text = text;
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;

		msg.description = text;
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("text"); 
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		
		api.sendReq(req);
	}
	
	
	public void sendPage(String webpageUrl,String title,String description,Bitmap thumb,boolean isTimeline){
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl =webpageUrl;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
		msg.description = description;
		msg.thumbData = bmpToByteArray(thumb, false);
		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = isTimeline ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
		api.sendReq(req);
	}
	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}
		
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}
}
