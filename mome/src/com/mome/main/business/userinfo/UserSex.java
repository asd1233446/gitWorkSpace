package com.mome.main.business.userinfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.mome.main.R;
import com.mome.main.business.access.WeiboLogin;
import com.mome.main.core.utils.Tools;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.constant.WBConstants;

public class UserSex extends Activity implements OnClickListener,
		OnCheckedChangeListener{

	private RadioGroup radio_gp;

	private TextView titlebar_left;
	private TextView titlebar_right;
	private TextView titlebar_title;
	private Intent intent;
	private String sex;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_usersex);
		radio_gp = (RadioGroup) findViewById(R.id.radio_gp);
		titlebar_left = (TextView) findViewById(R.id.titlebar_left);
		titlebar_right = (TextView) findViewById(R.id.titlebar_right);
		titlebar_title = (TextView) findViewById(R.id.titlebar_title);
		titlebar_left.setVisibility(View.VISIBLE);
		titlebar_right.setVisibility(View.VISIBLE);
		titlebar_right.setText("完成");
		titlebar_title.setText("昵称");
		titlebar_left.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.headview_btn_back));
		titlebar_right.setOnClickListener(this);
		titlebar_left.setOnClickListener(this);
		intent = getIntent();
		sex = intent.getExtras().getString("sex");
		radio_gp.setOnCheckedChangeListener(this);
		if (TextUtils.isEmpty(sex)) {
			radio_gp.check(-1);
			return;
		}
		radio_gp.check("男".equals(sex) ? 0 : 1);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.titlebar_left:
			finish();
			break;
		case R.id.titlebar_right:
			intent.putExtra("sex", sex);
			setResult(RESULT_OK, intent);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.man:
			sex = "男";
			break;
		case R.id.woman:
			sex = "女";
			break;

		default:
			break;
		}

	}

	

}
