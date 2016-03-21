package com.mome.main.business.userinfo;

import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mome.main.R;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
@LayoutInject(layout=R.layout.activity_setting)
public class Set extends BaseFragment {
	/*
	 * 新浪绑定
	 * */
	@ViewInject(id=R.id.sinaBind_tv)
	private TextView sinaBind_tv;
	@ViewInject(id=R.id.sinaBind_bt)
	private Button sinaBind_bt;
	/*
	 * 微信绑定
	 * */
	@ViewInject(id=R.id.wechatBind_tv)
	private TextView webchatBind_tv;
	@ViewInject(id=R.id.wechatBind_bt)
	private Button webchatBind_bt;
	/*
	 * 豆瓣绑定
	 * */
	@ViewInject(id=R.id.doubanBind_tv)
	private TextView doubanBind_tv;
	@ViewInject(id=R.id.doubanBind_bt)
	private Button doubanBind_bt;
	/*
	 * 手机绑定
	 * */
	@ViewInject(id=R.id.bangding_tv)
	private TextView bangding_tv;
	@ViewInject(id=R.id.bindPhone_tv)
	private TextView bindPhone_tv;
	@ViewInject(id=R.id.bindPhone_bt)
	private Button bindPhone_bt;
	
	/*
	 * 意见反馈
	 * */
	@ViewInject(id=R.id.opinion_rl)
	private RelativeLayout opinion_rl;
	/*
	 * 清除缓存
	 * */
	@ViewInject(id=R.id.clearCache_tv)
    private TextView clearCache_tv;
	
	@OnClick(id=R.id.sinaBind_bt)
	public void onSinaClick(){
		
	}
	@OnClick(id=R.id.wechatBind_bt)
	public void onWebChatClick(){
		
	}
	@OnClick(id=R.id.doubanBind_bt)
	public void onDoubanClick(){
		
	}
	@OnClick(id=R.id.bindPhone_bt)
	public void onPhoneClick(){
		
	}
	@OnClick(id=R.id.opinion_rl)
	public void onOpinionClick(){
		
	}
	
	
}
