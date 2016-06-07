package com.mome.main.business.userinfo;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.TextView;

import com.jessieray.api.model.MessageInfo;
import com.jessieray.api.model.TypeInfo;
import com.mome.main.R;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.netframe.volley.toolbox.ImageLoader;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

public class MyMessageListCell implements ListCellBase {
	private MessageInfo info;	
	public void setMessageInfo( MessageInfo info) {
		this.info = info;
	}
	
   public MessageInfo getMessage(){
	   return info;
   }

	@Override
	public View getView(int postion,View convertView) {
		// TODO Auto-generated method stub
		
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = InjectProcessor.injectListViewHolder(viewHolder);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
        if(info.getAvatar().equals("systemAvatar")){
        	viewHolder.headIcon.setDefaultImageResId(R.drawable.systemicon);
        	
        }else{
		viewHolder.headIcon.setImageUrl(info.getAvatar(), HttpRequest.getInstance().imageLoader);
        }
		viewHolder.name.setText(info.getNickname());
		viewHolder.news.setText(info.getLastwords());
		viewHolder.news_date.setText(info.getLastDate());
		viewHolder.news_num.setText(info.getUnread());
		return convertView;
	}

	@LayoutInject(layout = R.layout.mynews_item)
	public class ViewHolder {
		@ViewInject(id=R.id.user_icon)
		private NetworkImageView headIcon;
		
		@ViewInject(id = R.id.user_name)
		private TextView name;

		@ViewInject(id = R.id.user_news)
		private TextView news;

		@ViewInject(id = R.id.newsDate_tv)
		private TextView news_date;
		
		
		@ViewInject(id = R.id.newsNum_tv)
		private TextView news_num;
		
	}
}
