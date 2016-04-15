package com.mome.main.business.record;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.jessieray.api.model.CinemaInfo;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

@LayoutInject(layout = R.layout.recod_movie_list)
public class MovieList extends BaseFragment implements OnItemClickListener{
	@ViewInject(id=R.id.search_edit)
	private EditText search_edit;

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
	
	private CinemaInfo mCinemaInfo;
	private Bundle bundle;
		
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	    bundle=getArguments();
		mCinemaInfo=(CinemaInfo) (bundle!=null?bundle.getSerializable("cinemaInfo"):new CinemaInfo());
		initView();
	}

	private void initView() {
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
						UserFavoriteRequest.findUserFavorite(UserProperty
								.getInstance().getUid(), curPageIndex,
								AppConfig.PAGE_SIZE, MovieList.this);
						mPullRefreshListView.setMode(Mode.BOTH);
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
						if (curPageIndex < totalPage) {
							curPageIndex++;
							UserFavoriteRequest.findUserFavorite(UserProperty
									.getInstance().getUid(), curPageIndex,
									AppConfig.PAGE_SIZE, MovieList.this);
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
		listView.setOnItemClickListener(this);

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


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		MovieListCell cell=(MovieListCell) adapter.getItem(position+1);
		
		MovieInfo info=cell.getMovieInfo();
		Tools.toastShow(bundle.getSerializable("cinemaInfo")+"");
		if(bundle!=null){
		bundle.putSerializable("movieInfo", info);
		Tools.pushScreen(AddRecord.class, bundle);
	}
	}
}
