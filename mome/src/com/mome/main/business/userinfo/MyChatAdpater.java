package com.mome.main.business.userinfo;

import java.util.List;

import com.mome.db.ChatItem;
import com.mome.main.R;
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

public class MyChatAdpater extends ArrayAdapter<ChatItem>{
   private Context context;
   private String userName;
   /**
    * 发送还是收入
    * */
   
	public MyChatAdpater(Context context, String userName) {
		// TODO Auto-generated constructor stub
		super(context, 0);		
		this.context=context;
		this.userName=userName;
	}
	
	@Override
		public int getItemViewType(int position) {
			// TODO Auto-generated method stub
		ChatItem nowMsg = (ChatItem)getItem(position);
		return nowMsg.inOrOut;
		}
	
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	
	@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return super.getCount();
		}
	
	
	@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
		Tools.toastShow("getView");
		ViewHolder viewHolder = null;
		int msgType = getItemViewType(position);
		final ChatItem item = (ChatItem)getItem(position);
		if (convertView == null) {
			if (msgType ==0) {
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
		viewHolder.time.setText(item.sendDate);
		viewHolder.msg.setText(item.msg);
		//viewHolder.headIcon.setImageUrl(item.imageUrl, HttpRequest.getInstance().imageLoader);
		
			return convertView;
		}
	
	
	class ViewHolder{
		private TextView time;
		private NetworkImageView headIcon;
		private TextView msg;
	}
	
}
