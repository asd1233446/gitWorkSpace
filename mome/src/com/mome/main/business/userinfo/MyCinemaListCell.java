package com.mome.main.business.userinfo;

import com.jessieray.api.model.CinemaInfo;
import com.jessieray.api.model.DynamicInfo;
import com.mome.main.R;
import com.mome.main.business.module.ExpandListCellBase;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.userinfo.UserDynaicListCell.ViewHolder;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class MyCinemaListCell implements ListCellBase {

	private CinemaInfo cinemaInfo;

	public CinemaInfo getCinemaInfo() {
		return cinemaInfo;
	}
	public void setCinemaInfo(CinemaInfo cinemaInfo) {
		this.cinemaInfo = cinemaInfo;
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

		viewHolder.cinemaName_tv.setText(cinemaInfo.getTitle());
		
		return convertView;
	}
	@LayoutInject(layout = R.layout.mycineam_list_cell)
	public class ViewHolder {
		@ViewInject(id = R.id.mycinema_list_cell_name)
		private TextView cinemaName_tv;

}

}