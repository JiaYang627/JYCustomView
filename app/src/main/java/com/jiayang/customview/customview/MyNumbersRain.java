package com.jiayang.customview.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.jiayang.customview.R;

/**
 * @author ：张 奎
 * @date ：2018-05-21 08：55
 * 邮箱   ：JiaYang627@163.com
 * 鸿洋公众号推送 数字雨自定义控件：https://mp.weixin.qq.com/s/EvIqr_oOsrkk3jm_vb71zg
 */
public class MyNumbersRain extends LinearLayout{

    private int mNormalColor = Color.GREEN;
    private int mHighLightColor =Color.YELLOW;
    private float textSize = 15 * getResources().getDisplayMetrics().density;

    public MyNumbersRain(Context context) {
        this(context, null);
    }

    public MyNumbersRain(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs ,0);
    }

    public MyNumbersRain(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            parseAttrs(attrs);
        }
        initNormal();
    }

    private void parseAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NumberRain);
        mNormalColor = typedArray.getColor(R.styleable.NumberRain_normalColor, Color.GREEN);
        mHighLightColor = typedArray.getColor(R.styleable.NumberRain_highLightColor, Color.RED);
        textSize = typedArray.getDimension(R.styleable.NumberRain_textSize, textSize);
        typedArray.recycle();
    }

    private void initNormal() {
        setOrientation(HORIZONTAL);
        setBackgroundColor(Color.BLACK);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        addRainItems();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void addRainItems() {
        int mCount = getMeasuredWidth() / (int)textSize;
        for (int i = 0; i < mCount; i++) {
            MyNumberRainItem numberRainItem = new MyNumberRainItem(getContext());
            numberRainItem.setHightLightColor(mHighLightColor);
            numberRainItem.setTextSize(textSize);
            numberRainItem.setNormalColor(mNormalColor);
            LinearLayout.LayoutParams layoutParams = new LayoutParams((int)textSize + 10, getMeasuredHeight());
            numberRainItem.setStartOffset((long) (Math.random() * 1000));
            addView(numberRainItem, layoutParams);

        }

    }
}
