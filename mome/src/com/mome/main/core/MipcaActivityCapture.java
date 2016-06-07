package com.mome.main.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.mining.app.zxing.camera.CameraManager;
import com.mining.app.zxing.decoding.CaptureActivityHandler;
import com.mining.app.zxing.decoding.InactivityTimer;
import com.mining.app.zxing.image.RGBLuminanceSource;
import com.mining.app.zxing.view.ViewfinderView;
import com.mome.main.R;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.Tools;

@LayoutInject(layout = R.layout.photograph)
public class MipcaActivityCapture extends Activity implements Callback,
		View.OnClickListener {

	private CaptureActivityHandler handler;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;

	/**
	 * 扫描类型(0电影票扫描 1二维码扫描)
	 */
	@ViewInject(id = R.id.viewfinder_view)
	private ViewfinderView viewfinderView;

	@OnClick(id = R.id.titlebar_left)
	public void closeClick(View view) {
      finish();
	}

	@OnClick(id = R.id.titlebar_right)
	public void photosClick(View view) {
		Intent picture = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(picture, 200);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		InjectProcessor.activityInject(this);
		CameraManager.init(getApplication());
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = "GBK";

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(final Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		Log.e("扫描结果", obj.getText());
		if (obj!=null&&!TextUtils.isEmpty(obj.getText())&& obj.getText().contains("http://")
				|| obj.getText().contains("https://")){
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(obj
					.getText())));
		finish();
		}
		else{
			Toast.makeText(this, "解析失败", Toast.LENGTH_SHORT).show();
		}
		
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
			handleDecode(parseQRcodeBitmap(Tools.getRealPathFromUri(this, data.getData())),null);
			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	//解析二维码图片,返回结果封装在Result对象中
		private com.google.zxing.Result  parseQRcodeBitmap(String bitmapPath){
			//解析转换类型UTF-8
			Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
			hints.put(DecodeHintType.CHARACTER_SET, "GBK");
			//获取到待解析的图片
			BitmapFactory.Options options = new BitmapFactory.Options(); 
			//如果我们把inJustDecodeBounds设为true，那么BitmapFactory.decodeFile(String path, Options opt)
			//并不会真的返回一个Bitmap给你，它仅仅会把它的宽，高取回来给你
			options.inJustDecodeBounds = true;
			//此时的bitmap是null，这段代码之后，options.outWidth 和 options.outHeight就是我们想要的宽和高了
			Bitmap bitmap = BitmapFactory.decodeFile(bitmapPath,options);
			//我们现在想取出来的图片的边长（二维码图片是正方形的）设置为400像素
			/**
				options.outHeight = 400;
				options.outWidth = 400;
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeFile(bitmapPath, options);
			*/
			//以上这种做法，虽然把bitmap限定到了我们要的大小，但是并没有节约内存，如果要节约内存，我们还需要使用inSimpleSize这个属性
			options.inSampleSize = options.outHeight / 400;
			if(options.inSampleSize <= 0){
				options.inSampleSize = 1; //防止其值小于或等于0
			}
			/**
			 * 辅助节约内存设置
			 * 
			 * */
			  options.inPreferredConfig = Bitmap.Config.ARGB_4444;    // 默认是Bitmap.Config.ARGB_8888
			 options.inPurgeable = true; 
			  options.inInputShareable = true; 
			 
			options.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeFile(bitmapPath,options); 
			//新建一个RGBLuminanceSource对象，将bitmap图片传给此对象
			RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(bitmap);
			//将图片转换成二进制图片
			BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
			//初始化解析对象
			QRCodeReader reader = new QRCodeReader();
			//开始解析
			Result result = null;
			try {
				result = reader.decode(binaryBitmap, hints);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			return result;
		}
}