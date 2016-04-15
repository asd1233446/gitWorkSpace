package com.mome.main.business;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.mome.main.R;
import com.mome.main.business.discovery.Discovery;
import com.mome.main.business.dynamic.Dynamic;
import com.mome.main.business.movie.Movie;
import com.mome.main.business.record.Record;
import com.mome.main.business.userinfo.UserInfo;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.utils.AppConfig;

/**
 * tab管理类
 * 
 */
public class TabManager extends BaseFragment {

	private FragmentTabHost mTabHost;
	/**
	 * tab子页面
	 */
	@SuppressWarnings("rawtypes")
	private Class[] mFragmentArray = { 
			Dynamic.class, 
			Movie.class,
			Record.class, 
			Discovery.class, 
			UserInfo.class 
			};
	/**
	 * tab上的标题
	 */
	private String mTextArray[] = { "动态", "电影", "", "发现", "我" };
	/**
	 * tab上的默认icon
	 */
	private int[] iconNormal = { 
			R.drawable.tabmanager_tab_dynamic_normal,
			R.drawable.tabmanager_tab_movie_normal,
			R.drawable.tabmanager_tab_record_normal,
			R.drawable.tabmanager_tab_discovery_normal, 
			R.drawable.tabmanager_tab_userinfo_normal 
			};
	/**
	 * 按下的icon
	 */
	private int[] iconPressed = { 			
			R.drawable.tabmanager_tab_dynamic_pressed,
			R.drawable.tabmanager_tab_movie_pressed,
			R.drawable.tabmanager_tab_record_pressed,
			R.drawable.tabmanager_tab_discovery_pressed, 
			R.drawable.tabmanager_tab_userinfo_pressed 
			};
	/**
	 * 布局填充器
	 */
	private LayoutInflater inflater;
	/**
	 * tabicon容器
	 */
	private List<ImageView> iconList = new ArrayList<ImageView>();
	/**
	 * tabtitle容器
	 */
	private List<TextView> textList = new ArrayList<TextView>();
	/**
	 * 记录页面布局
	 */
	public static  LinearLayout topRecordLayout;
	/**
	 * 记录页面实例
	 */
	private Record record;
	/**
	 * 记录tab的索引
	 */
	private final int RECORD_INDEX = 2;
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tabmanager, container,false);
		initView(view);
		record = new Record();
		record.onCreateView(inflater, container, savedInstanceState);
		FragmentTransaction trans = this.getFragmentManager().beginTransaction();
		trans.replace(R.id.tabmanager_record, record);
		trans.commit();
		return view;
	}
       
	/**
	 * 初始化组件
	 */
	private void initView(View view) {
		topRecordLayout = (LinearLayout) view.findViewById(R.id.tabmanager_record);
//		topRecordLayout.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				iconList.get(RECORD_INDEX).setImageResource(iconNormal[RECORD_INDEX]);
//				topRecordLayout.setVisibility(View.GONE);
//			}
//		});
		mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
		mTabHost.setup(this.getActivity(), getChildFragmentManager(), R.id.realtabcontent);
		inflater = LayoutInflater.from(AppConfig.context);
		// 得到fragment的个数
		for (int i = 0; i < mFragmentArray.length; i++) {
			// 给每个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i]).setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, mFragmentArray[i], null);
		}
		mTabHost.setCurrentTab(0);
		updateTab();
		mTabHost.setTag(new Integer(0));
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				if(!tabId.equals(mTextArray[2])) {
					updateTab();
					mTabHost.setTag(new Integer(mTabHost.getCurrentTab()));
				} else {			
					showRecord();
					mTabHost.setCurrentTab((Integer)mTabHost.getTag());
				}
			}
		});
	}
	
	/**
	 * 显示记录页面
	 */
	private void showRecord() {
		iconList.get(RECORD_INDEX).setImageResource(iconPressed[RECORD_INDEX]);
		topRecordLayout.setVisibility(View.VISIBLE);
		record.initAni();
	}
	
	/**
	 * 更新tab点击后状态
	 */
	private void updateTab() {
		for(int i = 0; i<iconList.size(); i++) {
			if(i == mTabHost.getCurrentTab()) {
				textList.get(i).setTextColor(this.getResources().getColor(R.color.tabTitlePressed));
				iconList.get(i).setImageResource(iconPressed[i]);
			} else {
				textList.get(i).setTextColor(this.getResources().getColor(R.color.tabTitleDefault));
				iconList.get(i).setImageResource(iconNormal[i]);
			}
		}
	}
	
	/**
	 * 创建tab布局
	 * @param index
	 * @return
	 */
	private View getTabItemView(int index) {
		View view = inflater.inflate(R.layout.tabmanager_tab_item_view,null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(iconNormal[index]);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTextArray[index]);
		iconList.add(imageView);
		textList.add(textView);
		if(index == RECORD_INDEX) {
			textView.setVisibility(View.GONE);
		}
		return view;
	}
}
