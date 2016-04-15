package com.mome.main.business.discovery;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.jessieray.api.model.MayInterestIn;
import com.jessieray.api.model.UserInfo;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.MayInterestInRequest;
import com.jessieray.api.service.UserRecallRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ExpandListAdapter;
import com.mome.main.business.module.ExpandListCellBase;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;

@LayoutInject(layout = R.layout.interest_friend)
public class InterestFriend extends BaseFragment implements
		OnChildClickListener {

	/**
	 * 用户列表
	 */
	@ViewInject(id = R.id.expandableList)
	private ExpandableListView expandableList;
	/** 回忆录列表对应的Adapter **/
	private ExpandListAdapter friendAdapter;

	/** 分组集合 */
	private ArrayList<ExpandListCellBase> groupList = new ArrayList<ExpandListCellBase>();

	/** 分组下集合 */
	private ArrayList<List<ExpandListCellBase>> dataList = new ArrayList<List<ExpandListCellBase>>();
	private ArrayList<ExpandListCellBase> childList = new ArrayList<ExpandListCellBase>();

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
		setUpListview();
	}

	private void setUpListview() {

		friendAdapter = new ExpandListAdapter();
		friendAdapter.setDataList(dataList);
		friendAdapter.setGroupDataList(groupList);
		expandableList.setAdapter(friendAdapter);
		expandableList.expandGroup(0);
		expandableList.setOnChildClickListener(this);
		getUserRecall();
	}

	private void getUserRecall() {
		MayInterestInRequest.findMayInterestIn(UserProperty.getInstance()
				.getUid(), this);
	}

	@Override
	public <T> void sucess(Type arg0, ResponseResult<T> arg1) {
		// TODO Auto-generated method stub
		super.sucess(arg0, arg1);
		MayInterestIn interes = arg1.getModel();
		if (interes.getSame_movie().size() > 0) {
              InterestFriendAdapter  Interest=new InterestFriendAdapter();
              Interest.setFriendType("同场次观影用户");
              groupList.add(Interest);
               for(UserInfo info:interes.getSame_movie()){
                     Interest=new InterestFriendAdapter();
                     Interest.setUserinfo(info);
                     childList.add(Interest);
               }
               dataList.add(childList);
		}
		if (interes.getSame_hobby().size() > 0) {
			  InterestFriendAdapter  Interest=new InterestFriendAdapter();
              Interest.setFriendType("同类型爱好用户");
              groupList.add(Interest);
              childList.clear();
               for(UserInfo info:interes.getSame_movie()){
                     Interest=new InterestFriendAdapter();
                     Interest.setUserinfo(info);
                     childList.add(Interest);
               }
               dataList.add(childList);
		}
		if (interes.getSame_cinema().size() > 0) {
			  InterestFriendAdapter  Interest=new InterestFriendAdapter();
              Interest.setFriendType("常去影院相同用户");
              groupList.add(Interest);
              childList.clear();
               for(UserInfo info:interes.getSame_movie()){
                     Interest=new InterestFriendAdapter();
                     Interest.setUserinfo(info);
                     childList.add(Interest);
               }
               dataList.add(childList);
		}
		friendAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		return false;
	}

}