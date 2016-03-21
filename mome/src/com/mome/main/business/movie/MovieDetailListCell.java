package com.mome.main.business.movie;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mome.main.R;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

public class MovieDetailListCell implements ListCellBase{

	//网络数据
	
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
		viewHolder.movieImg.setDefaultImageResId(R.drawable.ic_launcher);
		viewHolder.movieImg.setErrorImageResId(R.drawable.ic_launcher);
//		viewHolder.movieImg.setImageUrl(momentInfo.getHeadPortrait(), HttpRequest.getInstance().imageLoader);

		viewHolder.title.setText("");
		viewHolder.date.setText("");
		viewHolder.score.setText("");
		viewHolder.rating.setRating(0);
		viewHolder.info.setText("");
		return view;
	}

	@LayoutInject(layout = R.layout.movie_detail_list_cell)
	private class ViewHolder {
		
		@ViewInject(id = R.id.movie_detail_list_cell_img)
		private NetworkImageView movieImg;
		
		@ViewInject(id = R.id.movie_detail_list_cell_name)
		private TextView title;
		
		@ViewInject(id = R.id.movie_detail_list_cell_time)
		private TextView date;
		
		@ViewInject(id = R.id.movie_detail_list_cell_score)
		private TextView score;
		
		@ViewInject(id = R.id.movie_detail_list_cell_rating)
		private RatingBar rating;
		
		@ViewInject(id = R.id.movie_detail_list_cell_info)
		private TextView info;
	}
}
