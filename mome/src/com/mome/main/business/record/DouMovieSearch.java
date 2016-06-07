package com.mome.main.business.record;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
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
import com.jessieray.api.model.DouMovieInfo;
import com.jessieray.api.model.GetCircleMovieList;
import com.jessieray.api.model.MovieInfo;
import com.jessieray.api.model.Moviesearch;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.GetCircleMovieListRequest;
import com.jessieray.api.service.GetDouBanToMovieRequest;
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
import com.mome.view.LoadingDialog;

@LayoutInject(layout = R.layout.dou_movie_search)
public class DouMovieSearch extends BaseFragment implements OnItemClickListener {
	@ViewInject(id = R.id.searchList)
	private PullToRefreshListView refreshListView;	
	private ListView searchList;
	private ArrayList<DouMovieInfo> mMovieList = new ArrayList<DouMovieInfo>();
	private Madapter adapter;
	private String queryStr;
	private int curPageIndex=0;
	
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		queryStr = bundle.getString("queryStr");
		if (!TextUtils.isEmpty(queryStr)) {
			setUpList();
		}
	}

	public void setUpList() {
		adapter = new Madapter(getActivity(), mMovieList);
		searchList=refreshListView.getRefreshableView();
		searchList.setAdapter(adapter);
		searchList.setOnItemClickListener(this);
		Tools.setRereshing(refreshListView);
		refreshListView.setMode(Mode.BOTH);
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
					curPageIndex = 0;
					getMovieByDou();

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
				getMovieByDou();

			}
		});

	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
	           DouMovieInfo info=(DouMovieInfo) parent.getItemAtPosition(position);
	           getDouBanToMovie(info.getId());
	}
	
	
	public void getDouBanToMovie(String id){
		GetDouBanToMovieRequest.findGetDouBanToMovie(id, this);
		
	}
	
	
	@Override
	public <T> void sucess(Type arg0, ResponseResult<T> arg1) {
		// TODO Auto-generated method stub
		if(arg1.getModel()!=null){
			com.jessieray.api.model.getDouBanToMovie movie=arg1.getModel();
			Bundle bundle=getArguments();
			bundle.putSerializable("movieInfo", movie.getMovie());
			Tools.pushScreen(AddRecord.class, bundle);
		}
	}

	@Override
	public void error(ResponseError arg0) {
		// TODO Auto-generated method stub
		super.error(arg0);
		Tools.toastShow(arg0.getMessage());
	}

	public void getMovieByDou() {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("q", queryStr);
		params.put("count", "10");
		params.put("start", curPageIndex+"");
		
				// TODO Auto-generated method stub
				UploadUtil.httpUtil(
						"http://api.douban.com/v2/movie/search", params,"GET",
						new CallBack() {

							@Override
							public void Back(Object params) {
								// TODO Auto-generated method stub
								if (!TextUtils.isEmpty((CharSequence) params)) {
									Gson son = new Gson(); 
									Moviesearch moviesearch = son.fromJson(
											(String) params, Moviesearch.class);
								
									if (curPageIndex == 0)
										mMovieList.clear();
									if (Integer.valueOf(moviesearch.getTotal()) >curPageIndex) {
										refreshListView.setMode(Mode.BOTH);
										curPageIndex=curPageIndex+10;
									} else {
										refreshListView.setMode(Mode.PULL_FROM_START);
									}
									mMovieList.addAll(moviesearch.getSubjects());
									handle.sendEmptyMessage(0);
								}
							}
						});

			


	}

	Handler handle = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			refreshListView.onRefreshComplete();
			adapter.notifyDataSetChanged();
		}
	};

	class Madapter extends BaseAdapter<DouMovieInfo> {
		private DouMovieInfo minfo;

		public Madapter(Context context, ArrayList<DouMovieInfo> models) {
			super(context, models);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = InjectProcessor.injectListViewHolder(viewHolder);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			minfo = models.get(position);
			viewHolder.image.setImageUrl(minfo.getImages().getSmall(),
					HttpRequest.getInstance().imageLoader);
			viewHolder.name.setText(minfo.getTitle());
			return convertView;
		}

		@LayoutInject(layout = R.layout.movie_search_item)
		public class ViewHolder {
			@ViewInject(id = R.id.head)
			private NetworkImageView image;

			@ViewInject(id = R.id.name)
			private TextView name;

		}

	}
}
