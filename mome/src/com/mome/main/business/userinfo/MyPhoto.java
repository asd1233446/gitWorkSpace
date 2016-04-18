package com.mome.main.business.userinfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.widget.GridView;

import com.jessieray.api.model.PhotoInfo;
import com.jessieray.api.model.UserAlbum;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.UserAlbumRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshGridView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
@LayoutInject(layout=R.layout.myphotos)
public class MyPhoto extends BaseFragment {
	/**
	 * 相册
	 * 
	 * */
@ViewInject(id=R.id.pull_refresh_grid)
private PullToRefreshGridView myPhotosGridView;
private ListAdapter adapter;
private GridView mGridView;
private List<ListCellBase> dataList=new ArrayList<ListCellBase>();
private  String userId;
private int pageNo=1;
private Bundle bundle;

@Override
public void onActivityCreated(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onActivityCreated(savedInstanceState);
	 bundle=getArguments();
	userId=bundle!=null?bundle.getString("userId"):UserProperty.getInstance().getUid();
	setUpGridview();
}


@Override
public void onStart() {
	// TODO Auto-generated method stub
	this.headView.setTitle(bundle==null?"我的相册":"Ta的相册");
	super.onStart();
}

private void setUpGridview(){
	mGridView = myPhotosGridView.getRefreshableView();
	adapter=new ListAdapter();
	adapter.setDataList(dataList);
	mGridView.setAdapter(adapter);
	Tools.setRereshing(myPhotosGridView);
	myPhotosGridView.setOnRefreshListener(new PullToRefreshGridView.OnRefreshListener2<GridView>() {

		
		public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
			getPhotoList();
		}

		
		public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
			getPhotoList();
		}

	});
}

private void getPhotoList(){
	UserAlbumRequest.findUserAlbum(userId, pageNo, AppConfig.PAGE_SIZE, this);
	
}

@Override
public <T> void sucess(Type arg0, ResponseResult<T> arg1) {
	// TODO Auto-generated method stub
	super.sucess(arg0, arg1);
	myPhotosGridView.onRefreshComplete();
	if(arg1.getCode()==AppConfig.REQUEST_CODE_SUCCESS&&arg1.getModel()!=null){
		UserAlbum userAlbum=arg1.getModel();
		for(PhotoInfo info: userAlbum.getPhotos()){
			UserPhotoListCell listCell=new UserPhotoListCell();
			listCell.setmPhotoInfo(info);
			dataList.add(listCell);
		}
		adapter.notifyDataSetChanged();
	}
}

@Override
public void error(ResponseError arg0) {
	// TODO Auto-generated method stub
	super.error(arg0);
}

}
