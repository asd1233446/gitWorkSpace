package com.mome.main.business.userinfo;

import java.lang.reflect.Type;
import java.util.RandomAccess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.jessieray.api.model.Complains;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.AddComplainRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.Tools;

@LayoutInject(layout = R.layout.answer_opinion)
public class AnswerComplian extends Activity  {

	@ViewInject(id = R.id.opinion_tv)
	private TextView opinion;
	@ViewInject(id = R.id.answer_tv)
	private TextView answer;

	@OnClick(id = R.id.back)
	public void backClick(View view) {
		finish();
	}

	private Intent intent;
	private Complains complain;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		InjectProcessor.activityInject(this);
		intent=getIntent();
		complain=(Complains) intent.getExtras().getSerializable("complian");
		initData();
	}
	
	public void  initData(){
		opinion.setText(complain.getBrief());
		answer.setText(complain.getFeedback());
	}

	
}
