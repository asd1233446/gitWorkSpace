package com.mome.main.core;


import com.jessieray.api.request.base.RequestProxy;
import com.mome.main.R;
import com.mome.main.business.LaunchScreen;
import com.mome.main.core.datacache.DataSaveManager;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;

public class MainActivity extends FragmentActivity {
	
	/**
	 * 退出应用时间
	 */
	private long lExitTime = 0l;
	/**
	 * 扫描结果标识
	 */
	public final static int SCANNIN_GREQUEST_CODE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
		if(savedInstanceState == null) {
			Tools.pushScreen(LaunchScreen.class, null);
		}
	}

	/**
	 * 初始化资源
	 */
	private void init() {
		AppConfig.context = this.getApplicationContext();
        AppConfig.SCREEN_WIDTH = this.getWindowManager().getDefaultDisplay().getWidth();
        AppConfig.SCREEN_HEIGHT = this.getWindowManager().getDefaultDisplay().getHeight();
        AppConfig.INFLATER = this.getLayoutInflater();
		AppConfig.mainActivity = this;
		DataSaveManager.getInstance().setContext(this.getApplicationContext());
		String version = DataSaveManager.getInstance().read(AppConfig.SAVE_KEY_VERSION);
		if(TextUtils.isEmpty(version) || !AppConfig.CLIENT_VERSION_VALUE.equals(version)) {
			AppConfig.isFirstInstall = true;
			DataSaveManager.getInstance().save(AppConfig.SAVE_KEY_VERSION, AppConfig.CLIENT_VERSION_VALUE);
		} else {
			AppConfig.isFirstInstall = false;
		}
		AppConfig.isFirstBoot = true;
		AppConfig.fragmentManager = this.getSupportFragmentManager();
		RequestProxy.setRequest(HttpRequest.getInstance());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(AppConfig.fragmentManager.getBackStackEntryCount() > 1) {
				AppConfig.fragmentManager.popBackStack();
			} else {
				if((System.currentTimeMillis() - lExitTime) > 2000) {
					Tools.toastShow(getResources().getString(R.string.ExitHint));
					lExitTime = System.currentTimeMillis();
				} else {
					//TODO 清理
					finish();
				}
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//		case SCANNIN_GREQUEST_CODE:
			if(resultCode == RESULT_OK){
				Bundle bundle = data.getExtras();
				Tools.toastShow("扫描成功:"+bundle.getString("result"));
//				mTextView.setText(bundle.getString("result"));
//				mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
			}
//			break;
//		}
    }
}
