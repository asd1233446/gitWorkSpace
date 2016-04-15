package com.mome.main.core.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mome.main.R;
import com.mome.main.business.widget.pulltorefresh.PullToRefreshBase;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.netframe.volley.toolbox.ImageLoader;
import com.mome.main.netframe.volley.toolbox.ImageLoader.ImageListener;
import com.mome.view.MyDatePicker;

public class Tools {
	
	public static int dip2px(Context context, float dpValue) {  
		  final float scale = context.getResources().getDisplayMetrics().density;  
		  return (int) (dpValue * scale + 0.5f);  
		} 
	/**
	 * 调用日期控件
	 * */
	

	public static  DatePickerDialog getDataPicker(Context context,final TextView textView) {
		 MyDatePicker datePickerDialog=null;
		if (datePickerDialog == null) {
			datePickerDialog = new MyDatePicker(context,
					AlertDialog.THEME_HOLO_LIGHT, new OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							// TODO Auto-generated method stub
							textView.setText(year + "-" + monthOfYear
									+ "-" + dayOfMonth);
						}
					}, Calendar.getInstance().get(Calendar.YEAR), Calendar
							.getInstance().get(Calendar.MARCH), Calendar
							.getInstance().get(Calendar.DAY_OF_MONTH));
		}

		return datePickerDialog;
	}

	/**
	 * 调用时间控件
	 * */
	

	public static  TimePickerDialog getTimePicker(Context context,final TextView textView) {
		TimePickerDialog timePickerDialog=null;
		if (timePickerDialog == null) {
			timePickerDialog = new TimePickerDialog(context,AlertDialog.THEME_HOLO_LIGHT, new OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					// TODO Auto-generated method stub
					textView.setText(hourOfDay+":"+minute);
				}
			}, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
		}
		return timePickerDialog;
	}

	
	
	/***
	 * 刷新
	 * 
	 * */	
	
	public static void setRereshing( final PullToRefreshBase<?> view){
		
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				view.setRefreshing();
			}
			}, 500);
		
	}
	
	

	/***
	 * 计算页数
	 * 
	 * */	
		public static  double  calculateTotalPage(int totalNum){
			
			Double a =(double) totalNum;
			Double b =(double) AppConfig.PAGE_SIZE;
			Tools.toastShow("一共几页"+a/b);
			return Math.ceil(a/b);
		}
	
	/**
	 * 观影方式
	 * 
	 * */
	
	public static final String getMovieLookedType(int type)
	{
		
		switch (type) {
		case 1:
         return "影院";
		case 2:
	         return "网络";
		case 3:
	         return "电视";
		default:
			break;
		}
		return "电视";
		
	}
	
	  /**
     * 检查是否存在SDCard
     * @return
     */
    public static boolean hsasSdcard(){
            String state = Environment.getExternalStorageState();
            if(state.equals(Environment.MEDIA_MOUNTED)){
                    return true;
            }else{
                    return false;
            }
    }
    
    
    /**
     * 保存照片到sd
     * @param bm 图片bitmap
     * @param fileName 文件名
     * @param path 地址
     */
    public  static final String SAVE_PIC_PATH=Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() : "/mnt/sdcard";//保存到SD卡
    public static final String SAVE_REAL_PATH = SAVE_PIC_PATH+"/mome";//保存的确切位置
    public static void saveFile(Bitmap bm, String fileName, String path,String suffixes) throws IOException {
    	String subForder = SAVE_REAL_PATH + path;
    	File foder = new File(subForder);
    	if (!foder.exists()) {
    	foder.mkdirs();
    	}
    	File myCaptureFile = new File(subForder, fileName+suffixes);
    	if (!myCaptureFile.exists()) {
    	myCaptureFile.createNewFile();
    	}
    	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
    	bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
    	bos.flush();
    	bos.close();
    	Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(foder);
		intent.setData(uri);
	    AppConfig.context.sendBroadcast(intent);

    	}
	
    
    
    public static Bitmap drawable2Bitmap(Drawable drawable) {  
        if (drawable instanceof BitmapDrawable) {  
            return ((BitmapDrawable) drawable).getBitmap();  
        } else if (drawable instanceof NinePatchDrawable) {  
            Bitmap bitmap = Bitmap  
                    .createBitmap(  
                            drawable.getIntrinsicWidth(),  
                            drawable.getIntrinsicHeight(),  
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888  
                                    : Bitmap.Config.RGB_565);  
            Canvas canvas = new Canvas(bitmap);  
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),  
                    drawable.getIntrinsicHeight());  
            drawable.draw(canvas);  
            return bitmap;  
        } else {  
            return null;  
        }  
    }  
	/**
	 * toast提示信息
	 * @param message
	 */
	public static final void toastShow(String message) {
		Toast.makeText(AppConfig.context, message, 1000).show();
	}
	
	/**
	 * 日志信息打印
	 * @param message
	 */
	public static final void Log(String message) {
		Log.i("MOME",message);
	}
	
	/**
	 * 跳转到一个屏幕
	 * @param baseFragment
	 * @param args
	 */
	public static final void pushScreen(Class<? extends BaseFragment> baseFragment,Bundle args) {
		try {
			BaseFragment curScreen = baseFragment.newInstance();
			curScreen.setArguments(args);
			AppConfig.currentScreen = curScreen;
			FragmentTransaction transaction = AppConfig.fragmentManager.beginTransaction();
			transaction.add(R.id.viewContainer, curScreen);
			transaction.addToBackStack(baseFragment.getName());
			//Tools.toastShow(baseFragment.getName());
			transaction.commit();
			if(AppConfig.fragmentManager.getBackStackEntryCount() == 0) {
				AppConfig.CurrentRootScreenName = baseFragment.getName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * 切换根屏幕
	 * @param baseFragment
	 * @param args
	 */
	public static final void replaceRootPushScreen(Class<? extends BaseFragment> baseFragment,Bundle args) {
		try {
			BaseFragment curScreen = baseFragment.newInstance();
			curScreen.setArguments(args);
			AppConfig.currentScreen = curScreen;
			AppConfig.fragmentManager.popBackStack(AppConfig.CurrentRootScreenName, FragmentManager.POP_BACK_STACK_INCLUSIVE);//弹出指定屏幕之上的包括自己的所有屏幕
			FragmentTransaction transaction = AppConfig.fragmentManager.beginTransaction();
			transaction.replace(R.id.viewContainer, curScreen);
			transaction.addToBackStack(baseFragment.getName());
			transaction.commit();
			AppConfig.CurrentRootScreenName = baseFragment.getName();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 在指定屏幕之上显示当前屏幕
	 * @param baseFragment 要跳转的屏幕
	 * @param args 参数
	 * @param assign 指定的屏幕
	 */
	public static final void pushOntoAssignScreen(Class<? extends BaseFragment> baseFragment,Bundle args,Class<? extends BaseFragment> assign) {
		try {
			BaseFragment curScreen = baseFragment.newInstance();
			curScreen.setArguments(args);
			AppConfig.currentScreen = curScreen;
			AppConfig.fragmentManager.popBackStack(assign.getName(), 0);//0表示弹出的屏幕不包括当前屏幕
			FragmentTransaction transaction = AppConfig.fragmentManager.beginTransaction();
			transaction.add(R.id.viewContainer, curScreen);
			transaction.addToBackStack(baseFragment.getName());
			transaction.commit();
			final String name = baseFragment.getName();
			AppConfig.fragmentManager.addOnBackStackChangedListener(new OnBackStackChangedListener() {
				
				@Override
				public void onBackStackChanged() {
					if(AppConfig.fragmentManager.getBackStackEntryCount() <= 1) {
						AppConfig.CurrentRootScreenName = name;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 替换当前屏幕
	 * @param baseFragment
	 * @param args
	 */
	public static final void replaceCurScreen(Class<? extends BaseFragment> baseFragment,Bundle args) {
		try {
			BaseFragment curScreen = baseFragment.newInstance();
			curScreen.setArguments(args);
			FragmentTransaction transaction = AppConfig.fragmentManager.beginTransaction();
			AppConfig.fragmentManager.popBackStack();
			transaction.add(R.id.viewContainer, curScreen);
			transaction.addToBackStack(baseFragment.getName());
			transaction. commitAllowingStateLoss();
			final String name = baseFragment.getName();
			AppConfig.fragmentManager.addOnBackStackChangedListener(new OnBackStackChangedListener() {
				
				@Override
				public void onBackStackChanged() {
					if(AppConfig.fragmentManager.getBackStackEntryCount() <= 1) {
						AppConfig.CurrentRootScreenName = name;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 用于tab切换时候的屏幕跳转
	 * @param screen
	 * @param args
	 */
	public static final void replaceScreen(BaseFragment screen,Bundle args) {
		try {
			FragmentTransaction transaction = AppConfig.fragmentManager.beginTransaction();
			transaction.replace(R.id.viewContainer, screen);
			AppConfig.CurrentRootScreenName = screen.getClass().getName();
			transaction.commit();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 弹出一个屏幕
	 */
	public static final void pullScreen() {
		AppConfig.fragmentManager.popBackStack();
	}
	
	/**
	 * 获取时间戳变体
	 * @param nowTimestamp
	 * @return
	 * @throws ParseException
	 */
	public static String getMogoTimestamp(long nowTimestamp)throws ParseException{
//		Date mogoDate = new Date();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//		mogoDate = formatter.parse("2013-09-22 18:20:06");
		long time = 1379845206000l;
		return String.valueOf(nowTimestamp - time);	
	}
	
	private static Comparator<String> signComparator = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			return o1.toLowerCase().compareTo(o2.toLowerCase());
		}
	};
	
	/**
	 * 
	 * @param paramMap 网络接口参数容器(1、该容器里必须包含timestamp参数，否则本次签名失败; 2、该容器不可以 包含sign和secret参数)
	 * @param secret md5(用户密码)；或者是 md5（时间戳变体）
	 * @return signValue
	 */
	public static String getSign( Map<String, String> tempMap ,String secret ) throws UnsupportedEncodingException{
		if( secret == null || tempMap == null){
			return "";
		}
		if(tempMap.size() <=0){
			return "";
		}
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.putAll(tempMap);
		String timeStampValue=paramMap.get("timestamp");
		if( timeStampValue == null || timeStampValue != null && timeStampValue.length() <=1){
			return "";
		}
		StringBuilder paramStringBuffer = new StringBuilder();
		List<String> keyList = new ArrayList<String>(); 
//		keyList.add(SECRET_STRING);
		paramMap.put("secret", getSecret(secret));
		Set keysSet = paramMap.keySet();
		Iterator iterator = keysSet.iterator();
		while(iterator.hasNext()) {
			keyList.add(((String)iterator.next()));
		}	
		
		//升序操作
		Collections.sort(keyList,signComparator);
		int timeMogoValue = getTimestampMogoIndex(timeStampValue);
		for(int i = 0; i < keyList.size(); i++){
			paramStringBuffer.append(keyList.get(i));
			if(timeMogoValue == i || (timeMogoValue >= keyList.size()-1 && i == keyList.size()-1)){
				paramStringBuffer.append("?");
			}
			paramStringBuffer.append("*");
			paramStringBuffer.append(paramMap.get(keyList.get(i)));
			if( i != keyList.size()-1 ){
				paramStringBuffer.append("?");
			}
		}
		byte [] temp = {0};
		temp = paramStringBuffer.toString().getBytes("UTF-8");
		return toMd5(temp).toUpperCase();
	}
	
	/**
	 * md5加密param，生成secret
	 * @param param md5后的用户密码或者变体时间戳
	 * @return
	 */
	public static String getSecret(String param ) throws UnsupportedEncodingException{
	    String secret = "";
		if (param != null && !param.equalsIgnoreCase("")) {
			byte [] secretBytes = param.getBytes("UTF-8");
			if(secretBytes != null && secretBytes.length >0 ){
				secret = toMd5(secretBytes);
			}
		}
	return secret;
	}
	
	public static int getTimestampMogoIndex(String timestamp ){
		if(timestamp != null && timestamp.length() > 0){
			char index = timestamp.charAt(timestamp.length()-1);
			return Integer.parseInt(String.valueOf(index));
		}else{
			return -1;
		}
	}
	
	public static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f' };

	public static String toHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);  
        for (int i = 0; i < bytes.length; i++) {  
            sb.append(HEX_DIGITS[(bytes[i] & 0xf0) >>> 4]);  
            sb.append(HEX_DIGITS[bytes[i] & 0x0f]);  
		}
        return sb.toString();
	}
	
	/**
	 * MD5 加密
	 * @param bytes
	 * @return
	 */
	public static String toMd5(byte[] bytes) {
		String result_md5 = "";
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(bytes);
			result_md5 = toHexString(algorithm.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_md5;
	}
	
	/**
	 * 获取一组动画序列
	 * @param ani
	 * @return
	 */
	public static Animation getGroupAni(Animation...ani) {
		if(ani == null || ani.length == 0) {
			return null;
		}
		AnimationSet aniSet = new AnimationSet(false);
		if(ani != null && ani.length > 0) {
			for(Animation animation : ani) {
				aniSet.addAnimation(animation);
			}
		}
		return aniSet;
	}
	
	/**
	 * 获取平移动画
	 * @param durationMillis
	 * @param startOffset
	 * @param fromXDelta
	 * @param toXDelta
	 * @param fromYDelta
	 * @param toYDelta
	 * @param inter
	 * @return
	 */
	public static Animation getTranslateAni(long durationMillis,long startOffset,float fromXDelta, float toXDelta, float fromYDelta, float toYDelta,Interpolator inter) {
		TranslateAnimation ani = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
		ani.setDuration(durationMillis);
		ani.setFillAfter(true);
		ani.setInterpolator(inter);
		ani.setStartOffset(startOffset);
		return ani;
	}
	
	/**
	 * 带物理效果的缩放
	 * @param duration
	 * @param interpolator
	 * @param from
	 * @param to
	 * @return
	 */
	public static Animation getScaleAnimation(long duration,long startOffset,Interpolator interpolator,float from,float to) {
		Animation scaleAni = new ScaleAnimation (from, to, from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAni.setDuration(duration);
		scaleAni.setInterpolator(interpolator);
		scaleAni.setStartOffset(startOffset);
		scaleAni.setFillAfter(true);
		return scaleAni;
	}
	
	/**
	 * 获取透明度动画
	 * @param durationMillis
	 * @param startOffset
	 * @param fromAlpha
	 * @param toAlpha
	 * @return
	 */
	public static Animation getAlphaAni(long durationMillis,long startOffset,float fromAlpha, float toAlpha) {
		AlphaAnimation alphaAni = new AlphaAnimation(fromAlpha, toAlpha);
		alphaAni.setDuration(durationMillis);
		alphaAni.setFillAfter(true);
		alphaAni.setStartOffset(startOffset);
		return alphaAni;
	}
	
	/**
	 * 截取字符串
	 * @param str 原串
	 * @param open 开始位置
	 * @param close 结束位置
	 * @return
	 */
	public static String substringBetween(String str, String open, String close) {
		return StringUtils.defaultIfEmpty(
				StringUtils.substringBetween(str, open, close),
				StringUtils.substringAfter(str, open)
				);
	}
	
    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return  dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp){
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }
    
	/**
	 * 加载网络图片
	 * 
	 * @param view
	 *            视图控件
	 * @param url
	 *            图片地址
	 * @param defaultImg
	 *            默认图片
	 * @param errorImg
	 *            下载失败图片
	 */
	public static void loadNetImage(View view, String url, int defaultImg, int errorImg) {
	  	if(url!=null&&!url.contains("http"))
    		url=AppConfig.Imageurl+"/"+url;
		ImageListener listener = ImageLoader.getImageListener(view, defaultImg, errorImg);
		HttpRequest.getInstance().imageLoader.get(url, listener);
	}
	
	/**
	 * 数据为空时的提示
	 * **/
	public static View setEmptyView(Context context){
		 View view=((Activity)context).getLayoutInflater().inflate(R.layout.empty_view, null);
		return view;
	}
	
	
	
	public static void showDialog(Activity activity,String title,String message,String negativeTitile,final OnClickListener negativeButton ){
		AlertDialog.Builder bulider=new AlertDialog.Builder(activity);
		bulider.setTitle(title);
		bulider.setMessage(message);
		bulider.setPositiveButton(negativeTitile, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				negativeButton.onClick(dialog, which);
			}
		});
		bulider.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		
		bulider.create();
		bulider.show();
	}
	
	
	
	
	
	/**
	 * fragment 之间的回调传值
	 * */
	public interface callBack{
		void Back(Object params);
	}
	
	private static callBack callListener;

	public static callBack getCallListener() {
		return callListener;
	}



	public static  void setCallListener(callBack call) {
	    callListener = call;
	}
	

	/**
	 * 调用系统的裁剪
	 * 
	 * @param uri
	 */
	public static void cropPhoto(Fragment context,Uri uri,int width,int height) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", width);
		intent.putExtra("outputY", height);
		intent.putExtra("return-data", true);
		context.startActivityForResult(intent, 3);

	}
	
	/* 头像文件 */
	public static final String IMAGE_FILE_NAME = "head";
	

	// 启动手机相机拍摄照片作为头像
	public static  void choseHeadImageFromCameraCapture(Fragment context) {
		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// 判断存储卡是否可用，存储照片文件

		intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(Tools.SAVE_REAL_PATH, IMAGE_FILE_NAME)));
		context.startActivityForResult(intentFromCapture, 1);

	}

	// 从本地相册选取图片作为头像
	public  static void choseHeadImageFromGallery(Fragment context) {
//		Intent intentFromGallery = new Intent();
//		// 设置文件类型
//		intentFromGallery.setType("image/*");
//		intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
//		context.startActivityForResult(intentFromGallery, 2);
		Intent picture = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		context.startActivityForResult(picture, 2);
	}
	/**
	 * 
	 * 头像弹出框
	 */
	private static String[] itemts = new String[] { "拍照", "相册" };
	public static  void AlertShow(final Fragment context,String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity());
		builder.setTitle(title);
		builder.setItems(itemts, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				if (which == 0) {
					Tools.choseHeadImageFromCameraCapture(context);
				} else {
					Tools.choseHeadImageFromGallery(context);
				}
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create();
		builder.show();

	}
	
	
	public static   Bitmap toRoundBitmap (Bitmap bitmap){
		int width = bitmap.getWidth(); 
		int height = bitmap.getHeight(); 
		float roundPx; 
		float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom; 
		if (width <= height) { 
		roundPx = width / 2; 
		top = 0; 
		bottom = width; 
		left = 0; 
		right = width; 
		height = width; 
		dst_left = 0; 
		dst_top = 0; 
		dst_right = width; 
		dst_bottom = width; 
		} else { 
		roundPx = height / 2; 
		float clip = (width - height) / 2; 
		left = clip; 
		right = width - clip; 
		top = 0; 
		bottom = height; 
		width = height; 
		dst_left = 0; 
		dst_top = 0; 
		dst_right = height; 
		dst_bottom = height; 
		} 
		Bitmap output = Bitmap.createBitmap(width, 
		height, Config.ARGB_8888); 
		Canvas canvas = new Canvas(output); 
		final int color = 0xff424242; 
		final Paint paint = new Paint(); 
		final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom); 
		final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom); 
		final RectF rectF = new RectF(dst); 
		paint.setAntiAlias(true); 
		canvas.drawARGB(0, 0, 0, 0); 
		paint.setColor(color); 
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint); 
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN)); 
		canvas.drawBitmap(bitmap, src, dst, paint); 
		return output; 
	}
	
	/**
	 * 
	 * Uri 转file
	 * 
	 * @throws IOException
	 * */
	public static  File BitmapToFile(Bitmap bitmap) {
		bitmap=Tools.toRoundBitmap(bitmap);
		File file = null;
			file = new File(Tools.SAVE_REAL_PATH+ "/"
					+ Tools.IMAGE_FILE_NAME + ".png");
			try {
				file.createNewFile();
				FileOutputStream fOut = null;
				fOut = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				file = null;
			}

		

		return file;
	}

}
