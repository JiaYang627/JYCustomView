package com.jiayang.customview.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jiayang.customview.utils.GeometryUtil;

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
    private PointF[] stablePoints = new PointF[]{};

    /**
     * 拖拽圆 附着点集合
     */
    private PointF[] dragPoints = new PointF[]{};

    private PointF controlPoint;
    private Path mPath;
    private int mStatusBarHeight;


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

    /**
     * 获取状态栏的高度
     *
     * @param view
     * @return
     */
    private int getStatusBarHeight(View view) {
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStatusBarHeight = getStatusBarHeight(this);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(0, -mStatusBarHeight);

        canvas.drawCircle(stableCenter.x, stableCenter.y, stableRadius, mPaint);
        canvas.drawCircle(dragCenter.x, dragCenter.y, dragRadius, mPaint);

        // 计算 固定圆 拖拽圆的附着点 已经 贝塞尔曲线的控制点

        // 计算两个圆圆心连线的斜率
        float dx = dragCenter.x - stableCenter.x;
        float dy = dragCenter.y - stableCenter.y;
        double lineK = 0;
        if (dx != 0) {
            lineK = dy / dx;
        }

        // 计算拖拽圆的两个附着点
        dragPoints = GeometryUtil.getIntersectionPoints(dragCenter, dragRadius, lineK);
        stablePoints = GeometryUtil.getIntersectionPoints(stableCenter, stableRadius, lineK);
        // 计算贝塞尔曲线的控制点 就是两个圆圆心连线的中点
        controlPoint = GeometryUtil.getMiddlePoint(dragCenter, stableCenter);


        // 绘制两圆中间的部分
        // 1 移动Path 到固定圆 附着点1
        // 2 附着点1 画贝塞尔曲线到 拖拽圆 附着点1
        // 3 拖拽圆 附着点1 直线 绘制到 拖拽圆 附着点2
        // 4 拖拽圆 附着点2 画贝塞尔曲线 到固定圆 附着点2
        // 5 闭合

        mPath.moveTo(stablePoints[0].x, stablePoints[0].y);

        mPath.quadTo(controlPoint.x, controlPoint.y, dragPoints[0].x, dragPoints[0].y);
        mPath.lineTo(dragPoints[1].x, dragPoints[1].y);
        mPath.quadTo(controlPoint.x, controlPoint.y, stablePoints[1].x, stablePoints[1].y);

        mPath.close();


        canvas.drawPath(mPath, mPaint);
        mPath.reset();

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float rawX = event.getRawX();
                float rawY = event.getRawY();
                dragCenter.set(rawX ,rawY);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                rawX = event.getRawX();
                rawY = event.getRawY();
                dragCenter.set(rawX ,rawY);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }
}
