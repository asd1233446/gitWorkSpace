package com.mome.main.business.movie;

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
import android.view.View.OnClickListener;
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
import com.jessieray.api.model.Friend;
import com.jessieray.api.model.GetCircleMovieList;
import com.jessieray.api.model.MovieInfo;
import com.jessieray.api.model.Moviesearch;
import com.jessieray.api.request.base.RequestUtils;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.GetCircleMovieListRequest;
import com.jessieray.api.service.SameFriendsListRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.BaseAdapter;
import com.mome.main.business.movie.SelectorPhotoListCell.ViewHolder;
import com.mome.main.business.userinfo.FriendHome;
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

@LayoutInject(layout = R.layout.same_friends_list)
public class SameFriendsList extends BaseFragment implements
		OnItemClickListener {
	@ViewInject(id = R.id.pull_refresh_list)
	private PullToRefreshListView pull_refresh_list;
	private ListView listView;
	private ArrayList<Friend> mMovieList = new ArrayList<Friend>();
	private Madapter adapter;
	private String movieId;
	private String title;
	/**
	 * 同场次用户列表 0 观看此片的好友 1 收藏过此篇的人2 mome推荐的人3，最新加入 4
	 * */
	private int friendType = 0;

	private int currentPage = 1;

	private int totalPage = 0;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		friendType = bundle.getInt("friendType");
		movieId = bundle.getString("movieId");
		title = bundle.getString("title");
		setUpList();

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		this.headView.setTitle(title);
	}

	public void setUpList() {
		adapter = new Madapter(getActivity(), mMovieList);
		listView = pull_refresh_list.getRefreshableView();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		Tools.setRereshing(pull_refresh_list);
		pull_refresh_list.setMode(Mode.PULL_FROM_START);
		pull_refresh_list
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
						currentPage = 1;
						getFriend();

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
						getFriend();
					}
				});

	}

	public void getFriend() {
		java.util.Map<String, String> params = new java.util.HashMap<String, String>();
		switch (friendType) {
		case 0:
			params.put("sceneid", RequestUtils.object2String(movieId));
			params.put("userid", RequestUtils.object2String(UserProperty
					.getInstance().getUid()));
			params.put("pageNo", RequestUtils.object2String(currentPage));
			params.put("pageSize",
					RequestUtils.object2String(AppConfig.PAGE_SIZE));
			SameFriendsListRequest.findFriendList(params,
					"/sameSceneList.shtml", this);
			break;
		case 1:
			params.put("movieid", RequestUtils.object2String(movieId));
			params.put("userid", RequestUtils.object2String(UserProperty
					.getInstance().getUid()));
			params.put("pageNo", RequestUtils.object2String(currentPage));
			params.put("pageSize",
					RequestUtils.object2String(AppConfig.PAGE_SIZE));
			SameFriendsListRequest.findFriendList(params,
					"/sameMovieFriends.shtml", this);

			break;
		case 2:
			params.put("movieid", RequestUtils.object2String(movieId));
			params.put("userid", RequestUtils.object2String(UserProperty
					.getInstance().getUid()));
			params.put("pageNo", RequestUtils.object2String(currentPage));
			params.put("pageSize",
					RequestUtils.object2String(AppConfig.PAGE_SIZE));
			SameFriendsListRequest.findFriendList(params,
					"/sameFavorMovieusers.shtml", this);

			break;
		case 3:
			params.put("userid", RequestUtils.object2String(UserProperty
					.getInstance().getUid()));
			SameFriendsListRequest.findFriendList(params,
					"/recommandUserList.shtml", this);
			break;
		case 4:
			params.put("userid", RequestUtils.object2String(UserProperty
					.getInstance().getUid()));
			SameFriendsListRequest.findFriendList(params,
					"/lastJoinUserList.shtml", this);
			break;
		default:
			break;
		}
	}

	@Override
	public <T> void sucess(Type arg0, ResponseResult<T> arg1) {
		// TODO Auto-generated method stub
		mMovieList.clear();
		pull_refresh_list.onRefreshComplete();
		if (arg1.getModel() != null) {
			com.jessieray.api.model.SameFriendsList list = arg1.getModel();
			switch (friendType) {
			case 0:
				mMovieList.addAll(list.getSamescene());

				break;
			case 1:
				mMovieList.addAll(list.getSamemovie());
				break;
			case 2:
				mMovieList.addAll(list.getSamefavor());
				break;
			case 3:
				mMovieList.addAll(list.getRecommand());
				break;
			case 4:
				mMovieList.addAll(list.getLastjoin());
				break;
			default:
				break;
			}
			adapter.notifyDataSetChanged();
		}

		super.sucess(arg0, arg1);
	}

	private void page() {
		if (currentPage == 1) {
			mMovieList.clear();
		}
		if (totalPage > currentPage) {
			pull_refresh_list.setMode(Mode.BOTH);
			currentPage++;
		} else {
			pull_refresh_list.setMode(Mode.PULL_FROM_START);
		}
	}

	@Override
	public void error(ResponseError arg0) {
		// TODO Auto-generated method stub
		pull_refresh_list.onRefreshComplete();
		Tools.toastShow(arg0.getMessage());
		super.error(arg0);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	class Madapter extends BaseAdapter<Friend> {
        private int index;
		public Madapter(Context context, ArrayList<Friend> models) {
			super(context, models);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			index=position;
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = InjectProcessor.injectListViewHolder(viewHolder);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			final Friend minfo = models.get(position);
			viewHolder.image.setImageUrl(minfo.getAvatar(),
					HttpRequest.getInstance().imageLoader);
			viewHolder.name.setText(minfo.getNickname());
			viewHolder.image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bundle bundle=new Bundle();
					Log.e("============", position+"========"+index);
					bundle.putString("userid", minfo.getUserid());
                Tools.pushScreen(FriendHome.class, bundle);
				}
			});
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
