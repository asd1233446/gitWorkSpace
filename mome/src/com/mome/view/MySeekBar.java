package com.mome.view;

import com.mome.main.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

public class MySeekBar extends SeekBar{

	private Paint paint;
	private float mTextWidth;
	private String mText="100";
	private Context context;
	private int textSize=50;
	public MySeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context=context;
		init();
	}
	
	private void init(){
		paint=new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(context.getResources().getColor(R.color.black));
		paint.setTextSize(textSize);
		paint.setAntiAlias(true);
		setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(),(int) (getTextHei()+getPaddingBottom()));
	}
	
  @SuppressLint("NewApi")
@Override
protected synchronized void onDraw(Canvas canvas) {
	// TODO Auto-generated method stub
	  super.onDraw(canvas);
	  float progress=(float) ((float)getProgress()/2.0);
	  mText =progress+"";
      mTextWidth = paint.measureText(mText);
      Rect thumb =  this.getThumb().getBounds();
      float xText =thumb.left+thumb.width()/2-mTextWidth/2+getPaddingLeft();
      float yText =thumb.height()+getTextHei()/2;
      canvas.drawText(mText, xText, yText, paint);
	
	
}
  private float getTextHei() {
      FontMetrics fm = paint.getFontMetrics();
      return (float)Math.ceil(fm.descent - fm.top) + 2;
  }

 
	
  
 @Override
public void setPadding(int left, int top, int right, int bottom) {
	// TODO Auto-generated method stub
	super.setPadding(left, top, right, bottom);
}


}
