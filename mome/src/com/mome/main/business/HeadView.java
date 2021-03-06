package com.mome.main.business;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.mome.main.R;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;

/**
 * 头布局
 *
 */
@LayoutInject(layout = R.layout.include_head_layout)
public class HeadView extends BaseFragment{

	/**
	 * 按钮点击监听
	 */
	private HeadViewBtnOnClickListener headViewBtnOnClickListener;
	/**
	 * 头布局参数
	 */
	private HeadRef headRef;
	/**
	 * 左按钮
	 */
	@ViewInject(id = R.id.titlebar_left)
	private  TextView btnLeft;
	/**
	 * 标题
	 */
	@ViewInject(id = R.id.titlebar_title)
	private TextView title;
	/**
	 * 右按钮
	 */
	@ViewInject(id = R.id.titlebar_right)
	public TextView btnRight;
	/**
	 * 输入框布局
	 */
	@ViewInject(id = R.id.titlebar_input)
	private FrameLayout inputLayout;
	/**
	 * 输入框
	 */
	@ViewInject(id = R.id.titlebar_input_edittext)
	private EditText inputText;
	@ViewInject(id = R.id.titlebar_bg_layout)
	public  RelativeLayout titlebar_bg_layout;
	
	/**
	 * 清除
	 * */
	@OnClick(id=R.id.clear)
	public  void clearClick(View view){
		inputText.setText("");
	}
	
	public HeadView(HeadRef headRef,HeadViewBtnOnClickListener listener) {
		this.headRef = headRef;
		this.headViewBtnOnClickListener = listener;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initHead();
		if(inputLayout.getVisibility()==View.VISIBLE){
		inputText.setFocusable(true);  
		inputText.setFocusableInTouchMode(true);  
		inputText.requestFocus();  
		InputMethodManager inputManager =  
		               (InputMethodManager)inputText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);  
		           inputManager.showSoftInput(inputText, 0);  
		}
		
	}

	
	/**
	 * 初始化头布局
	 */
	private void initHead() {
		if(this.headRef != null) {
			if(this.headRef.iLeftBtnType == 0) {
				btnLeft.setText(this.headRef.strLeftBtnText);
			} else if(this.headRef.iLeftBtnType == 1) {
				btnLeft.setBackgroundDrawable(this.headRef.leftBtnImg);
			}
			if(this.headRef.iLeftBtnShow == 0) {
				btnLeft.setVisibility(View.GONE);
			} else if(this.headRef.iLeftBtnShow == 1) {
				btnLeft.setVisibility(View.VISIBLE);
			}
			if(this.headRef.iRightBtnType == 0) {
				btnRight.setText(this.headRef.strRightBtnText);
			} else if(this.headRef.iRightBtnType == 1) {
				btnRight.setBackgroundDrawable(this.headRef.rightBtnImg);
			}
			if(this.headRef.iRightBtnShow == 0) {
				btnRight.setVisibility(View.GONE);
			} else if(this.headRef.iRightBtnShow == 1) {
				btnRight.setVisibility(View.VISIBLE);
			}
			if(this.headRef.iTitleShow == 0) {
				title.setText(this.headRef.strTitleText);
				title.setVisibility(View.VISIBLE);
			} else if(this.headRef.iTitleShow == 1) {
				title.setVisibility(View.GONE);
			}
			if(this.headRef.iInputShow == 0) {
				inputLayout.setVisibility(View.VISIBLE);
				//inputText.addTextChangedListener(textWatcher);
				inputText.setOnEditorActionListener(editorActionListener);
			} else if(this.headRef.iInputShow == 1) {
				inputLayout.setVisibility(View.GONE);
			}
		}
	}
	
	
	public  String getText(){
		return inputText.getText().toString();
	}
	
	
	//调用搜索键盘
	public TextView.OnEditorActionListener editorActionListener=new OnEditorActionListener() {
		
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			// TODO Auto-generated method stub
			if(actionId==EditorInfo.IME_ACTION_SEARCH){
				if(headViewBtnOnClickListener != null&&!TextUtils.isEmpty(inputText.getText())) {
					headViewBtnOnClickListener.editTextChange(inputText.getText().toString());
				}
			
				return true;
			}
			return false;
		}
	};
//	/**
//	 * 输入框监听
//	 */
//	public TextWatcher textWatcher = new TextWatcher() {
//		
//		@Override
//		public void onTextChanged(CharSequence s, int start, int before, int count) {
//			
//		}
//		
//		@Override
//		public void beforeTextChanged(CharSequence s, int start, int count,
//				int after) {
//			
//		}
//		
//		@Override
//		public void afterTextChanged(Editable s) {
//			if(headViewBtnOnClickListener != null&&!TextUtils.isEmpty(inputText.getText())) {
//				headViewBtnOnClickListener.editTextChange(inputText.getText().toString());
//			}
//		}
//	};
	
	/**
	 * 左按钮点击监听
	 */
	@OnClick(id = R.id.titlebar_left)
	public void leftClick(View view) {
		if(headViewBtnOnClickListener != null) {
			headViewBtnOnClickListener.leftOnClick();
		}
	}
	
	/**
	 * 右按钮点击监听
	 */
	@OnClick(id = R.id.titlebar_right)
	public void rightClick(View view) {
		if(headViewBtnOnClickListener != null) {
			headViewBtnOnClickListener.rightOnClick();
		}
	}
	
	public void setTitle(String title) {
		this.title.setText(title);
	}
	
	public void setRightBtnShow(int visibility) {
		btnRight.setVisibility(visibility);
	}
	
	public void setLeftBtnShow(int visibility) {
		btnLeft.setVisibility(visibility);
	}
	
	/**
	 * 按钮点击监听
	 */
	public interface HeadViewBtnOnClickListener {
		public void leftOnClick();
		public void rightOnClick();
		public void editTextChange(String text);
	}
}
