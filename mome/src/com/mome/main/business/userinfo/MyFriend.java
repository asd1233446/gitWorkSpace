package com.mome.main.business.userinfo;

import java.lang.reflect.Type;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ListView;
import com.jessieray.api.model.Friend;
import com.jessieray.api.model.RelationListByUserId;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
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

@LayoutInject(layout=R.layout.myfriends)
public class MyFriend extends BaseFragment implements OnTouchAssortListener  {

/** 好友列表 */
@ViewInject(id=R.id.expandableList)
private ExpandableListView myFriendsView;

/** 好友列表对应的Adapter **/
private FriendsExpandablelistAdapter friendAdapter;

/** 字母索引 **/
@ViewInject(id=R.id.letter_view)
/** 分组集合 */
private ArrayList<String> groupList = new ArrayList<String>();

/** 分组下城市集合 */
private ArrayList<Friend>childList = new ArrayList<Friend>();

/** 好友模型 **/
private Friend selectedFriendModel;
/** 字母**/
@ViewInject(id=R.id.letter_view)
private AssortView assortView;


/** 好友关系 **/
private int  realationType=1;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		 // 设置adapter并且全部展开
		Bundle bundle=getArguments();
		realationType=bundle.getInt("realationType", 1);
		getFriends();
		setUpListView();

	}
	
	private void setUpListView(){
		friendAdapter = new FriendsExpandablelistAdapter();
		
	

	}

    private void getFriends(){
    	RelationListByUserIdRequest.findRelationListByUserId(UserProperty.getInstance().getUid(), realationType, 1, AppConfig.PAGE_SIZE, new ResponseCallback() {
			
			@Override
			public <T> void sucess(Type type, ResponseResult<T> claszz) {
				// TODO Auto-generated method stub
				if(claszz.getCode()==AppConfig.REQUEST_CODE_SUCCESS&&claszz.getModel()!=null){
					RelationListByUserId relationListByUserId=claszz.getModel();
					for(Friend friend:relationListByUserId.getRelations()){
				    	 childList.add(friend);
					}
					friendAdapter.setChildList(childList);
					myFriendsView.setAdapter(friendAdapter);
					for (int i = 0, length = friendAdapter.getGroupCount(); i < length; i++) {
				     	myFriendsView.expandGroup(i);
				}
					friendAdapter.notifyDataSetChanged();
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
		int index=friendAdapter.getAssort().getHashList().indexOfKey(str);
		   if(index!=-1)
		   {
			   myFriendsView.setSelectedGroup(index);;
		   }
	}

	@Override
	public void onTouchAssortUP() {
		// TODO Auto-generated method stub
		
	}
}
