package com.mome.main.business.userinfo;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;

import com.mome.main.R;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.Tools;
@LayoutInject(layout=R.layout.myphotos)
public class MyPhoto extends BaseFragment {
	/**
	 * 相册
	 * 
	 * */
@ViewInject(id=R.id.myPhotosGridView)
private RecyclerView myPhotosGridView;

@Override
public void onActivityCreated(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onActivityCreated(savedInstanceState);

}




}
