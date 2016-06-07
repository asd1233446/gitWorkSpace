package com.mome.main.business.userinfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jessieray.api.model.ComplainList;
import com.jessieray.api.model.Complains;
import com.jessieray.api.model.DouMovieInfo;
import com.jessieray.api.model.Moviesearch;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.ComplainListRequest;
import com.jessieray.api.service.GetDouBanToMovieRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.BaseAdapter;
import com.mome.main.business.record.AddRecord;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;

@LayoutInject(layout=R.layout.opinion_list)
public class OpinionSet extends Activity implements OnItemClickListener {
	@ViewInject(id = R.id.searchList)
	private PullToRefreshListView refreshListView;	
	private ListView searchList;
	private ArrayList<Complains> mMovieList = new ArrayList<Complains>();
	private Madapter adapter;
	private int curPageIndex=0;
	
      @OnClick(id=R.id.back)
      public void backClick(View view){
    	    finish();
      }

      @OnClick(id=R.id.addOpinion)
      public void addOpinionClick(View view){
    	    Intent intent=new Intent(OpinionSet.this,AddOpinion.class);
    	    startActivity(intent);
      }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		InjectProcessor.activityInject(this);
		setUpList();

	}
	
	public void setUpList() {
		adapter = new Madapter(this, mMovieList);
		searchList=refreshListView.getRefreshableView();
		searchList.setAdapter(adapter);
		searchList.setOnItemClickListener(this);
		Tools.setRereshing(refreshListView);
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
					curPageIndex = 0;
					getOpinion();

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

			}
		});

	}
	
	public void getOpinion(){
		ComplainListRequest.findComplainList(UserProperty.getInstance().getUid(), new ResponseCallback() {
			
			@Override
			public <T> void sucess(Type type, ResponseResult<T> claszz) {
				// TODO Auto-generated method stub
				refreshListView.onRefreshComplete();
				mMovieList.clear();
				if(claszz.getModel()!=null){
					ComplainList complainList=claszz.getModel();
					mMovieList.addAll(complainList.getComplains());
					adapter.notifyDataSetChanged();
				}
			}
			
			@Override
			public boolean isRefreshNeeded() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void error(ResponseError error) {
				// TODO Auto-generated method stub
				refreshListView.onRefreshComplete();
				Toast.makeText(OpinionSet.this, error.getMessage(), Toast.LENGTH_SHORT).show();
				
			}
		});
	}

	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		 Intent intent=new Intent(OpinionSet.this,AnswerComplian.class);
		 intent.putExtra("complian", (Complains)parent.getItemAtPosition(position));
 	    startActivity(intent);
	         
	}
	
	
	

	
	class Madapter extends BaseAdapter<Complains> {
		private Complains minfo;

		public Madapter(Context context, ArrayList<Complains> models) {
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
			viewHolder.name.setText(minfo.getBrief());
			viewHolder.status.setText(minfo.getStatus().equals("1")?"未处理":"已处理");
			return convertView;
		}

		@LayoutInject(layout = R.layout.complain_item)
		public class ViewHolder {
			@ViewInject(id = R.id.name)
			private TextView name;
			
			@ViewInject(id = R.id.status)
			private TextView status;

		}

	}
}
