package com.mome.main.business.movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.jessieray.api.model.GetCircleMovieList;
import com.jessieray.api.model.MovieInfo;
import com.jessieray.api.model.UserFavorite;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.GetCircleMovieListRequest;
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
	private ArrayList<ListCellBase> movieListData = new ArrayList<ListCellBase>();
	/**
	 * 按钮的索引
	 */
	private final int BTN_LEFT = 2;
	private final int BTN_RIGHT = 1;
	/**
	 * 当前tab索引
	 */
	private int curIndex = 0;
	/**
	 * 当前页索引
	 */
	private int curPageIndex = 1;
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
				getMovieList();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(AppConfig.context, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

			}
		});
		mPullRefreshListView.setMode(Mode.PULL_FROM_START);
		listView = mPullRefreshListView.getRefreshableView();
		adapter = new ListAdapter();
		adapter.setDataList(movieListData);
		listView.setAdapter(adapter);
		hotClick(null);
	}
	
	private void getMovieList(){
		
			//2热门影片 1朋友圈
			GetCircleMovieListRequest.findGetCircleMovieList(UserProperty.getInstance().getUid()+"", curIndex+"", AppConfig.PAGE_SIZE, 1, this);
		
	}
	
	
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		this.headView.setLeftBtnShow(View.GONE);
		super.onStart();
	}
	
	@OnClick(id = R.id.movie_btn_hot)
	public void hotClick(View view) {
		updateBtn(BTN_LEFT);
		curIndex = BTN_LEFT;
		Tools.setRereshing(mPullRefreshListView);
		
	}
	
	@OnClick(id = R.id.movie_btn_friend)
	public void friendClick(View view) {
		updateBtn(BTN_RIGHT);
		curIndex = BTN_RIGHT;
		mPullRefreshListView.autoRefresh();
	}
	
	/**
	 * 更新列表数据
	 * @param list
	 */
	private void updateView(GetCircleMovieList userFavorite) {
		totalPage=(int) Tools.calculateTotalPage(userFavorite.getTotal());
		if (userFavorite.getMovies() == null
				|| userFavorite.getMovies().isEmpty()) {
			return;
		}
		if (curPageIndex == 1) {
			movieListData.clear();
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
		Tools.toastShow(arg0.getMessage());
	}

	@Override
	public <T> void sucess(Type type,ResponseResult<T> arg0) {
		mPullRefreshListView.onRefreshComplete();
		GetCircleMovieList circleMovieList=arg0.getModel();
		if(circleMovieList!=null&&circleMovieList.getTotal()>0)
			updateView(circleMovieList);
		else
			movieListData.clear();
		    adapter.notifyDataSetChanged();
		
		
	}
	
	/**
	 * 恢复按钮状态
	 */
	private void updateBtn(int index) {
		hotBtn.setTextColor(this.getActivity().getResources().getColor(R.color.dynamicTextNormal));
		friendBtn.setTextColor(this.getActivity().getResources().getColor(R.color.dynamicTextNormal));
		switch(index) {
		case BTN_LEFT:
			hotBtn.setTextColor(this.getActivity().getResources().getColor(R.color.dynamicTextPressed));
			break;
		case BTN_RIGHT:
			friendBtn.setTextColor(this.getActivity().getResources().getColor(R.color.dynamicTextPressed));
			break;
		}
	}
	
	
	@Override
	public void rightOnClick() {
		// TODO Auto-generated method stub
		super.rightOnClick();
		Tools.pushScreen(MovieSearch.class, null);

	}
}
