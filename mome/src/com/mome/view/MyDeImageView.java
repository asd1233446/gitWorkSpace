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
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.Xfermode;
import android.media.ThumbnailUtils;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyDeImageView extends ImageView {
	private Paint paint;
	private Bitmap bitmap;
	private Bitmap icon;
	private int TextSize = 1;
	private int widht, height;
	Context context;
	private boolean isDeleteShow = false;

	public MyDeImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public MyDeImageView(Context context) {
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public MyDeImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();

	}

	private void init() {
		icon = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.headview_icon_cross);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(getResources().getColor(R.color.gray));
		paint.setTextSize(getResources().getDimension(
				R.dimen.common_small_textSize));
		// paint.setStyle(Style.STROKE);

	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		this.bitmap = bm;
		// TODO Auto-generated method stub
		super.setImageBitmap(bm);
	}

	public void setIsDeleteShow(boolean isShow) {
		this.isDeleteShow = isShow;
		invalidate();

	}

	Rect rect;

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		bitmap = ThumbnailUtils.extractThumbnail(bitmap,
				widht - icon.getWidth() / 2, (int) (height - icon.getHeight()
						/ 2 - getTextHei()));
		canvas.drawBitmap(bitmap, 0, icon.getHeight() / 2, paint);
		if (!TextUtils.isEmpty(text))
			canvas.drawText(text,
					bitmap.getWidth() / 2 - paint.measureText(text) / 2, height
							- getTextHei() / 4, paint);
		if (isDeleteShow) {
			canvas.drawBitmap(icon, bitmap.getWidth() - icon.getWidth() / 2, 0,
					paint);
			rect = new Rect(bitmap.getWidth() - icon.getWidth() / 2, 0,
					bitmap.getWidth() + icon.getWidth() / 2, icon.getHeight());
		}
	}

	private float getTextHei() {
		FontMetrics fm = paint.getFontMetrics();
		if (TextUtils.isEmpty(text)) {
			return 0;
		}
		return (float) Math.ceil(fm.descent - fm.top) + 2;
	}

	private String text;

	public void setText(String text) {
		this.text = text;
	}

	public void checkClickArea(Rect rect) {
		if (rect.contains(x, y)) {
			listener.remove(this, index);
		} else {
			performClick();
		}
	}

	private int x;
	private int y;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			x = (int) event.getX();
			y = (int) event.getY();
			if (isDeleteShow) {
				invalidate();
				checkClickArea(rect);
			} else {
				performClick();
			}
			break;

		default:
			break;
		}
		return true;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub

		this.height = measureHeight(heightMeasureSpec);
		this.widht = measureWidth(widthMeasureSpec);

		setMeasuredDimension(widht, height);

	}

	private int measureHeight(int measureSpec) {

		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		// Default size if no limits are specified.

		int result = 80;
		if (specMode == MeasureSpec.AT_MOST) {
			Log.e("MyDeImageView", "高MeasureSpec.AT_MOST" + getHeight());
			// Calculate the ideal size of your
			// control within this maximum size.
			// If your control fills the available
			// space return the outer bound.
			result = bitmap.getHeight() + icon.getHeight() / 2;
		} else if (specMode == MeasureSpec.EXACTLY) {

			// If your control can fit within these bounds return that value.
			Log.e("MyDeImageView", "高MeasureSpec.EXACTLY" + specSize);

			result = specSize;
		}

		return result;
	}

	private int measureWidth(int measureSpec) {
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		// Default size if no limits are specified.
		int result = 80;
		if (specMode == MeasureSpec.AT_MOST) {
			// Calculate the ideal size of your control
			// within this maximum size.
			// If your control fills the available space
			// return the outer bound.
			Log.e("MyDeImageView", "宽MeasureSpec.AT_MOST" + getWidth());

			result = bitmap.getWidth() + icon.getWidth() / 2;
			;
		}

		else if (specMode == MeasureSpec.EXACTLY) {
			// If your control can fit within these bounds return that value.
			Log.e("MyDeImageView", "宽MeasureSpec.EXACTLY" + specSize);

			result = specSize;
		} else if (specMode == MeasureSpec.UNSPECIFIED) {
			Log.e("MyDeImageView", "宽MeasureSpec.UNSPECIFIED" + specSize);
			result = bitmap.getWidth() + icon.getWidth() / 2;
		}

		return result;
	}

	private OnRemveListener listener;

	public interface OnRemveListener {
		public void remove(View view, int index);
	}

	public void setOnRemveListener(OnRemveListener listener) {
		this.listener = listener;
	}

	private int index;

	public void setIndex(int index) {
		this.index = index;
	}

//	private String imageUrl;
//
//	public String getImageUrl() {
//		return imageUrl;
//	}
//
//	public void setImageUrl(String imageUrl) {
//		this.imageUrl = imageUrl;
//	}
//
//	private String imageId;
//
//	
//
//	public String getImageId() {
//		return imageId;
//	}
//
//	public void setImageId(String imageId) {
//		this.imageId = imageId;
//	}

	@Override
	public void setImageResource(int resId) {
		// TODO Auto-generated method stub
		Drawable drawable = this.getResources().getDrawable(resId);
		BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		bitmap = bitmapDrawable.getBitmap();
	}

}
