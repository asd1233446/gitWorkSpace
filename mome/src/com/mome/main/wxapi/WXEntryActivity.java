package com.mome.main.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.mome.main.business.access.LoginInterface;
import com.mome.main.core.utils.AppConfig;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{

	private IWXAPI api;
	
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
			result =resp.errStr;
			logins.sucess("成功");
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
		
		Toast.makeText(this, resp.errStr+"==="+result, Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	public static  LoginInterface logins;

	public static void setLoginInterface(LoginInterface login) {
		logins = login;

	}
}
