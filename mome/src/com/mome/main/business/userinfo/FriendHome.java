package com.mome.main.business.userinfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.jessieray.api.model.ArticleListByUserId;
import com.jessieray.api.model.DynamicInfo;
import com.jessieray.api.model.GetArticleByUserId;
import com.jessieray.api.model.MyHomepage;
import com.jessieray.api.model.PersonalHomepage;
import com.jessieray.api.model.UserInfo;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.ArticleListByUserIdRequest;
import com.jessieray.api.service.GetArticleByUserIdRequest;
import com.jessieray.api.service.MyHomepageRequest;
import com.jessieray.api.service.PersonalHomepageRequest;
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

@LayoutInject(layout = R.layout.friends_home)
public class FriendHome extends BaseFragment {

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
	/**
	 * 关注
	 */
	@ViewInject(id = R.id.attention)
	private ImageButton attention;

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
    	
    }
	
	/**
	 * TA收藏
	 */
	@ViewInject(id=R.id.TAcollectionLayout)
    private LinearLayout  TAcollectionLayout;
	
	@OnClick(id=R.id.TAphotosLayout)
    public void TAcollectionClick(View view){
    //	Tools.pushScreen(baseFragment, args)
    }
	
	
	/**
	 * TA关注的人
	 */
	@ViewInject(id=R.id.TAattentionLayout)
    private LinearLayout  TAattentionLayout;
	
	@OnClick(id=R.id.TAphotosLayout)
    public void TAattentionClick(View view){
    	
    }
	
	/**
	 * 动态信息容器
	 */
	@ViewInject(id = R.id.friendsDynamicList)
	private ListView dynamicListLayout;

	private ArrayList<ListCellBase> dynamicListData = new ArrayList<ListCellBase>();
	/**
	 * 列表数据容器
	 */
	private ListAdapter adapter;

	/**
	 * listView实例
	 */
	private ListView listView;

	/**
	 * 
	 * 用户信息
	 */
	private PersonalHomepage userinfo;
	
	
	 private DynamicInfo friendInfo;
	 

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Bundle bundle=getArguments();
		friendInfo=(DynamicInfo)bundle.getSerializable("friendInfo");
		Tools.toastShow(friendInfo.getUserid()+"");
		getUserPage();
	}

	/**
	 * 我的主页的信息
	 * 
	 * */

	private void getUserPage() {
		PersonalHomepageRequest.findPersonalHomepage(friendInfo.getUserid()+"",UserProperty.getInstance().getUid(),
				new ResponseCallback() {

					@Override
					public <T> void sucess(Type type, ResponseResult<T> arg0) {
						// TODO Auto-generated method stub
						if (arg0.getCode() == AppConfig.REQUEST_CODE_SUCCESS
								&& arg0.getModel() != null) {
							userinfo = arg0.getModel();
							Tools.loadNetImage(headIcon, userinfo.getUserinfo().getAvatar(), R.drawable.default_ptr_flip,
									R.drawable.ic_launcher);
							userName.setText(userinfo.getUserinfo().getNickname());
							lookNum.setText(userinfo.getUserinfo()
									.getWatchtotal());
							movieType.setText(userinfo.getUserinfo()
									.getMovietype());
							signature_tv.setText(userinfo.getUserinfo()
									.getSignature());
							movieHouse.setText(userinfo.getUserinfo().getCinema());
							userId.setText("ID: "+ friendInfo.getUserid());
					//		setListViewData(userinfo.getUserinfo().getLastarticle());

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
	
	private void setListViewData(List<DynamicInfo> list) {
		adapter = new ListAdapter();
		adapter.setDataList(dynamicListData);
		listView.setAdapter(adapter);
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
