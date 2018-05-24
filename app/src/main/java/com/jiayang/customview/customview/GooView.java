package com.jiayang.customview.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

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
    private float stableRadius = 25f;
    /**
     * 拖拽圆半径
     */
    private float dragRadius = 25f;


    /**
     * 固定圆 附着点集合
     */
    private PointF[] stablePoints = new PointF[]{};

    /**
     * 拖拽圆 附着点集合
     */
    private PointF[] dragPoints = new PointF[]{};

    /**
     * 确定 拖拽幅度最大为200f
     */
    private float maxDragDistance = 200f;

    private float minStableRadius = 5f;

    private PointF controlPoint;
    private Path mPath;
    private int mStatusBarHeight;
    /**
     * 判断 滑动 是否超出规定范围
     */
    private boolean isOutOfRange;
    /**
     * 抬起的时候 超出规定范围
     */
    private boolean isDisappear;

    private Rect mTextRect;
    private String mText ="";


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
        Log.e("JY", "init --");
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(25);

        mTextRect = new Rect();

        mPath = new Path();
    }

    public void setText(String text) {
        Log.e("JY", "setText --");
        mText = text;
    }

    public void initDownPoints(float x, float y) {
        Log.e("JY", "initDownPoints --");
        dragCenter.set(x, y);
        stableCenter.set(x, y);
        invalidate();
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
        Log.e("JY", "onSizeChanged --");

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("JY", "onDraw --");
        canvas.save();
        canvas.translate(0, -mStatusBarHeight);


        // 计算 固定圆 拖拽圆 圆心间距离，随着拖拽的距离变大，固定圆的半径变小
        float between2Points = GeometryUtil.getDistanceBetween2Points(dragCenter, stableCenter);
        float percent = between2Points / maxDragDistance;
        // 随着拖拽距离的百分比变大 固定圆 半径也百分比减小
        float changeRadius = GeometryUtil.evaluateValue(percent, stableRadius, minStableRadius);
        if (changeRadius < minStableRadius) {
            changeRadius = minStableRadius;
        }

        if (!isDisappear) {

            if (!isOutOfRange) {

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
                stablePoints = GeometryUtil.getIntersectionPoints(stableCenter, changeRadius, lineK);
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

                canvas.drawCircle(stableCenter.x, stableCenter.y, changeRadius, mPaint);
            }
            canvas.drawCircle(dragCenter.x, dragCenter.y, dragRadius, mPaint);
            drawText(canvas);
        }
        canvas.restore();
    }

    private void drawText(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        // 画文字的时候 文字的起始点在左下角。
        mPaint.getTextBounds(mText, 0, mText.length(), mTextRect);
        float x = dragCenter.x - mTextRect.width() * 0.5f;
        float y = dragCenter.y + mTextRect.height() * 0.5f;
        canvas.drawText(mText ,x ,y ,mPaint);
        mPaint.setColor(Color.RED);
    }

    public interface onGooViewChangeListener {
        /**
         * 移除
         */
        void onDisappear();

        /**
         * 重置
         */
        void onReset();
    }

    private onGooViewChangeListener mListener;

    public void setonGooViewChangeListener(onGooViewChangeListener listener) {
        this.mListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isOutOfRange = false;
                isDisappear=false;
                float rawX = event.getRawX();
                float rawY = event.getRawY();
                dragCenter.set(rawX, rawY);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                rawX = event.getRawX();
                rawY = event.getRawY();
                dragCenter.set(rawX, rawY);
                float points = GeometryUtil.getDistanceBetween2Points(dragCenter, stableCenter);
                if (points > maxDragDistance) {
                    isOutOfRange = true;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                points = GeometryUtil.getDistanceBetween2Points(dragCenter, stableCenter);
                // 抬起的时候 判断到 移动过程中超出过 最大距离
                if (isOutOfRange) {
                    // 抬起的时候 离开点超过 最大距离
                    if (points > maxDragDistance) {
                        isDisappear = true;
                        if (mListener != null) {
                            mListener.onDisappear();
                        }
                    } else {
                        dragCenter.set(stableCenter.x, stableCenter.y);
                        mListener.onReset();
                    }

                } else {
                    final PointF pointF = new PointF(dragCenter.x, dragCenter.y);
                    // 从参数一移动到参数二
                    ValueAnimator va = ValueAnimator.ofFloat(points, 0);
                    va.setDuration(1500);
                    // 动画设置 延长动效
                    va.setInterpolator(new OvershootInterpolator(3));
                    va.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mListener.onReset();
                        }
                    });
                    va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            // 获取动画变化的百分比
                            float animatedFraction = animation.getAnimatedFraction();
                            // 根据百分比 进行 拖拽圆圆心的变化
                            dragCenter = GeometryUtil.getPointByPercent(pointF, stableCenter, animatedFraction);
                            invalidate();
                        }
                    });
                    va.start();


                }
                invalidate();
                break;
        }

        return true;
    }
}
