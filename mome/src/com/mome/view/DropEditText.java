package com.mome.view;
import com.mome.main.R;

import android.R.color;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class DropEditText extends FrameLayout implements View.OnClickListener, OnItemClickListener {
	private EditText mEditText;  // 输入框
	private LinearLayout mDropImage; // 右边的图片按钮
	private PopupWindow mPopup; // 点击图片弹出popupwindow
	private WrapListView mPopView; // popupwindow的布局
	
	private int mDrawableLeft;
	private int mDropMode; // flow_parent or wrap_content
	private String mHit;
	
	public DropEditText(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public DropEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.edit_layout, this);
		mPopView = (WrapListView) LayoutInflater.from(context).inflate(R.layout.pop_view, null);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		mEditText = (EditText) findViewById(R.id.dropview_edit);
		mDropImage = (LinearLayout) findViewById(R.id.showPoP);
		
		mEditText.setSelectAllOnFocus(true);	
		if(!TextUtils.isEmpty(mHit)) {
			mEditText.setHint(mHit);
		}
		
		mDropImage.setOnClickListener(this);
		mPopView.setOnItemClickListener(this);
	}
	
	
	public void setText(String text){
		mEditText.setText(text);
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		// 如果布局发生改
		// 并且dropMode是flower_parent
		// 则设置ListView的宽度
		if(changed && 0 == mDropMode) {
			mPopView.setListWidth(getMeasuredWidth());
		}
	}
	
	/**
	 * 设置Adapter
	 * @param adapter ListView的Adapter
	 */
	public void setAdapter(BaseAdapter adapter) {
		mPopView.setAdapter(adapter);
		
		mPopup = new PopupWindow(mPopView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		mPopup.setBackgroundDrawable(new ColorDrawable(color.transparent));
		mPopup.setFocusable(true); // 让popwin获取焦点
	}
	
	/**
	 * 获取输入框内的内容
	 * @return String content
	 */
	public String getText() {
		return mEditText.getText().toString();
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.showPoP) {
			if(mPopup.isShowing()) {
				mPopup.dismiss();
				return;
			}
			
			mPopup.showAsDropDown(this, 0, 5);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mEditText.setText(mPopView.getAdapter().getItem(position).toString());
		mPopup.dismiss();
		itemListener.onItemSelected(parent, view, position, id);
	}
	
	public OnItemSelectListener itemListener;
	
	public OnItemSelectListener getItemListener() {
		return itemListener;
	}

	public void setItemListener(OnItemSelectListener itemListener) {
		this.itemListener = itemListener;
	}

	public interface OnItemSelectListener{
		void onItemSelected(AdapterView<?> parent, View view, int position,
				long id);
	}
}
