package com.mome.main.business.record;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jessieray.api.model.CinemaInfo;
import com.jessieray.api.model.GetCircleMovieList;
import com.jessieray.api.model.GetMovieUserArticleByKeyWord;
import com.jessieray.api.model.MovieInfo;
import com.jessieray.api.model.Moviesearch;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.GetCircleMovieListRequest;
import com.jessieray.api.service.getMovieByKeywordRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.BaseAdapter;
import com.mome.main.business.movie.SelectorPhotoListCell.ViewHolder;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.mome.main.core.utils.Tools.CallBack;
import com.mome.main.core.utils.UploadUtil;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

@SuppressLint("ResourceAsColor")
@LayoutInject(layout = R.layout.movie_search)
public class MovieSearch extends BaseFragment implements OnItemClickListener {
	@ViewInject(id = R.id.searchList)
	private PullToRefreshListView refreshListView;
	private ListView searchList;

	private ArrayList<MovieInfo> mMovieList = new ArrayList<MovieInfo>();
	private MovieSearchAdapter adapter;
	private String queryStr;
	private int curPageIndex = 1;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setUpList();
	}

	public void setUpList() {
		adapter = new MovieSearchAdapter(getActivity(), mMovieList);
		searchList = refreshListView.getRefreshableView();
		searchList.setAdapter(adapter);
		searchList.setOnItemClickListener(this);
		refreshListView.setMode(Mode.PULL_FROM_START);
		refreshListView
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
						getMovieList(queryStr);

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
						getMovieList(queryStr);

					}
				});

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		LayoutInflater infla = LayoutInflater.from(getActivity());
		View footView = infla.inflate(R.layout.douban_item, null);
		searchList.addFooterView(footView, null, true);
		this.headView.titlebar_bg_layout
				.setBackgroundResource(R.color.gary_line);
	}

	@Override
	public void editTextChange(String text) {
		// TODO Auto-generated method stub
		queryStr = text;
		Tools.setRereshing(refreshListView);
	}

	private void getMovieList(String str) {
		getMovieByKeywordRequest.findgetMovieByKeyword(UserProperty
				.getInstance().getUid() + "", queryStr, 1, AppConfig.PAGE_SIZE,
				this);
	}

	@Override
	public <T> void sucess(Type arg0, ResponseResult<T> data) {
		// TODO Auto-generated method stub
		refreshListView.onRefreshComplete();
		if (data != null && data.getModel() != null) {
			GetMovieUserArticleByKeyWord movieList = data.getModel();
			 if (curPageIndex == 1)
			 mMovieList.clear();
			 if (Tools.calculateTotalPage(movieList.getTotoal()) >
			 curPageIndex) {
			 refreshListView.setMode(Mode.BOTH);
			 curPageIndex++;
			 } else {
			 refreshListView.setMode(Mode.PULL_FROM_START);
			 }
			mMovieList.addAll(movieList.getMovies());
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void error(ResponseError arg0) {
		// TODO Auto-generated method stub
		refreshListView.onRefreshComplete();
		Tools.toastShow(arg0.getMessage());

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Bundle bundle = getArguments();
		Log.e("============", position+"");
		if (position == mMovieList.size()+2) {

			bundle.putString("queryStr", queryStr);
			Tools.pushScreen(DouMovieSearch.class, bundle);
			return;
		}
		bundle.putSerializable("movieInfo",
				(Serializable) parent.getItemAtPosition(position));
		Tools.pushScreen(AddRecord.class, bundle);

	}

}
