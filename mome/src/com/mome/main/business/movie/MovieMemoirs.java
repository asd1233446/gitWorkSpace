package com.mome.main.business.movie;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mome.main.R;
import com.mome.main.business.widget.coverflow.FancyCoverFlow;
import com.mome.main.business.widget.timeshaft.CenteringHorizontalScrollView;
import com.mome.main.business.widget.timeshaft.CustomTimeShaft.ItemSelectedListener;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;

/**
 * 电影回忆录
 *
 */
@LayoutInject(layout = R.layout.movie_memoirs)
public class MovieMemoirs extends BaseFragment implements ItemSelectedListener{

	/**
	 * 滑动时间轴
	 */
	@ViewInject(id = R.id.movie_memoirs_fancyCoverFlow)
	private FancyCoverFlow fancyCoverFlow;
	/**
	 * 中心选中文字
	 */
	@ViewInject(id = R.id.movie_memoirs_selector)
	private TextView selector;
	/**
	 * 日期容器
	 */
	private List<String> dateArray = new ArrayList<String>();
	/**
	 * 回忆卡片容器
	 */
	@ViewInject(id = R.id.movie_memoirs_card)
	private LinearLayout cardLayout;
	/**
	 * 横向滑动布局
	 */
	@ViewInject(id = R.id.movie_memoirs_card_layout)
	private CenteringHorizontalScrollView centerHScrollView;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		centerHScrollView.setItemSelectedListener(this);
        this.fancyCoverFlow.setAdapter(new MovieMemoirsAdapter());
        this.fancyCoverFlow.setUnselectedAlpha(1.0f);
        this.fancyCoverFlow.setUnselectedSaturation(1.0f);
        this.fancyCoverFlow.setUnselectedScale(1.0f);
        this.fancyCoverFlow.setSpacing(25);
        this.fancyCoverFlow.setMaxRotation(0);
        this.fancyCoverFlow.setScaleDownGravity(0.2f);
        this.fancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
        this.fancyCoverFlow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				selector.setText(dateArray.get(arg2));
				centerHScrollView.setCurrentItemAndCenter(arg2);
			}
		});
        fancyCoverFlow.setCallbackDuringFling(false);
        this.fancyCoverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				selector.setText(dateArray.get(arg2));
				centerHScrollView.setCurrentItemAndCenter(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        createCard();
	}

	private void createCard() {//传model
		dateArray.add("20150611");
		dateArray.add("20150612");
		dateArray.add("20150613");
		dateArray.add("20150614");
		dateArray.add("20150615");
		dateArray.add("20150616");
		cardLayout.removeAllViews();
		LinearLayout.LayoutParams imageParams;
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2,-2);
		lp.leftMargin = lp.rightMargin = 25;
		for(int i = 0; i<dateArray.size(); i++) {
			View view = AppConfig.INFLATER.inflate(R.layout.movie_memoirs_card,null);
			if(i == 0) {
				imageParams = new LinearLayout.LayoutParams(centerHScrollView.CENTER_W,centerHScrollView.CENTER_H);
			} else {
				imageParams = new LinearLayout.LayoutParams(centerHScrollView.CENTER_W-centerHScrollView.CENTER_OFFSET_SIZE,centerHScrollView.CENTER_H-centerHScrollView.CENTER_OFFSET_SIZE);
			}
			view.setLayoutParams(imageParams);
			cardLayout.addView(view,lp);
		}
		centerHScrollView.setCurrentItemAndCenter(dateArray.size()-1);
        fancyCoverFlow.setSelection(dateArray.size()-1);
	}
	
	@Override
	public void itemSelected(int index) {
		this.fancyCoverFlow.setSelection(index,true);
	}
	
}
