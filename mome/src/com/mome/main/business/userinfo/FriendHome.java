package com.mome.main.business.userinfo;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jessieray.api.model.ArticleListByUserId;
import com.jessieray.api.model.DynamicInfo;
import com.jessieray.api.model.PersonalHomepage;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.AddttentionRequest;
import com.jessieray.api.service.ArticleListByUserIdRequest;
import com.jessieray.api.service.CancelttentionRequest;
import com.jessieray.api.service.PersonalHomepageRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshScrollView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;
import com.mome.view.ListViewInScrollView;

@LayoutInject(layout = R.layout.friends_home)
public class FriendHome extends BaseFragment{

	/**
	 * 用户头像
	 */
	@ViewInject(id = R.id.user_icon)
	private NetworkImageView headIcon;

	/**
	 * 用户名
	 */
	@ViewInject(id = R.id.user_name)
	private TextView userName;
	/***
	 * 用户id
	 * 
	 * */
	@ViewInject(id=R.id.user_id)
    private TextView userId;
	
   /***
    * 个性签名
    * 
    */
	@ViewInject(id = R.id.signature_tv)
	private TextView signature_tv;
	

	/**
	 * 私信留言
	 */
	@ViewInject(id = R.id.message)
	private ImageButton message;
	@OnClick(id=R.id.message)
	public void sendMessageClick(View view){
		Bundle bundle=new Bundle();
		bundle.putString("friendName", userinfo.getUserinfo().getNickname());
		Tools.pushScreen(MyChat.class, bundle);
	}
	
	
	
	/**
	 * 关注
	 */
	private boolean isAttention=false;
	@ViewInject(id = R.id.attention)
	private ImageView attention;
	@OnClick(id=R.id.attention)
	public void takeAttention(View view){
		if(isAttention)
			cancelAddttention();
		else
			addAddttention();
	}
	

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
	 * TA电影院
	 */
	@ViewInject(id=R.id.TAMovieHouseLayout)
    private LinearLayout  TAMovieHouseLayout;
	
	@OnClick(id=R.id.TAMovieHouseLayout)
    public void TAMovieHouseClick(View view){
    	Tools.toastShow("Ta电影院");
    	Bundle bundle=new Bundle();
    	bundle.putString("userid", userid);
    	Tools.pushScreen(MyCinema.class, bundle);
    }
	
	/**
	 * TA电影名称
	 */
	@ViewInject(id=R.id.movieHouse)
    private TextView  movieHouse;
	
	
	
	/**
	 * TA相册
	 */
	@ViewInject(id=R.id.TAphotosLayout)
    private LinearLayout  TAphotosLayout;
	
	@OnClick(id=R.id.TAphotosLayout)
    public void TAphotosClick(View view){
		Bundle bundle=new Bundle();
		bundle.putString("userId",userid);
		Tools.pushScreen(MyPhoto.class, bundle);
		
    	
    }
	
	/**
	 * TA收藏
	 */
	@ViewInject(id=R.id.TAcollectionLayout)
    private LinearLayout  TAcollectionLayout;
	
	@OnClick(id=R.id.TAcollectionLayout)
    public void TAcollectionClick(View view){
		Bundle  bundle=new Bundle();
		bundle.putString("userId",userid);
       Tools.pushScreen(MyCollect.class, bundle);
    }
	
	
	/**
	 * TA关注的人
	 */
	@ViewInject(id=R.id.TAattentionLayout)
    private LinearLayout  TAattentionLayout;
	
	@OnClick(id=R.id.TAattentionLayout)
    public void TAattentionClick(View view){
    	 Tools.toastShow("Ta关注的人");
    	 Bundle bundle=new Bundle();
    	 bundle.putInt("realationType", 1);
     bundle.putString("userId", userid);
     bundle.putString("titleName", "Ta关注的人");
    	 Tools.pushScreen(MyFriend.class, bundle);
    }
	
	/**
	 * 动态信息容器
	 */
	@ViewInject(id = R.id.friendsDynamicList)
	private ListViewInScrollView dynamicListLayout;

