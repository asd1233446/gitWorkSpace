package com.mome.main.business.dynamic;


import java.lang.reflect.Type;
import java.util.ArrayList;

import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.mome.main.R;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

@LayoutInject(layout = R.layout.dynamic_detail)
public class DynamicDetail extends BaseFragment {

	/**
	 * 页面传递参数定义
	 */
	public static final String KEY_HEAD_IMG_URL = "dynamic_detail_head";
	public static final String KEY_NAME = "dynamic_detail_name";
	public static final String KEY_SCORE = "dynamic_detail_scroe";
	public static final String KEY_COMMENT = "dynamic_detail_comment";
	public static final String KEY_DATE = "dynamic_detail_date";
	public static final String KEY_MOVIE_IMG_URL = "dynamic_detail_movie_url";
	public static final String KEY_MOVIE_TITLE = "dynamic_detail_movie_title";
	
	@ViewInject(id = R.id.dynamic_list_cell_btn_comment)
	private LinearLayout commentLayout;

	@ViewInject(id = R.id.dynamic_list_cell_txt_comment_value)
	private TextView commentValue;

	@ViewInject(id = R.id.dynamic_list_cell_date)
	private TextView date;

	@ViewInject(id = R.id.dynamic_list_cell_head_icon)
	private NetworkImageView headIcon;

	@ViewInject(id = R.id.dynamic_list_cell_movie_img)
	private NetworkImageView movieImg;

	@ViewInject(id = R.id.dynamic_list_cell_movie_info)
	private TextView movieInfo;

	@ViewInject(id = R.id.dynamic_list_cell_movie_title)
	private TextView movieTitle;

	@ViewInject(id = R.id.dynamic_list_cell_btn_praise)
	private LinearLayout praiseLayout;

	@ViewInject(id = R.id.dynamic_list_cell_txt_praise_value)
	private TextView praiseValue;

	@ViewInject(id = R.id.dynamic_list_cell_rating)
	private RatingBar ratin;

	@ViewInject(id = R.id.dynamic_list_cell_score)
	private TextView score;

	@ViewInject(id = R.id.dynamic_list_cell_user_name)
	private TextView userName;
	/**
	 * 下拉刷新组件
	 */
	@ViewInject(id = R.id.pull_refresh_list)
	private PullToRefreshListView mPullRefreshListView;
	/**
	 * listView实例
	 */
	private ListView listView;
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
		Bundle bundle = this.getArguments();
		if(bundle != null) {
			date.setText(bundle.getString(KEY_DATE));
			movieInfo.setText(bundle.getString(KEY_COMMENT));
			score.setText(bundle.getString(KEY_SCORE));
			userName.setText(bundle.getString(KEY_NAME));
			movieTitle.setText(bundle.getString(KEY_MOVIE_TITLE));
			headIcon.setDefaultImageResId(R.drawable.ic_launcher);
			headIcon.setErrorImageResId(R.drawable.ic_launcher);
			headIcon.setImageUrl(bundle.getString(KEY_HEAD_IMG_URL), HttpRequest.getInstance().imageLoader);
			movieImg.setDefaultImageResId(R.drawable.ic_launcher);
			movieImg.setErrorImageResId(R.drawable.ic_launcher);
			movieImg.setImageUrl(bundle.getString(KEY_MOVIE_IMG_URL), HttpRequest.getInstance().imageLoader);
		}
		mPullRefreshListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(AppConfig.context, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(AppConfig.context, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			}
		});
		mPullRefreshListView.setMode(Mode.BOTH);
		listView = mPullRefreshListView.getRefreshableView();
		adapter = new ListAdapter();
		adapter.setDataList(listData);
		listView.setAdapter(adapter);
	}

	@OnClick(id = R.id.dynamic_list_cell_btn_comment)
	public void commentClick(View paramView) {
	}

	@OnClick(id = R.id.dynamic_list_cell_head_icon)
	public void headClick(View paramView) {
	}

	@OnClick(id = R.id.dynamic_list_cell_movie_img)
	public void movieImgClick(View paramView) {
	}

	@OnClick(id = R.id.dynamic_list_cell_btn_praise)
	public void praiseClick(View paramView) {
	}
	
	@Override
	public void error(ResponseError arg0) {
		Tools.toastShow("="+arg0.getMessage());
	}

	@Override
	public <T> void sucess(Type type,ResponseResult<T> arg0) {
		mPullRefreshListView.onRefreshComplete();
////		if(arg0.getModel().getClass().equals(MovieResponseInfo.class)) {
//			List<MovieResponseInfo> list = (List<MovieResponseInfo>) arg0.getModelList();
//			addMovieInfo(list);
////		}
	}
}
