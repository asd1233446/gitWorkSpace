package com.mome.main.business.movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.jessieray.api.model.DynamicInfo;
import com.jessieray.api.model.GetMovieUserArticleByKeyWord;
import com.jessieray.api.model.MovieInfo;
import com.jessieray.api.model.UserInfo;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.GetMovieUserArticleByKeyWordRequest;
import com.mome.main.R;
import com.mome.main.business.dynamic.DynamicDetail;
import com.mome.main.business.module.ExpandListAdapter;
import com.mome.main.business.module.ExpandListCellBase;
import com.mome.main.business.userinfo.FriendHome;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.MipcaActivityCapture;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.Tools;

@LayoutInject(layout = R.layout.searchmovie)
public class MovieSearch extends BaseFragment implements OnChildClickListener,
		OnGroupClickListener {
	@ViewInject(id = R.id.expandableList)
	private ExpandableListView expandableList;

	private MovieSearchAdapter listAdapter;

	private ArrayList<String> GroupData = new ArrayList<String>();
	private ArrayList<MovieInfo> MovieChildData = new ArrayList<MovieInfo>();
	private ArrayList<UserInfo> UserChildData = new ArrayList<UserInfo>();
	private ArrayList<DynamicInfo> dynamicChildData = new ArrayList<DynamicInfo>();

	private int currPage = 20;
	
	private String search;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setUpList();
	}

	private void setUpList() {
		listAdapter = new MovieSearchAdapter();
		expandableList.setAdapter(listAdapter);
		expandableList.setOnChildClickListener(this);
		expandableList.setOnGroupClickListener(this);

	}

	private void getSearchList(String text) {
		GetMovieUserArticleByKeyWordRequest.findGetMovieUserArticleByKeyWord(
				text, currPage, this);
	}

	@Override
	public void error(ResponseError arg0) {
		// TODO Auto-generated method stub
		super.error(arg0);
		Tools.toastShow(arg0.getMessage());
	}

	@Override
	public <T> void sucess(Type arg0, ResponseResult<T> arg1) {
		// TODO Auto-generated method stub
		super.sucess(arg0, arg1);
		GetMovieUserArticleByKeyWord search = arg1.getModel();
		MovieChildData = search.getMovies();
		UserChildData = search.getUsers();
		dynamicChildData = search.getArticles();
		listAdapter.clear();
		if (MovieChildData.size() > 0) {
			listAdapter.setMovieChildData(MovieChildData);
			GroupData.add("电影");
		}

		if (UserChildData.size() > 0) {
			listAdapter.setUserChildData(UserChildData);
			GroupData.add("用户");
		}
		if (dynamicChildData.size() > 0) {
		
			GroupData.add("动态");
			listAdapter.setDynamicChildData(dynamicChildData);
		}
		listAdapter.setGroupData(GroupData);
		listAdapter.setSearch(this.search);
		listAdapter.notifyDataSetChanged();
		for (int i = 0; i < GroupData.size(); i++) {
			expandableList.expandGroup(i);
		}

	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		this.headView.titlebar_bg_layout
				.setBackgroundColor(android.R.color.transparent);
	}

	@Override
	public void rightOnClick() {
		// TODO Auto-generated method stub
		super.rightOnClick();

		Tools.pullScreen();

	}

	@Override
	public void leftOnClick() {
		// TODO Auto-generated method stub
		// super.leftOnClick();

		Intent intent = new Intent(getActivity(), MipcaActivityCapture.class);
		startActivity(intent);
	}

	@Override
	public void editTextChange(String text) {
		// TODO Auto-generated method stub
		super.editTextChange(text);
		search=text;
		getSearchList(text);
		

	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		Bundle bundle=new Bundle();
		bundle.putString("search", search);
		if(GroupData.get(groupPosition).equals("电影")){
			if(childPosition==3){
				bundle.putSerializable("datalist", MovieChildData);
				bundle.putString("type", "电影");
				Tools.pushScreen(MovieSeachDetail.class, bundle);
				return false;
			}
			MovieInfo movieInfo=MovieChildData.get(childPosition);
			bundle.putString("movieId",movieInfo.getMovieid()+"");
			Tools.pushScreen(MovieDetail.class, bundle);
		}
		if(GroupData.get(groupPosition).equals("用户")){
			if(childPosition==3){
				bundle.putSerializable("datalist", UserChildData);
				bundle.putString("type", "用户");
				Tools.pushScreen(MovieSeachDetail.class, bundle);
				return false;
			}
			UserInfo userInfo=UserChildData.get(childPosition);
			bundle.putString("userid",userInfo.getUserid()+"");
			Tools.pushScreen(FriendHome.class, bundle);
		}
		if(GroupData.get(groupPosition).equals("动态")){
			if(childPosition==3){
				bundle.putSerializable("datalist", dynamicChildData);
				bundle.putString("type", "动态");
				Tools.pushScreen(MovieSeachDetail.class, bundle);
				return false;
			}
			DynamicInfo mDynamicInfo=dynamicChildData.get(childPosition);
			bundle.putSerializable("dynamic", mDynamicInfo);
			Tools.pushScreen(DynamicDetail.class, bundle);

		}
		
		return false;
	}

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		// TODO Auto-generated method stub
		return true;
	}
}
