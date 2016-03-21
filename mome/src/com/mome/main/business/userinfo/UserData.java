package com.mome.main.business.userinfo;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mome.main.R;
import com.mome.main.business.module.ViewPagerAdapter;
import com.mome.main.business.widget.digitalcloud.DigitalCloudView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;

@LayoutInject(layout = R.layout.user_data)
public class UserData extends BaseFragment {

	/**
	 * 中间滑动页面
	 */
	@ViewInject(id = R.id.user_data_viewpager)
	private ViewPager viewPager;
	
	private ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter();
	/**
	 * 页面容器
	 */
	private ArrayList<View> pageViewList = new ArrayList<View>();
	/**
	 * 页面布局id
	 */
	private int[] pageLayoutId = new int[]{R.layout.user_data_look_num, R.layout.user_data_grade, R.layout.user_data_digital_cloud, R.layout.user_data_expenditure_time};
	/**
	 * 页指示器
	 */
	@ViewInject(id = R.id.user_data_indicator_one)
	private ImageView indicatorOne;
	@ViewInject(id = R.id.user_data_indicator_two)
	private ImageView indicatorTwo;
	@ViewInject(id = R.id.user_data_indicator_three)
	private ImageView indicatorThree;
	@ViewInject(id = R.id.user_data_indicator_four)
	private ImageView indicatorFour;
	/**
	 * 数字云视图
	 */
	private DigitalCloudView digitalCloudView;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		createPage();
		viewPager.setAdapter(viewPagerAdapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				updateIndicator(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}
	
	private void updateIndicator(int index) {
		indicatorOne.setImageResource(R.drawable.newuser_guide_img_indicator_one);
		indicatorTwo.setImageResource(R.drawable.newuser_guide_img_indicator_one);
		indicatorThree.setImageResource(R.drawable.newuser_guide_img_indicator_one);
		indicatorFour.setImageResource(R.drawable.newuser_guide_img_indicator_one);
		switch(index) {
		case 0:
			indicatorOne.setImageResource(R.drawable.newuser_guide_img_indicator_two);
			break;
		case 1:
			indicatorTwo.setImageResource(R.drawable.newuser_guide_img_indicator_two);
			break;
		case 2:
			indicatorThree.setImageResource(R.drawable.newuser_guide_img_indicator_two);
			break;
		case 3:
			indicatorFour.setImageResource(R.drawable.newuser_guide_img_indicator_two);
			break;
		}
	}
	
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
			} else if(i == 3) {
				createFourthPage(view);
			}
			pageViewList.add(view);
		}
		
		viewPagerAdapter.setList(pageViewList);
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
			digitalCloudView = (DigitalCloudView) view.findViewById(R.id.user_data_digital_cloud_view);
			ArrayList<String> data = new ArrayList<String>();
			data.add("剧情");
			data.add("爱情");
			data.add("喜剧");
			data.add("犯罪");
			data.add("悬疑");
			data.add("动作");
			data.add("传记");
			digitalCloudView.setData(data);
		}
	}
	
	/**
	 * 创建第四页
	 * @param view
	 */
	private void createFourthPage(View view) {
		if(view != null) {

		}
	}
}
