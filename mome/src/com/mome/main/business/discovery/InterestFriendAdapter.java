package com.mome.main.business.discovery;
import java.util.List;

import com.jessieray.api.model.MemoirsInfo;
import com.jessieray.api.model.UserInfo;
import com.mome.main.R;
import com.mome.main.business.module.ExpandListCellBase;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.ImageLoader;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class InterestFriendAdapter extends ExpandListCellBase {
	
	
	private UserInfo userinfo;
	
	private  String  friendType;
	
	public void setFriendType(String friendType) {
		this.friendType = friendType;
	}


	public String getFriendType() {
		return friendType;
	}


	public UserInfo getUserinfo() {
		return userinfo;
	}


	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}


	@Override
	public View getGroupView(int groupIndex,View convertView,boolean isExpanded) {
		// TODO Auto-generated method stub
		View view = convertView;
		GroupViewHolder viewHolder;
		if (view == null) {
			viewHolder = new GroupViewHolder();
			view = InjectProcessor.injectListViewHolder(viewHolder);
			view.setTag(viewHolder);
		} else {
			viewHolder = (GroupViewHolder) view.getTag();
		}
		
		viewHolder.friendType.setText(friendType);
		  if(isExpanded){
              viewHolder.arrow.setImageResource(R.drawable.dynamic_img_rating_select);
          }else{
        	  viewHolder.arrow.setImageResource(R.drawable.dynamic_bg_rating);
          }
		return view;
	}

	@Override
	public View getChildView(int groupIndex,int childIndex,View convertView) {
		// TODO Auto-generated method stub
		View view = convertView;
		ChildViewHolder viewHolder;
		if (view == null) {
			viewHolder = new ChildViewHolder();
			view = InjectProcessor.injectListViewHolder(viewHolder);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ChildViewHolder) view.getTag();
		}
	    viewHolder.user_sign.setText(userinfo.getSignature());
		viewHolder.user_name.setText(userinfo.getNickname());
		viewHolder.user_icon.setImageUrl(userinfo.getAvatar(),HttpRequest.getInstance().imageLoader );

		
		
		return view;
	}

	
	
	@LayoutInject(layout=R.layout.interest_friend_child)
	 class ChildViewHolder{
	
		/**
		 * 关注按钮
		 * 
		 * */
		@ViewInject(id=R.id.attention)
		private ImageView attention;
		
		/**
		 * 个性签名
		 * 
		 * */
		@ViewInject(id=R.id.user_sign)
		private TextView user_sign;
		
		/**
		 * 用户名
		 * 
		 * */
		@ViewInject(id=R.id.user_name)
		private TextView user_name;
		/**
		 * 用户头像
		 * 
		 * */
		
		@ViewInject(id=R.id.user_icon)
		private NetworkImageView user_icon;
		
	}
	
	@LayoutInject(layout=R.layout.interest_friend_group)
	class GroupViewHolder{
		/**
		 * 感兴趣的人类型
		 * 
		 * */
		@ViewInject(id=R.id.friendType)
		private TextView friendType;
		
		/**
		 * 全部关注或取消
		 * 
		 * */
		@ViewInject(id=R.id.AllAttention)
		private TextView all;
		
		/**
		 * 箭头
		 * 
		 * */
		@ViewInject(id=R.id.arrow)
		private ImageView arrow;
		
	}
}
