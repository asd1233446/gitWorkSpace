package com.mome.main.business.userinfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.jessieray.api.model.ArticleListByUserId;
import com.jessieray.api.model.DynamicInfo;
import com.jessieray.api.model.Friend;
import com.jessieray.api.model.GetArticleByUserId;
import com.jessieray.api.model.MyHomepage;
import com.jessieray.api.model.UserInfo;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.ArticleListByUserIdRequest;
import com.jessieray.api.service.DeleteArticleRequest;
import com.jessieray.api.service.GetArticleByUserIdRequest;
import com.jessieray.api.service.MyHomepageRequest;
import com.mome.main.R;

import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.movie.MovieMemoirs;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshScrollView;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.mome.view.ListViewInScrollView;

import android.widget.TextView;

@LayoutInject(layout = R.layout.myself_home)
public class UserHome extends BaseFragment implements OnItemLongClickListener{

	/**
	 * 用户头像
	 */
	@ViewInject(id = R.id.user_icon)
	private NetworkImageView headIcon;

	/**z
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
	@ViewInject(id = R.id.attentionLayout)
	private RelativeLayout attentionLayout;

	@OnClick(id = R.id.attentionLayout)
	public void ToAttentionClick(View view) {
		Tools.toastShow("我关注的人");
		Bundle bundle = new Bundle();
		bundle.putInt("realationType", 1);
		bundle.putString("userId", UserProperty.getInstance().getUid());
		bundle.putString("titleName", "我关注的人");
		Tools.pushScreen(MyFriend.class, bundle);
	}

	/**
	 * 粉丝数量
	 */
	@ViewInject(id = R.id.fansNum)
	private TextView fans;
	@ViewInject(id = R.id.fansLayout)
	private RelativeLayout fansLayout;

	@OnClick(id = R.id.fansLayout)
	public void ToFansClick(View view) {
		Bundle bundle = new Bundle();
		bundle.putInt("realationType", 3);
		bundle.putString("userId", UserProperty.getInstance().getUid());
		bundle.putString("titleName", "我的粉丝");
		Tools.pushScreen(MyFriend.class, bundle);
	}

	/**
	 * 观影总数
	 */
	@ViewInject(id = R.id.seenFilmNum)
	private TextView lookNum;
	@ViewInject(id = R.id.seenFilmLayout)
	private RelativeLayout seenFilmLayout;

	@OnClick(id = R.id.seenFilmLayout)
	public void ToFilmNumClick(View view) {
		Tools.pushScreen(MovieMemoirs.class, null);
	}

	/**
	 * 电影类型
	 */
	@ViewInject(id = R.id.filmStyle)
	private TextView movieType;

	@ViewInject(id = R.id.filmStyleLayout)
	private RelativeLayout filmStyleLayout;

	@OnClick(id = R.id.filmStyleLayout)
	public void ToFilmStyleClick(View view) {
		Tools.pushScreen(UserData.class, null);
	}

	/**
	 * 动态信息容器
	 */
	@ViewInject(id = R.id.friendsDynamicList)
	private ListViewInScrollView dynamicListLayout;

	/**
	 * 上拉刷新，下拉加载的scrollview
	 */
	@ViewInject(id = R.id.pull_refresh_scrollview)
	private PullToRefreshScrollView scrollview;

	/*
	 * 编辑个人资料
	 */
	@ViewInject(id = R.id.userSet)
	private ImageButton userSet;

	@OnClick(id = R.id.userSet)
	public void onUserSetClick(View view) {
		Bundle bundle = new Bundle();
		if (userinfo != null) {
			bundle.putSerializable("userInfo", userinfo.getUserinfo());
		}
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
	private int curPageIndex = 1;

	/**
	 * 一共多少页数
	 */
	private double totalPage = 1;


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
							headIcon.setImageUrl(userinfo.getUserinfo()
									.getAvatar(),HttpRequest.getInstance().imageLoader);
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
		adapter.setDataList(dynamicListData);
		dynamicListLayout.setAdapter(adapter);
		dynamicListLayout.setFocusable(false);
		scrollview.setMode(Mode.PULL_FROM_START);
		dynamicListLayout.setOnItemLongClickListener(this);
		// 进入页面刷新
		Tools.setRereshing(scrollview);
		scrollview
				.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2<ScrollView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
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
							PullToRefreshBase<ScrollView> refreshView) {
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

	}

	private void getData(final int pageNo) {

		ArticleListByUserIdRequest.findArticleListByUserIdRequest(UserProperty
				.getInstance().getUid(), UserProperty.getInstance().getUid(),
				pageNo, AppConfig.PAGE_SIZE, new ResponseCallback() {

					@Override
					public <T> void sucess(Type type, ResponseResult<T> arg0) {
						// TODO Auto-generated method stub
						scrollview.onRefreshComplete();

						ArticleListByUserId getArticleByUserId = arg0
								.getModel();
						if (getArticleByUserId != null
								&& getArticleByUserId.getTotal() > 0) {
							totalPage = Tools
									.calculateTotalPage(getArticleByUserId
											.getTotal());
							if (pageNo == 1) {
								dynamicListData.clear();
								curPageIndex = 1;}
							
							if (totalPage > curPageIndex){
								scrollview.setMode(Mode.BOTH);
							    curPageIndex++;
							}
							else {
								scrollview.setMode(Mode.PULL_FROM_START);
							}
							for (DynamicInfo info : getArticleByUserId
									.getArticles()) {
								UserDynaicListCell userCell = new UserDynaicListCell();
								userCell.setMomentInfo(info);

								dynamicListData.add(userCell);

							}
							adapter.notifyDataSetChanged();
						}
//						else {
//							dynamicListLayout.setEmptyView(Tools
//									.setEmptyView(getActivity()));
//						}

					}

					@Override
					public void error(ResponseError error) {
						// TODO Auto-generated method stub
						scrollview.onRefreshComplete();
						Tools.toastShow(error.getMessage());
					}

					@Override
					public boolean isRefreshNeeded() {
						// TODO Auto-generated method stub
						return false;
					}
				});

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		final UserDynaicListCell  dynaic=(UserDynaicListCell) parent.getItemAtPosition(position);
		Tools.showDialog(getActivity(),"提示", "是否删除", "确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				deleteArticle(dynaic);
        
			}
		},null);
		return false;
	}
	
	
	public void deleteArticle(final UserDynaicListCell dynaic){
		DeleteArticleRequest.findDeleteArticle(dynaic.getMomentInfo().getArticleid()+"", new ResponseCallback() {
			
			@Override
			public <T> void sucess(Type type, ResponseResult<T> claszz) {
				// TODO Auto-generated method stub
				 dynamicListData.remove(dynaic);
				 adapter.notifyDataSetChanged();
			}
			
			@Override
			public boolean isRefreshNeeded() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void error(ResponseError error) {
				// TODO Auto-generated method stub
				Tools.toastShow("删除失败");
			}
		});
	}
}
