package com.mome.main.business.record;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jessieray.api.model.CinemaInfo;
import com.jessieray.api.model.MemoirsInfo;
import com.jessieray.api.model.MovieDetail;
import com.jessieray.api.model.MovieInfo;
import com.mome.main.R;
import com.mome.main.business.movie.AddMovieMemoir;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

@LayoutInject(layout = R.layout.add_record)
public class AddRecord extends BaseFragment {
	/**
	 *  电影海报
	 * */
	@ViewInject(id=R.id.movie_icon)
	private NetworkImageView movieIcon;
	
	/**
	 * 电影名字
	 * */
	@ViewInject(id=R.id.movie_name)
	private TextView movie_name;
	/**
	 * 观影日期
	 * */
	@ViewInject(id=R.id.movie_date)
	private TextView movie_date;
	@OnClick(id=R.id.movie_date)
	public void setMovieDataClick(View view){
		Tools.getDataPicker(getActivity(), movie_date).show();
	}
	/**
	 * 观影时间
	 * */
	@ViewInject(id=R.id.movie_time)
	private TextView movie_time;
	@OnClick(id=R.id.movie_time)
	public void setMovieTimeClick(View view){
		Tools.getTimePicker(getActivity(), movie_time).show();
	}
	
	/**
	 * 影院名称
	 * */
	@ViewInject(id=R.id.cinema_name)
	private TextView cinemaName;
	
	
	/**
	 * 影厅号码
	 * */
	@ViewInject(id=R.id.cinema_no)
	private EditText cinemaNo;
	
	/**
	 * 座位牌号
	 * */
	@ViewInject(id=R.id.seat_no)
	private EditText seatNo;
	/**
	 * 票价
	 * */
	@ViewInject(id=R.id.ticket_price)
	private EditText ticketPrice;
	
	
	private Bundle bundle;
	private MovieInfo movieInfo;
	private CinemaInfo mCinemaInfo;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		bundle=getArguments();
		if(bundle!=null){
			movieInfo=(MovieInfo) bundle.getSerializable("movieInfo");
			mCinemaInfo=(CinemaInfo) bundle.getSerializable("cinemaInfo");
			setUpView();
		}
	
		
	}
	
	public void setUpView(){
		movieIcon.setImageUrl(movieInfo.getImagesrc(), HttpRequest.getInstance().imageLoader);
		movie_name.setText(movieInfo.getTitle());
		cinemaName.setText(mCinemaInfo.getTitle());
		
		
	}
	
	@Override
	public void rightOnClick() {
		// TODO Auto-generated method stub
		MemoirsInfo memoirsInfo=new MemoirsInfo();
		memoirsInfo.setCinema(cinemaName.getText().toString());
		memoirsInfo.setImageSrc(movieInfo.getImagesrc());
		memoirsInfo.setDate(movie_date.getText().toString());
		memoirsInfo.setStartime(movie_time.getText().toString());
		memoirsInfo.setPrice(ticketPrice.getText().toString());
		memoirsInfo.setTitle(movie_name.getText().toString());
		memoirsInfo.setTheater(cinemaNo.getText().toString());
		memoirsInfo.setSeat(seatNo.getText().toString());
//		memoirsInfo.setMovietype(movietype)
////		memoirsInfo.setDuration(movieInfo.getDuration());
		
		Bundle bundle=new Bundle();
		bundle.putSerializable("memoirsInfo", memoirsInfo);
		Tools.pushScreen(AddMovieMemoir.class,bundle);
		super.rightOnClick();
	}
}
