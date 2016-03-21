package com.mome.main.business.module;

import android.view.View;

public abstract class ExpandListCellBase {
	
	/**
	 * 获取组视图
	 * @param convertView
	 * @return
	 */
	public abstract View getGroupView(View convertView);
	
	/**
	 * 获取子视图
	 * @param convertView
	 * @return
	 */
	public abstract View getChildView(View convertView);
}
