package com.mome.main.business.dynamic;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jessieray.api.model.DynamicInfo;
import com.mome.main.R;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.userinfo.FriendHome;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

/**
 * 电影详情列表
 * 
 */
public class DynamicDetailListCell implements ListCellBase {

	// 网络model评论数据 TODO
	private DynamicInfo dynamicInfo;

	public DynamicInfo getDynamicInfo() {
		return dynamicInfo;
	}

	public void setDynamicInfo(DynamicInfo dynamicInfo) {
		this.dynamicInfo = dynamicInfo;
	}

	@Override
	public View getView(int position,View convertView) {
		View view = convertView;
		ViewHolder viewHolder;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = InjectProcessor.injectListViewHolder(viewHolder);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		viewHolder.head.setImageUrl(dynamicInfo.getAvatar(),
				HttpRequest.getInstance().imageLoader);

		viewHolder.userName.setText(dynamicInfo.getNickname());
		viewHolder.date.setText(dynamicInfo.getCreatetime());
		viewHolder.comment.setText(dynamicInfo.getBrief());
		return view;
	}

	@LayoutInject(layout = R.layout.dynamic_detail_list_cell)
	public class ViewHolder {

		@ViewInject(id = R.id.dynamic_detail_list_cell_head_icon)
		private NetworkImageView head;

		@OnClick(id = R.id.dynamic_detail_list_cell_head_icon)
		public void headClick(View paramView) {
			Tools.toastShow("进入好友主页");
			Bundle bundle = new Bundle();
			bundle.putString("userid",dynamicInfo.getUserid()+"");
			Tools.pushScreen(FriendHome.class, bundle);
		}

		@ViewInject(id = R.id.dynamic_detail_list_cell_username)
		private TextView userName;

		@ViewInject(id = R.id.dynamic_detail_list_cell_date)
		private TextView date;

		@ViewInject(id = R.id.dynamic_detail_list_cell_comment)
		private TextView comment;
	}
}
