package com.mome.main.business.record;

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
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.Tools;
import com.mome.main.core.utils.Tools.callBack;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

@LayoutInject(layout = R.layout.select_cinema)
public class SelectCinema extends BaseFragment implements OnChildClickListener {

	/**
	 * 搜索框
	 */
	@ViewInject(id = R.id.select_cinema_search_edittext)
	private EditText search;
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
	private String cityName = "北京";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setUpListview();

	}

	private void setUpListview() {

		adapter = new ExpandListAdapter();
		adapter.setDataList(dataList);
		adapter.setGroupDataList(groupList);
		listView.setAdapter(adapter);
		listView.expandGroup(0);
		listView.setOnChildClickListener(this);
		Tools.setCallListener(new callBack() {

			@Override
			public void Back(Object params) {
				// TODO Auto-generated method stub
				cityName = (String) params;
				headView.btnRight.setText(cityName);
				getUserRecall();
				groupList.clear();
				dataList.clear();
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
		Bundle bundle = new Bundle();
		bundle.putSerializable("cinemaInfo", info);
		Tools.toastShow(info.getTitle());
		Tools.pushScreen(MovieList.class, bundle);
		return false;
	}

	@Override
	public void rightOnClick() {
		// TODO Auto-generated method stub
		Tools.pushScreen(SelectCity.class, null);
		super.rightOnClick();
	}

}
