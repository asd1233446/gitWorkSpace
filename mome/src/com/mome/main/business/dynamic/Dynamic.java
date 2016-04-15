package com.mome.main.business.dynamic;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.jessieray.api.model.DynamicInfo;
import com.jessieray.api.model.GetArticleByUserId;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.GetArticleByUserIdRequest;
import com.jessieray.api.service.UserFavoriteRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.movie.MovieSearch;
import com.mome.main.business.userinfo.MyCollect;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.mome.view.RadiusDrawable;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

@SuppressLint("NewApi")
@LayoutInject(layout = R.layout.dynamic)
public class Dynamic extends BaseFragment implements OnCheckedChangeListener {

	/**
	 * 精品按钮
	 */
	@ViewInject(id = R.id.dynamic_btn_boutique)
	private RadioButton boutiqueBtn;
	/**
	 * 动态按钮
	 */
	@ViewInject(id = R.id.dynamic_btn_dynamic)
	private RadioButton dynamicBtn;
	@ViewInject(id = R.id.radioGroup)
	private RadioGroup radioGroup;
	
	@ViewInject(id=R.id.framelayout)
	private FrameLayout frameLayout;
	
	private Fragment fragment;

	/**
	 * 当前tab索引
	 */
	private int curIndex = 1;


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		radioGroup.setOnCheckedChangeListener(this);
		dynamicBtn.setChecked(true);
		
	}


	@Override
	public void rightOnClick() {
		Tools.toastShow("右按钮点击");
		Tools.pushScreen(MovieSearch.class, null);
		
	}

	
	private void selectorStyle(int id) {
		RadiusDrawable drawableSelect_right = new RadiusDrawable(0, 30, 0, 30,
				true, getResources().getColor(R.color.mostgray));
		RadiusDrawable drawable_right = new RadiusDrawable(0, 30, 0, 30, true,
				getResources().getColor(R.color.white));
		drawable_right
				.setStrokeColor(getResources().getColor(R.color.mostgray));
		drawable_right.setStrokeWidth(2);
		RadiusDrawable drawableSelect_left = new RadiusDrawable(30, 0, 30, 0,
				true, getResources().getColor(R.color.mostgray));
		RadiusDrawable drawable_left = new RadiusDrawable(30, 0, 30, 0, true,
				getResources().getColor(R.color.white));
		drawable_left.setStrokeColor(getResources().getColor(R.color.mostgray));
		drawable_left.setStrokeWidth(2);
		switch (id) {
		case R.id.dynamic_btn_dynamic:
			fragment=new DynamicFrgment();
			dynamicBtn.setBackground(drawableSelect_right);
			boutiqueBtn.setBackground(drawable_left);
			break;
		case R.id.dynamic_btn_boutique:
			fragment=new BoutiqueFrgment();
			dynamicBtn.setBackground(drawable_right);
			boutiqueBtn.setBackground(drawableSelect_left);
			break;
		default:
			break;
		}
		 getActivity().getSupportFragmentManager().beginTransaction()
		 .replace(R.id.framelayout, fragment).commit();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		Log.e("onCheckedChanged", checkedId + "");
		selectorStyle(checkedId);
	}
}
