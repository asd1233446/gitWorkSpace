package com.mome.main.business.userinfo;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.mome.main.R;
import com.mome.main.business.dynamic.DynamicListCell;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@LayoutInject(layout = R.layout.myself_home)
public class UserHome extends BaseFragment {

	/**
	 * 用户头像
	 */
	@ViewInject(id = R.id.user_icon)
	private ImageView headIcon;

	/**
	 * 用户名
	 */
	@ViewInject(id = R.id.user_name)
	private TextView userName;
	/**
	 * 关注数量
	 */
	@ViewInject(id = R.id.attentionNum)
	private TextView attention;
	/**
	 * 粉丝数量
	 */
	@ViewInject(id = R.id.fansNum)
	private TextView fans;

	/**
	 * 观影总数
	 */
	@ViewInject(id = R.id.seenFilmNum)
	private TextView lookNum;
	/**
	 * 电影类型
	 */
	@ViewInject(id = R.id.filmStyle)
	private TextView movieType;
	
	/**
	 * 动态信息容器
	 */
	@ViewInject(id = R.id.friendsDynamicList)
	private ListView dynamicListLayout;
	
	/*
	 * 编辑个人资料
	 * */
	@ViewInject(id=R.id.userSet)
	private  ImageButton userSet;
	@OnClick(id=R.id.userSet)
	public void onUserSetClick(){
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	

	
}
