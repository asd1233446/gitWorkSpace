package com.mome.main.business.record;

import com.mome.main.R;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ExpandableListView;

@LayoutInject(layout = R.layout.select_cinema)
public class SelectCinema extends BaseFragment {

	/**
	 *  搜索框
	 */
	@ViewInject(id = R.id.select_cinema_search_edittext)
	private EditText search;
	/**
	 * 影院列表
	 */
	@ViewInject(id = R.id.select_cinema_list)
	private ExpandableListView listView;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	

}
