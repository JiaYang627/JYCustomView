package com.jiayang.customview.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author ：张 奎
 * @date ：2018-05-16 14：07
 * 邮箱   ：JiaYang627@163.com
 */
public class MyCanvasView extends View {
    // PointF 用于封存要绘制点 坐标的类 此处为要绘制圆脸的圆心坐标
    private PointF facePoint = new PointF(340, 400);
    // 圆脸 圆的半径
    private int faceRadius = 200;

    private PointF lineHorizontalStartPoint = new PointF(240, 250);
    private PointF lineHorizontalEndPoint = new PointF(440, 250);

    private PointF lineVerticalStartPoint = new PointF(340, 250);
    private PointF lineVerticalEndPoint = new PointF(340, 500);

    private PointF lineOtherEndPoint = new PointF(250, 450);

    // 矩形
    private RectF mouth = new RectF(160, 300, 520, 550);

    private float startAndle = 380f;
    private float sweepAndle = 140f;

    private PointF leftEyePoint = new PointF(270, 330);
    private PointF rightEyePoint = new PointF(410, 330);
    private int eyeRadius = 50;

    private Paint mPaint;

    public MyCanvasView(Context context) {
        this(context ,null);
    }

    public MyCanvasView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs ,0);
    }

    public MyCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化一系列操作
     */
    private void init() {
        // 初始化画笔
        mPaint = new Paint();
        // 设置画笔的颜色
        mPaint.setColor(Color.RED);
        // 设置 绘制空心图形
        mPaint.setStyle(Paint.Style.STROKE);
        // 开启抗锯齿
        mPaint.setAntiAlias(true);

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        // 参数1：绘制圆心的 X 坐标。
        // 参数2：绘制圆心的 Y 坐标。
        // 参数3：绘制圆的半径。
        // 参数4：画笔
        canvas.drawCircle(facePoint.x, facePoint.y, faceRadius, mPaint);

        // 参数1：线 开始点的 X 坐标
        // 参数2：线 开始点的 Y 坐标
        // 参数3：线 结束点的 X 坐标
        // 参数4：线 结束点的 Y 坐标
        // 参数5：画笔
        canvas.drawLine(lineHorizontalStartPoint.x, lineHorizontalStartPoint.y, lineHorizontalEndPoint.x, lineHorizontalEndPoint.y, mPaint);

        canvas.drawLine(lineVerticalStartPoint.x, lineVerticalStartPoint.y, lineVerticalEndPoint.x, lineVerticalEndPoint.y, mPaint);

        canvas.drawLine(lineVerticalEndPoint.x, lineVerticalEndPoint.y, lineOtherEndPoint.x, lineOtherEndPoint.y, mPaint);

        // 参数1:RectF:矩形,是圆弧所在圆的外接矩形
        // 参数2:弧的起始角度
        // 参数3:弧的弧度
        // 参数4:如果为true,则为扇形,否则为弧形
        // 参数5:画笔
        canvas.drawArc(mouth, startAndle, sweepAndle, false, mPaint);

        canvas.drawCircle(leftEyePoint.x, leftEyePoint.y, eyeRadius, mPaint);
        canvas.drawCircle(rightEyePoint.x, rightEyePoint.y, eyeRadius, mPaint);
    }
}
