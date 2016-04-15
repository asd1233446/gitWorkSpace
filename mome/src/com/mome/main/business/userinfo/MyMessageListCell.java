package com.mome.main.business.userinfo;

import android.view.View;
import android.widget.TextView;

import com.jessieray.api.model.TypeInfo;
import com.mome.main.R;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.netframe.volley.toolbox.ImageLoader;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

public class MyMessageListCell implements ListCellBase {
	private TypeInfo info;

	public void setTypeInfo( TypeInfo info) {
		this.info = info;
	}

	@Override
	public View getView(View convertView) {
		// TODO Auto-generated method stub
		
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = InjectProcessor.injectListViewHolder(viewHolder);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

	//	viewHolder.headIcon.setImageUrl(url, HttpRequest.getInstance().imageLoader);
		viewHolder.name.setText(info.getMovietype());
		viewHolder.news.setText(info.getAverage()+"");
		viewHolder.news_date.setText(info.getAverage()+"");
		viewHolder.news_num.setText(info.getAverage()+"");
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
