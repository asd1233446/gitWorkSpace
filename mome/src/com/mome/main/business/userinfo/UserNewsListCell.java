package com.mome.main.business.userinfo;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mome.main.R;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.Tools;

public class UserNewsListCell  implements ListCellBase {
	

	@Override
	public View getView(int postion,View convertView) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder = new ViewHolder();
			convertView = InjectProcessor.injectListViewHolder(viewHolder);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		viewHolder.userIcon_im.setImageUrl("ddd",HttpRequest.getInstance().imageLoader);
		viewHolder.userName_tv.setText("");
		viewHolder.userNews_tv.setText("");
		viewHolder.newsDate_tv.setText("");
		viewHolder.newsNum_tv.setText("");
		return convertView;
	}
	@LayoutInject(layout=R.layout.mynews_item)
	public class ViewHolder{
		@ViewInject(id=R.id.user_icon)
		private com.mome.main.netframe.volley.toolbox.NetworkImageView userIcon_im;
		
		@ViewInject(id=R.id.user_name)
		private TextView userName_tv;
		
		@ViewInject(id=R.id.user_news)
		private TextView userNews_tv;
		
		@ViewInject(id=R.id.newsDate_tv)
		private TextView newsDate_tv;
		
		
		@ViewInject(id=R.id.newsNum_tv)
		private TextView newsNum_tv;
		

		@ViewInject(id=R.id.news_ll)
		private LinearLayout news_ll;
		
		@OnClick(id=R.id.news_ll)
		private void newsOnClick(){
			//进入消息详情页面
		}
		
	
	}

}
