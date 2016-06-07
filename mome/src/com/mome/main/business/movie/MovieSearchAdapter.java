package com.mome.main.business.movie;

import java.util.ArrayList;
import java.util.List;

import com.jessieray.api.model.DynamicInfo;
import com.jessieray.api.model.MovieInfo;
import com.jessieray.api.model.UserInfo;
import com.mome.main.R;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MovieSearchAdapter extends BaseExpandableListAdapter {
	private List<String> GroupData = new ArrayList<String>();

	private List<MovieInfo> MovieChildData = new ArrayList<MovieInfo>();
	private List<UserInfo> UserChildData = new ArrayList<UserInfo>();
	private List<DynamicInfo> dynamicChildData = new ArrayList<DynamicInfo>();
	
	private boolean isShowMore=false;

	private String search;

	public  void setSearch(String search){
		this.search=search;
	}
	
	public void setGroupData(List<String> GroupData) {
		this.GroupData = GroupData;
	}

	public void setUserChildData(List<UserInfo> UserChildData) {
		this.UserChildData = UserChildData;
	}

	public void setMovieChildData(List<MovieInfo> MovieChildData) {
		this.MovieChildData = MovieChildData;
	}

	public void setDynamicChildData(List<DynamicInfo> dynamicChildData) {
		this.dynamicChildData = dynamicChildData;
	}

	public void clear() {
		this.GroupData.clear();
		this.UserChildData.clear();
		this.MovieChildData.clear();
		this.dynamicChildData.clear();
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return GroupData.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		if (GroupData.get(groupPosition).equals("电影")) {
            if(MovieChildData.size()>3){
             	isShowMore=true;
            	return 4;
            }
            else
			return MovieChildData.size();
		} else if (GroupData.get(groupPosition).equals("用户")) {
			if(UserChildData.size()>3){
				isShowMore=true;
            return 4;
			}
			else
			return UserChildData.size();
		} else if (GroupData.get(groupPosition).equals("动态")) {
			if(dynamicChildData.size()>3){
				isShowMore=true;
	            return 4;
			}
				else
			return dynamicChildData.size();
		}

		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return GroupData.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		if (GroupData.get(groupPosition).equals("电影")) {
			return MovieChildData.get(childPosition);
		} else if (GroupData.get(groupPosition).equals("用户")) {
			return UserChildData.get(childPosition);
		} else if (GroupData.get(groupPosition).equals("动态")) {
			return dynamicChildData.get(childPosition);
		}
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return GroupData.size();
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GroupHolder holder = null;
		if (convertView == null) {
			holder = new GroupHolder();
			convertView = InjectProcessor.injectListViewHolder(holder);
			convertView.setTag(holder);
		} else {
			holder = (GroupHolder) convertView.getTag();
		}
		holder.searchName.setText(GroupData.get(groupPosition));
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ChildHolder holder = null;
		if (convertView == null) {
			holder = new ChildHolder();
			convertView = InjectProcessor.injectListViewHolder(holder);
			convertView.setTag(holder);
		} else {
			holder = (ChildHolder) convertView.getTag();
		}
		if (GroupData.get(groupPosition).equals("动态")) {
			holder.username.setText(dynamicChildData.get(childPosition)
					.getNickname());
			Tools.getSearch(search,dynamicChildData.get(childPosition)
					.getBrief(), holder.comment);
			holder.date.setText(dynamicChildData.get(childPosition)
					.getCreatetime());
			holder.headicon.setImageUrl(dynamicChildData.get(childPosition)
					.getAvatar(), HttpRequest.getInstance().imageLoader);
			holder.date.setVisibility(View.VISIBLE);
			holder.comment.setVisibility(View.VISIBLE);
			holder.findMore_tv.setText("查看更多动态");
			
		} else {
			holder.date.setVisibility(View.GONE);
			holder.comment.setVisibility(View.GONE);
			if (GroupData.get(groupPosition).equals("电影")) {
				Tools.getSearch(search,MovieChildData.get(childPosition).getTitle(), holder.username);
				holder.headicon.setImageUrl(MovieChildData.get(childPosition)
						.getImagesrc(), HttpRequest.getInstance().imageLoader);
				holder.findMore_tv.setText("查看更多电影");
			} else {
				holder.headicon.setImageUrl(UserChildData.get(childPosition)
						.getAvatar(), HttpRequest.getInstance().imageLoader);
				Tools.getSearch(search,UserChildData.get(childPosition).getNickname(), holder.username);
				holder.findMore_tv.setText("查看更多用户");

			}
		}
	     if(isShowMore&&childPosition==3){
	    	 holder.head_layout.setVisibility(View.GONE);
	    	 holder.findMore_layout.setVisibility(View.VISIBLE);
	     }else{
	    	 holder.head_layout.setVisibility(View.VISIBLE);
	    	 holder.findMore_layout.setVisibility(View.GONE);
	     }
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	@LayoutInject(layout = R.layout.movie_search_group)
	class GroupHolder {
		@ViewInject(id = R.id.searchName)
		private TextView searchName;
	}

	@LayoutInject(layout = R.layout.movie_search_child)
	class ChildHolder {
		@ViewInject(id = R.id.username)
		private TextView username;

		@ViewInject(id = R.id.date)
		private TextView date;

		@ViewInject(id = R.id.headicon)
		private NetworkImageView headicon;

		@ViewInject(id = R.id.comment)
		private TextView comment;
		@ViewInject(id = R.id.findMore_layout)
		private RelativeLayout findMore_layout;
		
		@ViewInject(id = R.id.head_layout)
		private RelativeLayout head_layout;
		
		@ViewInject(id = R.id.findmore_tv)
		private TextView findMore_tv;
		
	}


	
}
