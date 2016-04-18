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
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.userinfo.FriendsExpandablelistAdapter;
import com.mome.main.business.widget.coverflow.FancyCoverFlow;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshExpandableListView;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
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
public class MovieMemoirs extends BaseFragment implements OnItemClickListener {

	/** 回忆录列表 */
	@ViewInject(id = R.id.expandableList)
	private PullToRefreshListView myFriendsView;

	/** 回忆录列表对应的Adapter **/
	private ListAdapter friendAdapter;

	private ArrayList<ListCellBase> dataList = new ArrayList<ListCellBase>();
	private ListView listview;

	private String year = "2015";
	
	@ViewInject(id=R.id.year)
	private Spinner spinner;
    private ArrayAdapter<String>spinnerAdapter;
    private List<String> list=new ArrayList<String>();
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setUpListView();
		initSpinner();
	}
	
	private void initSpinner(){
		spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		spinnerAdapter.addAll(list);
		spinner.setAdapter(spinnerAdapter);
	}

	private void setUpListView() {
		friendAdapter = new com.mome.main.business.module.ListAdapter();
		friendAdapter.setDataList(dataList);
		listview = myFriendsView.getRefreshableView();
		listview.setAdapter(friendAdapter);
		listview.setOnItemClickListener(this);
		Tools.setRereshing(myFriendsView);

		myFriendsView
				.setOnRefreshListener(new PullToRefreshExpandableListView.OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
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
							PullToRefreshBase<ListView> refreshView) {
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
		if (list.size() > 0) {
			
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}


}
