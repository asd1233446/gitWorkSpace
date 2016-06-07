package com.mome.main.business.movie;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.dimen;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.GridView;

import com.jessieray.api.model.PhotoInfo;
import com.jessieray.api.model.UploadImage;
import com.jessieray.api.model.UserAlbum;
import com.jessieray.api.model.savePicture;
import com.jessieray.api.model.savePicture.Success;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.UserAlbumRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.movie.SelectorPhotoListCell.onPhotoItemCheckedListener;
import com.mome.main.business.movie.SelectorPhotoListCell.onTakeCaptureListener;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshGridView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.mome.main.core.utils.UploadUtil;
import com.mome.main.netframe.volley.toolbox.MultipartRequestParams;
import com.mome.view.LoadingDialog;
import com.mome.view.MyDeImageView;

@LayoutInject(layout = R.layout.selector_photo)
public class SelectorPhoto extends BaseFragment implements
		onPhotoItemCheckedListener, onTakeCaptureListener {
	/**
	 * 相册
	 * 
	 * */
	@ViewInject(id = R.id.gv_photos_ar)
	private GridView mGridView;
	private SelectorPhotoListCell adapter;
	private ArrayList<PhotoInfo> dataList = new ArrayList<PhotoInfo>();
	private ArrayList<PhotoInfo> sucessPhotos = new ArrayList<PhotoInfo>();
	private Map<String, PhotoInfo> selectedPhotos = new HashMap<String, PhotoInfo>();
	private List<String> photour = new ArrayList<String>();
	private Map<Integer, Boolean> selectedPostion;
	private int CurrentCount = 0;
	private int MaxCount;
	private int recallid;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		MaxCount = bundle.getInt("photoCount");
		recallid = bundle.getInt("recallid");
		setUpGridview();
		getPhotos();

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	private void setUpGridview() {
		PhotoInfo mPhotoInfo = new PhotoInfo();
		dataList.add(mPhotoInfo);
		adapter = new SelectorPhotoListCell(getActivity(), dataList, MaxCount,
				this, this);
		mGridView.setAdapter(adapter);

	}

	public void getPhotos() {
		Cursor cursor = getActivity().getContentResolver().query(
				Media.EXTERNAL_CONTENT_URI,
				new String[] { ImageColumns.DATA,
						ImageColumns.BUCKET_DISPLAY_NAME, ImageColumns.SIZE },
				null, null, null);
		if (cursor == null || !cursor.moveToNext())
			return;
		cursor.moveToLast();
		do {
			if (cursor.getInt(cursor.getColumnIndex(ImageColumns.SIZE)) < 1024 * 10)
				continue;

			String name = cursor.getString(cursor
					.getColumnIndex(ImageColumns.BUCKET_DISPLAY_NAME));
			if (name.equals("Camera")) {
				PhotoInfo mPhotoInfo = new PhotoInfo();
				mPhotoInfo.setTitle(name);
				mPhotoInfo.setPhotourl(cursor.getString(cursor
						.getColumnIndex(ImageColumns.DATA)));
				dataList.add(mPhotoInfo);
			}

		} while (cursor.moveToPrevious());
		adapter.notifyDataSetChanged();
		cursor.close();
	}

	@Override
	public void onCheckedChanged(PhotoInfo photoModel,
			CompoundButton buttonView, boolean isChecked,
			Map<Integer, Boolean> selected) {
		// TODO Auto-generated method stub
		selectedPostion = selected;
		CurrentCount = selected.size();
		this.headView.btnRight.setText("上传(" + CurrentCount + ")");
	}

	@Override
	public void rightOnClick() {
		// TODO Auto-generated method stub
		if (CurrentCount > 0) {

			for (int position : selectedPostion.keySet()) {
				PhotoInfo info = dataList.get(position);
				String name = info.getPhotourl().substring(
						info.getPhotourl().lastIndexOf("/") + 1,
						info.getPhotourl().length());
				selectedPhotos.put(name, info);
				photour.add(info.getPhotourl());
			}
			uploadImage(photour);

		}
	}

	/**
	 * 上传图片
	 * 
	 * @param uri
	 */
	LoadingDialog dialog;

	private void uploadImage(List<String> mList) {
		dialog = new LoadingDialog(getActivity());
		dialog.show();
		List<Bitmap> bitmaplist = new ArrayList<Bitmap>();
		List<String> fileName = new ArrayList<String>();
		MultipartRequestParams params = new MultipartRequestParams();
		// File file=new File(selectedPhoto.get(i).getPhotourl());
		for (String url : mList) {
			String name = url.substring(url.lastIndexOf("/") + 1, url.length());
			Bitmap bitmap = Tools.getScaleImage(url, 200, 240);
			bitmaplist.add(bitmap);
			fileName.add(name);

		}
		params.put("file", bitmaplist, fileName);
		params.put("recallid", recallid + "");
		params.put("userid", UserProperty.getInstance().getUid());
		UploadUtil.upload("/addRecallPhoto.shtml", params, this);

	}

	@Override
	public <T> void sucess(Type arg0, ResponseResult<T> arg1) {
		// TODO Auto-generated method stub
		dialog.dismissMyDialog();
		if (arg1.getModel() != null) {
			final savePicture image = arg1.getModel();
			if (image.getSuccess().size() > 0) {
				for (int i = 0; i < image.getSuccess().size(); i++) {
					String name = image.getSuccess().get(i).getName();
					String id = image.getSuccess().get(i).getId();
					PhotoInfo mInfo = selectedPhotos.get(name);
					mInfo.setId(id);
					sucessPhotos.add(mInfo);
				}
			}
			if (image.getFault().size() > 0) {

				Tools.showDialog(getActivity(), "提示", image.getFault().size()
						+ "张图片上传失败！是否继续上传", "继续", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						photour.clear();
						for (String name : image.getFault()) {
							photour.add(selectedPhotos.get(name).getPhotourl());
						}
						uploadImage(photour);
					}
				}, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Tools.photoListener.getSelectedPhotos(sucessPhotos);
						Tools.pullScreen();
					}
				});
			} else {
				Tools.photoListener.getSelectedPhotos(sucessPhotos);
				Tools.pullScreen();
			}

		}

	}

	@Override
	public void error(ResponseError arg0) {
		// TODO Auto-generated method stub
		super.error(arg0);
	}

	@Override
	public void OnTakeCapture() {
		// TODO Auto-generated method stub
		Tools.choseHeadImageFromCameraCapture(this);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.e("照片路径", Tools.SAVE_REAL_PATH + "/" + Tools.IMAGE_FILE_NAME);
		if (resultCode == getActivity().RESULT_OK) {
			switch (requestCode) {
			case 1:
				PhotoInfo info = new PhotoInfo();
				info.setTitle("预览图片");
				info.setPhotourl(Tools.SAVE_REAL_PATH + "/"
						+ Tools.IMAGE_FILE_NAME);
				adapter.update(info);
			default:
				break;
			}
		}
	}

}
