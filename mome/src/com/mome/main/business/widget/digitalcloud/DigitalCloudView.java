package com.mome.main.business.widget.digitalcloud;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class DigitalCloudView extends View{

	/**
	 * 要展示的排好序的文字数组
	 */
	private List<String> data = new ArrayList<String>();
	/**
	 * 绘制文字随机位置
	 */
	private List<Integer> drawData = new ArrayList<Integer>(data.size()); ;
	/**
	 * 画笔
	 */
	private Paint paint;
	/**
	 * 文字最大尺寸
	 */
	private final int MAX_TEXT_SIZE = 100;
	/**
	 * 文字绘制位置
	 */
	private int offsetX;
	private int leftY = 100;
	private int rightY = 100;
	/**
	 * 是否是从左边开始绘制
	 */
	private boolean isLeft = true;
	private Rect mBounds;
	
	
	public DigitalCloudView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBounds = new Rect();
	}

	public void setData(ArrayList<String> data) {
		this.data = data;
		for(int i = 0; i<data.size(); i++) {
			drawData.add(null);
		}
		if(data != null) {
			for(int i = 0; i<data.size(); i++) {
				drawData.set(getRandomIndex(data.size()),i);
			}
		}
	}

    
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(Color.WHITE);
	    canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
		for(int i = 0; i<drawData.size(); i++) {
			int level = drawData.get(i);
			drawRandomText(data.get(level),level,canvas);
		}
	}


	/**
	 * 绘制文字
	 * @param text 绘制文字
	 * @param level 当前文字级别
	 * @param canvas 画布
	 */
	private void drawRandomText(String text,int level,Canvas canvas) {
        paint.setColor(getCharColor(level));
        paint.setTextSize(MAX_TEXT_SIZE-(level*10));
        paint.getTextBounds(text, 0, text.length(), mBounds);
        int textWidth = mBounds.width();
        int textHeight = mBounds.height()+10;
		int positionX = 0;
		int positionY = 0;
		if(level < 3) {
			offsetX = 0;
			positionX = random(offsetX,getWidth()-textWidth);
			positionY = Math.max(leftY, rightY);
			canvas.drawText(text, positionX, positionY, paint);
			leftY = positionY+textHeight;
			rightY = leftY;
			isLeft = true;
		} else {
			if(isLeft) {
				int tempX = offsetX-textWidth;
				if(tempX < 0) {//文字画不下,取最大高度换行绘制
					positionX = (random(0,getWidth()-textWidth));
					positionY = Math.max(leftY, rightY);
					offsetX = positionX+textWidth;
				} else {//文字画得下,取随机x和左边的y绘制
					positionX = (random(0,(tempX<=0?0:tempX)));
					positionY = leftY;
					offsetX = Math.max(offsetX, positionX+textWidth);
				}

				canvas.drawText(text, positionX, positionY, paint);
				leftY = positionY+textHeight;
				isLeft = false;
			} else {
				int tempX = offsetX + textWidth;
				if(tempX > getWidth()) {
					positionX = (random(0,getWidth()-textWidth));
					positionY = Math.max(leftY, rightY);
					isLeft = false;
					offsetX = positionX+textWidth;
				} else {
					positionX = offsetX;
					positionY = rightY;
					isLeft = true;
				}
			
				canvas.drawText(text, positionX, positionY, paint);
				rightY = positionY+textHeight;
			}
		}
	}
	
	/**
	 * 获取颜色
	 * @param level
	 * @return
	 */
	private int getCharColor(int level) {
		int color = 0;
		switch(level) {
		case 0:
			color = Color.BLACK;
			break;
		case 1:
			color = Color.BLACK;
			break;
		case 2:
			color = Color.BLUE;
			break;
		case 3:
			color = Color.CYAN;
			break;
		case 4:
			color = Color.GRAY;
			break;
		case 5:
			color = Color.RED;
			break;
		case 6:
			color = Color.YELLOW;
			break;
		}
		return color;
	}
	
	/**
     * 求两个正整数之间的随机数
     * @param a int
     * @param b int
     * @return int
     */
    public static final int random(int a, int b)
    {
        Random rnd = new Random();
        if (a <= b)
            return Math.abs(rnd.nextInt() % (b - a + 1)) + a;
        else
            return Math.abs(rnd.nextInt() % (a - b + 1)) + b;
    }
    
	/**
	 * 随机获取位置
	 * @param num
	 * @return
	 */
	private int getRandomIndex(int num) {
		Random random = new Random();
		int randomIndex = random.nextInt(num);
		while(drawData.get(randomIndex) != null) {
			randomIndex = random.nextInt(num);
		}
		return randomIndex;
	}
}
