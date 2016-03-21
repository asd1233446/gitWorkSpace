package com.mome.main.business.userinfo;

import android.widget.ListView;

import com.mome.main.R;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
@LayoutInject(layout=R.layout.mynews)
public class MyNews extends BaseFragment {
	/*
	 * 消息列表
	 * */
	@ViewInject(id=R.id.myMessage_lv)
 private ListView myMessage_lv;
}
