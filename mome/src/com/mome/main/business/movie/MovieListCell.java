package com.mome.main.business.movie;

import com.jessieray.api.model.MovieInfo;
import com.mome.main.R;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.record.Record;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class MovieListCell implements ListCellBase {

	/**
	 * 网络回传数据
	 */
	private MovieInfo movieInfo;

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
		
		viewHolder.title.setText(movieInfo.getTitle());
		viewHolder.ratin.setRating(movieInfo.getMark()/2);
		viewHolder.score.setText(String.valueOf(movieInfo.getMark()));
		viewHolder.attention.setText(String.valueOf(movieInfo.getAttentions()));
		viewHolder.lookedNum.setText(String.valueOf(movieInfo.getLookedfriends()));
		viewHolder.myScore.setText(String.valueOf(movieInfo.getMyscore()));
		viewHolder.ratin.setEnabled(false);
		if(movieInfo.getWasrecall()) {
			viewHolder.eye.setVisibility(View.VISIBLE);
		} else {
			viewHolder.eye.setVisibility(View.INVISIBLE);
		}
		viewHolder.poster.setDefaultImageResId(R.drawable.ic_launcher);
		viewHolder.poster.setErrorImageResId(R.drawable.ic_launcher);
		viewHolder.poster.setImageUrl(movieInfo.getImagesrc(), HttpRequest.getInstance().imageLoader);
		if(movieInfo.getFavoers() > 0) {
			viewHolder.collect.setText("共有 ");
			viewHolder.collect.append(String.valueOf(movieInfo.getFavoers()));
			viewHolder.collect.append(" 人收藏此片");
		}
		return view;
	}

	public MovieInfo getMovieInfo() {
		return movieInfo;
	}

	public void setMovieInfo(MovieInfo movieInfo) {
		this.movieInfo = movieInfo;
	}

	@LayoutInject(layout = R.layout.movie_list_cell)
	public class ViewHolder {

		@ViewInject(id = R.id.movie_list_cell_img)
		private NetworkImageView poster;

		@ViewInject(id = R.id.movie_list_cell_name)
		private TextView title;

		@ViewInject(id = R.id.movie_list_cell_rating)
		private RatingBar ratin;

		@ViewInject(id = R.id.movie_list_cell_score)
		private TextView score;

		@ViewInject(id = R.id.movie_list_cell_attention_num)
		private TextView attention;

		@ViewInject(id = R.id.movie_list_cell_looked_num)
		private TextView lookedNum;

		@ViewInject(id = R.id.movie_list_cell_my_score)
		private TextView myScore;
		
		@ViewInject(id = R.id.movie_list_cell_eyes)
		private ImageView eye;
		
		@ViewInject(id = R.id.movie_list_cell_add)
		private ImageView add;
		
		@ViewInject(id = R.id.movie_list_cell_collect)
		private TextView collect;


		@OnClick(id = R.id.movie_list_cell_img)
		public void posterClick(View paramView) {
			Tools.pushScreen(MovieDetail.class, null);
		}

		@OnClick(id = R.id.movie_list_cell_eyes)
		public void eyeClick(View paramView) {
			Tools.pushScreen(MovieMemoirs.class, null);
		}

		@OnClick(id = R.id.movie_list_cell_add)
		public void addClick(View paramView) {
			Tools.pushScreen(Record.class, null);
		}
	}
}