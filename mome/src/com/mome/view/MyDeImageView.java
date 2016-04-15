package com.mome.view;

import com.mome.main.R;
import com.mome.main.core.utils.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MyDeImageView extends ImageView {
	private Paint paint;
	private Bitmap bitmap;
	private Bitmap icon;
	private int StrokeWidth=1;
	Context context;
	private boolean isShow=false;
	

	public MyDeImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context=context;
		init();
	}

	public MyDeImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
		init();
	}

	private void init() {
		icon=BitmapFactory.decodeResource(context.getResources(), R.drawable.headview_icon_cross);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		paint.setStrokeWidth(StrokeWidth);
		//paint.setStyle(Style.STROKE);
	}
	@Override
	public void setImageBitmap(Bitmap bm) {
		this.bitmap=bm;
		// TODO Auto-generated method stub
	}
	
	public void setDeleIShow(boolean show){
		this.isShow=show;
		invalidate();
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(bitmap,0, icon.getHeight()/2, paint);
		
		if(isShow){
		canvas.drawBitmap(icon, bitmap.getWidth()-icon.getWidth()/2, 0, paint);
		Rect rect=new Rect(bitmap.getWidth()-icon.getWidth()/2, 0, bitmap.getWidth()+icon.getWidth()/2, icon.getHeight());
		checkClickArea(rect);
		}

	}
	
	
    public void checkClickArea(Rect rect){
    	
     	Log.e("rect","rect"+rect.left+"=="+rect.top+"==="+rect.right+"==="+rect.bottom);
    	Log.e("checkClickArea","checkClickArea"+rect.contains(x, y));
    	  if(rect.contains(x, y)){
    		  listener.remove(this,index);
    	  }
    }
    
    private int x;
    private int y;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			x=(int) event.getX();
			y=(int) event.getY();
			invalidate();
			Log.e("xy",x+"==="+y );
			break;

		default:
			break;
		}
		return true;
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		setMeasuredDimension(bitmap.getWidth()+icon.getWidth()/2, bitmap.getHeight()+icon.getHeight()/2);
	}
	
	private OnRemveListener listener;
	public interface OnRemveListener{
		public void remove(View view,int index);
	}
	
	public void setOnRemveListener(OnRemveListener listener){
		this.listener=listener;
	}
	private int index;
	public void setIndex(int index){
		this.index=index;
	}
}
