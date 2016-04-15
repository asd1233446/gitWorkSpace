package com.mome.main.business.userinfo;

import java.io.IOException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

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

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		photoInfo = (PhotoInfo) (bundle != null ? bundle.get("photoinfo")
				: new PhotoInfo());
		photo.setImageUrl(photoInfo.getPhotourl(),
				HttpRequest.getInstance().imageLoader);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		headView.setTitle(photoInfo.getTitle());
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
}
