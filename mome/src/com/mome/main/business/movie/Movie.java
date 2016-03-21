package com.mome.main.business.movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

@LayoutInject(layout = R.layout.movie)
public class Movie extends BaseFragment {

	/**
	 * 热门影片按钮
	 */
	@ViewInject(id = R.id.movie_btn_hot)
	private TextView hotBtn;
	/**
	 * 朋友圈影片按钮
	 */
	@ViewInject(id = R.id.movie_btn_friend)
	private TextView friendBtn;
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
	 * 列表数据
	 */
	private ArrayList<ListCellBase> hotListData = new ArrayList<ListCellBase>();
	private ArrayList<ListCellBase> friendListData = new ArrayList<ListCellBase>();
	/**
	 * 按钮的索引
	 */
	private final int BTN_LEFT = 0;
	private final int BTN_RIGHT = 1;
	/**
	 * 当前tab索引
	 */
	private int curIndex = 0;
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
		mPullRefreshListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(AppConfig.context, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//				MovieRequest.getHotMovies(UserProperty.getInstance().getUid(),0,AppConfig.PAGE_SIZE,curIndex, Movie.this);
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
//					MovieRequest.getHotMovies(UserProperty.getInstance().getUid(),curPageIndex,AppConfig.PAGE_SIZE,curIndex, Movie.this);
					mPullRefreshListView.setMode(Mode.BOTH);
				} else {
					mPullRefreshListView.setMode(Mode.PULL_FROM_START);
				}
			}
		});
		mPullRefreshListView.setMode(Mode.BOTH);
		listView = mPullRefreshListView.getRefreshableView();
		adapter = new ListAdapter();
		adapter.setDataList(hotListData);
		listView.setAdapter(adapter);
		hotClick(null);
	}
	
	@OnClick(id = R.id.movie_btn_hot)
	public void hotClick(View view) {
		updateBtn(BTN_LEFT);
		curIndex = BTN_LEFT;
		adapter.setDataList(hotListData);
		if(hotListData.isEmpty()) {
			mPullRefreshListView.autoRefresh();
//			MovieRequest.getHotMovies(UserProperty.getInstance().getUid(),0,AppConfig.PAGE_SIZE,curIndex, Movie.this);
		}
	}
	
	@OnClick(id = R.id.movie_btn_friend)
	public void friendClick(View view) {
		updateBtn(BTN_RIGHT);
		curIndex = BTN_RIGHT;
		adapter.setDataList(friendListData);
		if(friendListData.isEmpty()) {
			mPullRefreshListView.autoRefresh();
//			MovieRequest.getHotMovies(UserProperty.getInstance().getUid(),0,AppConfig.PAGE_SIZE,curIndex, Movie.this);
		}
	}
	
	/**
	 * 更新列表数据
	 * @param list
	 */
//	private void addMovieInfo(List<MovieResponseInfo> list) {
//		if(list == null || list.isEmpty()) {
//			return;
//		}
//
//		for(MovieResponseInfo movieInfo : list) {
//			MovieListCell movieListCell = new MovieListCell();
//			movieListCell.setMovieInfo(movieInfo);
//			if(curIndex == BTN_LEFT) {
//				hotListData.add(movieListCell);
//			} else if(curIndex == BTN_RIGHT) {
//				friendListData.add(movieListCell);
//			}
//		}
//		adapter.notifyDataSetChanged();
//	}
	
	@Override
	public void error(ResponseError arg0) {
		Tools.toastShow(arg0.getMessage());
	}

	@Override
	public <T> void sucess(Type type,ResponseResult<T> arg0) {
		mPullRefreshListView.onRefreshComplete();
//		if(arg0.getModel().getClass().equals(MovieResponseInfo.class)) {
//			List<MovieResponseInfo> list = (List<MovieResponseInfo>) arg0.getModelList();
//			totalPage = 1;
//			addMovieInfo(list);
//		}
	}
	
	/**
	 * 恢复按钮状态
	 */
	private void updateBtn(int index) {
		hotBtn.setTextColor(this.getActivity().getResources().getColor(R.color.dynamicTextNormal));
		friendBtn.setTextColor(this.getActivity().getResources().getColor(R.color.dynamicTextNormal));
//		hotBtn.setBackgroundResource(R.drawable.bg_left_round_corner_rect);
//		friendBtn.setBackgroundResource(R.drawable.bg_left_round_corner_rect);
		switch(index) {
		case BTN_LEFT:
			hotBtn.setTextColor(this.getActivity().getResources().getColor(R.color.dynamicTextPressed));
			break;
		case BTN_RIGHT:
			friendBtn.setTextColor(this.getActivity().getResources().getColor(R.color.dynamicTextPressed));
			break;
		}
	}
}
