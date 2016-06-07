package com.mome.main.business.userinfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.jessieray.api.model.Message;
import com.jessieray.api.model.MessageInfo;
import com.jessieray.api.model.TypeInfo;
import com.jessieray.api.request.base.RequestUtils;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.MessageInfoRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;

@LayoutInject(layout = R.layout.mynews)
public class MyNews extends BaseFragment implements OnItemClickListener {

	@ViewInject(id = R.id.pull_refresh_list)
	private PullToRefreshListView pull_refresh_list;

	private ArrayList<ListCellBase> messageData = new ArrayList<ListCellBase>();

	/**
	 * 列表数据容器
	 */
	private ListAdapter adapter;
	/**
	 * 当前页索引
	 */
	private int curPageIndex = 1;

	/**
	 * 第一页
	 */
	private int TotalPage = 1;

	/**
	 * listView实例
	 */
	private ListView listView;
	// 1 私信 2 消息
	private int mgsType = 1;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mgsType = getArguments().getInt("mgsType");
		setUpListView();
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		this.headView.setTitle(mgsType==2?"消息":"私信");
		super.onStart();
	}

	private void setUpListView() {
		listView = pull_refresh_list.getRefreshableView();
		pull_refresh_list.setOnItemClickListener(this);
		adapter = new ListAdapter();
		adapter.setDataList(messageData);
		listView.setAdapter(adapter);
		Tools.setRereshing(pull_refresh_list);
		pull_refresh_list.setMode(Mode.PULL_FROM_START);
		pull_refresh_list
				.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						String label = DateUtils.formatDateTime(
								AppConfig.context, System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						// 下拉刷新，，只刷新服务器最新的
						curPageIndex=1;
						getMessage();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						String label = DateUtils.formatDateTime(
								AppConfig.context, System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						// 下拉刷新，，只刷新服务器最新的
						getMessage();
					}
				});
	}

	private void getMessage() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid",
				RequestUtils.object2String(UserProperty.getInstance().getUid()));
		params.put("pageSize",RequestUtils.object2String(AppConfig.PAGE_SIZE));
		params.put("pageNo",RequestUtils.object2String(curPageIndex));
		MessageInfoRequest.findMeaage(params,
				mgsType == 1 ? "/PersonalMessageList.shtml"
						: "/systemMessageList.shtml", this);

	}

	@Override
	public <T> void sucess(Type arg0, ResponseResult<T> arg1) {
		// TODO Auto-generated method stub
		pull_refresh_list.onRefreshComplete();
		messageData.clear();
		if (arg1.getModel() != null) {
			Message messageList = arg1.getModel();
			 if (curPageIndex == 0)
				 messageData.clear();
				 if (Tools.calculateTotalPage(messageList.getTotoal()) >
				 curPageIndex) {
				 pull_refresh_list.setMode(Mode.BOTH);
				 curPageIndex++;
				 } else {
					 pull_refresh_list.setMode(Mode.PULL_FROM_START);
				 }
			for (MessageInfo info : messageList.getMessages()) {
				MyMessageListCell cell = new MyMessageListCell();
				cell.setMessageInfo(info);
				messageData.add(cell);
			}

			adapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void error(ResponseError arg0) {
		// TODO Auto-generated method stub
		pull_refresh_list.onRefreshComplete();
		Tools.toastShow(arg0.getMessage());
		super.error(arg0);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		MyMessageListCell cell = (MyMessageListCell) parent
				.getItemAtPosition(position);
		MessageInfo info = cell.getMessage();
		Bundle bundle = new Bundle();
		bundle.putSerializable("message", info);

		Tools.pushScreen(
				mgsType == 1 ? MyChat.class : MessageDeatailList.class, bundle);
	}

}
