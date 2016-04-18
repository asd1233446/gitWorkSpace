package com.mome.main.business.movie;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.jessieray.api.model.MemoirsInfo;
import com.mome.main.R;
import com.mome.main.business.widget.pulltorefresh.FlipLoadingLayout;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.MipcaActivityCapture;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.Tools;
import com.mome.main.core.utils.UploadUtil;
import com.mome.main.netframe.volley.toolbox.MultipartRequest;
import com.mome.main.netframe.volley.toolbox.MultipartRequestParams;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;
import com.mome.view.MyDeImageView;
import com.mome.view.MyDeImageView.OnRemveListener;
@LayoutInject(layout=R.layout.add_movie_memoirs)
public class AddMovieMemoir extends BaseFragment {
	
	/**
	 * 电影海报
	 * 
	 * */
	@ViewInject(id=R.id.movie_icon)
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
	 * 电影票照片
	 * 
	 * */
	@ViewInject(id=R.id.movie_ticket)
	private ImageView movie_ticket;
	@OnClick(id=R.id.movie_ticket)
	public void takeScanPhtotClick(View view){
		Intent intent=new Intent(getActivity(),MipcaActivityCapture.class);
		startActivity(intent);
		
	}
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
	
	@ViewInject(id=R.id.addSouvenir)
	private ImageView addSouvenir;
	@OnClick(id=R.id.addSouvenir)
	public void addSouvenirClick(View view){
		Tools.AlertShow(this, "照片留念");
	}
	
	
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
	@ViewInject(id=R.id.addComment)
	private ImageView movie_comment;
	
	private Bundle bundle;
	private MemoirsInfo memoirsInfo;
	private boolean isShow=false;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		bundle=getArguments();
		memoirsInfo=(MemoirsInfo) (bundle!=null?bundle.getSerializable("memoirsInfo"):new MemoirsInfo());
		setUpView();
	}
	
	private void setUpView(){
		movie_icon.setImageUrl(memoirsInfo.getImageSrc(), HttpRequest.getInstance().imageLoader);
		movie_name.setText(memoirsInfo.getTitle());
		movie_type.setText(memoirsInfo.getMovietype());
		movie_times.setText(memoirsInfo.getDuration()+"");
		cinema_name.setText(memoirsInfo.getCinema());
		cinema_date.setText(memoirsInfo.getDate());
		cinema_time.setText(memoirsInfo.getStartime());
		cinema_hallNum.setText(memoirsInfo.getTheater());
		cinema_seatNum.setText(memoirsInfo.getSeat());
		movie_money.setText(memoirsInfo.getPrice());		
	}
	
	@Override
	public void rightOnClick() {
		// TODO Auto-generated method stub
		super.rightOnClick();
		isShow=true;
		for (int i = 0; i < photoSouvenir.getChildCount()-1; i++) {
			MyDeImageView view=(MyDeImageView) photoSouvenir.getChildAt(i);
			view.setDeleIShow(isShow);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == getActivity().RESULT_OK) {
			switch (requestCode) {
			case 1:
				Tools.cropPhoto(this,Uri.fromFile(new File(Tools.SAVE_REAL_PATH, Tools.IMAGE_FILE_NAME)),300,300);
				break;
			case 2:
				Tools.cropPhoto(this,data.getData(),300,300);
				break;
			case 3:
				addSouvenir(data);
				break;
			default:
				break;

			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private List<Bitmap> list=new ArrayList<Bitmap>();
	
	private void addSouvenir(Intent intent){
	Bundle bundle=intent.getExtras();
	if (bundle != null) {
		Bitmap bitmap = bundle.getParcelable("data");
		MyDeImageView imageView=new MyDeImageView(getActivity());
		imageView.setImageBitmap(bitmap);
		imageView.setIndex(photoSouvenir.getChildCount()-1);
		imageView.setOnRemveListener(new OnRemveListener() {
			
			@Override
			public void remove(final View view,final int index) {
				// TODO Auto-generated method stub
				Tools.toastShow("删除"+index);
				new Handler().post(new Runnable() {
					
					public void run() {
						photoSouvenir.removeView(view);
						checkAddShow();
					//	list.remove(index);
					}
				});
			
			}
		});
		LinearLayout.LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.rightMargin=(int) getResources().getDimension(R.dimen.common_marginOrpadding);
		photoSouvenir.addView(imageView,photoSouvenir.getChildCount()-1,params);
		list.add(bitmap);
		checkAddShow();
	}
		
	}
	
	private void checkAddShow(){
		if(photoSouvenir.getChildCount()>=7){
			addSouvenir.setVisibility(View.GONE);
		}else{
			addSouvenir.setVisibility(View.VISIBLE);
		}
	}
	
	
	/**
	 * 上传图片
	 * 
	 * @param uri
	 */

	private void uploadImage() {
//		List<File> file=new ArrayList<File>();
//		for(Bitmap bitmap:list){
//			file.add(Tools.BitmapToFile(bitmap));
//		}
//		MultipartRequestParams params = new MultipartRequestParams();
//		params.photos("file", file);
//		params.put("recallid", "1");
//		UploadUtil.upload("/addRecallPhoto.shtml", params, this);
	}
}
