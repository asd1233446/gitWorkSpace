package com.mome.main.business.userinfo;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jessieray.api.model.DynamicInfo;
import com.jessieray.api.model.MemoirsInfo;
import com.jessieray.api.model.MessageInfo;
import com.jessieray.api.model.TypeInfo;
import com.mome.main.R;
import com.mome.main.business.dynamic.DynamicDetail;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.movie.AddMovieMemoir;
import com.mome.main.business.movie.MovieDetail;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.ImageLoader;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

public class MyMessageDetailListCell implements ListCellBase {
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
		viewHolder.msgView.setText(info.getBreif());
		viewHolder.timeView.setText(info.getCreatetime());
		return convertView;
	}

	@LayoutInject(layout = R.layout.friend_chat_item)
	public class ViewHolder {
		@ViewInject(id=R.id.userIcon)
		private NetworkImageView headIcon;
		@OnClick(id=R.id.userIcon)
		public void FriendHomeClick(View view){
			Bundle bundle=new Bundle();
			switch (info.getType()) {
			case 1:
				bundle.putString("userid", info.getTypeid()+"");
				Tools.pushScreen(FriendHome.class, bundle);
				break;
			case 2:
				DynamicInfo dynamicInfo=new DynamicInfo();
				dynamicInfo.setUserid(Integer.valueOf(info.getActiveid()));
				dynamicInfo.setArticleid(info.getTypeid());
				bundle.putSerializable("dynamic", dynamicInfo);
				Tools.pushScreen(DynamicDetail.class, bundle);
				break;
			case 3:
				bundle.putString("movieId",info.getTypeid()+"");
				Tools.pushScreen(MovieDetail.class, bundle);
				break;
				
			case 4:
				MemoirsInfo memoirsInfo=new MemoirsInfo();
				memoirsInfo.setRecallid(info.getTypeid());
				bundle.putSerializable("memoirsInfo", memoirsInfo);
				bundle.putInt("addOrUpdate", 2);
				Tools.pushScreen(AddMovieMemoir.class, bundle);
				break;

			default:
				break;
			}
		}

		@ViewInject(id = R.id.msgView)
		private TextView msgView;

		@ViewInject(id = R.id.timeView)
		private TextView timeView;
		
		
	}
}
