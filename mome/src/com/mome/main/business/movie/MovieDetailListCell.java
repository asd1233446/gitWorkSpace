package com.mome.main.business.movie;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jessieray.api.model.DynamicInfo;
import com.mome.main.R;
import com.mome.main.business.dynamic.DynamicDetail;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.userinfo.FriendHome;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

public class MovieDetailListCell implements ListCellBase{
	private Context context;
	public MovieDetailListCell(Context context){
		this.context=context;
	}
	
	private DynamicInfo dynamicInfo;

	public DynamicInfo getMomentInfo() {
		return dynamicInfo;
	}

	public void setMomentInfo(DynamicInfo momentInfo) {
		this.dynamicInfo = momentInfo;
	}

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
		viewHolder.userIcon.setImageUrl(dynamicInfo.getAvatar(), HttpRequest.getInstance().imageLoader);
		viewHolder.nickName.setText(dynamicInfo.getNickname());
		viewHolder.score.setText(""+dynamicInfo.getMark());
		viewHolder.rating.setRating((float) (dynamicInfo.getMark()*0.5));
		viewHolder.info.setText(dynamicInfo.getBrief());
		viewHolder.date.setText(dynamicInfo.getOrderType()==1?dynamicInfo.getCreatetime():dynamicInfo.getGoods()+"");
	    Drawable drawable=this.context.getResources().getDrawable(dynamicInfo.getOrderType()==1?R.drawable.dynamic_img_date:R.drawable.dynamic_img_praise);
	    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		viewHolder.date.setCompoundDrawables(drawable, null, null, null);
		return view;
	}

	@LayoutInject(layout = R.layout.movie_comment_item)
	private class ViewHolder {
		
		@ViewInject(id = R.id.user_icon)
		private NetworkImageView userIcon;
		
		@OnClick(id = R.id.user_icon)
		public void FriendHomeOnClick(View view) {
			Tools.toastShow("进入好友主页");
			Bundle bundle=new Bundle();
			bundle.putSerializable("friendInfo",dynamicInfo);
			Tools.pushScreen(FriendHome.class, bundle);
			

		}
		
		@ViewInject(id = R.id.userName_tv)
		private TextView nickName;
		
		@ViewInject(id = R.id.movie_comment_date_tv)
		private TextView date;
		
		@ViewInject(id = R.id.movieScore_tv)
		private TextView score;
		
		@ViewInject(id = R.id.dynamic_rating)
		private RatingBar rating;
		
		@ViewInject(id = R.id.movie_comment_info_tv)
		private TextView info;
		@OnClick(id = R.id.movie_comment_info_tv)
		public void MovieIntroductionOnClick(View view) {
			Tools.toastShow("进入动态正文");
           Bundle bundle=new Bundle();
			bundle.putSerializable("dynamic",dynamicInfo);
			Tools.pushScreen(DynamicDetail.class, bundle);
			// 进入动态正文

		}
	}
}
