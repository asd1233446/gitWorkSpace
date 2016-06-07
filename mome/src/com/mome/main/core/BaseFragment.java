package com.mome.main.core;

import java.lang.reflect.Type;

import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.mome.main.R;
import com.mome.main.business.HeadRef;
import com.mome.main.business.HeadView;
import com.mome.main.business.HeadView.HeadViewBtnOnClickListener;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.utils.Tools;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment implements HeadViewBtnOnClickListener,ResponseCallback{
	
	/**
	 * 页面视图
	 */
	private View rootView;
	/**
	 * 头布局
	 */
	public HeadView headView;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Tools.Log("=========onActivityCreated============"+this.getClass().getName());
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Tools.Log("=========onAttach============"+this.getClass().getName());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Tools.Log("=========onCreate============"+this.getClass().getName());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Tools.Log("=========onCreateView============"+this.getClass().getName());
		if(rootView == null) {
			rootView = InjectProcessor.injectFragmentLayout(this,inflater, container);
			InjectProcessor.injectFragment(this, rootView);
			Tools.Log("=========添加headview============");
			addHeadView(rootView);
		} else {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null) {
				parent.removeView(rootView);
			}
		}
		rootView.setClickable(true);
		return rootView;
	}
	
	/**
	 * 添加头布局
	 * @param view
	 */
	private void addHeadView(View view) {
		if(view != null) {
			HeadRef headRef = (HeadRef)rootView.findViewById(R.id.head_layout);
			if(headRef != null) {
				headView = new HeadView(headRef,this);
				FragmentTransaction transaction = this.getChildFragmentManager().beginTransaction();
				transaction.replace(R.id.head_layout, headView);
				transaction.commit();
			}
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Tools.Log("=========onDestroy============"+this.getClass().getName());
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Tools.Log("=========onDestroyView============"+this.getClass().getName());
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Tools.Log("=========onDetach============"+this.getClass().getName());
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Tools.Log("=========onPause============"+this.getClass().getName());
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Tools.Log("=========onResume============"+this.getClass().getName());
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Tools.Log("=========onStart============"+this.getClass().getName());
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Tools.Log("=========onStop============"+this.getClass().getName());
	}

	@Override
	public void leftOnClick() {
		Tools.pullScreen();
	}

	@Override
	public void rightOnClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editTextChange(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(ResponseError arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public <T> void sucess(Type arg0, ResponseResult<T> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRefreshNeeded() {
		// TODO Auto-generated method stub
		return false;
	}
}
