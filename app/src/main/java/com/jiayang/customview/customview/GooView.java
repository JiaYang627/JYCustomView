package com.jiayang.customview.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author ：张 奎
 * @date ：2018-05-22 17：35
 * 邮箱   ：JiaYang627@163.com
 */
public class GooView extends View {

    private Paint mPaint;
    /***
     * 固定圆圆心坐标
     */
    private PointF stableCenter = new PointF(200f, 200f);
    /**
     * 拖拽圆圆心坐标
     */
    private PointF dragCenter = new PointF(100f, 100f);

    /**
     * 固定圆半径
     */
    private float stableRadius = 20f;
    /**
     * 拖拽圆半径
     */
    private float dragRadius = 30f;




    public GooView(Context context) {
        this(context, null);
    }

    public GooView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GooView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(stableCenter.x ,stableCenter.y ,stableRadius ,mPaint);
        canvas.drawCircle(dragCenter.x, dragCenter.y, dragRadius, mPaint);
    }
}
