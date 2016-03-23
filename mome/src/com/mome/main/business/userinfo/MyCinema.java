package com.mome.main.business.userinfo;



import java.lang.reflect.Type;
import java.util.ArrayList;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ListView;
import android.widget.Toast;

import com.jessieray.api.model.CinemaInfo;
import com.jessieray.api.model.UserCinemas;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.UserCinemasRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;


@LayoutInject(layout = R.layout.mycineama)
public class MyCinema extends BaseFragment {
	
	@ViewInject(id = R.id.pull_refresh_list)
	private PullToRefreshListView mPullRefreshListView;
	private ArrayList<ListCellBase> CinemaListData = new ArrayList<ListCellBase>();
	/**
	 * 列表数据容器
	 */
	private ListAdapter adapter;
	/**
	 * 当前页索引
	 */
	private int curPageIndex = 0;

	/**
	 * 第一页
	 */
	private int firstPage = 1;

	/**
	 * listView实例
	 */
	private ListView listView;
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setUpListView();
	}
	/**
	 * 获取动态信息
	 * */
	private void setUpListView() {
		
		mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView
				.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								AppConfig.context, System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						// 下拉刷新，，只刷新服务器最新的
						getCinemaDatas(1);

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								AppConfig.context, System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						getCinemaDatas(curPageIndex);

					}
				});
		
		adapter = new ListAdapter();
		listView = mPullRefreshListView.getRefreshableView();
		adapter.setDataList(CinemaListData);
		listView.setAdapter(adapter);
		getCinemaDatas(1);
	}
	
	
	private void getCinemaDatas(int curPageIndex){
		mPullRefreshListView.onRefreshComplete();
		UserCinemasRequest.findUserCinemas(UserProperty.getInstance().getUid(), curPageIndex, AppConfig.PAGE_SIZE, new ResponseCallback() {
			
			@Override
			public <T> void sucess(Type type, ResponseResult<T> claszz) {
				// TODO Auto-generated method stub
			  if(claszz.getCode()==AppConfig.REQUEST_CODE_SUCCESS&&claszz.getModel()!=null){
				  UserCinemas  userCinemas=claszz.getModel();
				  for(CinemaInfo cinema:userCinemas.getCinemas()){
					  MyCinemaListCell myCinemaListCell=new MyCinemaListCell();
					  myCinemaListCell.setCinemaInfo(cinema);
					  CinemaListData.add(myCinemaListCell);
					  
				  }
					adapter.notifyDataSetChanged();
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
				mPullRefreshListView.onRefreshComplete();
				Tools.toastShow(error.getMessage());
			}
		});
	}

	
}
