package com.mome.main.business.userinfo;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.jessieray.api.model.MovieInfo;
import com.jessieray.api.model.UserFavorite;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
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
	private int curPageIndex = 0;
	/**
	 * 总页数
	 */
	private int totalPage = 0;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		UserFavoriteRequest.findUserFavorite(UserProperty.getInstance().getUid(), curPageIndex, AppConfig.PAGE_SIZE, this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		this.headView.setRightBtnShow(View.GONE);
		this.headView.setTitle("我的收藏");
	}
	
	private void initView() {
		upLine.setVisibility(View.GONE);
		btnLayout.setVisibility(View.GONE);
		downLine.setVisibility(View.GONE);
		mPullRefreshListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(AppConfig.context, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				curPageIndex = 1;
				UserFavoriteRequest.findUserFavorite(UserProperty.getInstance().getUid(), curPageIndex, AppConfig.PAGE_SIZE, MyCollect.this);
				mPullRefreshListView.setMode(Mode.BOTH);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(AppConfig.context, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				if(curPageIndex < totalPage) {
					curPageIndex++;
					UserFavoriteRequest.findUserFavorite(UserProperty.getInstance().getUid(), curPageIndex, AppConfig.PAGE_SIZE, MyCollect.this);
					mPullRefreshListView.setMode(Mode.BOTH);
				} else {
					mPullRefreshListView.setMode(Mode.PULL_FROM_START);
				}
			}
		});
		mPullRefreshListView.setMode(Mode.BOTH);
		listView = mPullRefreshListView.getRefreshableView();
		adapter = new ListAdapter();
		adapter.setDataList(movieListData);
		listView.setAdapter(adapter);
	}
	
	private void updateView(UserFavorite userFavorite) {
		totalPage = userFavorite.getTotal();
		if(userFavorite.getMovies() == null || userFavorite.getMovies().isEmpty()) {
			return;
		}
		if(curPageIndex == 1) {
			movieListData.clear();
		}
		for(MovieInfo movieInfo : userFavorite.getMovies()) {
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
		if(data.getCode() == AppConfig.REQUEST_CODE_SUCCESS) {
			if(UserFavoriteRequest.resultType.equals(type)) {
				UserFavorite userFavorite = data.getModel();
				if(userFavorite != null) {
					updateView(userFavorite);
				}
			}
		}
	}
}
