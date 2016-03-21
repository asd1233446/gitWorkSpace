package com.mome.main.business.dynamic;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.jessieray.api.model.DynamicInfo;
import com.jessieray.api.model.GetArticleByUserId;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.GetArticleByUserIdRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

@LayoutInject(layout = R.layout.dynamic)
public class Dynamic extends BaseFragment {
	
	/**
	 * 精品按钮
	 */
	@ViewInject(id = R.id.dynamic_btn_boutique)
	private TextView boutiqueBtn;
	/**
	 * 动态按钮
	 */
	@ViewInject(id = R.id.dynamic_btn_dynamic)
	private TextView dynamicBtn;
	/**
	 * 附近按钮
	 */
	@ViewInject(id = R.id.dynamic_btn_nearby)
	private TextView nearbyBtn;
	/**
	 * listView实例
	 */
	private ListView listView;
	/**
	 * 下拉刷新组件
	 */
	@ViewInject(id = R.id.pull_refresh_list)
	private PullToRefreshListView mPullRefreshListView;
	/**
	 * 列表数据容器
	 */
	private ListAdapter adapter;
	/**
	 * 列表数据
	 */
	private ArrayList<ListCellBase> boutiqueListData = new ArrayList<ListCellBase>();
	private ArrayList<ListCellBase> dynamicListData = new ArrayList<ListCellBase>();
	private ArrayList<ListCellBase> nearbyListData = new ArrayList<ListCellBase>();
	/**
	 * 按钮的索引
	 */
	private final int BTN_LEFT = 1;
	private final int BTN_MIDDLE = 2;
	private final int BTN_RIGHT = 3;
	/**
	 * 当前tab索引
	 */
	private int curIndex = 1;
	/**
	 * 当前页索引
	 */
	private int curPageIndex = 0;
	/**
	 * 总页数
	 */
	private int totalPage = 0;
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPullRefreshListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(AppConfig.context, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				GetArticleByUserIdRequest.findGetArticleByUserId(UserProperty.getInstance().getUid(), String.valueOf(curIndex), 0, AppConfig.PAGE_SIZE, Dynamic.this);
//				MomentsRequest.findMomentsByTabType(, 0, AppConfig.PAGE_SIZE, curIndex, Dynamic.this);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(AppConfig.context, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				if(curPageIndex < totalPage) {
					curPageIndex++;
//					MomentsRequest.findMomentsByTabType(UserProperty.getInstance().getUid(), curPageIndex, AppConfig.PAGE_SIZE, curIndex, Dynamic.this);
					GetArticleByUserIdRequest.findGetArticleByUserId(UserProperty.getInstance().getUid(), String.valueOf(curIndex), curPageIndex, AppConfig.PAGE_SIZE, Dynamic.this);
					mPullRefreshListView.setMode(Mode.BOTH);
				} else {
					mPullRefreshListView.setMode(Mode.PULL_FROM_START);
				}
			}
		});
		mPullRefreshListView.setMode(Mode.BOTH);
		listView = mPullRefreshListView.getRefreshableView();
		adapter = new ListAdapter();
		adapter.setDataList(dynamicListData);
		listView.setAdapter(adapter);
		dynamicClick(null);
	}

	/**
	 * 恢复按钮状态
	 */
	private void updateBtn(int index) {
		boutiqueBtn.setTextColor(this.getActivity().getResources().getColor(R.color.dynamicTextNormal));
		dynamicBtn.setTextColor(this.getActivity().getResources().getColor(R.color.dynamicTextNormal));
		nearbyBtn.setTextColor(this.getActivity().getResources().getColor(R.color.dynamicTextNormal));
//		boutiqueBtn.setBackgroundResource(R.drawable.bg_left_round_corner_rect);
//		dynamicBtn.setBackgroundResource(R.drawable.bg_left_round_corner_rect);
//		nearbyBtn.setBackgroundResource(R.drawable.bg_left_round_corner_rect);
		switch(index) {
		case BTN_LEFT:
			boutiqueBtn.setTextColor(this.getActivity().getResources().getColor(R.color.dynamicTextPressed));
			break;
		case BTN_MIDDLE:
			dynamicBtn.setTextColor(this.getActivity().getResources().getColor(R.color.dynamicTextPressed));
			break;
		case BTN_RIGHT:
			nearbyBtn.setTextColor(this.getActivity().getResources().getColor(R.color.dynamicTextPressed));
			break;
		}
	}
	
	/**
	 * 精品按钮点击
	 * @param view
	 */
	@OnClick(id = R.id.dynamic_btn_boutique)
	public void boutiqueClick(View view) {
		updateBtn(BTN_LEFT);
		curIndex = BTN_LEFT;
		adapter.setDataList(boutiqueListData);
		if(boutiqueListData.isEmpty()) {
			mPullRefreshListView.autoRefresh();
			GetArticleByUserIdRequest.findGetArticleByUserId(UserProperty.getInstance().getUid(), String.valueOf(curIndex), 0, AppConfig.PAGE_SIZE, Dynamic.this);

//			MomentsRequest.findMomentsByTabType(UserProperty.getInstance().getUid(), 0, AppConfig.PAGE_SIZE, curIndex, Dynamic.this);
		}
	}
	
	/**
	 * 动态按钮的点击
	 * @param view
	 */
	@OnClick(id = R.id.dynamic_btn_dynamic)
	public void dynamicClick(View view) {
		updateBtn(BTN_MIDDLE);
		curIndex = BTN_MIDDLE;
		adapter.setDataList(dynamicListData);
		if(dynamicListData.isEmpty()) {
			mPullRefreshListView.autoRefresh();
			GetArticleByUserIdRequest.findGetArticleByUserId(UserProperty.getInstance().getUid(), String.valueOf(curIndex), 0, AppConfig.PAGE_SIZE, Dynamic.this);

//			MomentsRequest.findMomentsByTabType(UserProperty.getInstance().getUid(), 0, AppConfig.PAGE_SIZE, curIndex, Dynamic.this);
		}
	}
	
	/**
	 * 附近按钮点击
	 * @param view
	 */
	@OnClick(id = R.id.dynamic_btn_nearby)
	public void nearbyClick(View view) {
		updateBtn(BTN_RIGHT);
		curIndex = BTN_RIGHT;
		adapter.setDataList(nearbyListData);
		if(nearbyListData.isEmpty()) {
			mPullRefreshListView.autoRefresh();
			GetArticleByUserIdRequest.findGetArticleByUserId(UserProperty.getInstance().getUid(), String.valueOf(curIndex), 0, AppConfig.PAGE_SIZE, Dynamic.this);

//			MomentsRequest.findMomentsByTabType(UserProperty.getInstance().getUid(), 0, AppConfig.PAGE_SIZE, curIndex, Dynamic.this);
		}
	}

	@Override
	public void rightOnClick() {
		Tools.toastShow("右按钮点击");
	}

	/**
	 * 更新动态信息
	 * @param list
	 */
	private void addComment(List<DynamicInfo> list) {
		if(list == null || list.isEmpty()) {
			return;
		}
		
		if(curPageIndex == 1) {
			if(curIndex == BTN_LEFT) {
				boutiqueListData.clear();
			} else if(curIndex == BTN_MIDDLE) {
				dynamicListData.clear();
			} else if(curIndex == BTN_RIGHT) {
				nearbyListData.clear();
			} 
		}
		for(DynamicInfo momentInfo : list) {
			DynamicListCell dynamicListCell = new DynamicListCell();
			dynamicListCell.setMomentInfo(momentInfo);
			if(curIndex == BTN_LEFT) {
				dynamicListCell.setType(DynamicListCell.TYPE_PRAISE_NUM);
				boutiqueListData.add(dynamicListCell);
			} else if(curIndex == BTN_MIDDLE) {
				dynamicListCell.setType(DynamicListCell.TYPE_DISTANCE);
				dynamicListData.add(dynamicListCell);
			} else if(curIndex == BTN_RIGHT) {
				dynamicListCell.setType(DynamicListCell.TYPE_TIME);
				nearbyListData.add(dynamicListCell);
			} 
		}
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void error(ResponseError arg0) {
		Tools.toastShow(arg0.getMessage());
	}

	@Override
	public <T> void sucess(Type type,ResponseResult<T> arg0) {
		mPullRefreshListView.onRefreshComplete();
		if(arg0.getCode() == AppConfig.REQUEST_CODE_SUCCESS) {
			GetArticleByUserId getArticleByUserId = arg0.getModel();
			if(getArticleByUserId != null) {
				List<DynamicInfo> list = getArticleByUserId.getRows();
				totalPage = 1;
				addComment(list);
			}
		}
	}
	
}
