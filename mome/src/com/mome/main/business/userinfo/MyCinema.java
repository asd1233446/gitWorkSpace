package com.mome.main.business.userinfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.jessieray.api.request.base.ResponseResult;
import com.mome.main.R;
import com.mome.main.business.module.ExpandListAdapter;
import com.mome.main.business.module.ExpandListCellBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener2;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshExpandableListView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;

import android.os.Bundle;
import android.widget.ExpandableListView;

@LayoutInject(layout = R.layout.mycineama)
public class MyCinema extends BaseFragment {
	
	@ViewInject(id = R.id.pull_refresh_scrollview)
	private PullToRefreshExpandableListView mPullRefreshListView;
	private ExpandListAdapter adapter;
	private ExpandableListView listView;
	private List<List<ExpandListCellBase>> listData = new ArrayList<List<ExpandListCellBase>>();
	private List<ExpandListCellBase> groupData = new ArrayList<ExpandListCellBase>();
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}
	
	private void initView() {
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ExpandableListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
			}
		});
		listView = mPullRefreshListView.getRefreshableView();
		adapter = new ExpandListAdapter();
		adapter.setDataList(listData);
		adapter.setGroupDataList(groupData);
		listView.setAdapter(adapter);
		listView.setGroupIndicator(null);
		listView.expandGroup(0);
	}
	
	@Override
	public <T> void sucess(Type arg0, ResponseResult<T> arg1) {
	}
}
