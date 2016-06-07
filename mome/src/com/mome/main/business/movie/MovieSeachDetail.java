package com.mome.main.business.movie;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jessieray.api.model.DynamicInfo;
import com.jessieray.api.model.MovieInfo;
import com.jessieray.api.model.UserInfo;
import com.mome.main.R;
import com.mome.main.business.dynamic.DynamicDetail;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.userinfo.FriendHome;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.Tools;
@LayoutInject(layout=R.layout.movie_search_detail_list)
public class MovieSeachDetail extends BaseFragment implements OnItemClickListener{
	@ViewInject(id=R.id.listView)
	private ListView mListView;
	private ListAdapter adapter;
	private ArrayList<Object> datalist=new ArrayList<Object>();
	private ArrayList<ListCellBase> listData = new ArrayList<ListCellBase>();
	private String search,type;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Bundle bundle=getArguments();
		search=bundle.getString("search");
		type=bundle.getString("type");
		datalist=(ArrayList<Object>) bundle.getSerializable("datalist");
		setUpView();
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		this.headView.setTitle(type);
		super.onStart();
	}
	private void  setUpView(){
		adapter=new ListAdapter();
		for(Object object:datalist){
			MovieSeDetailListCell cell=new MovieSeDetailListCell(type, search);
			cell.setInfo(object);
			listData.add(cell);
		}
		adapter.setDataList(listData);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Bundle bundle=new Bundle();
		MovieSeDetailListCell listcell=(MovieSeDetailListCell) parent.getItemAtPosition(position);
		
		if(type.equals("电影")){
		
			MovieInfo movieInfo=(MovieInfo) listcell.getinfo();
			bundle.putString("movieId",movieInfo.getMovieid()+"");
			Tools.pushScreen(MovieDetail.class, bundle);
		}
		if(type.equals("用户")){
			UserInfo userInfo=(UserInfo) listcell.getinfo();
			bundle.putString("userid",userInfo.getUserid()+"");
			Tools.pushScreen(FriendHome.class, bundle);
		}
		if(type.equals("动态")){
			DynamicInfo mDynamicInfo=(DynamicInfo) listcell.getinfo();
			bundle.putSerializable("dynamic", mDynamicInfo);
			Tools.pushScreen(DynamicDetail.class, bundle);

		}
		
		
	}

}
