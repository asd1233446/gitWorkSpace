package com.mome.main.business.record;

import com.mome.main.R;
import com.mome.main.business.movie.MovieMemoirsAdapter;
import com.mome.main.business.widget.coverflow.FancyCoverFlow;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.MainActivity;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.TextView;

@LayoutInject(layout = R.layout.add_record)
public class AddRecord extends BaseFragment {

	/**
	 * 滑动时间轴
	 */
	@ViewInject(id = R.id.add_record_coverflow)
	private FancyCoverFlow fancyCoverFlow;
	/**
	 * 添加方式名称
	 */
	@ViewInject(id = R.id.add_record_title)
	private TextView title;
	/**
	 * 添加方式说明
	 */
	@ViewInject(id = R.id.add_record_context)
	private TextView addTypeInfo;
	/**
	 * 滑动控件容器
	 */
	private MovieMemoirsAdapter movieMemoirsAdapter;
	/**
	 * 当前选择上传方式索引
	 */
	private int curIndex;
	/**
	 * 上传方式说明
	 */
	private final String[] TITLE_ITEM = new String[]{"手动输入添加","上传照片添加"};
	private final String[] TYPE_ITEM = new String[]{"即刻生成电影记录卡,无需等待.","将电影票照片传往后台进行网络扫描,大致需要10-15分钟时间."};
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		movieMemoirsAdapter = new MovieMemoirsAdapter();
		movieMemoirsAdapter.setImges(new int[]{R.drawable.add_record_btn_manual_normal,R.drawable.add_record_btn_camera_normal});
        this.fancyCoverFlow.setAdapter(movieMemoirsAdapter);
        this.fancyCoverFlow.setUnselectedAlpha(1.0f);
        this.fancyCoverFlow.setUnselectedSaturation(1.0f);
        this.fancyCoverFlow.setUnselectedScale(1.0f);
        this.fancyCoverFlow.setSpacing(25);
        this.fancyCoverFlow.setMaxRotation(0);
        this.fancyCoverFlow.setScaleDownGravity(0.2f);
        this.fancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
        this.fancyCoverFlow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	
			}
		});
        fancyCoverFlow.setCallbackDuringFling(false);
        this.fancyCoverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				curIndex = arg2;
//				if(arg2 == 0) {
//					movieMemoirsAdapter.getItem(arg2);
//					((FancyCoverFlowItemWrapper)arg1).setImageResource(R.drawable.add_record_btn_manual_pressed);
//				} else if(arg2 == 1) {
//					((FancyCoverFlowItemWrapper)arg1).setImageResource(R.drawable.add_record_btn_camera_pressed);
//				}
				title.setText(TITLE_ITEM[curIndex]);
				addTypeInfo.setText(TYPE_ITEM[curIndex]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				if(curIndex == 0) {
					((ImageView)arg0.getChildAt(1)).setImageResource(R.drawable.add_record_btn_camera_normal);
				} else {
					((ImageView)arg0.getChildAt(0)).setImageResource(R.drawable.add_record_btn_manual_normal);
				}
			}
		});
	}
	
	/**
	 * 添加按钮点击
	 */
	@OnClick(id = R.id.add_record_btn_ok)
	public void okClick(View view) {
		if(curIndex == 1) {//拍照
			Intent intent = new Intent();
//			intent.setClass(AppConfig.mainActivity, CaptureActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, MainActivity.SCANNIN_GREQUEST_CODE);
		} else {//手动添加
			
		}
	}
}
