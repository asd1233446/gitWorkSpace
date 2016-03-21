package com.mome.main.business.record;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.mome.main.R;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;

@LayoutInject(layout = R.layout.record)
public class Record extends BaseFragment{

	/**
	 * 电影院记录按钮
	 */
	@ViewInject(id = R.id.record_cinema_btn)
	private ImageView cinemaBtn;
	/**
	 * 电影院标题
	 */
	@ViewInject(id = R.id.record_cinema_title)
	private TextView cinemaTitle;
	/**
	 * 网络观影记录按钮
	 */
	@ViewInject(id = R.id.record_net_btn)
	private ImageView netBtn;
	/**
	 * 网络标题
	 */
	@ViewInject(id = R.id.record_net_title)
	private TextView netTitle;
	/**
	 * 电视记录按钮
	 */
	@ViewInject(id = R.id.record_tv_btn)
	private ImageView tvBtn;
	/**
	 * 电视标题
	 */
	@ViewInject(id = R.id.record_tv_title)
	private TextView tvTitle;
	/**
	 * 观影记录
	 */
	@ViewInject(id = R.id.record_title)
	private TextView title;
	/**
	 * 动画资源
	 */
	private Animation transAni;
	private Animation scaleAni;
	private Animation animationSet;
	private Animation alphaAni;
	
	private Animation netTransAni;
	private Animation netScaleAni;
	private Animation netAnimationSet;
	private Animation netAlphaAni;
	
	private Animation tvTransAni;
	private Animation tvScaleAni;
	private Animation tvAnimationSet;
	private Animation tvAlphaAni;
	private Animation titleAni;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		initAni();
		super.onResume();
	}

	public void initAni() {
		if(transAni == null) {
			transAni =  Tools.getTranslateAni(500, 0, 0, 0, AppConfig.SCREEN_HEIGHT-cinemaBtn.getTop(), cinemaBtn.getTop(), new AnticipateOvershootInterpolator());
			scaleAni = Tools.getScaleAnimation(300,200,new DecelerateInterpolator(),0.0f,1.0f);
			alphaAni = Tools.getAlphaAni(300, 0, 0.0f, 1.0f);
			animationSet = Tools.getGroupAni(transAni,scaleAni);
			animationSet.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					cinemaTitle.clearAnimation();
					cinemaTitle.startAnimation(alphaAni);
				}
			});
		}
		
		if(netTransAni == null) {
			netTransAni =  Tools.getTranslateAni(500, 100, netBtn.getLeft(), netBtn.getLeft(), AppConfig.SCREEN_HEIGHT-netBtn.getTop(), netBtn.getTop(), new AnticipateOvershootInterpolator());
			netScaleAni = Tools.getScaleAnimation(300,300,new DecelerateInterpolator(),0.0f,1.0f);
			netAlphaAni = Tools.getAlphaAni(300, 0, 0.0f, 1.0f);
			titleAni = Tools.getTranslateAni(500, 80, AppConfig.SCREEN_WIDTH, netBtn.getLeft(), 0, 0, new AnticipateOvershootInterpolator());
			netAnimationSet = Tools.getGroupAni(netTransAni,netScaleAni);
			netAnimationSet.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					netTitle.clearAnimation();
					netTitle.startAnimation(netAlphaAni);
					title.clearAnimation();
					title.startAnimation(titleAni);
				}
			});
		}
		
		if(tvTransAni == null) {
			tvTransAni =  Tools.getTranslateAni(500, 200, tvBtn.getLeft(), tvBtn.getLeft(), AppConfig.SCREEN_HEIGHT-tvBtn.getTop(), tvBtn.getTop(), new AnticipateOvershootInterpolator());
			tvScaleAni = Tools.getScaleAnimation(300,400,new DecelerateInterpolator(),0.0f,1.0f);
			tvAlphaAni = Tools.getAlphaAni(300, 0, 0.0f, 1.0f);
			tvAnimationSet = Tools.getGroupAni(tvTransAni,tvScaleAni);
			tvAnimationSet.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					tvTitle.clearAnimation();
					tvTitle.startAnimation(tvAlphaAni);
				}
			});
		}
		
		title.clearAnimation();
		cinemaTitle.clearAnimation();
		netTitle.clearAnimation();
		tvTitle.clearAnimation();
		cinemaBtn.clearAnimation();
		netBtn.clearAnimation();
		tvBtn.clearAnimation();
		cinemaBtn.startAnimation(animationSet);
		netBtn.startAnimation(netAnimationSet);
		tvBtn.startAnimation(tvAnimationSet);
	}
	
	/**
	 * 影院点击
	 * @param view
	 */
	@OnClick(id = R.id.record_cinema_btn)
	public void cinemaClick(View view) {
		Tools.pushScreen(AddRecord.class, null);
	}
	
	/**
	 * 网络点击
	 * @param view
	 */
	@OnClick(id = R.id.record_net_btn)
	public void netClick(View view) {
		
	}
	
	/**
	 * 电视点击
	 * @param view
	 */
	@OnClick(id = R.id.record_tv_btn)
	public void tvClick(View view) {
		
	}
}
