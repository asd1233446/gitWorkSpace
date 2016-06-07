package com.mome.main.business.dynamic;

import java.lang.reflect.Type;

import com.jessieray.api.model.DynamicInfo;
import com.jessieray.api.model.MovieInfo;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.AddArticleGoodRequest;
import com.jessieray.api.service.UndoArticleGoodRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.movie.MovieDetail;
import com.mome.main.business.userinfo.FriendHome;
import com.mome.main.business.userinfo.UserHome;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
	
	@Override
	public View getView(int postion,View convertView) {
		View view = convertView;
		ViewHolder viewHolder;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = InjectProcessor.injectListViewHolder(viewHolder);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		viewHolder.headIcon.setImageUrl(dynamicInfo.getAvatar(), HttpRequest.getInstance().imageLoader);
		
		viewHolder.movieImg.setImageUrl(dynamicInfo.getImageSrc(), HttpRequest.getInstance().imageLoader);
		
		viewHolder.userName.setText(dynamicInfo.getNickname());
		viewHolder.ratin.setRating(dynamicInfo.getRecallid()/2);
		viewHolder.score.setText(String.valueOf(dynamicInfo.getRecallid()));
		viewHolder.commentValue.setText(String.valueOf(dynamicInfo.getComments()));
		viewHolder.movieInfo.setText(dynamicInfo.getBrief());
		if(type == 2) {
			viewHolder.date.setText(String.valueOf(dynamicInfo.getGoods()));
			dateOrTimes(R.drawable.garyzan,viewHolder.date, dynamicInfo.getGoods()+"");
		} else if(type == 1) {
			dateOrTimes(R.drawable.dynamic_img_date, viewHolder.date, dynamicInfo.getCreatetime());

		} 
		
		viewHolder.movieTitle.setText(dynamicInfo.getTitle());
		viewHolder.praiseValue.setText(dynamicInfo.getIsgood()==true?"取消赞":"赞");
		selectorStyle(viewHolder.praiseValue,viewHolder.praiseValue.getText().toString());
		viewHolder.praiseValue.setOnClickListener(new mMyOnClickListener());
		viewHolder.commentValue.setOnClickListener(new mMyOnClickListener());
		viewHolder.movieImg.setOnClickListener(new mMyOnClickListener());
		viewHolder.headIcon.setOnClickListener(new mMyOnClickListener());
		viewHolder.movieInfo.setOnClickListener(new mMyOnClickListener());
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

	}

	private void selectorStyle(TextView view,String prise){
		if(prise.equals("取消赞")){
			Drawable drawable=AppConfig.context.getResources().getDrawable(R.drawable.iconfont_zan);
		    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		    view.setCompoundDrawables(drawable, null, null, null);
		    view.setText(prise);
		}else{
			Drawable drawable=AppConfig.context.getResources().getDrawable(R.drawable.dynamic_img_praise);
		    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		    view.setCompoundDrawables(drawable, null, null, null);
		    view.setText(prise);
		}
	}
	
	
	private void dateOrTimes(int id,TextView view,String text){
		Drawable drawable=AppConfig.context.getResources().getDrawable(id);
	    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
	    view.setCompoundDrawables(drawable, null, null, null);
	    view.setText(text);
	}
	
	class mMyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			Bundle bundle =new Bundle();
			switch (view.getId()) {
			case R.id.dynamic_list_cell_txt_praise_value:
				praise((TextView) view);
				break;
			case R.id.dynamic_list_cell_movie_info:
		         bundle.putSerializable("dynamic", dynamicInfo);
				Tools.pushScreen(DynamicDetail.class, bundle);
				break;
			case  R.id.dynamic_list_cell_movie_img:
				bundle.putString("movieId",dynamicInfo.getMovieid()+"");
				Tools.pushScreen(MovieDetail.class, bundle);
				break;
			case R.id.dynamic_list_cell_head_icon:
				bundle.putString("userid",dynamicInfo.getUserid()+"");
				Tools.pushScreen(FriendHome.class, bundle);
				break;
			case R.id.dynamic_list_cell_txt_comment_value:
				bundle.putSerializable("dynamic",dynamicInfo);
				Tools.pushScreen(DynamicDetail.class, bundle);
				break;
			default:
				break;
			}
			
		}
		
	}
	
	
	private void praise(final TextView praiseValue){
		if (!"取消赞".equals(praiseValue.getText().toString()))
			AddArticleGoodRequest.findAddArticleGood(UserProperty
					.getInstance().getUid(), dynamicInfo.getArticleid()
					+ "", new ResponseCallback() {

				@Override
				public <T> void sucess(Type type, ResponseResult<T> claszz) {
					// TODO Auto-generated method stub
					dynamicInfo.setIsgood(true);
					selectorStyle(praiseValue,"取消赞");
				}

				@Override
				public boolean isRefreshNeeded() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void error(ResponseError error) {
					// TODO Auto-generated method stub
					Tools.toastShow(error.getMessage());
				}
			});
		else
			UndoArticleGoodRequest.findUndoArticleGood(UserProperty
					.getInstance().getUid(), dynamicInfo.getArticleid()
					+ "", new ResponseCallback() {

				@Override
				public <T> void sucess(Type type, ResponseResult<T> claszz) {
					// TODO Auto-generated method stub
					dynamicInfo.setIsgood(false);
					selectorStyle(praiseValue,"赞" );
				}

				@Override
				public boolean isRefreshNeeded() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void error(ResponseError error) {
					// TODO Auto-generated method stub
					Tools.toastShow(error.getMessage());
				}
			});
	
	}
	
	
}