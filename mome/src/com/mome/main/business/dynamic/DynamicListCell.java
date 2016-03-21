package com.mome.main.business.dynamic;

import com.jessieray.api.model.DynamicInfo;
import com.mome.main.R;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.movie.MovieDetail;
import com.mome.main.business.userinfo.UserHome;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class DynamicListCell implements ListCellBase {
	
	/**
	 *  网络回传数据
	 */
	private DynamicInfo dynamicInfo;
	/**
	 * 动态数据类型
	 */
	private int type;
	/**
	 * 数据类型
	 */
	public static final int TYPE_PRAISE_NUM = 0;
	public static final int TYPE_TIME = 1;
	public static final int TYPE_DISTANCE = 2;

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
		
		viewHolder.headIcon.setDefaultImageResId(R.drawable.ic_launcher);
		viewHolder.headIcon.setErrorImageResId(R.drawable.ic_launcher);
		viewHolder.headIcon.setImageUrl(dynamicInfo.getAvatar(), HttpRequest.getInstance().imageLoader);
		
		viewHolder.movieImg.setDefaultImageResId(R.drawable.ic_launcher);
		viewHolder.movieImg.setErrorImageResId(R.drawable.ic_launcher);
		viewHolder.movieImg.setImageUrl(dynamicInfo.getImageSrc(), HttpRequest.getInstance().imageLoader);
		
		viewHolder.userName.setText(dynamicInfo.getNickname());
		viewHolder.ratin.setRating(dynamicInfo.getRecallid()/2);
		viewHolder.score.setText(String.valueOf(dynamicInfo.getRecallid()));
		viewHolder.commentValue.setText(String.valueOf(dynamicInfo.getComments()));
		viewHolder.movieInfo.setText(dynamicInfo.getBrief());
		if(type == TYPE_PRAISE_NUM) {
			viewHolder.date.setText(String.valueOf(dynamicInfo.getGoods()));
		} else if(type == TYPE_TIME) {
			viewHolder.date.setText(dynamicInfo.getCreatetime());
		} else {
//			viewHolder.date.setText(momentInfo.getDistance());
		}
		viewHolder.movieTitle.setText(dynamicInfo.getTitle());
		viewHolder.praiseValue.setText(String.valueOf(dynamicInfo.getGoods()));
		viewHolder.ratin.setEnabled(false);
		return view;
	}

	public DynamicInfo getMomentInfo() {
		return dynamicInfo;
	}

	public void setMomentInfo(DynamicInfo momentInfo) {
		this.dynamicInfo = momentInfo;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@LayoutInject(layout = R.layout.dynamic_list_cell)
	public class ViewHolder {

		@ViewInject(id = R.id.dynamic_list_cell_btn_comment)
		private LinearLayout commentLayout;

		@ViewInject(id = R.id.dynamic_list_cell_txt_comment_value)
		private TextView commentValue;

		@ViewInject(id = R.id.dynamic_list_cell_date)
		private TextView date;

		@ViewInject(id = R.id.dynamic_list_cell_head_icon)
		private NetworkImageView headIcon;

		@ViewInject(id = R.id.dynamic_list_cell_movie_img)
		private NetworkImageView movieImg;

		@ViewInject(id = R.id.dynamic_list_cell_movie_info)
		private TextView movieInfo;

		@ViewInject(id = R.id.dynamic_list_cell_movie_title)
		private TextView movieTitle;

		@ViewInject(id = R.id.dynamic_list_cell_btn_praise)
		private LinearLayout praiseLayout;

		@ViewInject(id = R.id.dynamic_list_cell_txt_praise_value)
		private TextView praiseValue;

		@ViewInject(id = R.id.dynamic_list_cell_rating)
		private RatingBar ratin;

		@ViewInject(id = R.id.dynamic_list_cell_score)
		private TextView score;

		@ViewInject(id = R.id.dynamic_list_cell_user_name)
		private TextView userName;

		@OnClick(id = R.id.dynamic_list_cell_btn_comment)
		public void commentClick(View paramView) {
			Bundle bundle = new Bundle();
			bundle.putString(DynamicComment.KEY_NAME, dynamicInfo.getNickname());
			Tools.pushScreen(DynamicComment.class, bundle);
		}

		@OnClick(id = R.id.dynamic_list_cell_head_icon)
		public void headClick(View paramView) {
			Tools.pushScreen(UserHome.class, null);
		}

		@OnClick(id = R.id.dynamic_list_cell_movie_img)
		public void movieImgClick(View paramView) {
			Tools.pushScreen(MovieDetail.class, null);
		}

		@OnClick(id = R.id.dynamic_list_cell_btn_praise)
		public void praiseClick(View paramView) {
		}
		
		@OnClick(id = R.id.dynamic_list_cell_movie_info)
		public void movieCommentClick(View paramView) {
			Bundle bundle = new Bundle();
			bundle.putString(DynamicDetail.KEY_HEAD_IMG_URL, dynamicInfo.getAvatar());
			bundle.putString(DynamicDetail.KEY_NAME, dynamicInfo.getNickname());
			bundle.putString(DynamicDetail.KEY_SCORE, String.valueOf(dynamicInfo.getRecallid()));
			bundle.putString(DynamicDetail.KEY_COMMENT, String.valueOf(dynamicInfo.getComments()));
			if(type == TYPE_PRAISE_NUM) {
				bundle.putString(DynamicDetail.KEY_DATE, String.valueOf(dynamicInfo.getGoods()));
			} else if(type == TYPE_TIME) {
				bundle.putString(DynamicDetail.KEY_DATE, dynamicInfo.getCreatetime());
			} else {
//				bundle.putString(DynamicDetail.KEY_DATE, momentInfo.getDistance());
			}
			bundle.putString(DynamicDetail.KEY_MOVIE_IMG_URL, dynamicInfo.getImageSrc());
			bundle.putString(DynamicDetail.KEY_MOVIE_TITLE, dynamicInfo.getTitle());
			Tools.pushScreen(DynamicDetail.class, bundle);
		}
	}
}