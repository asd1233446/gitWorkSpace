package com.mome.main.business.discovery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mome.main.R;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.utils.Tools;

@LayoutInject(layout = R.layout.discovery)
public class Discovery extends BaseFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}


	@OnClick(id = R.id.discovery_friend_layout)
	public void friendClick(View view) {
		Tools.pushScreen(FindFriend.class, null);
	}
	
	@OnClick(id = R.id.discovery_instract_layout)
	public void instractClick(View view) {
		Tools.pushScreen(InterestFriend.class, null);
	}
	
	@OnClick(id = R.id.discovery_activity_layout)
	public void activityClick(View view) {
		Tools.toastShow("敬请期待");
	}
}
