package com.mome.main.business.discovery;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jessieray.api.model.DynamicInfo;
import com.jessieray.api.model.MovieInfo;
import com.jessieray.api.model.UserInfo;
import com.mome.main.R;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.ImageLoader;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

public class FriendsListCell implements ListCellBase {
	private Context context;
	private UserInfo info;
	private int type;
    
	public FriendsListCell(Context context, int type) {
		this.context = context;
		this.type = type;
	}

	public void setUserInfo(UserInfo info) {
		this.info = info;
	}

	public UserInfo getUser() {
		return info;
	}

	@Override
	public View getView(int position,View convertView) {
		// TODO Auto-generated method stub
		ChildHolder holder = null;
		if (convertView == null) {
			holder = new ChildHolder();
			convertView = InjectProcessor.injectListViewHolder(holder);
			convertView.setTag(holder);
		} else {
			holder = (ChildHolder) convertView.getTag();
		}
		if (type == 1){
			holder.headicon.setDefaultImageResId(0);
		   holder.headicon.setNativeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.phone));
		}else{
			holder.headicon.setImageUrl(info.getAvatar(),HttpRequest.getInstance().imageLoader);
		}
				
		holder.username.setText(info.getNickname());
		holder.invite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (type == 1) {
					doSendSMSTo(info.getPhone(),
							context.getResources()
									.getString(R.string.inviteMessage));
				} else {
					FriendsList.weibo.sendText("@"+info.getNickname()+","+context.getResources()
									.getString(R.string.inviteMessage));
				}
			}
		});

		return convertView;
	}

	@LayoutInject(layout = R.layout.invite_friend_list_item)
	class ChildHolder {
		@ViewInject(id = R.id.name)
		private TextView username;

		@ViewInject(id = R.id.head)
		private NetworkImageView headicon;

		@ViewInject(id = R.id.invite_btn)
		private TextView invite;
	}
	/**
	 * 调起系统发短信功能
	 * 
	 * @param phoneNumber
	 * @param message
	 */
	public void doSendSMSTo(String phoneNumber, String message) {
		if (phoneNumber!=null&&PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber.replace(" ", ""))) {
			Intent intent = new Intent(Intent.ACTION_SENDTO,
					Uri.parse("smsto:" + phoneNumber));
			intent.putExtra("sms_body", message);
			context.startActivity(intent);
		}else{
			Tools.toastShow("电话号码格式错误");
		}
	}

}
