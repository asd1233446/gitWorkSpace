package com.mome.main.business.movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.hp.hpl.sparta.Text;
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
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshExpandableListView;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
import com.mome.main.business.widget.timeshaft.CenteringHorizontalScrollView;
import com.mome.main.business.widget.timeshaft.CustomTimeShaft.ItemSelectedListener;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.mome.view.DropEditText;
import com.mome.view.DropEditText.OnItemSelectListener;

/**
 * 电影回忆录
 * 
 */
@LayoutInject(layout = R.layout.movie_memoirs)
public class MovieMemoirs extends BaseFragment implements OnItemClickListener,
		OnItemSelectedListener {

	/** 回忆录列表 */
	@ViewInject(id = R.id.expandableList)
	private PullToRefreshListView myFriendsView;
	@ViewInject(id = R.id.looked_num)
	private TextView looked_num;

	/** 回忆录列表对应的Adapter **/
	private ListAdapter friendAdapter;

	private ArrayList<ListCellBase> dataList = new ArrayList<ListCellBase>();
	private ListView listview;
	private String year ="";
	@ViewInject(id = R.id.year)
	private Spinner spinner;
	private ArrayAdapter<String> spinnerAdapter;
	private List<String> spinnerlist = new ArrayList<String>();
	private List<Year> yearList;
	
	private boolean isFrist=true;
	/**
	 * 当前页索引
	 */
	private int curPageIndex = 1;

	/**
	 * 一共多少页数
	 */
	private double totalPage = 1;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setUpListView();
		initSpinner();
	}

	private void initSpinner() {
		spinnerAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_dropdown_item_1line, spinnerlist);
		
		spinner.setAdapter(spinnerAdapter);
		spinner.setOnItemSelectedListener(this);
	}

	private void setUpListView() {
		friendAdapter = new com.mome.main.business.module.ListAdapter();
		friendAdapter.setDataList(dataList);
		listview = myFriendsView.getRefreshableView();
		listview.setAdapter(friendAdapter);
		listview.setOnItemClickListener(this);
		Tools.setRereshing(myFriendsView);
		myFriendsView.setMode(Mode.PULL_FROM_START);

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
						curPageIndex = 1;
						getUserRecall(curPageIndex, year);
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
						getUserRecall(curPageIndex, year);
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
		yearList = userRecall.getYearcounts();
		if (yearList != null && yearList.size() > 0) {
			initYear(yearList);
			//if(userRecall.getRecalls().size()>0){
			initMemoris(userRecall);
			//myFriendsView.removeView(Tools.setEmptyView(getActivity()));
			//}
		//	else
			//	myFriendsView.setEmptyView(Tools.setEmptyView(getActivity()));
		} 
//		else {
//			myFriendsView.setEmptyView(Tools.setEmptyView(getActivity()));
//		}


	}

	@Override
	public void error(ResponseError arg0) {
		// TODO Auto-generated method stub
		myFriendsView.onRefreshComplete();
		super.error(arg0);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
          MovieMemoirsAdapter memoirsAdapter=(MovieMemoirsAdapter) parent.getItemAtPosition(position);
          Bundle bundle=new Bundle();
          bundle.putSerializable("addOrUpdate", 2);
          bundle.putSerializable("memoirsInfo", memoirsAdapter.getMemoirsChild());
          Tools.pushScreen(AddMovieMemoir.class, bundle);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
			
			if(!isFrist){
				year = (String) parent.getItemAtPosition(position);
			myFriendsView.setRefreshing();
			}else{
				year="";
				isFrist=false;	
			}
	}

	
	private void initYear(List<Year>yearList){
		spinnerlist.clear();
		for (Year year : yearList) {
			spinnerlist.add(year.getYear());
		}
		spinnerAdapter.notifyDataSetChanged();
		if(year.equals(""))
			looked_num.setText(yearList.get(0).getYeartotal());
		else
		looked_num.setText(spinnerlist.indexOf(year)>=0?yearList.get(spinnerlist.indexOf(year)).getYeartotal():"0");
		totalPage=Tools.calculateTotalPage( Integer.valueOf(looked_num.getText().toString()));
		Tools.toastShow(totalPage+"");
	}
	
   private void initMemoris(UserRecall userRecall){
		if (curPageIndex == 1) {
			   dataList.clear();
		}
		if (totalPage > curPageIndex) {
			myFriendsView.setMode(Mode.BOTH);
			curPageIndex++;
		} else {
			myFriendsView.setMode(Mode.PULL_FROM_START);
		}
		for (MemoirsInfo info : userRecall.getRecalls()) {
			MovieMemoirsAdapter adapter = new MovieMemoirsAdapter();
			adapter.setMemoirsChild(info);
			dataList.add(adapter);
		}
		friendAdapter.notifyDataSetChanged();
   }

@Override
public void onNothingSelected(AdapterView<?> parent) {
	// TODO Auto-generated method stub
	
}

}
