package com.mome.main.business.userinfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.jessieray.api.model.ArticleListByUserId;
import com.jessieray.api.model.DynamicInfo;
import com.jessieray.api.model.GetArticleByUserId;
import com.jessieray.api.model.MyHomepage;
import com.jessieray.api.model.UserInfo;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.ArticleListByUserIdRequest;
import com.jessieray.api.service.GetArticleByUserIdRequest;
import com.jessieray.api.service.MyHomepageRequest;
import com.mome.main.R;

import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.Mode;

import android.widget.TextView;

@LayoutInject(layout = R.layout.myself_home)
public class UserHome extends BaseFragment {

	/**
	 * 用户头像
	 */
	@ViewInject(id = R.id.user_icon)
	private ImageView headIcon;

	/**
	 * 用户名
	 */
	@ViewInject(id = R.id.user_name)
	private TextView userName;

	@ViewInject(id = R.id.signature_tv)
	private TextView signature_tv;

	/**
	 * 关注数量
	 */
	@ViewInject(id = R.id.attentionNum)
	private TextView attention;
	/**
	 * 粉丝数量
	 */
	@ViewInject(id = R.id.fansNum)
	private TextView fans;

	/**
	 * 观影总数
	 */
	@ViewInject(id = R.id.seenFilmNum)
	private TextView lookNum;
	/**
	 * 电影类型
	 */
	@ViewInject(id = R.id.filmStyle)
	private TextView movieType;

	/**
	 * 动态信息容器
	 */
	@ViewInject(id = R.id.friendsDynamicList)
	private PullToRefreshListView dynamicListLayout;

	/*
	 * 编辑个人资料
	 */
	@ViewInject(id = R.id.userSet)
	private ImageButton userSet;

	@OnClick(id = R.id.userSet)
	public void onUserSetClick(View view) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("userInfo", userinfo);
		Tools.pushScreen(UpdateUserInfo.class, bundle);

	}

	private ArrayList<ListCellBase> dynamicListData = new ArrayList<ListCellBase>();
	/**
	 * 列表数据容器
	 */
	private ListAdapter adapter;
	/**
	 * 当前页索引
	 */
	private int curPageIndex = 0;

	/**
	 * 第一页
	 */
	private int firstPage = 1;

	/**
	 * listView实例
	 */
	private ListView listView;

	/**
	 * 
	 * 用户信息
	 */
	private MyHomepage userinfo;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getUserPage();
		setUpListView();
	}

	/**
	 * 我的主页的信息
	 * 
	 * */

	private void getUserPage() {
		MyHomepageRequest.findMyHomepage(UserProperty.getInstance().getUid(),
				new ResponseCallback() {

					@Override
					public <T> void sucess(Type type, ResponseResult<T> arg0) {
						// TODO Auto-generated method stub
						if (arg0.getCode() == AppConfig.REQUEST_CODE_SUCCESS
								&& arg0.getModel() != null) {
							userinfo = arg0.getModel();
							Tools.loadNetImage(headIcon, userinfo.getUserinfo()
									.getAvatar(), R.drawable.default_ptr_flip,
									R.drawable.default_ptr_flip);
							userName.setText(userinfo.getUserinfo()
									.getNickname());
							fans.setText(userinfo.getUserinfo().getFans());
							attention.setText(userinfo.getUserinfo()
									.getAttention());
							lookNum.setText(userinfo.getUserinfo()
									.getWatchtotal());
							movieType.setText(userinfo.getUserinfo()
									.getMovietype());
							signature_tv.setText(userinfo.getUserinfo()
									.getSignature());

						}

					}

					@Override
					public boolean isRefreshNeeded() {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public void error(ResponseError error) {
						// TODO Auto-generated method stub
						Tools.toastShow(error.getMessage());
					}
				});

	}

	/**
	 * 获取动态信息
	 * */
	private void setUpListView() {
		adapter = new ListAdapter();
		dynamicListLayout.setMode(Mode.BOTH);
		dynamicListLayout
				.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								AppConfig.context, System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						// 下拉刷新，，只刷新服务器最新的
						getData(1);

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								AppConfig.context, System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						getData(curPageIndex);

					}
				});

		listView = dynamicListLayout.getRefreshableView();
		adapter.setDataList(dynamicListData);
		listView.setAdapter(adapter);
		getData(1);
	}

	private void getData(final int pageNo) {

		ArticleListByUserIdRequest.findArticleListByUserIdRequest(UserProperty
				.getInstance().getUid(), UserProperty.getInstance().getUid(),
				pageNo, AppConfig.PAGE_SIZE, new ResponseCallback() {

					@Override
					public <T> void sucess(Type type, ResponseResult<T> arg0) {
						// TODO Auto-generated method stub
						dynamicListLayout.onRefreshComplete();
						if (arg0.getCode() == AppConfig.REQUEST_CODE_SUCCESS
								&& arg0.getModel() != null) {
							ArticleListByUserId getArticleByUserId = arg0
									.getModel();
							if (getArticleByUserId != null) {
								List<DynamicInfo> list = getArticleByUserId
										.getArticles();
								if (getArticleByUserId.getTotal() == 0) {
									return;
								}
								if (pageNo == 1) {
									curPageIndex = 1;
								} else {
									curPageIndex++;
								}
								setData(list);
							}
						}

					}

					@Override
					public void error(ResponseError error) {
						// TODO Auto-generated method stub
						dynamicListLayout.onRefreshComplete();
						Tools.toastShow(error.getMessage());
					}

					@Override
					public boolean isRefreshNeeded() {
						// TODO Auto-generated method stub
						return false;
					}
				});

	}

	private void setData(List<DynamicInfo> list) {
		if (list == null || list.isEmpty()) {
			return;
		}
		for (DynamicInfo info : list) {
			UserDynaicListCell userCell = new UserDynaicListCell();
			userCell.setMomentInfo(info);
			dynamicListData.add(userCell);
			adapter.notifyDataSetChanged();
		}
	}

}
