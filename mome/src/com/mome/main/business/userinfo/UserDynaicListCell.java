package com.mome.main.business.userinfo;

import java.lang.reflect.Type;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jessieray.api.model.AddArticleGood;
import com.jessieray.api.model.DynamicInfo;
import com.jessieray.api.model.MovieInfo;
import com.jessieray.api.model.UndoArticleGood;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.AddArticleGoodRequest;
import com.jessieray.api.service.UndoArticleGoodRequest;
import com.mome.main.R;
import com.mome.main.business.dynamic.DynamicDetail;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.movie.MovieDetail;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;

public class UserDynaicListCell implements ListCellBase{
	private DynamicInfo dynamicInfo;

	public DynamicInfo getMomentInfo() {
		return dynamicInfo;
	}

	public void setMomentInfo(DynamicInfo momentInfo) {
		this.dynamicInfo = momentInfo;
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

		viewHolder.userIcon_im.setImageUrl(dynamicInfo.getAvatar(),
				HttpRequest.getInstance().imageLoader);
		viewHolder.userName_tv.setText(dynamicInfo.getNickname());
		viewHolder.movieInfo_tv.setText(dynamicInfo.getBrief());
		viewHolder.movieRating_rb
				.setRating((float) (dynamicInfo.getMark() * 0.5));
		viewHolder.movieScore_tv.setText(dynamicInfo.getMark() + "");
		viewHolder.movieDate.setText(dynamicInfo.getCreatetime());
		viewHolder.movieImage.setImageUrl(dynamicInfo.getImageSrc(),HttpRequest.getInstance().imageLoader);
		viewHolder.movieName_tv.setText(dynamicInfo.getTitle());
		viewHolder.dynamicComment_tv.setText("评论(" + dynamicInfo.getComments()
				+ ")");
		viewHolder.dynamic_list_cell_txt_praise_value.setText(dynamicInfo.getIsgood()==true?"取消赞":"赞");
		viewHolder.dynamic_list_cell_txt_praise_value.setOnClickListener(new TextListener());
		selectorStyle(viewHolder.dynamic_list_cell_txt_praise_value,viewHolder.dynamic_list_cell_txt_praise_value.getText().toString());
		return convertView;
	}

	
	 class TextListener implements OnClickListener {
	     //   private  position;
	        @Override
	        public void onClick(View view) {
	    			final TextView text=(TextView) view;
	    			if (!"取消赞".equals(text.getText().toString()))
	    				AddArticleGoodRequest.findAddArticleGood(UserProperty
	    						.getInstance().getUid(), dynamicInfo.getArticleid()
	    						+ "", new ResponseCallback() {

	    					@Override
	    					public <T> void sucess(Type type, ResponseResult<T> claszz) {
	    						// TODO Auto-generated method stub
	    						selectorStyle(text,"取消赞");
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
	    						selectorStyle(text,"赞" );
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
	
	
	@LayoutInject(layout = R.layout.user_dynamic_item)
	public class ViewHolder {
		@ViewInject(id = R.id.user_icon)
		private com.mome.main.netframe.volley.toolbox.NetworkImageView userIcon_im;

		@ViewInject(id = R.id.userName_tv)
		private TextView userName_tv;

		@ViewInject(id = R.id.dynamic_rating)
		private RatingBar movieRating_rb;

		@ViewInject(id = R.id.movieScore_tv)
		private TextView movieScore_tv;

		@ViewInject(id = R.id.movieinfo_tv)
		private TextView movieInfo_tv;

		@ViewInject(id = R.id.movieDate_tv)
		private TextView movieDate;

		@ViewInject(id = R.id.movieImage_iv)
		private com.mome.main.netframe.volley.toolbox.NetworkImageView movieImage;

		@ViewInject(id = R.id.movieTitile_tv)
		private TextView movieName_tv;

		@ViewInject(id = R.id.dynamicComment_tv)
		private TextView dynamicComment_tv;

		@ViewInject(id = R.id.dynamic_list_cell_btn_comment)
		private LinearLayout dynamic_list_cell_btn_comment;

		@ViewInject(id = R.id.dynamic_list_cell_txt_praise_value)
		private TextView dynamic_list_cell_txt_praise_value;

		@OnClick(id = R.id.dynamic_list_cell_btn_comment)
		// 评论
		public void commentBtnOnClick(View view) {
			 Bundle bundle=new Bundle();
				bundle.putSerializable("dynamic",dynamicInfo);
				Tools.pushScreen(DynamicDetail.class, bundle);
			// 进入评论页面
			Tools.toastShow("评论");

		}

		@OnClick(id = R.id.movieImage_iv)
		public void ToMovieInfoOnClick(View view) {
			// 进入电影详情
			Tools.toastShow("电影详情");
			Bundle bundle=new Bundle();
			bundle.putString("movieId", dynamicInfo.getMovieid()+"");
			 Tools.pushScreen(MovieDetail.class, bundle);

		}

		@OnClick(id = R.id.movieinfo_tv)
		public void MovieIntroductionOnClick(View view) {
			Tools.toastShow("进入动态正文");
           Bundle bundle=new Bundle();
			bundle.putSerializable("dynamic",dynamicInfo);
			Tools.pushScreen(DynamicDetail.class, bundle);
			// 进入动态正文

		}
		
		
		@OnClick(id = R.id.user_icon)
		public void FriendHomeOnClick(View view) {
			Tools.toastShow("进入好友主页");
			Bundle bundle=new Bundle();
			bundle.putString("userid",dynamicInfo.getUserid()+"");
			Tools.pushScreen(FriendHome.class, bundle);
			

		}

	}
}
