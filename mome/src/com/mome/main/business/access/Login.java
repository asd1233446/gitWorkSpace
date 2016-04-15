package com.mome.main.business.access;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.jessieray.api.model.UserInfo;
import com.mome.main.R;
import com.mome.main.business.TabManager;
import com.mome.main.business.record.MovieComment;
import com.mome.main.business.userinfo.UserSex;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.utils.Tools;
import com.mome.main.wxapi.WXEntryActivity;

/**
 * 登录页面
 *
 */
@LayoutInject(layout = R.layout.login)
public class Login extends BaseFragment implements LoginInterface{
public static 	 WeiboLogin sinaLogin;
	
	
	/**
	 * 新浪微博登录
	 * @param view
	 */
	@OnClick(id = R.id.login_sina_btn)
	public void sinaClick(View view) {
       sinaLogin=WeiboLogin.getInstance(getActivity());
      sinaLogin.setLoginInterface(this);
      sinaLogin.login();
	}
	
	/**
	 * 微信登录
	 * @param view
	 */
	@OnClick(id = R.id.login_weixin_btn)
	public void weixinClick(View view) {
//		WXLogin.getInstance(getActivity()).WXLoginRequest();
//		WXEntryActivity.setLoginInterface(this);
		Tools.replaceCurScreen(TabManager.class, null);
	}
	
	/**
	 * 豆瓣登录
	 * @param view
	 */
	@OnClick(id = R.id.login_douban_btn)
	public void doubanClick(View view) {
		Tools.pushScreen(TabManager.class, null);
	}

	@Override
	public void sucess(Object object) {
		// TODO Auto-generated method stub
		Tools.replaceCurScreen(TabManager.class, null);
	}

	@Override
	public void error(String error) {
		// TODO Auto-generated method stub
		
	}
}
