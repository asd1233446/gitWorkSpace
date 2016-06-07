package com.mome.main.wxapi;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.mome.main.business.access.ResultListener;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools.CallBack;
import com.mome.main.core.utils.UploadUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
  
	private IWXAPI api;
	
	public static   ResultListener myListener;
	
	public static void setResultListener(ResultListener listener){
		myListener=listener;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this, AppConfig.WEBCHAT_APP_KEY, false);
		api.handleIntent(getIntent(), this);
	}

	@Override
	public void onReq(BaseReq req) {
		// TODO Auto-generated method stub
		Toast.makeText(this, req.getType()+"=="+req.transaction, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onResp(BaseResp resp) {
		String result = "";
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result ="成功";
			if (ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX == resp.getType()) {
				Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
				break;
				}
			String code = ((SendAuth.Resp) resp).code; //即为所需的code
			getAcessToken(code);
			
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result ="取消";
			
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = "授权失败";
			break;
		default:
			result = "未知错误";
			break;
		}
		finish();
	}
	
	private void getAcessToken(String code){
		Map<String , String> map=new HashMap<String, String>();
		map.put("appid", AppConfig.WEBCHAT_APP_KEY);
		map.put("secret", AppConfig.WEBCHAT_APP_SECRET);
		map.put("code", code);
		map.put("grant_type", "authorization_code");
		UploadUtil.httpUtil("https://api.weixin.qq.com/sns/oauth2/access_token", map, "POST",new CallBack() {
			
			@Override
			public void Back(Object params) {
				// TODO Auto-generated method stub
				Log.e("=============", params.toString());
				if(!TextUtils.isEmpty((String)params)){
					JSONObject json;
					try {
						
						json = new JSONObject(params.toString());
						String accessToken=json.getString("access_token");
						String openid=json.getString("openid");
						getUserInfo(accessToken, openid);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}else{
					myListener.error("登陆失败");
				}
			}
		});
	}
	
	
	private void getUserInfo(String accessToken,String openid){
		Map<String , String> map=new HashMap<String, String>();
		map.put("access_token", accessToken);
		map.put("openid", openid);
		UploadUtil.httpUtil("https://api.weixin.qq.com/sns/userinfo", map,"POST", new CallBack() {
			
			@Override
			public void Back(Object params) {
				// TODO Auto-generated method stub
				Log.e("=============", params.toString());
				if(!TextUtils.isEmpty((String)params)){
					myListener.sucess(params);
				}
			}
		});
	}
	
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}
}
