package com.mome.main.business.discovery;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.mome.main.R;
import com.mome.main.business.access.ResultListener;
import com.mome.main.business.access.WXLogin;
import com.mome.main.business.access.WeiboLogin;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.utils.Tools;

@LayoutInject(layout = R.layout.findfriend)
public class FindFriend extends BaseFragment  {

	/**
	 * 微博好友
	 */
	@OnClick(id = R.id.findfriend_weibo)
	public void weiboFriendClick(View view) {
		Intent intent=new Intent(getActivity(),FriendsList.class);
		intent.putExtra("type", 0);
		startActivity(intent);
		
	}
	
	/**
	 * 微信好友
	 */
	@OnClick(id = R.id.findfriend_weixin)
	public void weixinFriendClick(View view) {
		WXLogin wxlogin=WXLogin.getInstance(getActivity());
		wxlogin.sendText(getResources().getString(R.string.inviteMessage));
	}
	
	/**
	 * 通讯录好友
	 */
	@OnClick(id = R.id.findfriend_address_book)
	public void addressFriendClick(View view) {
		Intent intent=new Intent(getActivity(),FriendsList.class);
		intent.putExtra("type", 1);
		startActivity(intent);
		
	}
	

	/**
	 * 新加入好友
	 */
	@OnClick(id = R.id.findfriend_new_add)
	public void newFriendClick(View view) {
		
	};
	
	/**
	 * mome推荐好友
	 */
	@OnClick(id = R.id.findfriend_mome)
	public void momeFriendClick(View view) {
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	
	
	
}
