package com.mome.main.business.discovery;
import java.lang.reflect.Type;
import java.util.List;

import com.jessieray.api.model.MemoirsInfo;
import com.jessieray.api.model.UserInfo;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.AddttentionRequest;
import com.jessieray.api.service.CancelttentionRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ExpandListCellBase;
import com.mome.main.business.userinfo.FriendHome;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.ImageLoader;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class InterestFriendAdapter extends ExpandListCellBase {
	
	private boolean isAttention=false;
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
//		  if(isExpanded){
//              viewHolder.arrow.setImageResource(R.drawable.dynamic_img_rating_select);
//          }else{
//        	  viewHolder.arrow.setImageResource(R.drawable.dynamic_bg_rating);
//          }
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
	    viewHolder.user_sign.setText(userinfo.getBartitle());
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
		@OnClick(id=R.id.attention)
		public void attentionClick(View view){
			if(isAttention)
				cancelAddttention(attention);
				else
				addAddttention(attention);
			
		}
		
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
		@OnClick(id=R.id.user_icon)
		public void HomeClick(View view){
			Bundle bundle=new Bundle();
			bundle.putString("userid", userinfo.getUserid());
			Tools.pushScreen(FriendHome.class, bundle);
			
		}
		
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
	
	private void addAddttention(final ImageView attention){
	AddttentionRequest.findAddttention(UserProperty.getInstance().getUid(), userinfo.getUserid(), new ResponseCallback() {
		
		@Override
		public <T> void sucess(Type type, ResponseResult<T> claszz) {
			// TODO Auto-generated method stub
			isAttention=true;
			 selectorStyle(attention);
		}
		
		@Override
		public boolean isRefreshNeeded() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void error(ResponseError error) {
			// TODO Auto-generated method stub
			Tools.toastShow(error.getMessage());
		}
	});
	}
	
	private void cancelAddttention(final ImageView attention){
		CancelttentionRequest.findCancelAddttention(UserProperty.getInstance().getUid(),userinfo.getUserid(), new ResponseCallback() {
			
			@Override
			public <T> void sucess(Type type, ResponseResult<T> claszz) {
				// TODO Auto-generated method stub
				isAttention=false;
				 selectorStyle(attention);
			}
			
			@Override
			public boolean isRefreshNeeded() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void error(ResponseError error) {
				// TODO Auto-generated method stub
				Tools.toastShow(error.getMessage());
			}
		});
		}

		private void selectorStyle(ImageView attention){
			if(isAttention){
				attention.setImageResource(R.drawable.attentioned);
			}else{
				attention.setImageResource(R.drawable.attenton);
			}
		}
}
