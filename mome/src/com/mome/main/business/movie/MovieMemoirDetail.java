package com.mome.main.business.movie;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.mome.main.R;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;
@LayoutInject(layout=R.layout.movie_memoirs_detail)
public class MovieMemoirDetail extends BaseFragment {
	
	/**
	 * 电影海报
	 * 
	 * */
	@ViewInject(id=R.id.MovieCompanion)
	private NetworkImageView movie_icon;
	/**
	 * 电影名称
	 * 
	 * */
	@ViewInject(id=R.id.movie_name)
	private TextView movie_name;
	/**
	 *电影类型
	 * 
	 * */
	@ViewInject(id=R.id.movie_type)
	private TextView movie_type;
	/**
	 * 电影时长
	 * 
	 * */
	@ViewInject(id=R.id.movie_times)
	private TextView movie_times;
	/**
	 * 我的评分
	 * 
	 * */
	@ViewInject(id=R.id.movie_mark)
	private TextView movie_mark;
	/**
	 * 电影票照片
	 * 
	 * */
	@ViewInject(id=R.id.movie_ticket)
	private NetworkImageView movie_ticket;
	/**
	 * 电影院
	 * 
	 * */
	@ViewInject(id=R.id.cinema_name)
	private TextView cinema_name;
	/**
	 * 电影开场日期
	 * 
	 * */
	@ViewInject(id=R.id.cinema_date)
	private TextView cinema_date;
	/**
	 * 电影开场时间
	 * 
	 * */
	@ViewInject(id=R.id.cinema_time)
	private TextView cinema_time;
	/**
	 * 几号厅
	 * 
	 * */
	@ViewInject(id=R.id.cinema_hallNum)
	private TextView cinema_hallNum;
	/**
	 * 座位号
	 * 
	 * */
	@ViewInject(id=R.id.cinema_seatNum)
	private TextView cinema_seatNum;
	/**
	 * 票价
	 * 
	 * */
	@ViewInject(id=R.id.movie_money)
	private TextView movie_money;
	/**
	 * 同场查看
	 * 
	 * */
	@ViewInject(id=R.id.sameMovieNum)
	private TextView sameMovieNum;
	/**
	 * 照片留念
	 * 
	 * */
	@ViewInject(id=R.id.photoSouvenir)
	private LinearLayout photoSouvenir;
	/**
	 * 观影同伴
	 * 
	 * */
	@ViewInject(id=R.id.MovieCompanion)
	private LinearLayout MovieCompanion;
	/**
	 * 我的影评
	 * 
	 * */
	@ViewInject(id=R.id.movie_comment)
	private TextView movie_comment;
	
	
}
