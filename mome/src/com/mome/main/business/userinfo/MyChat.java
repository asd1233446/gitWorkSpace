package com.mome.main.business.userinfo;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mome.db.ChatItem;
import com.mome.db.MsgDbHelper;
import com.mome.db.NewMsgDbHelper;
import com.mome.main.R;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshListView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
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

	private String friendName;

	private MyChatAdpater adapter;
	
	private ListView listView;

	private List<ChatItem> chatItems = new ArrayList<ChatItem>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		friendName = bundle.getString("friendName");
		setUpListView();
		qryMsg();
        
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		headView.setTitle(friendName);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				List<com.mome.db.ChatItem> moreChatItems = MsgDbHelper
						.getInstance(getActivity()).getChatMsgMore(
								listView.getCount() - 1, friendName);
				for (int i = 0; i < moreChatItems.size(); i++) {
					chatItems.add(i, moreChatItems.get(i));
				}
				adapter.clear();
				adapter.addAll(chatItems);
				adapter.notifyDataSetChanged();
				PullToRefreshListView.onRefreshComplete();
			}
		}
	};

	private void qryMsg() {
		chatItems = MsgDbHelper.getInstance(getActivity()).getChatMsg(
				friendName);
		adapter.clear();
		adapter.addAll(chatItems);
		listView.setSelection(adapter.getCount() + 1);
	}

	private void setUpListView() {
		listView=PullToRefreshListView.getRefreshableView();
		adapter = new MyChatAdpater(getActivity(), friendName);
		PullToRefreshListView.setAdapter(adapter);
		PullToRefreshListView.setMode(Mode.DISABLED);
		PullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				Tools.toastShow("下拉刷新");
				handler.sendEmptyMessage(0);
			}
		});

	}

	// 发送留言
	@OnClick(id = R.id.sendMgs)
	public void sendMsgClick(View view) {
		if(TextUtils.isEmpty( message.getText())){
			return;
		}
		message.clearFocus();
		ChatItem msg = new ChatItem(0, friendName, friendName, "", message.getText()
				.toString(), DateUtil.now_yyyy_MM_dd_HH_mm(), 0);
		NewMsgDbHelper.getInstance(getActivity()).saveNewMsg("");
		MsgDbHelper.getInstance(getActivity()).saveChatMsg(msg);
		adapter.clear();
		chatItems.add(msg);
	    adapter.addAll(chatItems);
		adapter.notifyDataSetChanged();
		message.setText("");
	}

}
