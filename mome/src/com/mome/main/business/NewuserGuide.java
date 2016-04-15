package com.mome.main.business;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.mome.main.R;
import com.mome.main.business.access.Login;
import com.mome.main.business.module.ViewPagerAdapter;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;

/**
 * 引导页面
 *
 */
@LayoutInject(layout = R.layout.newuser_guide)
public class NewuserGuide extends BaseFragment implements OnClickListener {
	
	/**
	 * 滑动容器
	 */
	@ViewInject(id = R.id.newuser_guide_viewpager)
	private ViewPager viewPager;
	
	/**
	 * 页指示器
	 */
	@ViewInject(id = R.id.newuser_guide_indicator)
	private ImageView indicator;
	
	/**
	 * 容器页面代理
	 */
	private ViewPagerAdapter newuserPageAdapter = new ViewPagerAdapter();
	
	/**
	 * 页面容器
	 */
	private ArrayList<View> pageViewList = new ArrayList<View>();
	
	/**
	 * 页面布局id
	 */
	private int[] pageLayoutId = new int[]{R.layout.newuser_guide_one, R.layout.newuser_guide_two, R.layout.newuser_guide_three};

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		createPage();
		viewPager.setAdapter(newuserPageAdapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				if(arg0 == 0) {
					indicator.setVisibility(View.VISIBLE);
					indicator.setImageResource(R.drawable.newuser_guide_img_indicator_one);
				} else if(arg0 == 1) {
					indicator.setVisibility(View.VISIBLE);
					indicator.setImageResource(R.drawable.newuser_guide_img_indicator_two);
				} else {
					indicator.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}


	/**
	 * 构建页面
	 */
	private void createPage() {
		View view = null;
		for(int i = 0; i<pageLayoutId.length; i++) {
			view = AppConfig.INFLATER.inflate(pageLayoutId[i], null);
			if(i == 0) {
				createFirstPage(view);
			} else if(i == 1) {
				createSecondPage(view);
			} else if(i == 2) {
				createThirdPage(view);
			}
			pageViewList.add(view);
		}
		
		newuserPageAdapter.setList(pageViewList);
	}
	
	/**
	 * 创建第一页
	 * @param view
	 */
	private void createFirstPage(View view) {
		if(view != null) {

		}
	}
	
	/**
	 * 创建第二页
	 * @param view
	 */
	private void createSecondPage(View view) {
		if(view != null) {

		}
	}
	
	/**
	 * 创建第三页
	 * @param view
	 */
	private void createThirdPage(View view) {
		if(view != null) {
			Button enterBtn = (Button) view.findViewById(R.id.button1);
			enterBtn.setOnClickListener(this);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		pageViewList.clear();
		pageViewList = null;
		newuserPageAdapter = null;
		pageLayoutId = null;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button1:
			Tools.replaceCurScreen(Login.class, null);
			break;
		}
	}

}
