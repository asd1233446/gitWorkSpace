package com.mome.main.business.userinfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.jessieray.api.model.Friend;
import com.jessieray.api.model.RelationListByUserId;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.CancelttentionRequest;
import com.jessieray.api.service.RelationListByUserIdRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.mome.pinyin.AssortView;
import com.mome.pinyin.AssortView.OnTouchAssortListener;

@LayoutInject(layout = R.layout.myfriends)
public class MyFriend extends BaseFragment implements OnTouchAssortListener,OnItemLongClickListener{
   @ViewInject(id=R.id.seachFriend)
	private EditText seachFriend;
   
   /** 刷新 */
   @ViewInject(id=R.id.refresh_layout)
   private SwipeRefreshLayout swipeRefresh;
	
	/** 好友列表 */
	@ViewInject(id = R.id.expandableList)
	private ExpandableStickyListHeadersListView myFriendsView;

	/** 好友列表对应的Adapter **/
	private FriendsExpandablelistAdapter friendAdapter;
	/** 字母索引 **/
	@ViewInject(id = R.id.letter_view)
	

	/** 分组下集合 */
	private ArrayList<Friend> childList = new ArrayList<Friend>();

	/** 字母 **/
	@ViewInject(id = R.id.letter_view)
	private AssortView assortView;

	/** 好友关系 1：关注的人 2：好友  3 粉丝**/
	private int realationType = 1;
	private String userId;
	private Bundle bundle;
	private String titleName;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		// 设置adapter并且全部展开
		bundle = getArguments();
		realationType = bundle.getInt("realationType", 1);
		titleName=bundle.getString("titleName");
		userId = bundle.getString("userId") != null ? bundle
				.getString("userId") : UserProperty.getInstance().getUid();

				
		setUpListView();
		fastSearch();
		setRefresh();

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
			this.headView.setTitle(titleName);	
	}

	private void setUpListView() {
		friendAdapter = new FriendsExpandablelistAdapter();
		friendAdapter.setInfo(realationType+"");
		assortView.setOnTouchAssortListener(this);
		myFriendsView.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
	            @Override
	            public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
	                if(myFriendsView.isHeaderCollapsed(headerId)){
	                	myFriendsView.expand(headerId);
	                }else {
	                	myFriendsView.collapse(headerId);
	                }
	            }
	        });
		if(realationType==2){
		myFriendsView.setOnItemLongClickListener(this);
		}
	}
	
	
	private void closeRefresh(){
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
	private void setRefresh(){
		swipeRefresh.setColorScheme(R.color.gary_line,R.color.white,R.color.gary_line,R.color.white);
		  swipeRefresh.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				swipeRefresh.setRefreshing(true);
			}
		});
		  OnRefreshListener listener = new OnRefreshListener(){
			    public void onRefresh(){
			         //TODO
			    	getFriends();
			    }
			};
			swipeRefresh.setOnRefreshListener(listener);
			listener.onRefresh();
		
		
	}
	
	/**
	 * 快速搜索
	 * */
	private void fastSearch(){
		
		seachFriend.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
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
				String content =seachFriend.getText().toString();
				if(!TextUtils.isEmpty(content)){
				
				Log.e("模糊查询", search(content).size()+"  ");
				friendAdapter.updateListView(search(content));
				}else{
					friendAdapter.updateListView(childList);
				}
			}
		});
		
	}
	
	/**
	 * 模糊查询
	 * @param str
	 * @return
	 */
	private ArrayList<Friend> search(String str) {
		ArrayList<Friend> filterList = new ArrayList<Friend>();// 过滤后的list
			for (Friend friend : childList) {
					//姓名全匹配,姓名首字母简拼匹配,姓名全字母匹配
					if (friend.getNickname().toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE))
							|| friendAdapter.getAssort().getFirstChar(friend.getNickname()).toLowerCase(Locale.CHINESE).replace(" ", "").contains(str.toLowerCase(Locale.CHINESE))) {
						if (!filterList.contains(friend)) {
							filterList.add(friend);
						}
					}
				
			}
		
		return filterList;
	}
	
	
	//public void get
	
	
	

	private void getFriends() {
		RelationListByUserIdRequest.findRelationListByUserId(userId, realationType, 1, AppConfig.PAGE_SIZE,
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
		int index = friendAdapter.getAssort().getHashList().getFristCharIndex(str);
		//Tools.toastShow("==="+index);
		if (index != -1) {
			myFriendsView.setSelection(index);			
		}
	}

	@Override
	public void onTouchAssortUP() {
		// TODO Auto-generated method stub

	}
	 
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		final Friend  friend=(Friend) parent.getItemAtPosition(position);
		Tools.toastShow(friend.getNickname());
		Tools.showDialog(getActivity(),"提示", "是否删除", "确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				 cancelAddttention(friend);
			}
		},null);
	
		return false;
	}
	
	private void cancelAddttention(final Friend friend){
	CancelttentionRequest.findCancelAddttention(UserProperty.getInstance()
			.getUid(), friend
			.getUserid(), new ResponseCallback() {

		@Override
		public <T> void sucess(Type type, ResponseResult<T> claszz) {
			// TODO Auto-generated method stub
			 childList.remove(friend);
			 friendAdapter.updateListView(childList);
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


	
}
