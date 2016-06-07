package com.mome.main.business.record;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jessieray.api.model.AddRecall;
import com.jessieray.api.model.CinemaInfo;
import com.jessieray.api.model.MemoirsInfo;
import com.jessieray.api.model.MovieDetail;
import com.jessieray.api.model.MovieInfo;
import com.jessieray.api.model.PlatformList;
import com.jessieray.api.model.PlatformList.Platform;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.AddRecallRequest;
import com.jessieray.api.service.PlatformListRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.movie.AddMovieMemoir;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.Tools;
import com.mome.main.core.utils.Tools.CallBack;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

@LayoutInject(layout = R.layout.add_record)
public class AddRecord extends BaseFragment implements CallBack,OnItemSelectedListener {
	/**
	 * 电影海报
	 * */
	@ViewInject(id = R.id.movie_icon)
	private NetworkImageView movieIcon;
	@ViewInject(id = R.id.icon_bur)
	private ImageView icon_bur;

	/**
	 * 电影名字
	 * */
	@ViewInject(id = R.id.movie_name)
	private TextView movie_name;
	/**
	 * 观影日期
	 * */
	@ViewInject(id = R.id.movie_date)
	private TextView movie_date;

	@OnClick(id = R.id.movie_date)
	public void setMovieDataClick(View view) {
		Tools.getDataPicker(getActivity(), movie_date).show();
	}

	/**
	 * 观影时间
	 * */
	@ViewInject(id = R.id.movie_time)
	private TextView movie_time;

	@OnClick(id = R.id.movie_time)
	public void setMovieTimeClick(View view) {
		Tools.getTimePicker(getActivity(), movie_time).show();
	}

	/**
	 * 影院名称
	 * */
	@ViewInject(id = R.id.cinema_name)
	private EditText cinemaName;

	@OnClick(id = R.id.cinema_name)
	public void checkCinemaClick(View view) {
		Tools.pushScreen(SelectCinema.class, null);
		Tools.setCallListener(this);
	}

	/**
	 * 影厅号码
	 * */
	@ViewInject(id = R.id.cinema_no)
	private EditText cinemaNo;

	/**
	 * 座位牌号
	 * */
	@ViewInject(id = R.id.seat_no)
	private EditText seatNo;
	/**
	 * 票价
	 * */
	@ViewInject(id = R.id.ticket_price)
	private EditText ticketPrice;
	/**
	 * 影院观看模式
	 * */
	@ViewInject(id = R.id.cinema_linear)
	private LinearLayout cinema_linear;

	/**
	 * 网络和电视观看模式
	 * */
	@ViewInject(id = R.id.internet_linear)
	private LinearLayout internet_linear;

	/**
	 * 观看平台
	 * */
	@ViewInject(id = R.id.looked_type)
	private Spinner looked_type;
	
	/**
	 * 添加观影记录
	 * */
	@ViewInject(id = R.id.addComment)
	private LinearLayout addComment;
	
	@OnClick(id=R.id.addComment)
	public void addCommentClick(View view){
		Intent intent = new Intent(getActivity(), MovieComment.class);
		intent.putExtra("memoirsInfo", memoirsInfo);
		startActivity(intent);
	}

	private Bundle bundle;
	private MovieInfo movieInfo;
	private CinemaInfo info;
	private MemoirsInfo memoirsInfo;
	private ArrayAdapter<String> spinnerAdapter;
	private List<String> spinnerlist = new ArrayList<String>();
	// 观影方式1：影院，2：网络，3：电视 ，
	private int lookedType = 1;
	private List<Platform> platformList;
	
