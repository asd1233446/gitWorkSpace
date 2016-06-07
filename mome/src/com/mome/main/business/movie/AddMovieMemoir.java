package com.mome.main.business.movie;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jessieray.api.model.Friend;
import com.jessieray.api.model.Me;
import com.jessieray.api.model.MemoirsInfo;
import com.jessieray.api.model.PhotoInfo;
import com.jessieray.api.model.RecallDetail;
import com.jessieray.api.model.savePicture;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.DeleteRecallPhotoRequest;
import com.jessieray.api.service.RecallDetailRequest;
import com.jessieray.api.service.addRecallFriendsRequest;
import com.mome.main.R;
import com.mome.main.business.TabManager;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.record.MovieComment;
import com.mome.main.business.userinfo.FriendHome;
import com.mome.main.business.userinfo.MyFriend;

import com.mome.main.business.userinfo.UserPhotoDetail;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.mome.main.core.utils.Tools.CallBack;
import com.mome.main.core.utils.Tools.onPhotoItemSelectedListener;
import com.mome.main.core.utils.UploadUtil;
import com.mome.main.netframe.volley.toolbox.MultipartRequestParams;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;
import com.mome.view.MyDeImageView;
import com.mome.view.MyDeImageView.OnRemveListener;

@LayoutInject(layout = R.layout.add_movie_memoirs)
public class AddMovieMemoir extends BaseFragment implements
		onPhotoItemSelectedListener {

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
	 * 电影票照片
	 * 
	 * */
	@ViewInject(id = R.id.movie_ticket)
	private ImageView movie_ticket;

	@OnClick(id = R.id.movie_ticket)
	public void takeScanPhtotClick(View view) {
		if (isUpdate || Tools.getBitmapByImage(movie_ticket) == null)
			Tools.AlertShow(this, "电影票");
		else {
			Bundle bundle = new Bundle();
			bundle.putString("bitmap",
					Tools.bitmap2String(Tools.getBitmapByImage(movie_ticket)));
			Tools.pushScreen(UserPhotoDetail.class, bundle);
		}

	}

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
	@OnClick(id = R.id.sameMovieNum_linear)
	public void sameFriendClick(View view){
		if(!sameMovieNum.getText().equals("0")){
		Bundle bundle=new Bundle();
		bundle.putInt("friendType", 0);
		bundle.putString("movieId", memoirsInfo.getSceneid());
		bundle.putString("title", "同场好友列表");
		Tools.pushScreen(SameFriendsList.class,bundle );
		}
	}

	/**
	 * 照片留念
	 * 
	 * */
	@ViewInject(id = R.id.photoSouvenir)
	private LinearLayout photoSouvenir;

	@ViewInject(id = R.id.addSouvenir)
	private ImageView addSouvenir;

	@OnClick(id = R.id.addSouvenir)
	public void addSouvenirClick(View view) {
		Bundle bundle = new Bundle();
		bundle.putInt("photoCount", maxCount
				- (photoSouvenir.getChildCount() - 1));
		bundle.putInt("recallid", memoirsInfo.getRecallid());
		Tools.pushScreen(SelectorPhoto.class, bundle);
	}

	/**
	 * 观影同伴
	 * 
	 * */
	@ViewInject(id = R.id.MovieCompanion)
	private LinearLayout MovieCompanion;

	@ViewInject(id = R.id.addCompanion)
	private ImageView addCompanion;

	@OnClick(id = R.id.addCompanion)
	public void addCompanionClick(View view) {
		Bundle bundle = new Bundle();
		bundle.putInt("FriendsCount",
				maxCount - (MovieCompanion.getChildCount() - 1));
		bundle.putInt("recallid", memoirsInfo.getRecallid());
		bundle.putInt("type", 0);
		Tools.pushScreen(SelectedFriends.class, bundle);
	}

	/**
	 * 我的影评
	 * 
	 * */
	@ViewInject(id = R.id.addComment)
	private ImageView movie_comment;

	@OnClick(id = R.id.addComment)
	public void addCommentClick(View view) {
		isFromComment = true;
		Intent intent = new Intent(getActivity(), MovieComment.class);
		intent.putExtra("memoirsInfo", memoirsInfo);
		intent.putExtra("comment", mycomment.getText().toString());
		startActivityForResult(intent, 3);

	}

	@ViewInject(id = R.id.myComment)
	private TextView mycomment;

	@ViewInject(id = R.id.markLinearLayout)
	private LinearLayout markLinearLayout;

	@ViewInject(id = R.id.movie_mark)
	private TextView movie_mark;

	// 网络观影
	@ViewInject(id = R.id.internet_linear)
	private LinearLayout internet_linear;
	/**
	 * 观看平台
	 * */
	@ViewInject(id = R.id.looked_type)
	private TextView looked_type;
	/**
	 * 观影日期
	 * 
	 * */
	@ViewInject(id = R.id.movie_date)
	private TextView movie_date;

	// 影院观影
	@ViewInject(id = R.id.cinema_linear)
	private RelativeLayout cinema_linear;

	private Bundle bundle;
	private MemoirsInfo memoirsInfo;
	private boolean isUpdate = true;
	/**
	 * 1是添加，2是详情
	 * */
	private int addOrUpdate = 1;

	private int maxCount = 6;

	// 影评返回 不让执行onstart方法里面的逻辑

	private boolean isFromComment = false;

	private List<Friend> listFriends = new ArrayList<Friend>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		bundle = getArguments();
		if (bundle != null) {
			memoirsInfo = (MemoirsInfo) bundle.getSerializable("memoirsInfo");
			addOrUpdate = bundle.getInt("addOrUpdate");
			if (addOrUpdate == 2) {
				RecallDetailRequest.findRecallDetail(memoirsInfo.getRecallid()
						+ "", this);
				markLinearLayout.setVisibility(View.VISIBLE);
				isUpdate = false;
				setAddGone();
				return;
			}
			setUpView();
		}
	}

	private void setUpView() {
		movie_icon.setImageUrl(memoirsInfo.getImageSrc(),
				HttpRequest.getInstance().imageLoader);
		movie_name.setText(memoirsInfo.getTitle());
		movie_type.setText(memoirsInfo.getMovietype());
		movie_times.setText(memoirsInfo.getDuration() + "分钟");
		cinema_name.setText(memoirsInfo.getCinema());
		cinema_date.setText(memoirsInfo.getDate());
		cinema_time.setText(memoirsInfo.getStartime());
		cinema_hallNum.setText(memoirsInfo.getTheater() + "号厅");
		cinema_seatNum.setText(memoirsInfo.getSeat());
		movie_money.setText(memoirsInfo.getPrice() + "元");
		movie_mark.setText(memoirsInfo.getMark() + "");
		sameMovieNum.setText("("+memoirsInfo.getSamescene()+")");
		if (memoirsInfo.getType() == 1) {
			// 影院
			internet_linear.setVisibility(View.GONE);
			cinema_linear.setVisibility(View.VISIBLE);
		} else {
			// 电视 网络
			internet_linear.setVisibility(View.VISIBLE);
			cinema_linear.setVisibility(View.GONE);
			looked_type.setText(memoirsInfo.getTheater());
			movie_date.setText(memoirsInfo.getDate());
		}
		// 注册回调
		Tools.setListener(this);
	}

	// 隐藏添加信息
	private void setAddGone() {
		addSouvenir.setVisibility(View.GONE);
		addCompanion.setVisibility(View.GONE);
		movie_comment.setVisibility(View.GONE);

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		if (!isFromComment) {
			this.headView.btnRight.setText(addOrUpdate == 2 ? "编辑" : "完成");
		}
		super.onStart();
	}

	@Override
	public void rightOnClick() {
		// TODO Auto-generated method stub
		super.rightOnClick();
		if (this.headView.btnRight.getText().equals("编辑")) {
			isUpdate = true;
			this.headView.btnRight.setText("完成");
			movie_comment.setVisibility(View.VISIBLE);
		} else {
			isUpdate = false;
			this.headView.btnRight.setText("编辑");
			movie_comment.setVisibility(View.GONE);
		}
		isUpdate();
	}

	@Override
	public void leftOnClick() {
		// TODO Auto-generated method stub
		AppConfig.currentIndex = 4;
		Tools.replaceScreen(new TabManager(), null);
		super.leftOnClick();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == getActivity().RESULT_OK) {
			switch (requestCode) {
			case 1:
				uploadImage(Tools.getScaleImage(Tools.SAVE_REAL_PATH + "/"
						+ Tools.IMAGE_FILE_NAME,
						Tools.dip2px(getActivity(), 100),
						Tools.dip2px(getActivity(), 100)));
				break;
			case 2:
				uploadImage(Tools
						.getScaleImage(
								Tools.getRealPathFromUri(getActivity(),
										data.getData()),
								Tools.dip2px(getActivity(), 100),
								Tools.dip2px(getActivity(), 100)));
				break;
			case 3:
				markLinearLayout.setVisibility(View.VISIBLE);
				movie_mark.setText(data.getExtras().getString("mark"));
				mycomment.setText(data.getExtras().getString("comment"));
				break;

			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void addCompanion(List<Friend> list) {
		listFriends.addAll(list);
		LinearLayout.LayoutParams params = new LayoutParams(Tools.dip2px(
				getActivity(), 100), Tools.dip2px(getActivity(), 100));
		params.rightMargin = (int) getResources().getDimension(
				R.dimen.common_samll_marginOrpadding);
		for (final Friend friend : list) {

			final MyDeImageView imageView = new MyDeImageView(getActivity());
			imageView.setIsDeleteShow(isUpdate);
			Tools.loadNetImage(imageView, friend.getAvatar(),
					R.drawable.defualt, R.drawable.defualt);

			imageView.setText(friend.getNickname());
			imageView.setIndex(MovieCompanion.getChildCount() - 1);
			if (MovieCompanion.getChildCount() - 1 >= maxCount) {
				return;
			}
			MovieCompanion.addView(imageView,
					MovieCompanion.getChildCount() - 1, params);
			imageView.setOnRemveListener(new OnRemveListener() {

				@Override
				public void remove(final View view, final int index) {
					// TODO Auto-generated method stub
					Tools.toastShow("删除" + index);
					new Handler().post(new Runnable() {

						public void run() {
							deleteCompanion(friend, view);
						}
					});

				}
			});

			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					bundle.putString("userid", friend.getUserid());
					Tools.pushScreen(FriendHome.class, bundle);
				}
			});
		}
	}

	private void checkAddShow() {
		addSouvenir
				.setVisibility((photoSouvenir.getChildCount() - 1 < maxCount && isUpdate == true) ? View.VISIBLE
						: View.GONE);
	}

	private void checkCompanionShow() {
		addCompanion
				.setVisibility((MovieCompanion.getChildCount() - 1 < maxCount && isUpdate == true) ? View.VISIBLE
						: View.GONE);
	}

	@Override
	public <T> void sucess(Type arg0, ResponseResult<T> arg1) {
		// TODO Auto-generated method stub
		// Tools.getLoadingDialog(getActivity()).dismissDialog();
		if (arg1.getModel() != null
				&& arg1.getModel().getClass() == RecallDetail.class) {
			RecallDetail mRecallDetail = arg1.getModel();
			if (mRecallDetail != null) {
				memoirsInfo=mRecallDetail.getBase();
				setUpView();
				Tools.loadNetImage(movie_ticket, mRecallDetail.getBase().getTicketphoto(), R.drawable.defualt, R.drawable.defualt);
				addSouvenir(mRecallDetail.getPhotos());
				addCompanion(mRecallDetail.getFriends());
				updateComment(mRecallDetail.getArticles().size() > 0 ? mRecallDetail
						.getArticles().get(0).getKey_brief()
						: "");
			}
		}
	}

	/**
	 * 照片留念
	 * */

	private void addSouvenir(List<PhotoInfo> infoList) {
		if (infoList == null || infoList.size() == 0) {
			return;
		}
		LinearLayout.LayoutParams params = new LayoutParams(Tools.dip2px(
				getActivity(), 100), Tools.dip2px(getActivity(), 100));
		params.rightMargin = (int) getResources().getDimension(
				R.dimen.common_samll_marginOrpadding);
		for (final PhotoInfo info : infoList) {
			MyDeImageView imageView = new MyDeImageView(getActivity());
			imageView.setIsDeleteShow(isUpdate);
			imageView.setIndex(photoSouvenir.getChildCount() - 1);
			if (info.getPhotourl().startsWith("/")) {
				// 来自sd路径的照片
				Bitmap bitmap = Tools.getScaleImage(info.getPhotourl(), 80, 80);
				imageView.setImageBitmap(bitmap);
			} else {
				Tools.loadNetImage(imageView, info.getPhotourl(),
						R.drawable.defualt, R.drawable.defualt);
			}
			if (photoSouvenir.getChildCount() - 1 > maxCount) {
				return;
			}
			photoSouvenir.addView(imageView, photoSouvenir.getChildCount() - 1,
					params);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					if (info.getPhotourl().startsWith("/")) {
						bundle.putBoolean("isNetwork", false);
					} else {
						bundle.putBoolean("isNetwork", true);
					}
					bundle.putSerializable("photoinfo", info);
					info.setTitle("预览图片");
					Tools.pushScreen(UserPhotoDetail.class, bundle);
				}
			});

			imageView.setOnRemveListener(new OnRemveListener() {

				@Override
				public void remove(View view, int index) {
					// TODO Auto-generated method stub
					deletePhoto(info.getId(), view);
				}
			});

		}
	}

	/**
	 * 我的影评
	 * */

	private void updateComment(String commet) {

		mycomment.setText(commet);
	}

	@Override
	public <T> void getSelectedPhotos(ArrayList<T> SelectorPhoto) {
		// TODO Auto-generated method stub
		if (SelectorPhoto.size() > 0
				&& SelectorPhoto.get(0) instanceof PhotoInfo) {
			addSouvenir((List<PhotoInfo>) SelectorPhoto);

			checkAddShow();

		} else {
			addCompanion((List<Friend>) SelectorPhoto);
			checkCompanionShow();

		}
	}

	/**
	 * 编辑
	 * 
	 * */

	public void isUpdate() {
		checkAddShow();
		for (int i = 0; i < photoSouvenir.getChildCount() - 1; i++) {
			MyDeImageView view = (MyDeImageView) photoSouvenir.getChildAt(i);
			view.setIsDeleteShow(isUpdate);
		}
		checkCompanionShow();
		for (int i = 0; i < MovieCompanion.getChildCount() - 1; i++) {
			MyDeImageView view = (MyDeImageView) MovieCompanion.getChildAt(i);
			view.setIsDeleteShow(isUpdate);
		}

	}

	/**
	 * 上传电影票图片
	 * 
	 * */

	private void uploadImage(final Bitmap bitmap) {

		MultipartRequestParams params = new MultipartRequestParams();
		params.put("file", bitmap, "ticket.png");
		 params.put("recallid", memoirsInfo.getRecallid() + "");
		params.put("userid", UserProperty.getInstance().getUid());
		UploadUtil.upload("/addTicketPhoto.shtml", params,
				new ResponseCallback() {

					@Override
					public <T> void sucess(Type type, ResponseResult<T> claszz) {
						// TODO Auto-generated method stub
						movie_ticket.setImageBitmap(bitmap);
					}

					@Override
					public boolean isRefreshNeeded() {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public void error(ResponseError error) {
						// TODO Auto-generated method stub
						Tools.toastShow("上传电影票失败！请重新上传");
					}
				});

	}

	/**
	 * 删除图片
	 * 
	 * */

	public void deletePhoto(String id, final View view) {
		DeleteRecallPhotoRequest.findDeleteRecallPhoto(id,
				new ResponseCallback() {

					@Override
					public <T> void sucess(Type type, ResponseResult<T> claszz) {
						// TODO Auto-generated method stub
						photoSouvenir.removeView(view);
						checkAddShow();
					}

					@Override
					public boolean isRefreshNeeded() {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public void error(ResponseError error) {
						// TODO Auto-generated method stub
						Tools.toastShow("删除图片失败");
					}
				});
	}

	/**
	 * 删除观影同伴
	 * */
	public void deleteCompanion(Friend friend, final View view) {
		StringBuffer buffer = new StringBuffer();
		listFriends.remove(friend);
		for (Friend myfriend : listFriends) {
			buffer.append(myfriend.getUserid() + ",");

		}
		buffer.append(listFriends.size()>0?"":",");
		if (!buffer.equals("") || buffer != null) {
			addRecallFriendsRequest.findaddRecallFriends(
					memoirsInfo.getRecallid() + "", buffer.toString(),
					new ResponseCallback() {

						@Override
						public <T> void sucess(Type type,
								ResponseResult<T> claszz) {
							// TODO Auto-generated method stub
							MovieCompanion.removeView(view);
							checkCompanionShow();
						}

						@Override
						public boolean isRefreshNeeded() {
							// TODO Auto-generated method stub
							return false;
						}

						@Override
						public void error(ResponseError error) {
							// TODO Auto-generated method stub

						}
					});
		}

	}

}
