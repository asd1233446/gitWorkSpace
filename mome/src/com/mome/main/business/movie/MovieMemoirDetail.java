package com.mome.main.business.movie;

import java.lang.reflect.Type;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.jessieray.api.model.MemoirsInfo;
import com.jessieray.api.model.MovieInfo;
import com.jessieray.api.model.PhotoInfo;
import com.jessieray.api.model.RecallDetail;
import com.jessieray.api.model.UserInfo;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.RecallDetailRequest;
import com.mome.main.R;
import com.mome.main.business.record.MovieComment;
import com.mome.main.business.userinfo.MyPhoto;
import com.mome.main.business.userinfo.UserPhotoDetail;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

@LayoutInject(layout = R.layout.movie_memoirs_detail)
public class MovieMemoirDetail extends BaseFragment {

	/**
	 * 电影海报
	 * 
	 * */
	@ViewInject(id = R.id.movie_icon)
	private NetworkImageView movie_icon;
	/**
	 * 电影名称
	 * 
	 * */
	@ViewInject(id = R.id.movie_name)
	private TextView movie_name;
	/**
	 * 电影类型
	 * 
	 * */
	@ViewInject(id = R.id.movie_type)
	private TextView movie_type;
	/**
	 * 电影时长
	 * 
	 * */
	@ViewInject(id = R.id.movie_times)
	private TextView movie_times;
	/**
	 * 我的评分
	 * 
	 * */
	@ViewInject(id = R.id.movie_mark)
	private TextView movie_mark;
	/**
	 * 电影票照片
	 * 
	 * */
	@ViewInject(id = R.id.movie_ticket)
	private NetworkImageView movie_ticket;

	/**
	 * 电影院
	 * 
	 * */
	@ViewInject(id = R.id.cinema_name)
	private TextView cinema_name;
	/**
	 * 电影开场日期
	 * 
	 * */
	@ViewInject(id = R.id.cinema_date)
	private TextView cinema_date;
	/**
	 * 电影开场时间
	 * 
	 * */
	@ViewInject(id = R.id.cinema_time)
	private TextView cinema_time;
	/**
	 * 几号厅
	 * 
	 * */
	@ViewInject(id = R.id.cinema_hallNum)
	private TextView cinema_hallNum;
	/**
	 * 座位号
	 * 
	 * */
	@ViewInject(id = R.id.cinema_seatNum)
	private TextView cinema_seatNum;
	/**
	 * 票价
	 * 
	 * */
	@ViewInject(id = R.id.movie_money)
	private TextView movie_money;
	/**
	 * 同场查看
	 * 
	 * */
	@ViewInject(id = R.id.sameMovieNum)
	private TextView sameMovieNum;
	/**
	 * 照片留念
	 * 
	 * */
	@ViewInject(id = R.id.photoSouvenir)
	private LinearLayout photoSouvenir;
	/**
	 * 观影同伴
	 * 
	 * */
	@ViewInject(id = R.id.MovieCompanion)
	private LinearLayout MovieCompanion;
	/**
	 * 我的影评
	 * 
	 * */
	@ViewInject(id = R.id.movie_comment)
	private TextView movie_comment;
	@OnClick(id=R.id.movie_comment)
	public void addMovieComment(View view){
		Intent intent=new Intent(getActivity(),MovieComment.class);
		startActivity(intent);
	}
	
	

	private MemoirsInfo minfo;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			minfo = (MemoirsInfo) bundle.getSerializable("MemoirsInfo");
			getRecallDetail();
		}
		
	}

	private void getRecallDetail() {
		RecallDetailRequest.findRecallDetail("1" + "", this);
	}

	@Override
	public void error(ResponseError arg0) {
		// TODO Auto-generated method stub
		super.error(arg0);
		Tools.toastShow(arg0.getMessage());
	}

	@Override
	public <T> void sucess(Type arg0, ResponseResult<T> arg1) {
		// TODO Auto-generated method stub
		super.sucess(arg0, arg1);
		RecallDetail mRecallDetail = arg1.getModel();
		if (mRecallDetail != null) {
			sameMovieNum.setText(mRecallDetail.getSamescene());
			updateMovieView(mRecallDetail.getMovie());
			updateMemoirsView(mRecallDetail.getBase());
			updateSouvenir(mRecallDetail.getPhotos());
			updateFriend(mRecallDetail.getFriends());
		}
	}

	/**
	 * 电影信息
	 * */
	private void updateMovieView(MovieInfo info) {
		movie_icon.setImageUrl(info.getImagesrc(),
				HttpRequest.getInstance().imageLoader);
		movie_name.setText(info.getKey_title_seo());
		movie_type.setText(info.getType());
		movie_times.setText(info.getDuration()+"分钟");
	}

	/**
	 * 回忆录信息
	 * */
	private void updateMemoirsView(MemoirsInfo info) {
		this.minfo = info;
		movie_mark.setText(info.getMark() + "");
		movie_ticket.setImageUrl(info.getTicketphoto(),
				HttpRequest.getInstance().imageLoader);
		cinema_name.setText(info.getTheater());
		cinema_date.setText(info.getDate());
		cinema_hallNum.setText(info.getSeat());// 无几号厅
		cinema_seatNum.setText(info.getSeat());
		cinema_time.setText(info.getStartime());
		movie_money.setText(info.getPrice());
	}

	@OnClick(id = R.id.movie_ticket)
	public void ticketClick(View view) {
		PhotoInfo photo = new PhotoInfo();
		photo.setPhotoid(minfo.getTicketphoto());
		photo.setTitle("电影票");
		// 放大图片
		Bundle bundle = new Bundle();
		bundle.putSerializable("photoinfo", photo);
		Tools.pushScreen(UserPhotoDetail.class, bundle);
	}

	/**
	 * 照片留念
	 * */

	private void updateSouvenir(List<PhotoInfo> infoList) {
		if (infoList == null || infoList.size() == 0) {
			return;
		}
		 LinearLayout.LayoutParams prames=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
         prames.rightMargin=(int) getResources().getDimension(R.dimen.common_samll_marginOrpadding);
		for (PhotoInfo info : infoList) {
			NetworkImageView view = new NetworkImageView(getActivity());
			view.setImageUrl(info.getPhotourl(),
					HttpRequest.getInstance().imageLoader);
			photoSouvenir.addView(view,prames);
		}
	}

	/**
	 * 观影同伴
	 * */

	private void updateFriend(List<UserInfo> infoList) {
		if (infoList == null || infoList.size() == 0) {
			return;
		}
             LinearLayout.LayoutParams prames=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
             prames.rightMargin=(int) getResources().getDimension(R.dimen.common_samll_marginOrpadding);
             for (UserInfo info : infoList) {
			View view = getActivity().getLayoutInflater().inflate(
					R.layout.gridview_item, null);
			NetworkImageView imageView = (NetworkImageView) view
					.findViewById(R.id.usericon);
			imageView.setImageUrl(info.getAvatar(),
					HttpRequest.getInstance().imageLoader);
			TextView nickName = (TextView) view.findViewById(R.id.username);
			nickName.setText(info.getNickname());
			MovieCompanion.addView(view,prames);
		}
	}
}
