package com.jiayang.customview.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jiayang.customview.bean.PieEntity;

import java.util.List;

/**
 * @author ：张 奎
 * @date ：2018-05-17 10：45
 * 邮箱   ：JiaYang627@163.com
 */
public class MyPieChartView extends View {
    private List<PieEntity> mDatas;
    private int width;
    private int height;
    private RectF mPieRectF;
    private Paint mPiePaint;
    // 计算 数据源中总数 数量
    private float totalValue;
    private Path mPiePath;
    private int mRadius;
    private Paint mLinePaint;

    public MyPieChartView(Context context) {
        this(context ,null);
    }

    public MyPieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs ,0);
    }

    public MyPieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.e("JY", "init");
        mPieRectF = new RectF();

        mPiePath = new Path();

        mPiePaint = new Paint();
        mPiePaint.setAntiAlias(true);

        mLinePaint = new Paint();
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setAntiAlias(true);
    }

    // 当自定义控件的尺寸已经定好的时候  回调此方法
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("JY", "onSizeChanged");
        this.width = w;
        this.height = h;
        // 为了保证 圆饼图 不会超出屏幕 并保证 外边 数据部分不会遮挡，此处 将 半径定义为 屏幕 的 70%
        int min = Math.min(w, h);
        mRadius = (int) (min * 0.7f / 2);


        mPieRectF.left = -mRadius;
        mPieRectF.top = -mRadius;
        mPieRectF.right = mRadius;
        mPieRectF.bottom = mRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("JY", "onDraw");
        // 绘制圆饼图的思路：
        // 平移 坐标原点到 屏幕中间。
        canvas.save();
        canvas.translate(width/2 ,height/2);
        drawPie(canvas);
        canvas.restore();
    }

    private void drawPie(Canvas canvas) {
        float startAngle = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            // 将 piePath 点移动到坐标原点。注意：此时的坐标原点 是画布平移后的。
            mPiePath.moveTo(0, 0);
            // 设置每块扇形的颜色
            mPiePaint.setColor(mDatas.get(i).color);

            //计算每块扇形弧度大小
            PieEntity pieEntity = mDatas.get(i);
            float sweepAngle = pieEntity.value /totalValue * 360 - 2;

            mPiePath.arcTo(mPieRectF,startAngle,sweepAngle);
            canvas.drawPath(mPiePath ,mPiePaint);

            // 画 每块扇形区域外的短线 Math.toRadians() 将弧度转换为角度
            float startX = (float) (mRadius * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
            float startY = (float) (mRadius * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
            float endX = (float) ((mRadius + 30) * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
            float endY = (float) ((mRadius + 30) * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
            canvas.drawLine(startX ,startY ,endX ,endY ,mLinePaint);

            // 下一块扇形区域的起点为上一块的终点。
            startAngle += sweepAngle + 2;
            // 每画完一块扇形后，将piepath 重置，否则会将上一块的 画笔 记录携带过来并使用，并不会使用上面的。
            mPiePath.reset();
        }
    }

    public void setDatas(List<PieEntity> datas) {
        Log.e("JY", "setDatas");
        this.mDatas = datas;
        for (PieEntity data : datas) {
            totalValue += data.value;
        }
    }
}