	private ArrayList<ListCellBase> dynamicListData = new ArrayList<ListCellBase>();
	/**
	 * 列表数据容器
	 */
	private ListAdapter adapter;

	/**
	 * 上拉刷新，下拉加载的scrollview
	 */
	@ViewInject(id = R.id.pull_refresh_scrollview)
	private  PullToRefreshScrollView scrollview;
	/**
	 * 
	 * 用户信息
	 */
	private PersonalHomepage userinfo;
	
	
	 private String userid ;
	 
	 /**
		 * 当前页索引
		 */
		private int curPageIndex = 1;
		
		/**
		 * 一共多少页数
		 */
		private double totalPage = 1;
		
	 

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Bundle bundle=getArguments();
		userid=(String)bundle.getString("userid");
		getUserPage();
		setUpListView();
	}

	/**
	 * 我的主页的信息
	 * 
	 * */

	private void getUserPage() {
		PersonalHomepageRequest.findPersonalHomepage(userid,UserProperty.getInstance().getUid(),
				new ResponseCallback() {

					@Override
					public <T> void sucess(Type type, ResponseResult<T> arg0) {
						// TODO Auto-generated method stub
						if ( arg0.getModel() != null) {
							userinfo = arg0.getModel();
							headIcon.setImageUrl(userinfo.getUserinfo()
									.getAvatar(),HttpRequest.getInstance().imageLoader);
							userName.setText(userinfo.getUserinfo().getNickname());
							lookNum.setText(userinfo.getUserinfo()
									.getWatchtotal());
							movieType.setText(userinfo.getUserinfo()
									.getMovietype());
							signature_tv.setText(userinfo.getUserinfo()
									.getSignature());
							movieHouse.setText(userinfo.getUserinfo().getCinema());
							userId.setText("ID: "+ userid);
							isAttention=userinfo.getUserinfo().isIsattention();
							 selectorStyle();

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
		//进入页面刷新
		Tools.setRereshing(scrollview);
		scrollview
				.setOnRefreshListener(new PullToRefreshScrollView.OnRefreshListener2<ScrollView>() {

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

		ArticleListByUserIdRequest.findArticleListByUserIdRequest(userid, UserProperty.getInstance().getUid(),
				pageNo, AppConfig.PAGE_SIZE, new ResponseCallback() {

					@Override
					public <T> void sucess(Type type, ResponseResult<T> arg0) {
						// TODO Auto-generated method stub
						scrollview.onRefreshComplete();
						ArticleListByUserId getArticleByUserId = arg0
								.getModel();
							if (getArticleByUserId!= null&&getArticleByUserId.getTotal()> 0) {
								totalPage=Tools.calculateTotalPage(getArticleByUserId.getTotal());
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
								for(DynamicInfo info:getArticleByUserId.getArticles()){
									UserDynaicListCell userCell = new UserDynaicListCell();
									userCell.setMomentInfo(info);
									dynamicListData.add(userCell);
									
									
								}
								
								adapter.notifyDataSetChanged();
							}
//							else{
//								dynamicListLayout.setEmptyView(Tools
//										.setEmptyView(getActivity()));
//							}
						

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


	private void addAddttention(){
	AddttentionRequest.findAddttention(UserProperty.getInstance().getUid(), userid, new ResponseCallback() {
		
		@Override
		public <T> void sucess(Type type, ResponseResult<T> claszz) {
			// TODO Auto-generated method stub
			isAttention=true;
			 selectorStyle();
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
	
	private void cancelAddttention(){
	CancelttentionRequest.findCancelAddttention(UserProperty.getInstance().getUid(),userid, new ResponseCallback() {
		
		@Override
		public <T> void sucess(Type type, ResponseResult<T> claszz) {
			// TODO Auto-generated method stub
			isAttention=false;
			 selectorStyle();
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

	private void selectorStyle(){
		if(isAttention){
			attention.setImageResource(R.drawable.attentioned);
		}else{
			attention.setImageResource(R.drawable.attenton);
		}
	}

//	@Override
//	public void removeItem(RemoveDirection direction, int position) {
//		// TODO Auto-generated method stub
//		dynamicListData.remove(position);
//		adapter.notifyDataSetChanged();
//	}
	
}
