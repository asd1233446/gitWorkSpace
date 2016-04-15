package com.mome.main.business.movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jessieray.api.model.Friend;
import com.jessieray.api.model.MemoirsInfo;
import com.jessieray.api.model.UserRecall;
import com.jessieray.api.model.Year;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.UserRecallRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ExpandListAdapter;
import com.mome.main.business.module.ExpandListCellBase;
import com.mome.main.business.userinfo.FriendsExpandablelistAdapter;
import com.mome.main.business.widget.coverflow.FancyCoverFlow;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshExpandableListView;
import com.mome.main.business.widget.timeshaft.CenteringHorizontalScrollView;
import com.mome.main.business.widget.timeshaft.CustomTimeShaft.ItemSelectedListener;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;

/**
 * 电影回忆录
 * 
 */
@LayoutInject(layout = R.layout.movie_memoirs)
public class MovieMemoirs extends BaseFragment implements OnChildClickListener,
		OnGroupClickListener {

	/** 回忆录列表 */
	@ViewInject(id = R.id.expandableList)
	private PullToRefreshExpandableListView myFriendsView;

	/** 回忆录列表对应的Adapter **/
	private ExpandListAdapter friendAdapter;

	/** 分组集合 */
	private ArrayList<ExpandListCellBase> groupList = new ArrayList<ExpandListCellBase>();

	/** 分组下集合 */
	private ArrayList<List<ExpandListCellBase>> dataList = new ArrayList<List<ExpandListCellBase>>();
	private ArrayList<ExpandListCellBase> childList = new ArrayList<ExpandListCellBase>();
	private ExpandableListView listview;

	private String year = "2015";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setUpListView();
	}

	private void setUpListView() {
		friendAdapter = new ExpandListAdapter();
		friendAdapter.setDataList(dataList);
		friendAdapter.setGroupDataList(groupList);
		listview = myFriendsView.getRefreshableView();
		listview.setAdapter(friendAdapter);
		listview.setOnChildClickListener(this);
		listview.setOnGroupClickListener(this);
		Tools.setRereshing(myFriendsView);

		myFriendsView
				.setOnRefreshListener(new PullToRefreshExpandableListView.OnRefreshListener2<ExpandableListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ExpandableListView> refreshView) {
						// TODO Auto-generated method stub
						String label = DateUtils.formatDateTime(
								AppConfig.context, System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						getUserRecall(1, year);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ExpandableListView> refreshView) {
						// TODO Auto-generated method stub
						String label = DateUtils.formatDateTime(
								AppConfig.context, System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

					}
				});
	}

	private void getUserRecall(int page, String year) {
		UserRecallRequest.findUserRecall(UserProperty.getInstance().getUid(),
				year, page, AppConfig.PAGE_SIZE, this);
	}

	@Override
	public <T> void sucess(Type arg0, ResponseResult<T> arg1) {
		myFriendsView.onRefreshComplete();
		// TODO Auto-generated method stub
		UserRecall userRecall = arg1.getModel();
		List<Year> list = userRecall.getYearcounts();
		MovieMemoirsAdapter adapter;
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				adapter = new MovieMemoirsAdapter();
				adapter.setYear(list.get(i));
				groupList.add(adapter);
				childList = new ArrayList<ExpandListCellBase>();
				if (year.equals(list.get(i).getYear())
						&& userRecall.getRecalls().size() > 0) {
					Log.e("回忆录记录个数", userRecall.getRecalls().size() + "");
					for (MemoirsInfo info : userRecall.getRecalls()) {
						adapter = new MovieMemoirsAdapter();
						adapter.setMemoirsChild(info);
						childList.add(adapter);

					}
					dataList.add(childList);
					listview.expandGroup(i);
					continue;
				}
				Log.e("跳过continue", "continue");
				adapter = new MovieMemoirsAdapter();
				childList.add(adapter);
				dataList.add(childList);
			}
		} else {
			myFriendsView.setEmptyView(Tools.setEmptyView(getActivity()));
		}

		friendAdapter.notifyDataSetChanged();

	}

	@Override
	public void error(ResponseError arg0) {
		// TODO Auto-generated method stub
		super.error(arg0);
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		Tools.toastShow("回忆录详情页面");
		Tools.pushScreen(MovieMemoirDetail.class, null);

		return true;
	}

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		// TODO Auto-generated method stub
		MovieMemoirsAdapter adpter=(MovieMemoirsAdapter) groupList.get(groupPosition);
		Log.e("=====", groupPosition + "");
		getUserRecall(1,adpter.getYear().getYear());
		return false;
	}
}
