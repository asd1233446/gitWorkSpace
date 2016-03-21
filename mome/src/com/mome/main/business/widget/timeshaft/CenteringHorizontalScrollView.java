package com.mome.main.business.widget.timeshaft;

import com.mome.main.business.widget.timeshaft.CustomTimeShaft.ItemSelectedListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CenteringHorizontalScrollView extends HorizontalScrollView implements View.OnTouchListener {

	private Context mContext;

	private static final int SWIPE_PAGE_ON_FACTOR = 10;

	private int mActiveItem;

	private float mPrevScrollX;

	private boolean mStart;

	private int mItemWidth;

	View targetLeft, targetRight;
	
	public final int CENTER_W = 550;
	public final int CENTER_H = 700;
	public final int CENTER_OFFSET_SIZE = 100;
	private ItemSelectedListener itemSelectedListener;

	public CenteringHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mContext=context;
		mItemWidth = 500; // or whatever your item width is.
		setOnTouchListener(this);
	}

	private int getMaxItemCount() {
		return ((LinearLayout) getChildAt(0)).getChildCount();
	}

	private LinearLayout getLinearLayout() {
		return (LinearLayout) getChildAt(0);
	}

	/**
	 * Centers the current view the best it can.
	 */
	public void centerCurrentItem() {
		if (getMaxItemCount() == 0) {
			return;
		}

		int currentX = getScrollX();
		View targetChild;
		int currentChild = -1;

		do {
			currentChild++;
			targetChild = getLinearLayout().getChildAt(currentChild);
		} while (currentChild < getMaxItemCount() && targetChild.getLeft() < currentX);

		if (mActiveItem != currentChild) {
			mActiveItem = currentChild;
			scrollToActiveItem();
		}
	}

	/**
	 * Scrolls the list view to the currently active child.
	 */
	private void scrollToActiveItem() {
		int maxItemCount = getMaxItemCount();
		if (maxItemCount == 0) {
			return;
		}

		int targetItem = Math.min(maxItemCount - 1, mActiveItem);
		targetItem = Math.max(0, targetItem);

		mActiveItem = targetItem;
		if(itemSelectedListener != null) {
			itemSelectedListener.itemSelected(mActiveItem);
		}

		// Scroll so that the target child is centered
		View targetView = getLinearLayout().getChildAt(targetItem);

//		ImageView centerImage = (ImageView)targetView;
		int height=targetView.getHeight();//set size of centered image
		LinearLayout.LayoutParams flparams = new LinearLayout.LayoutParams(CENTER_W, CENTER_H);
		targetView.setLayoutParams(flparams);

		//get the image to left of the centered image
		if((targetItem-1)>=0){
			targetLeft = getLinearLayout().getChildAt(targetItem-1);
			int width=250;//set the size of left image
			LinearLayout.LayoutParams leftParams = new LinearLayout.LayoutParams(CENTER_W-CENTER_OFFSET_SIZE,CENTER_H-CENTER_OFFSET_SIZE);
			leftParams.setMargins(25, 50, 25, 0);
			targetLeft.setLayoutParams(leftParams);
		}

		//get the image to right of the centered image
		if((targetItem+1)<maxItemCount){
			targetRight = getLinearLayout().getChildAt(targetItem+1);
			int width=250;//set the size of right image
			LinearLayout.LayoutParams rightParams = new LinearLayout.LayoutParams(CENTER_W-CENTER_OFFSET_SIZE,CENTER_H-CENTER_OFFSET_SIZE);
			rightParams.setMargins(25, 50, 25, 0);
			targetRight.setLayoutParams(rightParams);
		}
		
//		RelativeLayout parentRl=(RelativeLayout)getLinearLayout().getParent().getParent();
//		if(parentRl!=null){
//			TextView numTv=(TextView)parentRl.getChildAt(1);
//			numTv.setText("Image number: "+String.valueOf(targetItem+1)+" enlarged");
//		}

		int targetLeft = targetView.getLeft();
		int childWidth = targetView.getRight() - targetLeft;

		int width = getWidth() - getPaddingLeft() - getPaddingRight();
		int targetScroll = targetLeft - ((width - childWidth) / 2);

		super.smoothScrollTo(targetScroll, 0);
	}

	/**
	 * Sets the current item and centers it.
	 * @param currentItem The new current item.
	 */
	public void setCurrentItemAndCenter(int currentItem) {
		mActiveItem = currentItem;
		scrollToActiveItem();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int x = (int) event.getRawX();

		boolean handled = false;
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			if (mStart) {
				mPrevScrollX = x;
				mStart = false;
			}

			break;
		case MotionEvent.ACTION_UP:
			mStart = true;
			int minFactor = mItemWidth / SWIPE_PAGE_ON_FACTOR;

			if ((mPrevScrollX - (float) x) > minFactor) {
				if (mActiveItem < getMaxItemCount() - 1) {
					mActiveItem = mActiveItem + 1;
				}
			}
			else if (((float) x - mPrevScrollX) > minFactor) {
				if (mActiveItem > 0) {
					mActiveItem = mActiveItem - 1;
				}
			}

			scrollToActiveItem();

			handled = true;
			break;
		}

		return handled;
	}

	public ItemSelectedListener getItemSelectedListener() {
		return itemSelectedListener;
	}

	public void setItemSelectedListener(ItemSelectedListener itemSelectedListener) {
		this.itemSelectedListener = itemSelectedListener;
	}

}
