package com.mome.main.business.widget.timeshaft;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mome.main.R;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;

public class CustomTimeShaft extends LinearLayout implements OnTouchListener{

	/**
	 * 日期容器
	 */
	private LinearLayout dateLayout;
	/**
	 * 选择器
	 */
	private LinearLayout selector;
	/**
	 * 选择显示日期
	 */
	private TextView curdate;
	private Context context;
	private ImageView curSelectorImg;
	/**
	 * 横滑控件
	 */
	private HorizontalScrollView horizontalScroll;
	/**
	 * 日期
	 */
	private List<String> dateArray = new ArrayList<String>();
	/**
	 * 日期索引
	 */
	private int index = 0;
	/**
	 * 坐标参数
	 */
	private int lastX;
	private int lastY;
	private int itemWidth;
	private boolean isScroll;
	/**
	 * 滑动布局参数
	 */
	private RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-2,-2);
	private ItemSelectedListener itemSelectedListener;
	
	
	public CustomTimeShaft(Context context) {
		super(context);
		this.context = context;
		initView();
	}
	
	public CustomTimeShaft(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	private void initView() {
		View view = LayoutInflater.from(context).inflate(R.layout.custom_time_shaft, this);
		horizontalScroll = (HorizontalScrollView) view.findViewById(R.id.custom_time_shaft_horizontalScrollView);
		dateLayout = (LinearLayout) view.findViewById(R.id.custom_time_shaft_date_layout);
		selector = (LinearLayout) view.findViewById(R.id.custom_time_shaft_selector);
		curSelectorImg = (ImageView) view.findViewById(R.id.custom_time_shaft_img);
		curdate = (TextView) view.findViewById(R.id.custom_time_shaft_curdate);
		selector.setOnTouchListener(this);
	}

	public List<String> getDateArray() {
		return dateArray;
	}

	public void setDateArray(List<String> dateArray) {
		this.dateArray = dateArray;
		if(this.dateArray == null || this.dateArray.isEmpty()) {
			return;
		}
		createDateLayout();
		curdate.setText(dateArray.get(0));
	}

	private void createDateLayout() {
		if(dateLayout.getChildCount() > 0) {
			dateLayout.removeAllViews();
		}
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.FILL_PARENT);
		lp.weight = 1;
		for(int i = 0; i<dateArray.size(); i++) {
			View view = LayoutInflater.from(context).inflate(R.layout.custom_time_shaft_item, null);
			ImageView img = (ImageView) view.findViewById(R.id.custom_time_shaft_item_img);
			img.setImageResource(R.drawable.ic_launcher);
			itemWidth = img.getWidth();
			dateLayout.addView(view,lp);
		}
		itemWidth = 50;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = (int) event.getRawX();
			lastY = (int) event.getRawY();
			itemWidth = dateLayout.getChildAt(0).getWidth();
			break;
		case MotionEvent.ACTION_MOVE:
			int dx = (int) event.getRawX() - lastX;
			int dy = (int) event.getRawY() - lastY;

			int left = v.getLeft() + dx;
			int top = v.getTop();
			if (left <= 0) {
				left = 0;
				isScroll = true;
			}
			if (left >= AppConfig.SCREEN_WIDTH - v.getWidth()) {
				left = AppConfig.SCREEN_WIDTH - v.getWidth();
				isScroll = true;
			}
			if (top < 0) {
				top = 0;
			}
			lp.leftMargin = left;
			lp.topMargin = top;
			v.setLayoutParams(lp);
			index = Math.abs(left/itemWidth);
			curdate.setText(dateArray.get(index));
			lastX = (int) event.getRawX();
			lastY = (int) event.getRawY();
			if(isScroll) {
				if(left <= 0) {
					horizontalScroll.arrowScroll(View.FOCUS_LEFT);
				} else if(left >= AppConfig.SCREEN_WIDTH - v.getWidth()){
					horizontalScroll.arrowScroll(View.FOCUS_RIGHT);
				} else {
					isScroll = false;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			index = Math.abs(lastX/itemWidth);
			setSelector(index);
			isScroll = false;
			break;
		}
		return false;
	}

	/**
	 * 设置当前选中的日期项
	 * @param index
	 */
	public void setSelector(int index) {
		lp.leftMargin = dateLayout.getChildAt(index).getLeft();
		lp.topMargin = selector.getTop();
		selector.setLayoutParams(lp);
		curdate.setText(dateArray.get(index));
		if(itemSelectedListener != null) {
			itemSelectedListener.itemSelected(index);
		}
	}
	
	public ItemSelectedListener getItemSelectedListener() {
		return itemSelectedListener;
	}

	public void setItemSelectedListener(ItemSelectedListener itemSelectedListener) {
		this.itemSelectedListener = itemSelectedListener;
	}

	/**
	 * 选中监听
	 *
	 */
	public interface ItemSelectedListener {
		public void itemSelected(int index);
	}
}
