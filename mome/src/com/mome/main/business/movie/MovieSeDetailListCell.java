package com.mome.main.business.movie;

import java.util.ArrayList;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jessieray.api.model.DynamicInfo;
import com.jessieray.api.model.MovieInfo;
import com.jessieray.api.model.UserInfo;
import com.mome.main.R;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.movie.MovieSearchAdapter.ChildHolder;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

public class MovieSeDetailListCell implements ListCellBase {
	private String type, search;
	private MovieInfo movieinfo;
	private DynamicInfo dynamicInfo;
	private UserInfo userinfo;
	private Object object;

	public MovieSeDetailListCell(String type, String search) {
		this.type = type;
		this.search = search;
	}

	public void setInfo(Object info) {
		this.object=info;
		if (type.equals("电影")) {
			movieinfo = (MovieInfo) info;
		} else if (type.equals("用户")) {
			userinfo = (UserInfo) info;
		} else {
			dynamicInfo = (DynamicInfo) info;
		}
	}
	
	public  Object getinfo(){
		return object;
	}

	@Override
	public View getView(int postion,View convertView) {
		// TODO Auto-generated method stub
		ChildHolder holder = null;
		if (convertView == null) {
			holder = new ChildHolder();
			convertView = InjectProcessor.injectListViewHolder(holder);
			convertView.setTag(holder);
		} else {
			holder = (ChildHolder) convertView.getTag();
		}
		if (type.equals("动态")) {
			holder.username.setText(dynamicInfo.getNickname());
			Tools.getSearch(search, dynamicInfo.getBrief(), holder.comment);
			holder.date.setText(dynamicInfo.getCreatetime());
			holder.headicon.setImageUrl(dynamicInfo.getAvatar(),
					HttpRequest.getInstance().imageLoader);
			holder.date.setVisibility(View.VISIBLE);
			holder.comment.setVisibility(View.VISIBLE);

		} else {
			holder.date.setVisibility(View.GONE);
			holder.comment.setVisibility(View.GONE);
			if (type.equals("电影")) {
				Tools.getSearch(search, movieinfo.getTitle(), holder.username);
				holder.headicon.setImageUrl(movieinfo.getImagesrc(),
						HttpRequest.getInstance().imageLoader);
			} else {
				holder.headicon.setImageUrl(userinfo.getAvatar(),
						HttpRequest.getInstance().imageLoader);
				Tools.getSearch(search, userinfo.getNickname(), holder.username);

			}
		}

		return convertView;
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

	}

}
