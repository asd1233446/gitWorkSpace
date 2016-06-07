package com.mome.main.business.dynamic;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.jessieray.api.model.AddArticleComment;
import com.jessieray.api.model.ArticleDetail;
import com.jessieray.api.model.DynamicInfo;
import com.jessieray.api.model.GetCommentsByArticleId;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.AddArticleCommentRequest;
import com.jessieray.api.service.AddArticleGoodRequest;
import com.jessieray.api.service.ArticleDetailRequest;
import com.jessieray.api.service.GetCommentsByArticleIdRequest;
import com.jessieray.api.service.UndoArticleGoodRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.userinfo.FriendHome;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshScrollView;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshScrollView.CallBack;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.DateUtil;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

@LayoutInject(layout = R.layout.dynamic_detail)
public class DynamicDetail extends BaseFragment {
	
	/**
	 * 置顶悬浮框处理
	 */
	@ViewInject(id = R.id.suspend_ll)
	private LinearLayout suspend_ll;
	@ViewInject(id = R.id.head_ll)
	private RelativeLayout head_ll;
	@ViewInject(id = R.id.originally_ll)
	private LinearLayout originally_ll;
	
	@ViewInject(id=R.id.movie_detail_comment_num)
	private TextView comment_num;

	/**
	 * 页面传递参数定义
	 */

	/**
	 * 评论
	 */
	@ViewInject(id = R.id.dynamicComment_tv)
	private TextView commentValue;

