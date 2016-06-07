package com.mome.main.business.dynamic;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.jessieray.api.model.DynamicInfo;
import com.jessieray.api.model.GetArticleByUserId;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.GetArticleByUserIdRequest;
import com.jessieray.api.service.UserFavoriteRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.userinfo.MyCollect;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.InjectProcessor;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

@SuppressLint("NewApi")
@LayoutInject(layout = R.layout.dynamic_fragment)
public class BoutiqueFrgment extends Fragment implements ResponseCallback{


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
	private ArrayList<ListCellBase> dynamicListData = new ArrayList<ListCellBase>();
	/**
	 * 当前tab索引
	 */
	private int curIndex = 2;
	/**
	 * 当前页索引
	 */
	private int curPageIndex = 1;
	/**
	 * 总页数
	 */
	private int totalPage = 0;

	
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = InjectProcessor.injectFragmentLayout(this,inflater, container);
		InjectProcessor.injectFragment(this, rootView);
		return rootView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPullRefreshListView
				.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								AppConfig.context, System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
							curPageIndex = 1;
							getDynamicList(curPageIndex);

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								AppConfig.context, System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
							getDynamicList(curPageIndex);

					}
				});
		mPullRefreshListView.setMode(Mode.PULL_FROM_START);
		listView = mPullRefreshListView.getRefreshableView();
		adapter = new ListAdapter();
		adapter.setDataList(dynamicListData);
		listView.setAdapter(adapter);
		Tools.setRereshing(mPullRefreshListView);
	}

	private void getDynamicList(int page) {
		GetArticleByUserIdRequest.findGetArticleByUserId(UserProperty
				.getInstance().getUid(), String.valueOf(curIndex), page,
				AppConfig.PAGE_SIZE, this);

	}

	/**
	 * 更新动态信息
	 * 
	 * @param list
	 */
	private void addComment(GetArticleByUserId getArticleByUserId) {
		List<DynamicInfo> list=getArticleByUserId.getRows();
		if (list == null || list.isEmpty()) {
			return;
		}
		Tools.toastShow(list.size() + "===");
				totalPage = (int) Tools
						.calculateTotalPage(getArticleByUserId.getTotal());
			
			if (curPageIndex == 1)
				dynamicListData.clear();
			if (totalPage > curPageIndex) {
				mPullRefreshListView.setMode(Mode.BOTH);
				curPageIndex++;
			} else {
				mPullRefreshListView.setMode(Mode.PULL_FROM_START);
			}
	
		
		for (DynamicInfo momentInfo : list) {
			DynamicListCell dynamicListCell = new DynamicListCell();
			dynamicListCell.setMomentInfo(momentInfo);
			dynamicListCell.setType(curIndex);
				dynamicListData.add(dynamicListCell);
		
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void error(ResponseError arg0) {
		mPullRefreshListView.onRefreshComplete();
		Tools.toastShow(arg0.getMessage());
	}

	@Override
	public <T> void sucess(Type type, ResponseResult<T> arg0) {
		mPullRefreshListView.onRefreshComplete();
		GetArticleByUserId getArticleByUserId = arg0.getModel();
		if (getArticleByUserId != null && getArticleByUserId.getTotal() > 0) {
			addComment(getArticleByUserId);

		} 
//		else {
//			mPullRefreshListView
//					.setEmptyView(Tools.setEmptyView(getActivity()));
//		}
	}

	@Override
	public boolean isRefreshNeeded() {
		// TODO Auto-generated method stub
		return true;
	}

}
