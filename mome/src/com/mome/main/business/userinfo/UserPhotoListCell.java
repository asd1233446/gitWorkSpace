package com.mome.main.business.userinfo;

import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;


import com.jessieray.api.model.PhotoInfo;
import com.jessieray.api.model.UserAlbum;
import com.mome.main.R;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.Tools;

public class UserPhotoListCell  implements ListCellBase {
     private PhotoInfo mPhotoInfo;

	
	public PhotoInfo getmPhotoInfo() {
		return mPhotoInfo;
	}
	public void setmPhotoInfo(PhotoInfo mPhotoInfo) {
		this.mPhotoInfo = mPhotoInfo;
	}
	

	ViewHolder viewHolder=null;
	@Override
	public View getView(int postion,View convertView) {
		// TODO Auto-generated method stub
		
		if(convertView==null){
			viewHolder = new ViewHolder();
			convertView = InjectProcessor.injectListViewHolder(viewHolder);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				viewHolder.userIcon_im.setImageUrl(mPhotoInfo.getPhotourl(),HttpRequest.getInstance().imageLoader);
			}
		}, new Random().nextInt(10));
		
		return convertView;
	}
	@LayoutInject(layout=R.layout.myphoto_item)
	public class ViewHolder{
		@ViewInject(id=R.id.user_icon)
		private com.mome.main.netframe.volley.toolbox.NetworkImageView userIcon_im;
		@OnClick(id=R.id.user_icon)
		public  void zoomImageClick(View view){
			//放大图片
			Bundle bundle=new Bundle();
			bundle.putSerializable("photoinfo", mPhotoInfo);
			Tools.pushScreen(UserPhotoDetail.class, bundle);
		}
		
	
	}

}
