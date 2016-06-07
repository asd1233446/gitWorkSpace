package com.mome.main.business.movie;
import java.util.List;

import com.jessieray.api.model.MemoirsInfo;
import com.jessieray.api.model.Year;
import com.mome.main.R;
import com.mome.main.business.module.ExpandListCellBase;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.ImageLoader;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieMemoirsAdapter implements ListCellBase {
	
	
	private MemoirsInfo memoirsChild;
	public MemoirsInfo getMemoirsChild() {
		return memoirsChild;
	}

	public void setMemoirsChild(MemoirsInfo memoirsChild) {
		this.memoirsChild = memoirsChild;
	}
	

	@Override
	public View getView(int postion,View convertView) {
		// TODO Auto-generated method stub
		View view = convertView;
		ChildViewHolder viewHolder;
		if (view == null) {
			viewHolder = new ChildViewHolder();
			view = InjectProcessor.injectListViewHolder(viewHolder);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ChildViewHolder) view.getTag();
		}
		Log.e("======memoirsChild", memoirsChild+"===");
		if(memoirsChild!=null){
	    viewHolder.looked_date.setText(memoirsChild.getDate());
		viewHolder.movie_name.setText(memoirsChild.getTitle());
		viewHolder.card_poster.setImageUrl(memoirsChild.getImageSrc(),HttpRequest.getInstance().imageLoader );
		viewHolder.movie_type.setText("剧情 "+memoirsChild.getMovietype());
		viewHolder.movie_lookType.setText(Tools.getMovieLookedType(memoirsChild.getType()));
		viewHolder.movie_times.setText(memoirsChild.getDuration()+"分钟");
		viewHolder.movie_mark.setText(memoirsChild.getMark()+"");
		viewHolder.movie_cinema.setText(memoirsChild.getCinema());
		viewHolder.movie_hallNum.setText(memoirsChild.getTheater());
		viewHolder.movie_setNum.setText(memoirsChild.getSeat());
		viewHolder.movie_payTime.setText(memoirsChild.getStartime());
		viewHolder.card_monery.setText(memoirsChild.getPrice()+"");
		}
		return view;
	}

	
	
	@LayoutInject(layout=R.layout.movie_memoirs_card)
	 class ChildViewHolder{
		
		/**
		 * 观看日期
		 * 
		 * */
		@ViewInject(id=R.id.movie_memoirs_card_date)
		private TextView looked_date;
		
		/**
		 * 电影名称
		 * 
		 * */
		@ViewInject(id=R.id.movie_memoirs_card_movie_title)
		private TextView movie_name;
		
		/**
		 * 电影类型
		 * 
		 * */
		@ViewInject(id=R.id.movie_memoirs_card_movie_type)
		private TextView movie_type;
		/**
		 * 电影时长
		 * 
		 * */
		@ViewInject(id=R.id.movie_memoirs_card_movie_time)
		private TextView movie_times;
		/**
		 * 电影评分
		 * 
		 * */
		@ViewInject(id=R.id.movie_memoirs_card_my_grade)
		private TextView movie_mark;
		/**
		 * 影院观看
		 * 
		 * */
		@ViewInject(id=R.id.movie_memoirs_card_look_type)
		private TextView movie_lookType;
		/**
		 * 电影院
		 * 
		 * */
		@ViewInject(id=R.id.movie_memoirs_card_cinema)
		private TextView movie_cinema;
		
		/**
		 * 几号厅
		 * 
		 * */
		@ViewInject(id=R.id.movie_memoirs_card_hall_number)
		private TextView movie_hallNum;
		
		/**
		 * 座位号
		 * 
		 * */
		@ViewInject(id=R.id.movie_memoirs_card_seat_number)
		private TextView movie_setNum;
		
		/**
		 * 影片播放时间
		 * 
		 * */
		@ViewInject(id=R.id.movie_memoirs_card_show_time)
		private TextView movie_payTime;
		
		/**
		 * 票价
		 * 
		 * */
		@ViewInject(id=R.id.movie_memoirs_card_money)
		private TextView card_monery;
		/**
		 * 海报
		 * 
		 * */
		
		@ViewInject(id=R.id.movie_memoirs_card_poster)
		private NetworkImageView card_poster;
		
	}



}
