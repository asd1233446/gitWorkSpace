package com.mome.main.business.userinfo;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jessieray.api.model.BindLogin;
import com.jessieray.api.model.UserInfo;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.BindThridpartRequest;
import com.mome.main.R;
import com.mome.main.business.TabManager;
import com.mome.main.business.access.ResultListener;
import com.mome.main.business.access.WXLogin;
import com.mome.main.business.access.WeiboLogin;
import com.mome.main.business.model.UserProperty;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.datacache.DataSaveManager;
import com.mome.main.core.utils.MyWebView;
import com.mome.main.core.utils.Tools;
import com.mome.main.wxapi.WXEntryActivity;

@LayoutInject(layout = R.layout.activity_setting)
public class Set extends Activity implements ResultListener {
	private WeiboLogin weibo;

	private int loginType = 1;// 1 新浪，2，微信，3豆瓣

	/**
	 * 返回
	 * */
	@OnClick(id = R.id.titlebar_left)
	public void backClick(View view) {
		this.finish();
	}

	/*
	 * 新浪绑定
	 */
	@ViewInject(id = R.id.sinaBind_bt)
	private Button sinaBind_bt;
	/*
	 * 微信绑定
	 */
	@ViewInject(id = R.id.wechatBind_bt)
	private Button webchatBind_bt;
	/*
	 * 豆瓣绑定
	 */
	@ViewInject(id = R.id.doubanBind_bt)
	private Button doubanBind_bt;
	/*
	 * 手机绑定
	 */
	@ViewInject(id = R.id.bangding_tv)
	private TextView bangding_tv;
	@ViewInject(id = R.id.bindPhone_tv)
	private TextView bindPhone_tv;
	@ViewInject(id = R.id.bindPhone_bt)
	private Button bindPhone_bt;

	/*
	 * 意见反馈
	 */
	@ViewInject(id = R.id.opinion_rl)
	private RelativeLayout opinion_rl;
	/*
	 * 清除缓存
	 */
	@ViewInject(id = R.id.clearCache_tv)
	private TextView clearCache_tv;

	@OnClick(id = R.id.clearCache_tv)
	public void clearCacheClick(View view) {
		DataSaveManager.getInstance().cleanApplicationData(this);
		clearCache_tv.setText("0");
	}

	@OnClick(id = R.id.sinaBind_bt)
	public void onSinaClick(View view) {
		loginType = 1;
		weibo = new WeiboLogin(this);
		weibo.query(0, null, this);

	}

	@OnClick(id = R.id.wechatBind_bt)
	public void onWebChatClick(View view) {
		loginType = 2;
		WXLogin.getInstance(this).WXLoginRequest();
		WXEntryActivity.setResultListener(this);

	}

	@OnClick(id = R.id.doubanBind_bt)
	public void onDoubanClick(View view) {
		loginType = 3;
		MyWebView.setResultListener(this);
		Intent intent = new Intent(this, MyWebView.class);
		startActivity(intent);
	}

	@OnClick(id = R.id.bindPhone_bt)
	public void onPhoneClick(View view) {

	}

	@OnClick(id = R.id.opinion_rl)
	public void onOpinionClick(View view) {
		Intent intent=new Intent(Set.this,OpinionSet.class);
		startActivity(intent);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		InjectProcessor.activityInject(this);
		init();
	}

	private void init() {
		try {
			clearCache_tv.setText(DataSaveManager.getInstance()
					.getTotalCacheSize(this));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
							if(loginType==1){
							sinaBind_bt.setText("已绑定");
							sinaBind_bt.setBackgroundResource(R.drawable.button_corner_gary);
							sinaBind_bt.setEnabled(false);
							}else if(loginType==2){
								webchatBind_bt.setText("已绑定");
								webchatBind_bt.setBackgroundResource(R.drawable.button_corner_gary);
								webchatBind_bt.setEnabled(false);	
							}else if(loginType==3){
								doubanBind_bt.setText("已绑定");
								doubanBind_bt.setBackgroundResource(R.drawable.button_corner_gary);
								doubanBind_bt.setEnabled(false);	
							}
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

					}
				});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (weibo.getSsoHandler() != null) {
			weibo.getSsoHandler().authorizeCallBack(requestCode, resultCode,
					data);
		}
	}
}
