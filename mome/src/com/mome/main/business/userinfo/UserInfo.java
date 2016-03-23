package com.mome.main.business.userinfo;

import java.lang.reflect.Type;

import com.jessieray.api.model.Me;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.MeRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.movie.MovieMemoirs;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

@LayoutInject(layout = R.layout.userinfo)
public class UserInfo extends BaseFragment {
	
	/**
	 * 用户头像
	 */
	@ViewInject(id = R.id.userinfo_head_icon)
	private ImageView userIcon;
	/**
	 * 用户名称
	 */
	@ViewInject(id = R.id.userinfo_name)
	private TextView name;
	/**
	 * mome号
	 */
	@ViewInject(id = R.id.userinfo_mome_number)
	private TextView momeNumber;
	/**
	 * 消息数量
	 */
	@ViewInject(id = R.id.userinfo_message_value)
	private TextView messageNum;
	/**
	 * 评论数量
	 */
	@ViewInject(id = R.id.userinfo_comment_value)
	private TextView commentNum;
	/**
	 * 私信数量
	 */
	@ViewInject(id = R.id.userinfo_mail_value)
	private TextView mailNum;
	/**
	 * 缓存数据大小
	 */
	@ViewInject(id = R.id.userinfo_clear_value)
	private TextView cache;
	/**
	 * 我的影院名称
	 */
	@ViewInject(id = R.id.userinfo_my_cinema_value)
	private TextView cinemaName;

	@Override
	public void onResume() {
		super.onResume();
		MeRequest.findMe(UserProperty.getInstance().getUid(), this);
	}
	
	/**
	 * 设置点击事件
	 * @param view
	 */
	@OnClick(id = R.id.userinfo_set)
	public void setClick(View view) {
		Tools.pushScreen(Set.class, null);
	}
	
	/**
	 * 私信点击事件
	 * @param view
	 */
	@OnClick(id = R.id.userinfo_mail_layout)
	public void mailClick(View view) {
		Tools.pushScreen(MyMessage.class, null);
		
	}
	
	/**
	 * 消息点击事件
	 * @param view
	 */
	@OnClick(id = R.id.userinfo_message_layout)
	public void messageClick(View view) {
		Tools.pushScreen(MyNews.class, null);
	}
	
	/**
	 * 我的收藏
	 */
	@OnClick(id = R.id.userinfo_my_enshrine)
	public void enshrineClick(View view) {
		Tools.pushScreen(MyCollect.class, null);
	}
	
	/**
	 * 我的数据
	 */
	@OnClick(id = R.id.userinfo_my_data)
	public void myDataClick(View view) {
		Tools.pushScreen(UserData.class, null);
	}
	
	/**
	 * 我的回忆
	 */
	@OnClick(id = R.id.userinfo_memory)
	public void memoryClick(View view) {
		Tools.pushScreen(MovieMemoirs.class, null);
	}
	
	/**
	 * 用户头像点击
	 * @param view
	 */
	@OnClick(id = R.id.userinfo_head_icon)
	public void userHeadClick(View view) {
	Tools.pushScreen(MovieMemoirs.class, null);

	}
	
	/**
	 * 用户信息点击
	 * @param view
	 */
	@OnClick(id = R.id.userinfo_head_layout)
	public void userHeadLayoutClick(View view) {
		Tools.pushScreen(UserHome.class, null);
	}
	
	/**
	 * 意见反馈
	 * @param view
	 */
	@OnClick(id = R.id.userinfo_feedback)
	public void feedbackClick(View view) {
		
	}
	
	/**
	 * 我的评分点击事件
	 * @param view
	 */
	@OnClick(id = R.id.userinfo_my_score)
	public void scoreClick(View view) {
		
	}
	
	/**
	 * 我的相册点击
	 * @param view
	 */
	@OnClick(id = R.id.userinfo_my_photo)
	public void myPhotoClick(View view) {
		Tools.pushScreen(MyPhoto.class, null);

	}
	
	/**
	 * 我的好友
	 * @param view
	 */
	@OnClick(id = R.id.userinfo_my_friend)
	public void myFriendClick(View view) {
		Tools.pushScreen(MyFriend.class, null);

	}
	
	/**
	 * 我的影院点击
	 * @param view
	 */
	@OnClick(id = R.id.userinfo_my_cinema)
	public void myCinemaClick(View view) {
		Tools.pushScreen(MyCinema.class, null);

		
	}
	
	/**
	 * 清楚缓存
	 * @param view
	 */
	@OnClick(id = R.id.userinfo_clear_layout)
	public void clearClick(View view) {
		
	}
	
	/**
	 * 退出按钮
	 * @param view
	 */
	@OnClick(id = R.id.userinfo_btn_quit)
	public void quitClick(View view) {
		
	}
	
	private void updateUI() {
		Tools.loadNetImage(userIcon, UserProperty.getInstance().getUserInfo().getAvatar(), R.drawable.default_ptr_flip, R.drawable.default_ptr_flip);
		name.setText(UserProperty.getInstance().getUserInfo().getNickname());
		if(TextUtils.isEmpty(UserProperty.getInstance().getUserInfo().getMomeid())) {
			momeNumber.setText("MOME号:"+UserProperty.getInstance().getUserInfo().getMomeid());
		}
		messageNum.setText(UserProperty.getInstance().getUserInfo().getSystemmessages());
		commentNum.setText(UserProperty.getInstance().getUserInfo().getCustommessages());
		mailNum.setText(UserProperty.getInstance().getUserInfo().getCustommessages());
		cinemaName.setText(UserProperty.getInstance().getUserInfo().getCinema());
	}
	
	@Override
	public <T> void sucess(Type type, ResponseResult<T> data) {
		if(data.getCode() == AppConfig.REQUEST_CODE_SUCCESS) {
			if(MeRequest.resultType.equals(type)) {
				Me userInfo = data.getModel();
				if(userInfo != null) {
					UserProperty.getInstance().setUserInfo(userInfo.getUserinfo());
					updateUI();
				}
			}
		}
	}
}
