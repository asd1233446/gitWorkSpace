package com.mome.main.business.discovery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jessieray.api.model.UserInfo;
import com.mome.main.R;
import com.mome.main.business.access.ResultListener;
import com.mome.main.business.access.WeiboLogin;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.Tools;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.constant.WBConstants;

@LayoutInject(layout = R.layout.invite_friends_list)
public class FriendsList extends Activity implements OnItemClickListener,
		ResultListener, IWeiboHandler.Response {
	@ViewInject(id = R.id.titlebar_left)
	public TextView back;

	@OnClick(id = R.id.titlebar_left)
	public void backClick(View view) {
		finish();
	}


	@ViewInject(id = R.id.titlebar_title)
	public TextView title;

	@ViewInject(id = R.id.listView)
	private ListView mListView;
	private ListAdapter adapter;
	private ArrayList<ListCellBase> listData = new ArrayList<ListCellBase>();
	// 0新浪
	private int type = 0;
	public static WeiboLogin weibo;

	private IWeiboShareAPI mWeiboShareAPI;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		InjectProcessor.activityInject(this);
		if (savedInstanceState != null && mWeiboShareAPI != null) {
			mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
		}
		Intent intent = getIntent();
		type = intent.getIntExtra("type", 0);
		init();
		setUpView();
	}

	public void init() {
		switch (type) {
		case 0:
			title.setText("微博好友");
			Log.e("查询好友", "查询微博好友");
			Map<String, String> parms = new HashMap<String, String>();
			parms.put("count", "20");
			parms.put("page", "1");
			weibo = new WeiboLogin(this);
			weibo.query(1, parms, this);
			mWeiboShareAPI = weibo.registerToSina();
			break;
		case 1:
			title.setText("通讯录好友");
			try {
				getContact();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	private void setUpView() {
		back.setVisibility(View.VISIBLE);
		back.setBackgroundResource(R.drawable.headview_btn_back);
		adapter = new ListAdapter();
		adapter.setDataList(listData);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);
	}


	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		FriendsListCell listcell = (FriendsListCell) parent
				.getItemAtPosition(position);
		Tools.toastShow(listcell.getUser().getNickname()+"==========");

	}

	// 查询所有联系人的姓名，电话，邮箱
	public void getContact() throws Exception {
		Uri uri = Uri.parse("content://com.android.contacts/contacts");
		ContentResolver resolver = this.getContentResolver();
		Cursor cursor = resolver.query(uri, new String[] { "_id" }, null, null,
				null);
		while (cursor.moveToNext()) {
			int contractID = cursor.getInt(0);
			StringBuilder sb = new StringBuilder();
			uri = Uri.parse("content://com.android.contacts/contacts/"
					+ contractID + "/data");
			Cursor cursor1 = resolver.query(uri, new String[] { "mimetype",
					"data1", "data2" }, null, null, null);
			FriendsListCell cell = new FriendsListCell(this, type);
			UserInfo userinfo = new UserInfo();
			while (cursor1.moveToNext()) {
				String data1 = cursor1.getString(cursor1
						.getColumnIndex("data1"));
				String mimeType = cursor1.getString(cursor1
						.getColumnIndex("mimetype"));
				if ("vnd.android.cursor.item/name".equals(mimeType)) { // 是姓名
					userinfo.setNickname(data1);
				} else if ("vnd.android.cursor.item/phone_v2".equals(mimeType)) { // 手机
					// sb.append("" + data1);//取得最后一个手机号
					userinfo.setPhone(data1);
				}
			}

			cell.setUserInfo(userinfo);
			listData.add(cell);
			cursor1.close();
			Log.i("查找联系人", sb.toString());
		}

		adapter.notifyDataSetChanged();
		cursor.close();
	}

	@Override
	public void sucess(Object object) {
		// TODO Auto-generated method stub
		Log.e("成功返回", object.toString());
		JSONObject json;
		JSONArray arry;
		try {
			json = new JSONObject((String) object);
			arry = json.getJSONArray("users");
			if (arry != null && arry.length() > 0)
				for (int i = 0; i < arry.length(); i++) {
					json = arry.getJSONObject(i);
					UserInfo info = new UserInfo();
					info.setUserid(json.getString("id"));
					info.setAvatar(json.getString("profile_image_url"));
					info.setNickname(json.getString("screen_name"));
					FriendsListCell cell = new FriendsListCell(this, type);
					cell.setUserInfo(info);
					listData.add(cell);
				}
			adapter.notifyDataSetChanged();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void error(String error) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "查询失败", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (weibo.getSsoHandler() != null) {
			weibo.getSsoHandler().authorizeCallBack(requestCode, resultCode,
					data);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		mWeiboShareAPI.handleWeiboResponse(intent, this); // 当前应用唤起微博分享后,返回当前应用
	}

	/**
	 * 接收微客户端博请求的数据。 当微博客户端唤起当前应用并进行分享时，该方法被调用。
	 * 
	 * @param baseRequest
	 *            微博请求数据对象
	 * @see {@link IWeiboShareAPI#handleWeiboRequest}
	 */
	@Override
	public void onResponse(BaseResponse baseResp) {
		switch (baseResp.errCode) {
		case WBConstants.ErrorCode.ERR_OK:
			Toast.makeText(this, "邀请成功", Toast.LENGTH_LONG).show();
			break;
		case WBConstants.ErrorCode.ERR_CANCEL:
			Toast.makeText(this, "邀请失败" + baseResp.errMsg + baseResp.errCode,
					Toast.LENGTH_LONG).show();
			break;
		case WBConstants.ErrorCode.ERR_FAIL:
			Toast.makeText(this, "邀请失败" + baseResp.errMsg + baseResp.errCode,
					Toast.LENGTH_LONG).show();
			break;
		}
	}

}
