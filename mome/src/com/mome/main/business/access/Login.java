package com.mome.main.business.access;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.mome.main.R;
import com.mome.main.business.TabManager;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.utils.Tools;

/**
 * 登录页面
 *
 */
@LayoutInject(layout = R.layout.login)
public class Login extends BaseFragment {
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	/**
	 * 新浪微博登录
	 * @param view
	 */
	@OnClick(id = R.id.login_sina_btn)
	public void sinaClick(View view) {
		WeiboLogin.getInstance().login(new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what == 0){
					Tools.replaceCurScreen(TabManager.class, null);
				}
			}
		});
	}
	
	/**
	 * 微信登录
	 * @param view
	 */
	@OnClick(id = R.id.login_weixin_btn)
	public void weixinClick(View view) {
		Tools.replaceCurScreen(TabManager.class, null);
	}
	
	/**
	 * 豆瓣登录
	 * @param view
	 */
	@OnClick(id = R.id.login_douban_btn)
	public void doubanClick(View view) {
		
	}
}
