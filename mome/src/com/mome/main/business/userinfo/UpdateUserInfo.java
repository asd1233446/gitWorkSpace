package com.mome.main.business.userinfo;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mome.main.R;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
@LayoutInject(layout=R.layout.update_userinfo)
public class UpdateUserInfo extends BaseFragment {
	/***
	 * 修改头像
	 * 
    **/
	@ViewInject(id=R.id.userIcon_rl)
	private RelativeLayout userIcon_rl;
	@ViewInject(id=R.id.userIcon_iv)
	private ImageView userIcon_iv;
	/***
	 * 用户名
	 * 
    **/
	@ViewInject(id=R.id.userName_rl)
	private RelativeLayout userName_rl;
	@ViewInject(id=R.id.userName_tv)
	private TextView userName_tv;
	/***
	 *性别
	 * 
    **/
	@ViewInject(id=R.id.userSex_rl)
	private RelativeLayout userSex_rl;
	@ViewInject(id=R.id.userSex_tv)
	private TextView userSex_tv;
	/***
	 * 个人签名
	 * 
    **/
	@ViewInject(id=R.id.userSign_rl)
	private RelativeLayout userSign_rl;
	@ViewInject(id=R.id.userSign_tv)
	private TextView userSign_tv;
	/***
	 * 所在地
	 * 
    **/
	@ViewInject(id=R.id.userAddress_rl)
	private RelativeLayout userAddress_rl;
	@ViewInject(id=R.id.userAddress_tv)
	private TextView userAddress_tv;
	
	/***
	 * 生日
	 * 
    **/
	@ViewInject(id=R.id.userBirthday_rl)
	private RelativeLayout userBirthday_rl;
	@ViewInject(id=R.id.userBirthday_tv)
	private TextView userBirthday_tv;

}
