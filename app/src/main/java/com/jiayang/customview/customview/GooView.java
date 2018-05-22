package com.jiayang.customview.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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


    /**
     * 固定圆 附着点集合
     */
    private PointF[] stablePoints = new PointF[]{
            new PointF(300f, 300f),
            new PointF(300f, 400f)
    };

    /**
     * 拖拽圆 附着点集合
     */
    private PointF[] dragPoints = new PointF[]{
            new PointF(100f, 300f),
            new PointF(100f, 400f)
    };

    private PointF controlPoint = new PointF(200f, 350f);
    private Path mPath;


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

        mPath = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(stableCenter.x ,stableCenter.y ,stableRadius ,mPaint);
        canvas.drawCircle(dragCenter.x, dragCenter.y, dragRadius, mPaint);

        // 绘制两圆中间的部分
        // 1 移动Path 到固定圆 附着点1
        // 2 附着点1 画贝塞尔曲线到 拖拽圆 附着点1
        // 3 拖拽圆 附着点1 直线 绘制到 拖拽圆 附着点2
        // 4 拖拽圆 附着点2 画贝塞尔曲线 到固定圆 附着点2
        // 5 闭合


        mPath.moveTo(stablePoints[0].x ,stablePoints[0].y);

        mPath.quadTo(controlPoint.x,controlPoint.y ,dragPoints[0].x ,dragPoints[0].y);
        mPath.lineTo(dragPoints[1].x ,dragPoints[1].y);
        mPath.quadTo(controlPoint.x,controlPoint.y ,stablePoints[1].x,stablePoints[1].y);

        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }
}
