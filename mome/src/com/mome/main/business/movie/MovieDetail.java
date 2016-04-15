package com.mome.main.business.movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jessieray.api.model.ArticleListByMovieId;
import com.jessieray.api.model.DynamicInfo;
import com.jessieray.api.model.MovieInfo;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.AddMovieFavorRequest;
import com.jessieray.api.service.ArticleListByMovieIdRequest;
import com.jessieray.api.service.MovieDetailRequest;
import com.jessieray.api.service.UndoMovieFavorRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.record.Record;
import com.mome.main.business.userinfo.MyFriend;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshScrollView;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshScrollView.CallBack;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;
import com.mome.view.ListViewInScrollView;

/**
 * 电影详情页面
 * 
 */
@LayoutInject(layout = R.layout.movie_detail)
public class MovieDetail extends BaseFragment implements
		OnCheckedChangeListener {

	/**
	 * 置顶悬浮框处理
	 */
	@ViewInject(id = R.id.suspend_ll)
	private LinearLayout suspend_ll;
	@ViewInject(id = R.id.head_ll)
	private LinearLayout head_ll;
	@ViewInject(id = R.id.originally_ll)
	private LinearLayout originally_ll;

	/**
	 * 上拉刷新，下拉加载的scrollview
	 */
	@ViewInject(id = R.id.pull_refresh_scrollview)
	private PullToRefreshScrollView scrollview;

	/**
	 * 电影海报
	 */
	@ViewInject(id = R.id.movie_detail_poster)
	private NetworkImageView poster;
	/**
	 * 电影标题
	 */
	@ViewInject(id = R.id.movie_detail_movie_title)
	private TextView movieTitle;
	/**
	 * 评分控件
	 */
	@ViewInject(id = R.id.movie_detail_rating)
	private RatingBar rating;
	/**
	 * 评分
	 */
	@ViewInject(id = R.id.movie_detail_score)
	private TextView score;
	/**
	 * 关注人数
	 */
	@ViewInject(id = R.id.movie_detail_attention_num)
	private TextView attentionNum;
	/**
	 * 电影类型
	 */
	@ViewInject(id = R.id.movie_detail_type)
	private TextView type;
	/**
	 * 电影时长
	 */
	@ViewInject(id = R.id.movie_detail_time)
	private TextView time;
	/**
	 * 添加观影记录
	 */
	@ViewInject(id = R.id.movie_detail_add_record)
	private TextView addBtn;

	@OnClick(id = R.id.movie_detail_add_record)
	public void ToMovieRecord(View view) {
		Tools.toastShow("添加观影纪录");
		Tools.pushScreen(Record.class, null);

	}

	/**
	 * 查看观影记录
	 */
	@ViewInject(id = R.id.movie_detail_look_record)
	private RelativeLayout lookBtn;

	@OnClick(id = R.id.movie_detail_look_record)
	public void ToLookedRecord(View view) {
		Tools.toastShow("查看观影纪录");
		Tools.pushScreen(MovieMemoirs.class, null);

	}

	/**
	 * 观看过的人数
	 */
	@ViewInject(id = R.id.movie_detail_looked_num)
	private TextView lookNum;
	@ViewInject(id = R.id.movie_detail_looked_rl)
	private RelativeLayout movie_detail_looked_ry;

	@OnClick(id = R.id.movie_detail_looked_rl)
	public void goLookedClick(View view) {
		Tools.toastShow("观看过的人数");
		Bundle bundle = new Bundle();
		bundle.putInt("realationType", 2);
		bundle.putString("titleName", "我的好友");
		Tools.pushScreen(MyFriend.class, bundle);
	}

	/**
	 * 收藏该片的人数
	 */
	@ViewInject(id = R.id.movie_detail_collect_num)
	private TextView collectNum;

	@ViewInject(id = R.id.movie_detail_collect_rl)
	private RelativeLayout movie_detail_collect_rl;

	@OnClick(id = R.id.movie_detail_collect_rl)
	public void goCollectedClick(View view) {
		Tools.toastShow("观收藏该片的人数过的人数");
		Bundle bundle = new Bundle();
		bundle.putInt("realationType", 3);
		bundle.putString("titleName", "我的好友");
		Tools.pushScreen(MyFriend.class, bundle);
	}

	/**
	 * 未收藏
	 */
	@ViewInject(id = R.id.movie_detail_not_collec)
	private TextView notCollectNum;

	@OnClick(id = R.id.movie_detail_not_collec)
	public void collecClick(View view) {
		Tools.toastShow("收藏");
		if (notCollectNum.getText().equals("收藏"))
			addMovieFavor();
		else
			undoMovieFavor();

	}

	/**
	 * 推荐的好友数
	 */
	@ViewInject(id = R.id.movie_detail_commend_friends)
	private TextView commend_friends;

	@OnClick(id = R.id.movie_detail_commend_friends)
	public void toCommentFriendClick(View view) {
		Tools.toastShow("推荐给好友");
		Bundle bundle = new Bundle();
		bundle.putInt("realationType", 2);
		bundle.putString("titleName", "我的好友");
		Tools.pushScreen(MyFriend.class, bundle);
	}

	/**
	 * 网友点评数量
	 */
	@ViewInject(id = R.id.movie_detail_comment_num)
	private TextView commentNum;
	// /**
	// * 按最新排序
	// */
	@ViewInject(id = R.id.movie_detail_new_btn)
	private RadioButton newBtn;
	//
	// // @OnClick(id=R.id.movie_detail_new_btn)
	// // public void newClick(View view){
	// // getMovieComment(1);
	// // }
	/**
	 * 影评排序
	 */
	@ViewInject(id = R.id.radioGroup)
	private RadioGroup rGroup;

	// /**
	// * 按热度排序
	// */
	// @ViewInject(id = R.id.movie_detail_hot_btn)
	// private RadioButton hotBtn;
	// // @OnClick(id=R.id.movie_detail_new_btn)
	// // public void hotClick(View view){
	// // getMovieComment(2);
	// // }
	/**
	 * 豆瓣详情介绍页
	 */
	@ViewInject(id = R.id.movieIntroduce_tv)
	private TextView movieIntroduce_tv;

	@OnClick(id = R.id.movieIntroduce_tv)
	public void goMovieIntroduceClick(View view) {
		Tools.toastShow("进入豆瓣详情页面");
	}

	/**
	 * 竖线
	 */
	@ViewInject(id = R.id.line)
	private View line;

	/**
	 * 下拉刷新评论列表
	 */
	@ViewInject(id = R.id.movie_detail_listview)
	private ListViewInScrollView pullListView;

	/**
	 * 列表数据容器
	 */
	private ListAdapter adapter;
	/**
	 * 列表数据
	 */
	private ArrayList<ListCellBase> listData = new ArrayList<ListCellBase>();
	/**
	 * 上个页面传过来的数据
	 */
	private MovieInfo movieinfo;

	private com.jessieray.api.model.MovieDetail movieInfo;

	/**
	 * 排序类型 1代表最新，2代表热度
	 */
	private int orderType = 1;

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
		setUpListView();

		rGroup.setOnCheckedChangeListener(this);
		newBtn.setChecked(true);
		// 置顶悬浮框
		scrollview.setCallBack(new CallBack() {

			@Override
			public void onBack(int a) {
				// TODO Auto-generated method stub
				if (a >= head_ll.getBottom()) {
					if (rGroup.getParent() != suspend_ll) {
						originally_ll.removeView(rGroup);
						suspend_ll.addView(rGroup);
					}
				} else {
					if (rGroup.getParent() != originally_ll) {
						suspend_ll.removeView(rGroup);
						originally_ll.addView(rGroup);
					}
				}
			}
		});
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		headView.setTitle(movieinfo.getTitle());
		super.onResume();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setUpMovieInfo();
	}

	private void setUpListView() {
		adapter = new ListAdapter();
		adapter.setDataList(listData);
		pullListView.setAdapter(adapter);
		pullListView.setFocusable(false);
		scrollview.setMode(Mode.PULL_FROM_START);
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
						curPageIndex = 1;
						getMovieComment();

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
						getMovieComment();

					}
				});

	}

	/**
	 * 详情页面电影信息
	 * **/

	private void setUpMovieInfo() {
		Bundle bundle = getArguments();
		movieinfo = (MovieInfo) bundle.getSerializable("MovieInfo");
		MovieDetailRequest.findMovieDetail(UserProperty.getInstance().getUid(),
				movieinfo.getMovieid() + "", new ResponseCallback() {

					@Override
					public <T> void sucess(Type type, ResponseResult<T> arg0) {
						// TODO Auto-generated method stub
						if (arg0.getCode() == AppConfig.REQUEST_CODE_SUCCESS
								&& arg0.getModel() != null) {
							movieInfo = arg0.getModel();
							poster.setImageUrl(movieInfo.getImagesrc(),
									HttpRequest.getInstance().imageLoader);
							movieTitle.setText(movieInfo.getTitle());
							rating.setRating((float) (movieInfo.getMark() * 0.5));
							score.setText(movieInfo.getMark() + "");
						
							attentionNum.setText(movieInfo.getAttentions()+"");
							MovieDetail.this.type.setText("类型："
									+ movieInfo.getType());
							time.setText("时长：" + movieInfo.getDuration() + "分钟");
							lookNum.setText(movieInfo.getLookedfriends() + "");
							collectNum.setText(movieInfo.getFavors() + "");
							notCollectNum
									.setText(movieInfo.getMyfavorite() == true ? "取消收藏"
											: "收藏");
							commentNum.setText("网友点评("
									+ movieInfo.getArticles() + ")");
							lookBtn.setVisibility(movieInfo.getWasrecall() == true ? View.VISIBLE
									: View.GONE);
							line.setVisibility(movieInfo.getWasrecall() == true ? View.VISIBLE
									: View.GONE);
							selectorStyle(notCollectNum);
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
	 * 网友评论纪录
	 */

	private void getMovieComment() {
		ArticleListByMovieIdRequest.findArticleListByMovieId(
				movieinfo.getMovieid() + "", orderType, 1,
				AppConfig.PAGE_SIZE, this);
	}

	@Override
	public void error(ResponseError arg0) {
		scrollview.onRefreshComplete();
		Tools.toastShow(arg0.getMessage());
	}

	@Override
	public <T> void sucess(Type type, ResponseResult<T> arg0) {
		scrollview.onRefreshComplete();
		if (arg0.getModel() != null) {
			// 影评排序
			ArticleListByMovieId movieDetailList = arg0.getModel();
			if (movieDetailList.getTotal() > 0) {
				totalPage = Tools
						.calculateTotalPage(movieDetailList.getTotal());
				if (curPageIndex == 1) {
					listData.clear();
				}

				if (totalPage > curPageIndex) {
					scrollview.setMode(Mode.BOTH);
					curPageIndex++;
				} else {
					scrollview.setMode(Mode.PULL_FROM_START);
				}
				setDataListView(movieDetailList.getArticles());
			}else{
				listData.clear();
				adapter.notifyDataSetChanged();
				scrollview.setMode(Mode.PULL_FROM_START);
			}
		}
	}

	private void setDataListView(List<DynamicInfo> movieDetailList) {
		if (movieDetailList != null && !movieDetailList.isEmpty()) {
			for (DynamicInfo momentInfo : movieDetailList) {
				MovieDetailListCell listCell = new MovieDetailListCell(
						getActivity());
				momentInfo.setOrderType(orderType);
				listCell.setMomentInfo(momentInfo);
				listData.add(listCell);
			}
			adapter.notifyDataSetChanged();

		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		if (checkedId == R.id.movie_detail_new_btn)
			orderType = 1;
		else
			orderType = 2;
		    curPageIndex = 1;
		getMovieComment();
	}

	/***
	 * 收藏电影
	 */
	public void addMovieFavor() {
		AddMovieFavorRequest.findAddMovieFavor(UserProperty.getInstance()
				.getUid(), movieinfo.getMovieid() + "",
				new ResponseCallback() {

					@Override
					public <T> void sucess(Type type, ResponseResult<T> claszz) {
						// TODO Auto-generated method stub
						if (claszz.getCode() == AppConfig.REQUEST_CODE_SUCCESS)
							notCollectNum.setText("取消收藏");
						selectorStyle(notCollectNum);
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
	 * 取消收藏
	 */

	public void undoMovieFavor() {
		UndoMovieFavorRequest.findUndoMovieFavor(UserProperty.getInstance()
				.getUid(), movieinfo.getMovieid() + "",
				new ResponseCallback() {

					@Override
					public <T> void sucess(Type type, ResponseResult<T> claszz) {
						// TODO Auto-generated method stub
						if (claszz.getCode() == AppConfig.REQUEST_CODE_SUCCESS)
							notCollectNum.setText("收藏");
						selectorStyle(notCollectNum);

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

	private void selectorStyle(TextView view) {
		if (view.getText().toString().equals("取消收藏")) {
			Drawable drawable = AppConfig.context.getResources().getDrawable(
					R.drawable.xin_select);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			view.setCompoundDrawables(null,drawable, null, null);
		} else {
			Drawable drawable = AppConfig.context.getResources().getDrawable(
					R.drawable.xin);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			view.setCompoundDrawables(null,drawable, null, null);
		}
	}
}
