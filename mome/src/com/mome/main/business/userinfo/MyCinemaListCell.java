package com.mome.main.business.userinfo;

import com.mome.main.R;
import com.mome.main.business.module.ExpandListCellBase;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCinemaListCell extends ExpandListCellBase {

	
	@Override
	public View getGroupView(View convertView) {
		View view = convertView;
		GroupViewHolder viewHolder;
		if(view == null) {
			viewHolder = new GroupViewHolder();
			view = InjectProcessor.injectListViewHolder(viewHolder);
			view.setTag(viewHolder);
		} else {
			viewHolder = (GroupViewHolder) view.getTag();
		}
		return view;
	}

	@Override
	public View getChildView(View convertView) {
		View view = convertView;
		ChildViewHolder viewHolder;
		if(view == null) {
			viewHolder = new ChildViewHolder();
			view = InjectProcessor.injectListViewHolder(viewHolder);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ChildViewHolder) view.getTag();
		}
		return view;
	}

	@LayoutInject(layout = R.layout.mycinema_group_list_cell)
	class GroupViewHolder {
		@ViewInject(id = R.id.mycinema_group_name)
		public TextView name;
		@ViewInject(id = R.id.mycinema_group_num)
		public TextView num;
		@ViewInject(id = R.id.mycinema_group_arrow)
		public ImageView arrow;
	}
	
	@LayoutInject(layout = R.layout.mycineam_list_cell)
	class ChildViewHolder {
		@ViewInject(id = R.id.mycinema_list_cell_name)
		public TextView name;
		@ViewInject(id = R.id.mycinema_list_cell_address)
		public TextView address;
		@ViewInject(id = R.id.mycinema_list_cell_distance)
		public TextView distance;
	}
}

