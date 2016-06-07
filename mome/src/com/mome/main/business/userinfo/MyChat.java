package com.mome.main.business.userinfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.jessieray.api.model.Message;
import com.jessieray.api.model.MessageInfo;
import com.jessieray.api.request.base.RequestUtils;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.MessageInfoRequest;
import com.mome.db.ChatItem;
import com.mome.db.MsgDbHelper;
import com.mome.db.NewMsgDbHelper;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.DateUtil;
import com.mome.main.core.utils.Tools;

/**
 * 
 * 私信留言
 * */
@LayoutInject(layout = R.layout.friend_chat)
public class MyChat extends BaseFragment {
	/**
	 * 留言列表
	 * **/
	@ViewInject(id = R.id.listView)
	private PullToRefreshListView PullToRefreshListView;
	/**
	 * 信息
	 * **/
	@ViewInject(id = R.id.msgText)
	private EditText message;
	/**
	 * 发送
	 * **/
	@ViewInject(id = R.id.sendMgs)
	private Button sendMsg;

	/**
	 * 留言对象
	 * **/

	private MessageInfo mgsinfo;

	private MyChatAdpater adapter;

	private ListView listView;
	private String Msg;
	private int curPageIndex = 1;
	private ArrayList<MessageInfo> chatItems = new ArrayList<MessageInfo>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		mgsinfo = (MessageInfo) bundle.getSerializable("message");
		setUpListView();

		// qryMsg();

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		headView.setTitle(mgsinfo.getNickname());
	}

	// private Handler handler = new Handler() {
	// @Override
	// public void handleMessage(Message msg) {
	// if (msg.what == 0) {
	// List<com.mome.db.ChatItem> moreChatItems = MsgDbHelper
	// .getInstance(getActivity()).getChatMsgMore(
	// listView.getCount() - 1, mgsinfo.getNickname());
	// for (int i = 0; i < moreChatItems.size(); i++) {
	// chatItems.add(i, moreChatItems.get(i));
	// }
	// adapter.clear();
	// adapter.addAll(chatItems);
	// adapter.notifyDataSetChanged();
	// PullToRefreshListView.onRefreshComplete();
	// }
	// }
	// };
	//
	// private void qryMsg() {
	// chatItems = MsgDbHelper.getInstance(getActivity()).getChatMsg(
	// mgsinfo.getNickname());
	// adapter.clear();
	// adapter.addAll(chatItems);
	// listView.setSelection(adapter.getCount() + 1);
	// }

	private void setUpListView() {
		listView = PullToRefreshListView.getRefreshableView();
		adapter = new MyChatAdpater(getActivity(), chatItems);
		listView.setAdapter(adapter);
		PullToRefreshListView.setMode(Mode.PULL_FROM_START);
		Tools.setRereshing(PullToRefreshListView);
		PullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						Tools.toastShow("下拉刷新");
						getMessage();
					}
				});

	}

	// 发送留言
	@OnClick(id = R.id.sendMgs)
	public void sendMsgClick(View view) {
		if (TextUtils.isEmpty(message.getText())) {
			return;
		}
		Msg = message.getText().toString();
		getSendMessage(message.getText().toString());
		message.setText("");
		// message.clearFocus();
		// ChatItem msg = new ChatItem(0, mgsinfo.getNickname(),
		// mgsinfo.getNickname(), "", message.getText().toString(),
		// DateUtil.now_yyyy_MM_dd_HH_mm(), 0);
		// NewMsgDbHelper.getInstance(getActivity()).saveNewMsg("");
		// MsgDbHelper.getInstance(getActivity()).saveChatMsg(msg);
		// adapter.clear();
		// chatItems.add(msg);
		// adapter.addAll(chatItems);
		// adapter.notifyDataSetChanged();
		// message.setText("");
	}

	private void getMessage() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid",
				RequestUtils.object2String(UserProperty.getInstance().getUid()));
		params.put("activeid", RequestUtils.object2String(mgsinfo.getActiveid()));
		params.put("pageSize", RequestUtils.object2String(AppConfig.PAGE_SIZE));
		params.put("pageNo", RequestUtils.object2String(curPageIndex));
		MessageInfoRequest.findMeaage(params, "/PersonalMessages.shtml", this);

	}

	private void getSendMessage(String brief) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid",
				RequestUtils.object2String(UserProperty.getInstance().getUid()));
		params.put("toid", mgsinfo.getActiveid());
		params.put("brief", brief);

		MessageInfoRequest.sendMeaage(params, "/SendPersonalMessage.shtml",
				this);

	}

	@Override
	public <T> void sucess(Type arg0, ResponseResult<T> arg1) {
		// TODO Auto-generated method stub
		PullToRefreshListView.onRefreshComplete();
		if (arg1.getModel() != null && arg1.getModel() instanceof Message) {
			Message messageList = arg1.getModel();
			if (Tools.calculateTotalPage(messageList.getTotoal()) > curPageIndex) {
				PullToRefreshListView.setMode(Mode.PULL_FROM_START);
				curPageIndex++;
			} else {
				PullToRefreshListView.setMode(Mode.DISABLED);
			}
			chatItems.addAll(0, messageList.getMessages());
			adapter.notifyDataSetChanged();

		} else {
			Tools.toastShow("添加成功");
			MessageInfo message = new MessageInfo();
			message.setFromid(UserProperty.getInstance().getUid());
			message.setAvatar(mgsinfo.getAvatar());
			message.setBreif(Msg);
			message.setCreatetime(DateUtil.now_yyyy_MM_dd_HH_mm());
			chatItems.add(message);
			adapter.notifyDataSetChanged();

		}
	}

	@Override
	public void error(ResponseError arg0) {
		// TODO Auto-generated method stub
		PullToRefreshListView.onRefreshComplete();
		Tools.toastShow(arg0.getMessage());
	}
}
