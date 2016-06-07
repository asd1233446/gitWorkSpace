package com.mome.main.business.movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.jessieray.api.model.Friend;
import com.jessieray.api.model.PhotoInfo;
import com.jessieray.api.model.RelationListByUserId;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.RecommendMovieToFriendsRequest;
import com.jessieray.api.service.RelationListByUserIdRequest;
import com.jessieray.api.service.addRecallFriendsRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.movie.SelectedFriendsListCell.onFriendsItemCheckedListener;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.mome.pinyin.AssortView;
import com.mome.pinyin.AssortView.OnTouchAssortListener;
import com.mome.view.MyDeImageView;

@LayoutInject(layout = R.layout.myfriends)
public class SelectedFriends extends BaseFragment implements
		OnTouchAssortListener, onFriendsItemCheckedListener {
	@ViewInject(id = R.id.seachFriend)
	private EditText seachFriend;

	/** 刷新 */
	@ViewInject(id = R.id.refresh_layout)
	private SwipeRefreshLayout swipeRefresh;

	/** 好友列表 */
	@ViewInject(id = R.id.expandableList)
	private ExpandableStickyListHeadersListView myFriendsView;

	/** 好友列表对应的Adapter **/
	private SelectedFriendsListCell friendAdapter;
	/** 字母索引 **/
	@ViewInject(id = R.id.letter_view)
	/** 分组下集合 */
	private ArrayList<Friend> childList = new ArrayList<Friend>();

	/** 字母 **/
	@ViewInject(id = R.id.letter_view)
	private AssortView assortView;
	// 选择的个数
	private int currentCount;
	private Map<Integer, Boolean> selectedPostion = new HashMap<Integer, Boolean>();
	private ArrayList<Friend> selectedFriend = new ArrayList<Friend>();
	
	private int MaxCount;
	private int recallid;
	
	private String movieid;
	/**0代表选择观影同伴
	 * 1 代表推荐给好友
	 * */
	private int type=0;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Bundle bundle=getArguments();
		type=bundle.getInt("type");
		if(type==0){
		MaxCount=bundle.getInt("FriendsCount");
		recallid=bundle.getInt("recallid");
		}else{
			movieid =bundle.getString("movieid");
		}
		
		// 设置adapter并且全部展开
		setUpListView();
		fastSearch();
		setRefresh();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		this.headView.setTitle("选择好友");
		this.headView.btnRight.setVisibility(View.VISIBLE);
		this.headView.btnRight.setText("确定(0)");
	}

	private void setUpListView() {
		friendAdapter = new SelectedFriendsListCell();
		friendAdapter.setListener(this);
		friendAdapter.setMaxCount(MaxCount,type);
		assortView.setOnTouchAssortListener(this);
		myFriendsView
				.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
					@Override
					public void onHeaderClick(StickyListHeadersListView l,
							View header, int itemPosition, long headerId,
							boolean currentlySticky) {
						if (myFriendsView.isHeaderCollapsed(headerId)) {
							myFriendsView.expand(headerId);
						} else {
							myFriendsView.collapse(headerId);
						}
					}
				});

	}

	private void closeRefresh() {
		swipeRefresh.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				swipeRefresh.setRefreshing(false);
			}
		});
	}

	/**
	 * 刷新
	 * */
	private void setRefresh() {
		swipeRefresh.setColorScheme(R.color.gary_line, R.color.white,
				R.color.gary_line, R.color.white);
		swipeRefresh.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				swipeRefresh.setRefreshing(true);
			}
		});
		OnRefreshListener listener = new OnRefreshListener() {
			public void onRefresh() {
				// TODO
				getFriends();
			}
		};
		swipeRefresh.setOnRefreshListener(listener);
		listener.onRefresh();

	}

	/**
	 * 快速搜索
	 * */
	private void fastSearch() {

		seachFriend.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String content = seachFriend.getText().toString();
				if (!TextUtils.isEmpty(content)) {

					Log.e("模糊查询", search(content).size() + "  ");
					friendAdapter.updateListView(search(content));
				} else {
					friendAdapter.updateListView(childList);
				}
			}
		});

	}

	/**
	 * 模糊查询
	 * 
	 * @param str
	 * @return
	 */
	private ArrayList<Friend> search(String str) {
		ArrayList<Friend> filterList = new ArrayList<Friend>();// 过滤后的list
		for (Friend friend : childList) {
			// 姓名全匹配,姓名首字母简拼匹配,姓名全字母匹配
			if (friend.getNickname().toLowerCase(Locale.CHINESE)
					.contains(str.toLowerCase(Locale.CHINESE))
					|| friendAdapter.getAssort()
							.getFirstChar(friend.getNickname())
							.toLowerCase(Locale.CHINESE).replace(" ", "")
							.contains(str.toLowerCase(Locale.CHINESE))) {
				if (!filterList.contains(friend)) {
					filterList.add(friend);
				}
			}

		}

		return filterList;
	}

	private void getFriends() {
		RelationListByUserIdRequest.findRelationListByUserId(UserProperty
				.getInstance().getUid(), 2, 1, AppConfig.PAGE_SIZE,
				new ResponseCallback() {

					@Override
					public <T> void sucess(Type type, ResponseResult<T> claszz) {
						// TODO Auto-generated method stub
						closeRefresh();
						if (claszz.getCode() == AppConfig.REQUEST_CODE_SUCCESS
								&& claszz.getModel() != null) {

							RelationListByUserId relationListByUserId = claszz
									.getModel();
							childList.clear();
							for (Friend friend : relationListByUserId
									.getRelations()) {

								childList.add(friend);
							}
							friendAdapter.setChildList(childList);
							myFriendsView.setAdapter(friendAdapter);
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

	@Override
	public void onTouchAssortListener(String str) {
		// TODO Auto-generated method stub
		int index = friendAdapter.getAssort().getHashList()
				.getFristCharIndex(str);
		// Tools.toastShow("==="+index);
		if (index != -1) {
			myFriendsView.setSelection(index);
		}
	}

	@Override
	public void onTouchAssortUP() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCheckedChanged(Friend friendModel, CompoundButton buttonView,
			boolean isChecked, Map<Integer, Boolean> selected) {
		// TODO Auto-generated method stub
		selectedPostion = selected;
		this.currentCount = selected.size();
		this.headView.btnRight.setText("确定(" + currentCount + ")");
	}

	@Override
	public void rightOnClick() {
		// TODO Auto-generated method stub
		super.rightOnClick();
		if (currentCount > 0) {
			if(type==0)
			addCompanion();
			else
			recommondMovie();
		}
	}

	/**
	 * 上传观影同伴
	 * 
	 * @param uri
	 */
	private void addCompanion() {
		StringBuffer buffer = new StringBuffer();
		for (int position : selectedPostion.keySet()) {
			Friend friend = childList.get(position);
			buffer.append(friend.getUserid() + ",");
			selectedFriend.add(friend);
		}
		if (!buffer.equals("") || buffer != null) {
			addRecallFriendsRequest.findaddRecallFriends(recallid + "",
					buffer.toString(), this);
		}

	}

	@Override
	public <T> void sucess(Type arg0, ResponseResult<T> arg1) {
		// TODO Auto-generated method stub
		super.sucess(arg0, arg1);
		if(type==0){
		Tools.photoListener.getSelectedPhotos(selectedFriend);
		}else{
			Tools.toastShow("推荐成功");
		}
		Tools.pullScreen();
		
	}

	@Override
	public void error(ResponseError arg0) {
		// TODO Auto-generated method stub
		Tools.toastShow(arg0.getMessage());
	}
	
	/**
	 * 推荐给好友
	 * 
	 * */
	
	public void recommondMovie(){
		StringBuffer buffer = new StringBuffer();
		for (int position : selectedPostion.keySet()) {
			Friend friend = childList.get(position);
			buffer.append(friend.getUserid() + ",");
		}
		if (!buffer.equals("") || buffer != null) {
		RecommendMovieToFriendsRequest.findRecommendMovie(UserProperty.getInstance().getUid(), movieid, buffer.toString(), this);
		}
	}
	
	
}