	private String type;
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		bundle = getArguments();
		if (bundle != null) {
			movieInfo = (MovieInfo) bundle.getSerializable("movieInfo");
			lookedType = bundle.getInt("lookedType");
			setUpView();
			initSpinner();
			if (lookedType == 1) {
				cinema_linear.setVisibility(View.VISIBLE);
				internet_linear.setVisibility(View.GONE);
			} else {
				getPlatformList();
				cinema_linear.setVisibility(View.GONE);
				internet_linear.setVisibility(View.VISIBLE);
			}
			
		}

	}

	public void setUpView() {
		movieIcon.setBurImageView(icon_bur);
		movieIcon.setImageUrl(movieInfo.getImagesrc(),
				HttpRequest.getInstance().imageLoader);
		movie_name.setText(movieInfo.getTitle());

	}
	
	private void initSpinner() {
		spinnerAdapter = new ArrayAdapter<String>(getActivity(),
				R.layout.spinner_item, spinnerlist);
		
		looked_type.setAdapter(spinnerAdapter);
		looked_type.setOnItemSelectedListener(this);
	}

	@Override
	public void rightOnClick() {
		// TODO Auto-generated method stub
//		if (TextUtils.isEmpty(movie_name.getText())
//				|| TextUtils.isEmpty(cinemaName.getText())) {
//			Tools.toastShow("信息不全");
//			return;
//		}
		memoirsInfo = new MemoirsInfo();
		memoirsInfo.setCinema(cinemaName.getText().toString());
		memoirsInfo.setImageSrc(movieInfo.getImagesrc());
		memoirsInfo.setDate(movie_date.getText().toString());
		memoirsInfo.setStartime(movie_time.getText().toString());
		memoirsInfo.setPrice(ticketPrice.getText().toString());
		memoirsInfo.setTitle(movie_name.getText().toString());
		if(lookedType==1){
		memoirsInfo.setTheater(cinemaNo.getText().toString());
		memoirsInfo.setCinemaid(info.getCinemaid());
		}else{
			memoirsInfo.setTheater(type);	
			memoirsInfo.setCinemaid(platformList.get(spinnerlist.indexOf(type)).getCinemaid());

		}
		
		memoirsInfo.setSeat(seatNo.getText().toString());
		memoirsInfo.setMovieid(movieInfo.getMovieid() + "");
		memoirsInfo.setTicketphoto("");
		memoirsInfo.setMark(movieInfo.getMark());
		memoirsInfo.setType(lookedType);
		memoirsInfo.setMovietype(movieInfo.getType());
		//memoirsInfo.setBrief(movieInfo)
		addRecall(memoirsInfo);
		

		super.rightOnClick();
	}

	@Override
	public void Back(Object params) {
		// TODO Auto-generated method stub
		info = (CinemaInfo) params;
		cinemaName.setText(info.getTitle());

	}

	/**
	 * 添加观影
	 * 
	 * */
	private void addRecall(MemoirsInfo memoirsInfo) {
		AddRecallRequest.findAddRecall(UserProperty.getInstance().getUid(),
				memoirsInfo.getMovieid(), memoirsInfo.getType(),
				memoirsInfo.getTitle(), memoirsInfo.getDate(),
				memoirsInfo.getMark(), memoirsInfo.getTheater(),
				memoirsInfo.getCinemaid(), memoirsInfo.getSeat(),
				memoirsInfo.getBrief(),
				memoirsInfo.getStartime().replace(":", ""),
				memoirsInfo.getTicketphoto(), memoirsInfo.getPrice(), this);
	}

	@Override
	public <T> void sucess(Type arg0, ResponseResult<T> arg1) {
		// TODO Auto-generated method stub
		super.sucess(arg0, arg1);
		if(arg1.getModel() instanceof AddRecall){
		AddRecall addRecall = arg1.getModel();
		memoirsInfo.setRecallid(Integer.valueOf(addRecall.getRecallid()));
		memoirsInfo.setSamescene(addRecall.getSamescene());
		memoirsInfo.setSceneid(addRecall.getSceneid());
		Bundle bundle = new Bundle();
		bundle.putSerializable("memoirsInfo", memoirsInfo);
		bundle.putInt("addOrUpdate", 1);
		Tools.pushScreen(AddMovieMemoir.class, bundle);
		}else{
			PlatformList mList= arg1.getModel();
			this.platformList=mList.getPlatform();
			for (int i = 0; i < platformList.size(); i++) {
				spinnerlist.add(platformList.get(i).getTitle());
			}
			spinnerAdapter.notifyDataSetChanged();
		}
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		type=(String) parent.getItemAtPosition(position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	private void getPlatformList(){
		PlatformListRequest.findPlatformList(lookedType+"", this);
	}
}
