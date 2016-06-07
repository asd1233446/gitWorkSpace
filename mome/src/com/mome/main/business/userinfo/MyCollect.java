package com.mome.main.business.userinfo;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.jessieray.api.model.MovieInfo;
import com.jessieray.api.model.UserFavorite;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.GetCircleMovieListRequest;
import com.jessieray.api.service.RecallStatisticalRequest;
import com.jessieray.api.service.UserFavoriteRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.movie.MovieListCell;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

@LayoutInject(layout = R.layout.movie)
public class MyCollect extends BaseFragment {

	/**
	 * 按钮布局
	 */
	@ViewInject(id = R.id.movie_btn_up_line)
	private LinearLayout upLine;
	@ViewInject(id = R.id.movie_btn_down_line)
	private LinearLayout downLine;
	@ViewInject(id = R.id.movie_btn_layout)
	private LinearLayout btnLayout;
	/**
	 * listView实例
	 */
	private ListView listView;
	/**
	 * 下拉刷新组件
	 */
	@ViewInject(id = R.id.pull_refresh_list)
	private PullToRefreshListView mPullRefreshListView;
	/**
	 * 列表数据容器
	 */
	private ListAdapter adapter;
	/**
	 * 电影数据
	 */
	private ArrayList<ListCellBase> movieListData = new ArrayList<ListCellBase>();
	/**
	 * 当前页索引
	 */
	private int curPageIndex = 1;
	/**
	 * 总页数
	 */
	private int totalPage = 0;
	
	
	private  String userId;
	
	private Bundle bundle;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		 bundle=getArguments();
		userId=bundle!=null?bundle.getString("userId"):UserProperty.getInstance().getUid();
		initView();
	}

	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		this.headView.setLeftBtnShow(View.VISIBLE);
		this.headView.setRightBtnShow(View.GONE);
		this.headView.setTitle(bundle==null?"我的收藏":"Ta的收藏");
	
	}
	
	private void initView() {
		upLine.setVisibility(View.GONE);
		btnLayout.setVisibility(View.GONE);
		downLine.setVisibility(View.GONE);
		Tools.setRereshing(mPullRefreshListView);
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

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						curPageIndex = 1;
						getMovieList();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								AppConfig.context, System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						
						getMovieList();
					
					}
				});
		mPullRefreshListView.setMode(Mode.PULL_FROM_START);
		listView = mPullRefreshListView.getRefreshableView();
		adapter = new ListAdapter();
		adapter.setDataList(movieListData);
		listView.setAdapter(adapter);
	}

	private void updateView(UserFavorite userFavorite) {
		totalPage=(int) Tools.calculateTotalPage(userFavorite.getTotal());
		if (userFavorite.getMovies() == null
				|| userFavorite.getMovies().isEmpty()) {
			return;
		}
		if (curPageIndex == 1) {
			movieListData.clear();
		}
		if (totalPage > curPageIndex){
			mPullRefreshListView.setMode(Mode.BOTH);
		    curPageIndex++;
		}
		else {
			mPullRefreshListView.setMode(Mode.PULL_FROM_START);
		}		
		for (MovieInfo movieInfo : userFavorite.getMovies()) {
			MovieListCell movieListCell = new MovieListCell();
			movieListCell.setMovieInfo(movieInfo);
			movieListData.add(movieListCell);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void error(ResponseError arg0) {
		mPullRefreshListView.onRefreshComplete();
		Tools.toastShow(arg0.getMessage());
	}

	@Override
	public <T> void sucess(Type type, ResponseResult<T> data) {
		mPullRefreshListView.onRefreshComplete();
		if (data.getCode() == AppConfig.REQUEST_CODE_SUCCESS
				&& data.getModel() != null) {
			if (UserFavoriteRequest.resultType.equals(type)) {
				UserFavorite userFavorite = data.getModel();
				if (userFavorite != null) {
					updateView(userFavorite);
				}
			}
		}
	}
	
	private void getMovieList(){
		
		UserFavoriteRequest.findUserFavorite(UserProperty
				.getInstance().getUid(), curPageIndex,
				AppConfig.PAGE_SIZE, MyCollect.this);
	
}
}
