package com.mome.main.business.discovery;

import android.os.Bundle;

import com.mome.main.R;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;

@LayoutInject(layout = R.layout.findfriend)
public class FindFriend extends BaseFragment {

	/**
	 * 微博好友
	 */
	@OnClick(id = R.id.findfriend_weibo)
	public void weiboFriendClick() {
		
	}
	
	/**
	 * 微信好友
	 */
	@OnClick(id = R.id.findfriend_weixin)
	public void weixinFriendClick() {
		
	}
	
	/**
	 * 通讯录好友
	 */
	@OnClick(id = R.id.findfriend_address_book)
	public void addressFriendClick() {
		
	}
	

	/**
	 * 新加入好友
	 */
	@OnClick(id = R.id.findfriend_new_add)
	public void newFriendClick() {
		
	};
	
	/**
	 * mome推荐好友
	 */
	@OnClick(id = R.id.findfriend_mome)
	public void momeFriendClick() {
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	
}
