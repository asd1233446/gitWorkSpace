package com.mome.main.business.userinfo;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.jessieray.api.model.TypeInfo;
import com.mome.main.R;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
@LayoutInject(layout=R.layout.mynews)
public class MyNews extends BaseFragment  implements OnItemClickListener{
	/*
	 * 私信列表
	 */
	@ViewInject(id = R.id.pull_refresh_list)
	private PullToRefreshListView pull_refresh_list;

	private ArrayList<ListCellBase> messageData = new ArrayList<ListCellBase>();

	/**
	 * 列表数据容器
	 */
	private ListAdapter adapter;
	/**
	 * 当前页索引
	 */
	private int curPageIndex = 0;

	/**
	 * 第一页
	 */
	private int firstPage = 1;

	/**
	 * listView实例
	 */
	private ListView listView;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		 setUpListView() ;
	}

	private void setUpListView() {
		listView=pull_refresh_list.getRefreshableView();
		pull_refresh_list.setOnItemClickListener(this);
		adapter=new ListAdapter();
		adapter.setDataList(messageData);
		listView.setAdapter(adapter);
		Tools.setRereshing(pull_refresh_list);
		pull_refresh_list.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				String label = DateUtils.formatDateTime(
						AppConfig.context, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy()
						.setLastUpdatedLabel(label);
				// 下拉刷新，，只刷新服务器最新的
				getMessage(1);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				String label = DateUtils.formatDateTime(
						AppConfig.context, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy()
						.setLastUpdatedLabel(label);
				// 下拉刷新，，只刷新服务器最新的
				getMessage(1);
			}
		});
	}

	
	private void getMessage(int page){
		
		for(int i=0;i<5;i++){
		     TypeInfo info=new TypeInfo();
		     info.setAverage(i+100);
		     info.setMovietype(i+"剧情");
			MyMessageListCell cell=new MyMessageListCell();
			cell.setTypeInfo(info);
			messageData.add(cell);			
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
	//	parent.getItemAtPosition(position);
		Bundle bundle=new Bundle();
		bundle.putString("friendName", "张颖");
		Tools.pushScreen(MyChat.class, bundle);
	}
	

	
}
