package com.mome.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ListViewInScrollView extends ListView {
	/**
	 * @deprecated 用于ListView必须嵌入ScrollView时使用,重写了onMeasure()
	 * @author haoxiqiang
	 */
	public ListViewInScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	/**
	 * 重写ListView与GridView，让其失去滑动特性
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}