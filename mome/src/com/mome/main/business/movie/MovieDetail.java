package com.mome.main.business.movie;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.mome.main.R;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener2;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshScrollView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 电影详情页面
 *
 */
@LayoutInject(layout = R.layout.movie_detail)
public class MovieDetail extends BaseFragment{

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
	/**
	 * 查看观影记录
	 */
	@ViewInject(id = R.id.movie_detail_look_record)
	private TextView lookBtn;
	/**
	 * 观看过的人数
	 */
	@ViewInject(id = R.id.movie_detail_looked_num)
	private TextView lookNum;
	/**
	 * 收藏该片的人数
	 */
	@ViewInject(id = R.id.movie_detail_collect_num)
	private TextView collectNum;
	/**
	 * 未收藏该片的人数
	 */
	@ViewInject(id = R.id.movie_detail_not_collect_num)
	private TextView notCollectNum;
	/**
	 * 推荐的好友数
	 */
	@ViewInject(id = R.id.movie_detail_commend_friends)
	private TextView recommendNum;
	/**
	 * 网友点评数量
	 */
	@ViewInject(id = R.id.movie_detail_comment_num)
	private TextView commentNum;
	/**
	 * 按最新排序
	 */
	@ViewInject(id = R.id.movie_detail_new_btn)
	private TextView newBtn;
	/**
	 * 按热度排序
	 */
	@ViewInject(id = R.id.movie_detail_hot_btn)
	private TextView hotBtn;
	/**
	 * 评论列表
	 */
	@ViewInject(id = R.id.movie_detail_listview)
	private ListView listView;
	/**
	 * 下拉刷新组件
	 */
	@ViewInject(id = R.id.pull_refresh_scrollview)
	private PullToRefreshScrollView mPullRefreshScrollView;
	/**
	 * 列表数据容器
	 */
	private ListAdapter adapter;
	/**
	 * 列表数据
	 */
	private ArrayList<ListCellBase> listData = new ArrayList<ListCellBase>();
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener2<ScrollView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ScrollView> refreshView) {
				Tools.Log("==========1=========");
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ScrollView> refreshView) {
				Tools.Log("==========2=========");
			}
		});
		adapter = new ListAdapter();
		adapter.setDataList(listData);
		listView.setAdapter(adapter);
	}

	@Override
	public void error(ResponseError arg0) {
		mPullRefreshScrollView.onRefreshComplete();
		Tools.toastShow(arg0.getMessage());
	}

	@Override
	public <T> void sucess(Type type,ResponseResult<T> arg0) {
		mPullRefreshScrollView.onRefreshComplete();
	}
}
