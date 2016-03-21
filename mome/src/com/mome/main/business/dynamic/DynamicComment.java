package com.mome.main.business.dynamic;

import android.os.Bundle;
import android.widget.EditText;

import com.mome.main.R;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;

/**
 * 写评论
 *
 */
@LayoutInject(layout = R.layout.dynamic_comment)
public class DynamicComment extends BaseFragment{
	
	@ViewInject(id = R.id.dynamic_comment_edit)
	private EditText editText;
	/**
	 * 本类需要的参数
	 */
	public static final String KEY_NAME = "comment_user_name";
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = this.getArguments();
		if(bundle != null) {
			editText.setText(bundle.getString(KEY_NAME));
		}
	}

	@Override
	public void rightOnClick() {
		// TODO Auto-generated method stub
		super.rightOnClick();
	}

	
}
