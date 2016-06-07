package com.mome.main.business.record;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.jessieray.api.model.CinemaInfo;
import com.jessieray.api.model.CinemaListByCity;
import com.jessieray.api.model.DistrictsInfo;
import com.jessieray.api.model.MayInterestIn;
import com.jessieray.api.model.UserInfo;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.CinemaListByCityRequest;
import com.jessieray.api.service.MayInterestInRequest;
import com.mome.main.R;
import com.mome.main.business.discovery.InterestFriendAdapter;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ExpandListAdapter;
import com.mome.main.business.module.ExpandListCellBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshExpandableListView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.MainActivity;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.Tools;
import com.mome.main.core.utils.Tools.CallBack;
import com.mome.main.core.utils.Tools.CallInnerBack;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

@LayoutInject(layout = R.layout.select_cinema)
public class SelectCinema extends BaseFragment implements OnChildClickListener {
	List<CinemaInfo> fuzzySearch = new ArrayList<CinemaInfo>();

	/**
	 * 搜索框
	 */
	@ViewInject(id = R.id.select_cinema_search_edittext)
	private EditText search;

	@OnClick(id = R.id.select_cinema_search_edittext)
	public void onSearchClick(View view) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("list", (Serializable) fuzzySearch);
		Tools.pushScreen(CinemaSearch.class, bundle);
	}

	/**
	 * 影院列表
	 */
	@ViewInject(id = R.id.select_cinema_list)
	private ExpandableListView listView;

	/** 列表对应的Adapter **/
	private ExpandListAdapter adapter;

	/** 分组集合 */
	private ArrayList<ExpandListCellBase> groupList = new ArrayList<ExpandListCellBase>();

	/** 分组下集合 */
	private ArrayList<List<ExpandListCellBase>> dataList = new ArrayList<List<ExpandListCellBase>>();
	private String cityName = MainActivity.cityName;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setUpListview();

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		headView.btnRight.setText(cityName);
		super.onStart();
	}

	private void setUpListview() {
		adapter = new ExpandListAdapter();
		adapter.setDataList(dataList);
		adapter.setGroupDataList(groupList);
		listView.setAdapter(adapter);
		listView.expandGroup(0);
		listView.setOnChildClickListener(this);
		Tools.setCallInnerListener(new CallInnerBack() {
			@Override
			public void Back(Object params) {
				// TODO Auto-generated method stub
				cityName = (String) params;
				headView.btnRight.setText(cityName);
				getUserRecall();
			}
		});
		getUserRecall();
	}

	private void getUserRecall() {
		CinemaListByCityRequest.findCinemaListByCity(UserProperty.getInstance()
				.getUid(), cityName, this);
	}

	@Override
	public <T> void sucess(Type arg0, ResponseResult<T> arg1) {
		// TODO Auto-generated method stub
		super.sucess(arg0, arg1);
		fuzzySearch.clear();
		groupList.clear();
		dataList.clear();

		CinemaListByCity cinema = arg1.getModel();
		if (cinema.getMycinemas().size() > 0) {
			SelectCinemaListCell cell = new SelectCinemaListCell();
			DistrictsInfo info = new DistrictsInfo();
			info.setDistrict("常去的影院");
			info.setTotal(cinema.getMycinemas().size());
			cell.setDistance(info);
			groupList.add(cell);
			ArrayList<ExpandListCellBase> childList = new ArrayList<ExpandListCellBase>();
			for (CinemaInfo cinemainfo : cinema.getMycinemas()) {
				cell = new SelectCinemaListCell();
				cell.setInfo(cinemainfo);
				childList.add(cell);
				fuzzySearch.add(cinemainfo);
			}
			dataList.add(childList);
		}
		if (cinema.getDistricts().size() > 0) {
			for (DistrictsInfo distanceinfo : cinema.getDistricts()) {
				SelectCinemaListCell cell = new SelectCinemaListCell();
				cell.setDistance(distanceinfo);
				groupList.add(cell);
				ArrayList<ExpandListCellBase> childList = new ArrayList<ExpandListCellBase>();
				for (CinemaInfo cinemainfo : distanceinfo.getCinemas()) {
					cell = new SelectCinemaListCell();
					cell.setInfo(cinemainfo);
					childList.add(cell);
					fuzzySearch.add(cinemainfo);
				}
				dataList.add(childList);
			}
		}
		Tools.toastShow("总数" + dataList.size());
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub

		SelectCinemaListCell cell = (SelectCinemaListCell) adapter.getChild(
				groupPosition, childPosition);
		CinemaInfo info = cell.getInfo();
		Tools.getCallListener().Back(info);
		Tools.pullScreen();
		return false;
	}

	@Override
	public void rightOnClick() {
		// TODO Auto-generated method stub
		Tools.pushScreen(SelectCity.class, null);
		super.rightOnClick();
	}

}
