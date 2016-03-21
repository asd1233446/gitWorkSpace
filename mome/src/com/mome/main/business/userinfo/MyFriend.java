package com.mome.main.business.userinfo;

import android.os.Bundle;
import android.widget.ListView;

import com.mome.main.R;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;

@LayoutInject(layout=R.layout.myfriends)
public class MyFriend extends BaseFragment {
@ViewInject(id=R.id.myFriendsListView)
private ListView myFriendsView;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

}
