package com.mome.main.business.userinfo;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.jessieray.api.model.PhotoInfo;
import com.jessieray.api.model.UserAlbum;
import com.mome.main.R;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.DateUtil;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.ImageLoader;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

@LayoutInject(layout = R.layout.user_photo_detail)
public class UserPhotoDetail extends BaseFragment {
	@ViewInject(id = R.id.photo)
	private NetworkImageView photo;
	

	private PhotoInfo photoInfo;
	private boolean isNetwork=true;
	private String bitmap;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		bitmap=bundle.getString("bitmap");
		if(bitmap!=null){
			photo.setDefaultImageResId(0);
			photo.setNativeBitmap(Tools.string2Bitmap(bitmap));	
		
			return;
		}
		photoInfo = (PhotoInfo) (bundle != null ? bundle.get("photoinfo")
				: new PhotoInfo());
		isNetwork=bundle.getBoolean("isNetwork", true);
		if(isNetwork)
		photo.setImageUrl(photoInfo.getPhotourl(),
				HttpRequest.getInstance().imageLoader);
		else{
			photo.setDefaultImageResId(0);
			photo.setNativeBitmap(Tools.getScaleImage(photoInfo.getPhotourl(), 200, 240));	
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(!isNetwork)
		headView.btnRight.setVisibility(View.GONE);
		headView.setTitle(photoInfo==null?"预览图片":photoInfo.getTitle());
	}

	/***
	 * 保存图片
	 * 
	 * */
	@Override
	public void rightOnClick() {
		// TODO Auto-generated method stub
		super.rightOnClick();
		Drawable drawable = photo.getDrawable();
		if (drawable != null)
			try {
				Tools.saveFile(Tools.drawable2Bitmap(drawable),
						DateUtil.now_MM_dd_HH_mm_ss(), "",".png");
				Tools.toastShow("保存成功");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Tools.toastShow("保存失败");

			}
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	
}
