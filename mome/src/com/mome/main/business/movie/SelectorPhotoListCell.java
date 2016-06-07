package com.mome.main.business.movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.AbsListView.LayoutParams;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jessieray.api.model.PhotoInfo;
import com.jessieray.api.model.UserAlbum;
import com.mome.main.R;
import com.mome.main.business.module.BaseAdapter;
import com.mome.main.business.userinfo.UserPhotoDetail;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.NativeImageLoader;
import com.mome.main.core.utils.NativeImageLoader.NativeImageCallBack;
import com.mome.main.core.utils.Tools;

public class SelectorPhotoListCell extends BaseAdapter<PhotoInfo> {
	private onPhotoItemCheckedListener listener;
	private onTakeCaptureListener mCaptureListener;
	private int itemWidth;
	private int horizentalNum = 3;
	private PhotoInfo mInfo;
	private android.widget.RelativeLayout.LayoutParams itemLayoutParams;
	private Map<Integer, Boolean> selected = new HashMap<Integer, Boolean>();
	private int maxCount;

	public SelectorPhotoListCell(Context context, ArrayList<PhotoInfo> models,
			int maxCount, onPhotoItemCheckedListener listener,
			onTakeCaptureListener mCaptureListener) {
		super(context, models);
		this.maxCount = maxCount;
		this.listener = listener;
		this.mCaptureListener = mCaptureListener;
		setItemWidth();
		// TODO Auto-generated constructor stub
	}

	public void setItemWidth() {
		int horizentalSpace = context.getResources().getDimensionPixelSize(
				R.dimen.common_samll_marginOrpadding);
		this.itemWidth = (AppConfig.SCREEN_WIDTH - (horizentalSpace * (horizentalNum - 1)))
				/ horizentalNum;
		this.itemLayoutParams = new RelativeLayout.LayoutParams(itemWidth,
				itemWidth);
	}

	ViewHolder viewHolder;

	@Override
	public View getView(final int position, View convertView,
			android.view.ViewGroup parent) {
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = InjectProcessor.injectListViewHolder(viewHolder);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.image.setLayoutParams(itemLayoutParams);
		if (position == 0) {
			viewHolder.image.setImageResource(R.drawable.addphoto);
			viewHolder.mCheckBox.setVisibility(View.INVISIBLE);
		} else {
			mInfo = models.get(position);
			NativeImageLoader.getInstance().loadNativeImage(viewHolder.image,
					mInfo.getPhotourl(),
					new Point(itemWidth / 2, itemWidth / 2), null);
			viewHolder.mCheckBox.setVisibility(View.VISIBLE);
			viewHolder.mCheckBox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked) {
								selected.put(position, true);
							} else {
								selected.remove(position);
							}
							listener.onCheckedChanged(mInfo, buttonView,
									isChecked, selected);
						}
					});

			viewHolder.mCheckBox.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub

					boolean isTouch = selected.size() == maxCount
							&& !((CheckBox) v).isChecked();
					if (isTouch) {
						Tools.toastShow("照片留念最多6位");
					}
					return isTouch;
				}
			});
			viewHolder.mCheckBox
					.setChecked(selected.get(position) == null ? false : true);
		}
		viewHolder.image.setOnClickListener(new lvButtonListener(position));

		return convertView;

	};

	@LayoutInject(layout = R.layout.layout_photoitem)
	public class ViewHolder {
		@ViewInject(id = R.id.iv_photo_lpsi)
		private ImageView image;

		@ViewInject(id = R.id.cb_photo_lpsi)
		private CheckBox mCheckBox;

	}

	class lvButtonListener implements OnClickListener {
		private int position;

		lvButtonListener(int pos) {
			position = pos;
		}

		@Override
		public void onClick(View v) {
			if (position == 0) {
				// 吊起相机
				mCaptureListener.OnTakeCapture();
				return;
			}
			Bundle bundle = new Bundle();
			bundle.putSerializable("photoinfo", models.get(position));
			bundle.putSerializable("isNetwork", false);
			Tools.pushScreen(UserPhotoDetail.class, bundle);
		}
	}

	public interface onPhotoItemCheckedListener {
		public void onCheckedChanged(PhotoInfo photoModel,
				CompoundButton buttonView, boolean isChecked,
				Map<Integer, Boolean> selected);
	}

	public interface onTakeCaptureListener {
		public void OnTakeCapture();
	}

}