	@OnClick(id = R.id.dynamicComment_tv)
	public void doCommentClick(View view){
		Tools.toastShow("评论");
		sendComment_et.setFocusable(true);  
		sendComment_et.setFocusableInTouchMode(true);  
		sendComment_et.requestFocus();  
		InputMethodManager inputManager =  
		               (InputMethodManager)sendComment_et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);  
		           inputManager.showSoftInput(sendComment_et, 0);  

			
	}

	/**
	 * 输入评论
	 */
	@ViewInject(id = R.id.sendComment_et)
	private EditText sendComment_et;
	@ViewInject(id = R.id.sendCommetn_bt)
	private Button sendCommetn_bt;
   @OnClick(id=R.id.sendCommetn_bt)
	public void sendCommentClick(View view) {
		if (!TextUtils.isEmpty(sendComment_et.getText()))
			sendComment();
	}

	/**
	 * 日期
	 */
	@ViewInject(id = R.id.movieDate_tv)
	private TextView date;

	/**
	 * 用户头像
	 */
	@ViewInject(id = R.id.user_icon)
	private NetworkImageView headIcon;
	@OnClick(id = R.id.user_icon)
	public void headClick(View paramView) {
		Bundle bundle = new Bundle();
		bundle.putString("userid",dynamic.getUserid()+"");
		Tools.pushScreen(FriendHome.class, bundle);
	}
	
	
	/**
	 * 电影详情
	 */
	@ViewInject(id = R.id.movieinfo_tv)
	private TextView movieInfo;

	/**
	 * 电影名
	 */
	@ViewInject(id = R.id.movieName_tv)
	private TextView movieTitle;

	/**
	 * 赞数
	 */
	@ViewInject(id = R.id.dynamic_list_cell_txt_praise_value)
	private TextView praiseValue;

	private boolean isPraise=false;
	
	@OnClick(id = R.id.dynamic_list_cell_txt_praise_value)
	public void doPraiseClick(View view) {
		Tools.toastShow("点赞");
		if(!isPraise)
			AddArticleGood();
		else
			UndoArticleGood();
		
		
	}

	
	/**
	 * 评分控件
	 */
	@ViewInject(id = R.id.dynamic_rating)
	private RatingBar ratin;
	/**
	 * 评分
	 */
	@ViewInject(id = R.id.movieScore_tv)
	private TextView score;
	/**
	 * 用户名
	 */
	@ViewInject(id = R.id.userName_tv)
	private TextView userName;
	/**
	 * 下拉刷新组件
	 */
	@ViewInject(id=R.id.pull_refresh_scrollview)
	private PullToRefreshScrollView mPullRefreshListView;
	/**
	 * listView实例
	 */
	@ViewInject(id = R.id.pull_refresh_list)
	private ListView listView;
	/**
	 * 列表数据容器
	 */
	private ListAdapter adapter;
	/**
	 * 列表数据
	 */
	private ArrayList<ListCellBase> listData = new ArrayList<ListCellBase>();

	private DynamicInfo dynamic;

	/**
	 * 当前页索引
	 */
	private int curPageIndex = 1;

	/**
	 * 一共多少页数
	 */
	private double totalPage = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = this.getArguments();
		if (bundle != null) {
			dynamic = (DynamicInfo) bundle.getSerializable("dynamic");
		}
		getArticleDetail();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Tools.setRereshing(mPullRefreshListView);
		mPullRefreshListView
				.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2<ScrollView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						String label = DateUtils.formatDateTime(
								AppConfig.context, System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						curPageIndex = 1;
						getCommentsByArticleId(curPageIndex);

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						String label = DateUtils.formatDateTime(
								AppConfig.context, System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						
						    getCommentsByArticleId(curPageIndex);
						
					}
				});
		mPullRefreshListView.setMode(Mode.PULL_FROM_START);
		adapter = new ListAdapter();
		adapter.setDataList(listData);
		listView.setAdapter(adapter);
		listView.setFocusable(false);
		//置顶悬浮框
		mPullRefreshListView.setCallBack(new CallBack() {
			
			@Override
			public void onBack(int a) {
				// TODO Auto-generated method stub
				if(a>=head_ll.getBottom()){
					 if (comment_num.getParent()!=suspend_ll) {
						 originally_ll.removeView(comment_num);
						 suspend_ll.addView(comment_num);
					      }
					    }else{
					      if (comment_num.getParent()!=originally_ll) {
					    	  suspend_ll.removeView(comment_num);
					    	  originally_ll.addView(comment_num);
					      }
					    }
			}
		});
	}

	@Override
	public void error(ResponseError arg0) {
		mPullRefreshListView.onRefreshComplete();
		Tools.toastShow("=" + arg0.getMessage());
	}

	@Override
	public <T> void sucess(Type type, ResponseResult<T> arg0) {
		mPullRefreshListView.onRefreshComplete();
	      if(arg0.getModel() != null) {

			if (arg0.getModel().getClass().equals(ArticleDetail.class)) {
				// 获取动态详情
				ArticleDetail articleDetail = arg0.getModel();
				dynamic=articleDetail.getInfo();
				setArticleDetail(articleDetail.getInfo());
			}
			if (arg0.getModel().getClass().equals(GetCommentsByArticleId.class)) {
				// 获取评论列表
				GetCommentsByArticleId getCommentsByArticleId = arg0.getModel();
				if(getCommentsByArticleId.getTotal()>0){
					totalPage = Tools
							.calculateTotalPage(getCommentsByArticleId
									.getTotal());
					if (curPageIndex == 1) {
						listData.clear();
						}
					if (totalPage > curPageIndex){
						mPullRefreshListView.setMode(Mode.BOTH);
					    curPageIndex++;
					}
					else {
						mPullRefreshListView.setMode(Mode.PULL_FROM_START);
					}
				for (DynamicInfo comments : getCommentsByArticleId.getRows()) {
					DynamicDetailListCell cell = new DynamicDetailListCell();
					cell.setDynamicInfo(comments);
					listData.add(cell);
				}
				adapter.notifyDataSetChanged();
			}
		}
			//				else{
//			listView.setEmptyView(Tools
//					.setEmptyView(getActivity()));
//		}
	      }
	}

	/***
	 * 设置动态详情
	 */
	private void setArticleDetail(DynamicInfo dynamic) {
		date.setText(dynamic.getCreatetime());
		movieInfo.setText(dynamic.getBrief());
		score.setText(dynamic.getMark() + "");
		ratin.setRating((float) (dynamic.getMark() * 0.5));
		userName.setText(dynamic.getNickname());
		movieTitle.setText("评论“" + dynamic.getTitle() + "”");
		headIcon.setImageUrl(dynamic.getAvatar(),
				HttpRequest.getInstance().imageLoader);
		commentValue.setText(dynamic.getComments() + "");
		praiseValue.setText(dynamic.getGoods() + "");
		isPraise=dynamic.getIsgood();
		selectorStyle(praiseValue);
	}

	/***
	 * 动态详情
	 */
	private void getArticleDetail() {
		ArticleDetailRequest.findArticleDetail(dynamic.getUserid() + "",
				dynamic.getArticleid() + "", this);
	}

	/***
	 * 评论列表
	 */
	private void getCommentsByArticleId(int pageNo) {
		GetCommentsByArticleIdRequest.findGetCommentsByArticleId(
				dynamic.getUserid() + "", dynamic.getArticleid() + "",
				AppConfig.PAGE_SIZE,pageNo,this);
	}

	/***
	 * 点赞
	 */

	private void AddArticleGood() {
		AddArticleGoodRequest.findAddArticleGood(UserProperty.getInstance()
				.getUid(), dynamic.getArticleid() + "", new ResponseCallback() {
					
					@Override
					public <T> void sucess(Type type, ResponseResult<T> claszz) {
						// TODO Auto-generated method stub
						// 点赞
						isPraise=true;
						praiseValue.setText((Integer.valueOf(praiseValue.getText()
								.toString()) + 1)+"");
						   selectorStyle(praiseValue);
					}
					
					@Override
					public boolean isRefreshNeeded() {
						// TODO Auto-generated method stub
						return false;
					}
					
					@Override
					public void error(ResponseError error) {
						// TODO Auto-generated method stub
						Tools.toastShow("=" + error.getMessage());

					}
				});
	}
	

	/***
	 * 取消点赞
	 */

	private void UndoArticleGood() {
		UndoArticleGoodRequest.findUndoArticleGood(UserProperty
				.getInstance().getUid(), dynamic.getArticleid() + ""
				+ "", new ResponseCallback() {

			@Override
			public <T> void sucess(Type type, ResponseResult<T> claszz) {
				// TODO Auto-generated method stub
				isPraise=false;
				praiseValue.setText((Integer.valueOf(praiseValue.getText()
						.toString()) -1)+"");
			   selectorStyle(praiseValue);
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
	

	/***
	 * 发表评论
	 */

	private void sendComment() {
		AddArticleCommentRequest.findAddArticleComment(UserProperty
				.getInstance().getUid(), dynamic.getArticleid() + "",
				sendComment_et.getText().toString(), new ResponseCallback() {
					
					@Override
					public <T> void sucess(Type type, ResponseResult<T> claszz) {
						// TODO Auto-generated method stub
						// 发表评论
						
						DynamicDetailListCell cell = new DynamicDetailListCell();
						DynamicInfo dynamic=new DynamicInfo();
						dynamic.setCreatetime(DateUtil.now_yyyy_MM_dd_HH_mm_ss());
						dynamic.setBrief(sendComment_et.getText().toString());
						dynamic.setAvatar(DynamicDetail.this.dynamic.getAvatar());
						dynamic.setNickname(DynamicDetail.this.dynamic.getNickname());
						cell.setDynamicInfo(dynamic);
						listData.add(0,cell);
						adapter.notifyDataSetChanged();
						sendComment_et.setText("");
					}
					
					@Override
					public boolean isRefreshNeeded() {
						// TODO Auto-generated method stub
						return false;
					}
					
					@Override
					public void error(ResponseError error) {
						// TODO Auto-generated method stub
						Tools.toastShow("=" + error.getMessage());

					}
				});
	}
	private void selectorStyle(TextView view){
		if(isPraise){
			Drawable drawable=AppConfig.context.getResources().getDrawable(R.drawable.iconfont_zan);
		    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		    view.setCompoundDrawables(drawable, null, null, null);
		}else{
			Drawable drawable=AppConfig.context.getResources().getDrawable(R.drawable.dynamic_img_praise);
		    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		    view.setCompoundDrawables(drawable, null, null, null);
		}
	}
}
