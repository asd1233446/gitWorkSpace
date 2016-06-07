package com.mome.main.business.userinfo;

import java.util.ArrayList;
import java.util.List;

import com.jessieray.api.model.Friend;
import com.jessieray.api.model.MessageInfo;
import com.mome.db.ChatItem;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.BaseAdapter;
import com.mome.main.business.userinfo.UserDynaicListCell.ViewHolder;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyChatAdpater extends BaseAdapter<MessageInfo>{
   private Context context;
   /**
    * 发送还是收入
    * */
   public MyChatAdpater(Context context, ArrayList<MessageInfo> models) {
		super(context, models);
		this.context=context;
		// TODO Auto-generated constructor stub
	}

   @Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if(models.get(position).getFromid().equals(UserProperty.getInstance().getUid())){
			return 0;
		}else{
			return 1;
		}
	}
   
   @Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	
	@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		final MessageInfo item = models.get(position);
		if (convertView == null) {
			if (getItemViewType(position)==0) {
				convertView = LayoutInflater.from(context).inflate(R.layout.mine_chat_item, null);
			} 
			else {
				convertView = LayoutInflater.from(context).inflate(R.layout.friend_chat_item, null);
			}
			viewHolder = new ViewHolder();
			viewHolder.time=(TextView) convertView.findViewById(R.id.timeView);
			viewHolder.headIcon=(NetworkImageView) convertView.findViewById(R.id.userIcon);
			viewHolder.msg=(TextView) convertView.findViewById(R.id.msgView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.time.setText(item.getCreatetime());
		viewHolder.msg.setText(item.getBreif());
		viewHolder.headIcon.setImageUrl(item.getAvatar(), HttpRequest.getInstance().imageLoader);
		
			return convertView;
		}
	
	
	class ViewHolder{
		private TextView time;
		private NetworkImageView headIcon;
		private TextView msg;
	}
	
}
