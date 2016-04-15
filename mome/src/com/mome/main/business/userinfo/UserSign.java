package com.mome.main.business.userinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.mome.main.R;


public class UserSign extends Activity implements OnClickListener{
	
	private EditText userSign;
	
	private TextView  titlebar_left;
	private TextView  titlebar_right;
	private TextView  titlebar_title;
	private Intent intent;
		
   @SuppressWarnings("deprecation")
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.update_usersig);
	userSign=(EditText) findViewById(R.id.userSign_et);
	titlebar_left=(TextView) findViewById(R.id.titlebar_left);
	titlebar_right=(TextView) findViewById(R.id.titlebar_right);
	titlebar_title=(TextView) findViewById(R.id.titlebar_title);
	titlebar_left.setVisibility(View.VISIBLE);
	titlebar_right.setVisibility(View.VISIBLE);
	titlebar_right.setText("完成");
	titlebar_title.setText("个性签名");
	titlebar_left.setBackgroundDrawable(getResources().getDrawable(R.drawable.headview_btn_back));
	titlebar_right.setOnClickListener(this);
	titlebar_left.setOnClickListener(this);
	intent=getIntent();
	Log.e("昵称", intent+"=="+intent.getExtras().getString("nickName"));
	userSign.setText(intent.getExtras().getString("sign"));
}


@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.titlebar_left:
		finish();
		break;
	case R.id.titlebar_right:
		intent.putExtra("sign", userSign.getText().toString());
		setResult(RESULT_OK, intent);
		finish();
		break;
	default:
		break;
	}
}


	
	
	
	

}
