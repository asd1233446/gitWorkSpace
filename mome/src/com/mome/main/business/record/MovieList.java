package com.mome.main.business.record;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.jessieray.api.model.CinemaInfo;
import com.jessieray.api.model.GetCircleMovieList;
import com.jessieray.api.model.MovieInfo;
import com.jessieray.api.model.UserFavorite;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.GetCircleMovieListRequest;
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
import com.mome.main.core.annotation.OnClick;
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
	@OnClick(id = R.id.search_edit)
	public void onSearchClick(View view) {
		Bundle bundle=getArguments();
		Tools.pushScreen(MovieSearch.class, bundle);
	}

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
	
		
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
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
		listView.setOnItemClickListener(this);

	}
	
	
	private void getMovieList(){
		GetCircleMovieListRequest.findGetCircleMovieList(UserProperty.getInstance().getUid()+"", "2", AppConfig.PAGE_SIZE, curPageIndex, MovieList.this);

	}
	
	

	private void updateView(GetCircleMovieList userFavorite) {
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
			MyMovieListCell movieListCell = new MyMovieListCell();
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
		            if(data!=null&&data.getModel()!=null){
		            	GetCircleMovieList movieList=data.getModel();
		            	updateView(movieList);
		            }

	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		MyMovieListCell cell=(MyMovieListCell) adapter.getItem(position-1);
		
		MovieInfo info=cell.getMovieInfo();
         Bundle bundle=getArguments();
		bundle.putSerializable("movieInfo", info);
		Tools.pushScreen(AddRecord.class, bundle);
	
	}
}
