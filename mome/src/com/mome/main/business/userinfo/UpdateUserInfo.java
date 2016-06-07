package com.mome.main.business.userinfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Calendar;

import org.apache.http.util.EncodingUtils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.hp.hpl.sparta.Text;
import com.jessieray.api.model.UserInfo;
import com.jessieray.api.model.savePicture;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.ChangeUserInfoRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.mome.main.core.utils.UploadUtil;
import com.mome.main.netframe.volley.Response.ErrorListener;
import com.mome.main.netframe.volley.Response.Listener;
import com.mome.main.netframe.volley.VolleyError;
import com.mome.main.netframe.volley.toolbox.ImageLoader;
import com.mome.main.netframe.volley.toolbox.MultipartRequestParams;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;
import com.mome.view.MyDatePicker;

@LayoutInject(layout = R.layout.update_userinfo)
public class UpdateUserInfo extends BaseFragment {



	/***
	 * 修改头像
	 * 
	 **/
	@ViewInject(id = R.id.userIcon_rl)
	private RelativeLayout userIcon_rl;

	@OnClick(id = R.id.userIcon_rl)
	public void changeHeadClick(View view) {
		Tools.AlertShow(this,"修改头像");
	}

	@ViewInject(id = R.id.userIcon_iv)
	private NetworkImageView userIcon_iv;
	/***
	 * 用户名
	 * 
	 **/
	@ViewInject(id = R.id.userName_rl)
	private RelativeLayout userName_rl;

	@OnClick(id = R.id.userName_rl)
	public void changeNickNameClick(View view) {
		Intent intent = new Intent(getActivity(), UserNickName.class);
		intent.putExtra("nickName", userName_tv.getText().toString());
		startActivityForResult(intent, 5);
	}

	@ViewInject(id = R.id.userName_tv)
	private TextView userName_tv;
	/***
	 * 性别
	 * 
	 **/
	@ViewInject(id = R.id.userSex_rl)
	private RelativeLayout userSex_rl;

	@OnClick(id = R.id.userSex_rl)
	public void changeSexClick(View view) {
		Intent intent = new Intent(getActivity(), UserSex.class);
		intent.putExtra("sex", userSex_tv.getText().toString());
		startActivityForResult(intent, 6);
	}

	@ViewInject(id = R.id.userSex_tv)
	private TextView userSex_tv;
	/***
	 * 个人签名
	 * 
	 **/
	@ViewInject(id = R.id.userSign_rl)
	private RelativeLayout userSign_rl;

	@OnClick(id = R.id.userSign_rl)
	public void changeSignClick(View view) {
		Intent intent = new Intent(getActivity(), UserSign.class);
		intent.putExtra("sign", userSign_tv.getText().toString());
		startActivityForResult(intent, 4);

	}

	@ViewInject(id = R.id.userSign_tv)
	private TextView userSign_tv;
	/***
	 * 所在地
	 * 
	 **/
	@ViewInject(id = R.id.userAddress_rl)
	private RelativeLayout userAddress_rl;
	@ViewInject(id = R.id.userAddress_tv)
	private TextView userAddress_tv;

	/***
	 * 生日
	 * 
	 **/
	@ViewInject(id = R.id.userBirthday_rl)
	private RelativeLayout userBirthday_rl;

	@OnClick(id = R.id.userBirthday_rl)
	public void setBirthdayClick(View view) {
		Tools.getDataPicker(getActivity(),userBirthday_tv).show();
	}

	@ViewInject(id = R.id.userBirthday_tv)
	private TextView userBirthday_tv;

	/**
	 * 
	 * 
	 * 用户信息
	 */
	private UserInfo userinfo;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		userinfo = (UserInfo) bundle.getSerializable("userInfo");
		userinfo = userinfo != null ? userinfo : new UserInfo();
		setUpView();
	}

	private void setUpView() {
		userIcon_iv.setImageUrl(userinfo.getAvatar(),
				HttpRequest.getInstance().imageLoader);
		userName_tv.setText(userinfo.getNickname());
		userSex_tv.setText(userinfo.getSex());
		userSign_tv.setText(userinfo.getSignature());
		userAddress_tv.setText(userinfo.getLocation());
		userBirthday_tv.setText(userinfo.getBrithday());

	}

	private void ChangeUserInfo() {
		ChangeUserInfoRequest.findChangeUserInfo(UserProperty.getInstance()
				.getUid(), userSex_tv.getText().toString(), userName_tv
				.getText().toString(), avatar,
				userSign_tv.getText().toString(), userAddress_tv.getText()
						.toString(), this);

	}

	/**
	 * 上传图像新地址
	 * 
	 * */

	private String avatar = "";

	@Override
	public <T> void sucess(Type arg0, ResponseResult<T> arg1) {
		// TODO Auto-generated method stub
		super.sucess(arg0, arg1);
		if (arg1.getCode() == AppConfig.REQUEST_CODE_SUCCESS) {
			if (arg1.getImage()!= null
					&& arg1.getImage().getClass() == savePicture.class) {
				savePicture picture = arg1.getImage();
				avatar = picture.getPath();
				userIcon_iv.setImageBitmap(Tools.toRoundBitmap(photo));
				Tools.toastShow("上传成功");
			} else {
				Tools.toastShow("修改成功");
			}
		}

	}

	@Override
	public void error(ResponseError arg0) {
		// TODO Auto-generated method stub
		super.error(arg0);
		Tools.toastShow(arg0.getMessage());
	}

	

	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == getActivity().RESULT_OK) {
			Log.e("---------------", Tools.dip2px(getActivity(), 60)+"==");
			switch (requestCode) {
			case 1:
				Tools.cropPhoto(this,Uri.fromFile(new File(Tools.SAVE_REAL_PATH, Tools.IMAGE_FILE_NAME)),Tools.dip2px(getActivity(), 60),Tools.dip2px(getActivity(), 60));
				break;
			case 2:
				Tools.cropPhoto(this,data.getData(),Tools.dip2px(getActivity(), 60),Tools.dip2px(getActivity(), 60));
				break;
			case 3:
				setImageToHeadView(data);
				break;
			case 4:
				// 个性签名
				userSign_tv.setText(data.getExtras().getString("sign"));
				break;
			case 5:
				userName_tv.setText(data.getExtras().getString("nickName"));
				break;
			case 6: // 性别
				userSex_tv.setText(data.getExtras().getString("sex"));
			default:
				break;

			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/* 头像文件 */
	private File tempFile;


	/**
	 * 提取保存裁剪之后的图片数据，并设置头像部分的View
	 */
	Bitmap photo;

	private void setImageToHeadView(Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			photo = extras.getParcelable("data");
			tempFile = Tools.BitmapToFile(photo);
			if (tempFile != null)
				uploadImage(tempFile);
		}
	}

	
	/**
	 * 上传图片
	 * 
	 * @param uri
	 */

	private void uploadImage(File file) {
		MultipartRequestParams params = new MultipartRequestParams();
		params.put("file", file);
		UploadUtil.upload("/savePicture.shtml", params, this);
	}


	@Override
	public void rightOnClick() {
		// TODO Auto-generated method stub
		ChangeUserInfo();
		super.rightOnClick();
	}
}
