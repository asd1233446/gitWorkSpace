package com.mome.main.core.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.mome.main.R;
import com.mome.main.business.access.ResultListener;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.Tools.CallBack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

@LayoutInject(layout = R.layout.webview)
public class MyWebView extends Activity {
	@ViewInject(id = R.id.webview)
	private WebView mWebView;
@OnClick(id=R.id.titlebar_left)
	public void back(View view){
	if(mWebView.canGoBack())
		mWebView.goBack();
	else
		finish();
}
	
	
	public static ResultListener myListener;

	public static void setResultListener(ResultListener listener) {
		myListener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		InjectProcessor.activityInject(this);
		mWebView.loadUrl("https://www.douban.com/service/auth2"
				+ "/auth?redirect_uri="
				+ AppConfig.WEIBO_REDIRECT_URL
				+ "&scope=shuo_ba"
				+ "sic_r,shuo_basic_w,douban_basic_common&response_type=code&client_id="
				+ AppConfig.DOUBAN_APP_KEY);

		init();
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void init() {
		// 启用支持javascript
		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				if (url.contains(AppConfig.WEIBO_REDIRECT_URL)) {
					if (url.contains("code=")) {
						getAcessToken(url.split("=")[1]);
						return true;
					} else {
						finish();
					}

				}

				return false;
			}

		});
	}

	private void getAcessToken(String code) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("client_id", AppConfig.DOUBAN_APP_KEY);
		map.put("client_secret", AppConfig.DOUBAN_APP_SECRET);
		map.put("redirect_uri", AppConfig.WEIBO_REDIRECT_URL);
		map.put("grant_type", "authorization_code");
		map.put("code", code);

		UploadUtil.httpUtil("https://www.douban.com/service/auth2/token", map,
				"POST", new CallBack() {

					@Override
					public void Back(Object params) {
						// TODO Auto-generated method stub
						Log.e("=============", params.toString());
						if (!TextUtils.isEmpty((String) params)) {
							JSONObject json;
							try {

								json = new JSONObject(params.toString());
								String accessToken = json
										.getString("access_token");
								// UploadUtil.accesstoken=accessToken;
								String openid = json
										.getString("douban_user_id");
								getUserInfo(openid);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else {
							myListener.error("登录失败");
							finish();
						}
					}
				});
	}

	private void getUserInfo(String openid) {
		Map<String, String> map = new HashMap<String, String>();

		UploadUtil.httpUtil("https://api.douban.com/v2/user/" + openid, map,
				"GET", new CallBack() {

					@Override
					public void Back(Object params) {
						// TODO Auto-generated method stub
						if (!TextUtils.isEmpty(params.toString())) {
							myListener.sucess(params);
							
						} else {
							myListener.error("登录失败");
						}
						finish();
						Log.e("=============", params.toString());

					}
				});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {  
        case KeyEvent.KEYCODE_BACK:  
        	if(mWebView.canGoBack())
        		mWebView.goBack();
        	else
        		finish();
        
        	break;
        	}
		return true;
	}

}
