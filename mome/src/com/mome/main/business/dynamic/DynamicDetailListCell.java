package com.mome.main.business.dynamic;

import android.view.View;
import android.widget.TextView;

import com.mome.main.R;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

/**
 * 电影详情列表
 *
 */
public class DynamicDetailListCell implements ListCellBase{

	//网络model评论数据 TODO
	
	@Override
	public View getView(View convertView) {
		View view = convertView;
		ViewHolder viewHolder;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = InjectProcessor.injectListViewHolder(viewHolder);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.head.setDefaultImageResId(R.drawable.ic_launcher);
		viewHolder.head.setErrorImageResId(R.drawable.ic_launcher);
//		viewHolder.head.setImageUrl(momentInfo.getHeadPortrait(), HttpRequest.getInstance().imageLoader);

		viewHolder.userName.setText("");
		viewHolder.date.setText("");
		viewHolder.comment.setText("");
		return view;
	}

	@LayoutInject(layout = R.layout.dynamic_detail_list_cell)
	public class ViewHolder {
		
		@ViewInject(id = R.id.dynamic_detail_list_cell_head_icon)
		private NetworkImageView head;
		
		@ViewInject(id = R.id.dynamic_detail_list_cell_username)
		private TextView userName;
		
		@ViewInject(id = R.id.dynamic_detail_list_cell_date)
		private TextView date;
		
		@ViewInject(id = R.id.dynamic_detail_list_cell_comment)
		private TextView comment;
	}
}
