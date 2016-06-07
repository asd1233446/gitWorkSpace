package com.mome.main.business.access;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.jessieray.api.model.BindLogin;
import com.jessieray.api.model.UserInfo;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.BindThridpartRequest;
import com.mome.main.R;
import com.mome.main.business.TabManager;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.movie.AddMovieMemoir;
import com.mome.main.business.movie.SelectorPhoto;
import com.mome.main.business.record.MovieComment;
import com.mome.main.business.userinfo.UserSex;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.utils.MyWebView;
import com.mome.main.core.utils.Tools;
import com.mome.main.core.utils.Tools.CallBack;
import com.mome.main.core.utils.UploadUtil;
import com.mome.main.wxapi.WXEntryActivity;

/**
 * 登录页面
 * 
 */
@LayoutInject(layout = R.layout.login)
public class Login extends BaseFragment implements ResultListener {
	public static WeiboLogin sinaLogin;// 新浪微博ssohandler
										// 必须在activity的onActivityResult回调

	private int loginType = 1;// 1 新浪，2，微信，3豆瓣

	/**
	 * 新浪微博登录
	 * 
	 * @param view
	 */
	@OnClick(id = R.id.login_sina_btn)
	public void sinaClick(View view) {
		loginType = 1;
		sinaLogin = new WeiboLogin(getActivity());
		sinaLogin.query(0, null, this);
	}

	/**
	 * 微信登录
	 * 
	 * @param view
	 */
	@OnClick(id = R.id.login_weixin_btn)
	public void weixinClick(View view) {
		loginType = 2;
		WXLogin.getInstance(getActivity()).WXLoginRequest();
		WXEntryActivity.setResultListener(this);
	}

	/**
	 * 豆瓣登录
	 * 
	 * @param view
	 */
	@OnClick(id = R.id.login_douban_btn)
	public void doubanClick(View view) {
		// Tools.replaceCurScreen(TabManager.class, null);
		loginType = 3;
		MyWebView.setResultListener(this);
		Intent intent = new Intent(getActivity(), MyWebView.class);
		startActivity(intent);

	}

	@Override
	public void sucess(Object object) {
		// TODO Auto-generated method stub
		try {
			JSONObject json = new JSONObject((String) object);
			UserInfo info = new UserInfo();
			if (loginType == 1) {
				info.setUserid(json.getString("id"));
				info.setAvatar(json.getString("profile_image_url"));
				info.setNickname(json.getString("screen_name"));
			} else if (loginType == 2) {
				info.setUserid(json.getString("openid"));
				info.setAvatar(json.getString("headimgurl"));
				info.setNickname(json.getString("nickname"));
			}else if(loginType==3){
				info.setUserid(json.getString("uid"));
				info.setAvatar(json.getString("avatar"));
				info.setNickname(json.getString("name"));
			}
			bindLogin(info);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void error(String error) {
		// TODO Auto-generated method stub
		Tools.toastShow(error);

	}

	private void bindLogin(UserInfo userInfo) {

		BindThridpartRequest.findBindThridpart(loginType + "",
				userInfo.getUserid(), userInfo.getNickname(),
				userInfo.getNickname(), userInfo.getAvatar(),
				new ResponseCallback() {

					@Override
					public <T> void sucess(Type type, ResponseResult<T> claszz) {
						// TODO Auto-generated method stub
						if (claszz.getModel() != null) {
							BindLogin info = claszz.getModel();
							UserProperty.getInstance().setUserInfo(
									info.getUserinfo());
							Tools.replaceCurScreen(TabManager.class, null);
						}
					}

					@Override
					public boolean isRefreshNeeded() {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public void error(ResponseError error) {
						// TODO Auto-generated method stub
						Tools.toastShow(error.getMessage());
					}
				});

	}
}
