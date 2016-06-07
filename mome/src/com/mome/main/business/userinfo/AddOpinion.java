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
import android.widget.Toast;

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
@LayoutInject(layout=R.layout.add_opinion)
public class AddOpinion extends Activity implements OnCheckedChangeListener{

	@ViewInject(id=R.id.opinion_et)
	private EditText opinion_et;
	@ViewInject(id=R.id.adviseType)
    private RadioGroup adviseType;
	@ViewInject(id=R.id.qq_edite)
	private EditText qq;
	@ViewInject(id=R.id.telephone_edite)
	private EditText telephone;
	@ViewInject(id=R.id.phone_edite)
	private EditText phone;
	
	private int type=1;
	@OnClick(id=R.id.back)
    public void backClick(View view){
  	    finish();
    }

    @OnClick(id=R.id.addOpinion)
    public void addOpinionClick(View view){
    	addOpinion();
    }

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		InjectProcessor.activityInject(this);
		adviseType.setOnCheckedChangeListener(this);
	}
	
	
	public void addOpinion(){
		if(!TextUtils.isEmpty(opinion_et.getText().toString()))
		AddComplainRequest.findAddComplain(UserProperty.getInstance().getUid(), type, opinion_et.getText().toString(), qq.getText().toString(), telephone.getText().toString(), phone.getText().toString(),new ResponseCallback() {
			
			@Override
			public <T> void sucess(Type type, ResponseResult<T> claszz) {
				// TODO Auto-generated method stub
				Toast.makeText(AddOpinion.this, "提交成功", Toast.LENGTH_SHORT).show();
				finish();
			}
			
			@Override
			public boolean isRefreshNeeded() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void error(ResponseError error) {
				// TODO Auto-generated method stub
				Toast.makeText(AddOpinion.this, error.getMessage(), Toast.LENGTH_SHORT).show();

			}
		});
		else
			Tools.toastShow("意见不能为空");
	}


	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.bug:
			type = 1;
			break;
		case R.id.advise:
			type =2;
			break;
		case R.id.complain:
			type=3;
			break;

		default:
			break;
		}
	}
}
