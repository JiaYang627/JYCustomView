package com.jiayang.customview.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.jiayang.customview.R;

/**
 * @author ：张 奎
 * @date ：2018-05-21 16：03
 * 邮箱   ：JiaYang627@163.com
 * 鸿洋公众号推送 数字雨自定义控件：https://mp.weixin.qq.com/s/EvIqr_oOsrkk3jm_vb71zg
 */
public class MyNumberRainItem extends View {
    public int hightLightColor;
    public float textSize = 15 * getResources().getDisplayMetrics().density;
    public int normalColor;
    public long startOffset = 0L;
    private float nowHeight = 0f;
    private Paint mPaint;
    private double hightLightNumIndex;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setHightLightColor(int hightLightColor) {
        this.hightLightColor = hightLightColor;
        if (isAttachedToWindow()) {
            postInvalidate();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setTextSize(float textSize) {
        this.textSize = textSize;
        if (isAttachedToWindow()) {
            postInvalidate();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
        if (isAttachedToWindow()) {
            postInvalidate();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setStartOffset(long startOffset) {
        this.startOffset = startOffset;
        if (isAttachedToWindow()) {
            postInvalidate();
        }
    }

    public MyNumberRainItem(Context context) {
        this(context, null);
    }

    public MyNumberRainItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyNumberRainItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            parseAttrs(attrs);
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    private void parseAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NumberRainItem);
        normalColor = typedArray.getColor(R.styleable.NumberRainItem_normalColor, Color.GREEN);
        hightLightColor = typedArray.getColor(R.styleable.NumberRainItem_highLightColor, Color.RED);
        startOffset = (long) typedArray.getInt(R.styleable.NumberRainItem_startOffset, 0);
        textSize = typedArray.getDimension(R.styleable.NumberRainItem_textSize, textSize);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        configPaint();
        if (isShowAllNumber()) {
            drawTotalNumbers(canvas);
        } else {
            drawPartNumbers(canvas);
        }
    }

    private void configPaint() {
        mPaint.setTextSize(textSize);
        mPaint.setColor(normalColor);
    }

    private boolean isShowAllNumber() {
        return nowHeight >= getHeight();
    }

    private void drawTotalNumbers(Canvas canvas) {
        int count = (int) (getHeight() / textSize);
        drawNumbers(canvas, count);
    }

    private void drawPartNumbers(Canvas canvas) {
        int count = (int) (nowHeight / textSize);
        nowHeight += textSize;
        drawNumbers(canvas, count);
    }

    private void drawNumbers(Canvas canvas, int count) {
        if (count == 0) {
            postInvalidateDelayed(startOffset);
        } else {
            float offset = 0f;
            for (int i = 0; i < count; i++) {

                String text = String.valueOf((int) (Math.random() * 9));

                if (hightLightNumIndex == i) {
                    mPaint.setColor(hightLightColor);

                    mPaint.setShadowLayer(10f, 0f, 0f, hightLightColor);

                } else {
                    mPaint.setColor(normalColor);
                    mPaint.setShadowLayer(10f, 0f, 0f, normalColor);
                }

                canvas.drawText(text, 0f, textSize + offset, mPaint);
                offset += textSize;

            }

            if (!isShowAllNumber()) {
                hightLightNumIndex++;
            } else {
                hightLightNumIndex = (++hightLightNumIndex) % count;
            }
            postInvalidateDelayed(100L);
        }
    }

}
