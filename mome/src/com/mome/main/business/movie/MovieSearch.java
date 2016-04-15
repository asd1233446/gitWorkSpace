package com.mome.main.business.movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.jessieray.api.model.GetMovieUserArticleByKeyWord;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.GetMovieUserArticleByKeyWordRequest;
import com.mome.main.R;
import com.mome.main.business.module.ExpandListAdapter;
import com.mome.main.business.module.ExpandListCellBase;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.Tools;
@LayoutInject(layout=R.layout.searchmovie)
public class MovieSearch extends BaseFragment{
	@ViewInject(id=R.id.expandableList)
	private  ExpandableListView  expandableList;
	
	private ExpandListAdapter listAdapter;
	
	/** 分组集合 */
	private ArrayList<ExpandListCellBase> groupList = new ArrayList<ExpandListCellBase>();

	/** 分组下集合 */
	private ArrayList<List<ExpandListCellBase>> dataList = new ArrayList<List<ExpandListCellBase>>();
	private ArrayList<ExpandListCellBase> childList = new ArrayList<ExpandListCellBase>();
	
	private  int currPage=3;
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setUpList();
	}
	
	
	
	private void setUpList(){
		listAdapter.setDataList(dataList);
		expandableList.setAdapter(listAdapter);
	}
	
	@Override
	public void editTextChange(String text) {
		// TODO Auto-generated method stub
		getSearchList(text);
	}
	
	private void getSearchList(String text){
		GetMovieUserArticleByKeyWordRequest.findGetMovieUserArticleByKeyWord(text, currPage, this);
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
		GetMovieUserArticleByKeyWord search=arg1.getModel();
//	/	if(search!=null&&search.getMovies().size())
	}
	
	@SuppressLint("ResourceAsColor")
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		this.headView.titlebar_bg_layout.setBackgroundColor(android.R.color.transparent);
	}

}
