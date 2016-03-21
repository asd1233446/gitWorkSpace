package com.mome.main.business.module;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ViewPagerAdapter extends PagerAdapter {
	
	/**
	 * 视图列表
	 */
	private ArrayList<View> views;
    private LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
    		LinearLayout.LayoutParams.FILL_PARENT,
    		LinearLayout.LayoutParams.FILL_PARENT);
	
	public void setList(ArrayList<View> v){
		this.views = v;
	}
	
	@Override
	public int getCount() {
		return this.views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
    	if (views != null && position < views.size()){
    		container.removeView(views.get(position));        
    	}
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		if(views == null || views.isEmpty()) {
			return null;
		}
		container.addView(views.get(position),param);
		return views.get(position);
	}
}