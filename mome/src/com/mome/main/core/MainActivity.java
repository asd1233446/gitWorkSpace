package com.mome.main.core;

import java.io.IOException;
import java.util.List;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.jessieray.api.request.base.RequestProxy;
import com.mome.main.R;
import com.mome.main.business.TabManager;
import com.mome.main.business.access.Login;
import com.mome.main.core.datacache.DataSaveManager;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;

public class MainActivity extends FragmentActivity {

	/**
	 * 退出应用时间
	 */
	private long lExitTime = 0l;

	public static String cityName = "北京";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//openGPSSettings();
		Tools.getRawResoure(MainActivity.this);
		setContentView(R.layout.main);
		init();
		if (savedInstanceState == null) {
			Tools.pushScreen(Login.class, null);
		}
	}

	/**
	 * 初始化资源
	 */
	private void init() {
		AppConfig.context = this.getApplicationContext();
		AppConfig.SCREEN_WIDTH = this.getWindowManager().getDefaultDisplay()
				.getWidth();
		AppConfig.SCREEN_HEIGHT = this.getWindowManager().getDefaultDisplay()
				.getHeight();
		AppConfig.INFLATER = this.getLayoutInflater();
		AppConfig.mainActivity = this;
		DataSaveManager.getInstance().setContext(this.getApplicationContext());
		String version = DataSaveManager.getInstance().read(
				AppConfig.SAVE_KEY_VERSION);
		if (TextUtils.isEmpty(version)
				|| !AppConfig.CLIENT_VERSION_VALUE.equals(version)) {
			AppConfig.isFirstInstall = true;
			DataSaveManager.getInstance().save(AppConfig.SAVE_KEY_VERSION,
					AppConfig.CLIENT_VERSION_VALUE);
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
			if (TabManager.topRecordLayout != null
					&& TabManager.topRecordLayout.getVisibility() == View.VISIBLE) {
				TabManager.topRecordLayout.setVisibility(View.GONE);
				return true;
			}
			if (AppConfig.fragmentManager.getBackStackEntryCount() > 1) {
				AppConfig.fragmentManager.popBackStack();
			} else {
				if ((System.currentTimeMillis() - lExitTime) > 2000) {
					Tools.toastShow(getResources().getString(R.string.ExitHint));
					lExitTime = System.currentTimeMillis();
				} else {
					// TODO 清理
					finish();
				}
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	/**
	 * 定位位置监听器
	 */
	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			updateToNewLocation(location);
		}

		public void onProviderDisabled(String provider) {
			updateToNewLocation(null);
		}

		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// 通过GPS获取位置
			Location location = locationManager.getLastKnownLocation(provider);

			updateToNewLocation(location);
		}
	};

	// 获取位置管理服务
	LocationManager locationManager;

	/**
	 * 首先判断GPS模块是否存在或者是开启：
	 */
	protected void openGPSSettings() {
		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
		boolean network = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		Log.e("是否定位", gps + "---" + network + "===");
		if (gps || network) {
			getLocation();
			return;
		}

	}

	/**
	 * 如果开启正常，则会直接进入到显示页面，如果开启不正常，则会进行到GPS设置页面
	 */
	protected void getLocation() {

		String serviceName = Context.LOCATION_SERVICE;

		locationManager = (LocationManager) this.getSystemService(serviceName);

		// 查找到服务信息
		Criteria criteria = new Criteria();
		// 高精度
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		// 低功耗
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		// 获取GPS信息
		String provider = locationManager.getBestProvider(criteria, true);
		// 通过GPS获取位置
		Location location = locationManager.getLastKnownLocation(provider);

		updateToNewLocation(location);

		// 设置监听*器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米

		locationManager.requestLocationUpdates(provider, 10 * 1000, 50,
				locationListener);
	}

	// 到这里就可以获取到地理位置信息了，但是还是要显示出来，那么就用下面的方法进行显示：
	private void updateToNewLocation(Location location) {
		if (location != null) {
			Geocoder cegGeocoder = new Geocoder(this);
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			List<Address> addresses;
			try {
				addresses = cegGeocoder.getFromLocation(latitude, longitude, 1);
				if (addresses.size() > 0) {
					cityName = addresses.get(0).getLocality();
				}

				Log.e("定位信息", "精度" + latitude + "纬度" + longitude + cityName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("weewonActivityResultewewe", requestCode + "==");
		// 新浪登陆
		if (Login.sinaLogin != null && Login.sinaLogin.getSsoHandler() != null)
			Login.sinaLogin.getSsoHandler().authorizeCallBack(requestCode,
					resultCode, data);
	}

}
