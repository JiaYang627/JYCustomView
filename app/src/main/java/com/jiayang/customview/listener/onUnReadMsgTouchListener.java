package com.jiayang.customview.listener;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jiayang.customview.customview.GooView;

/**
 * @author ：张 奎
 * @date ：2018-05-24 14：05
 * 邮箱   ：JiaYang627@163.com
 */
public class onUnReadMsgTouchListener implements View.OnTouchListener, GooView.onGooViewChangeListener {

    private final WindowManager mManager;
    private final GooView mGooView;
    private final WindowManager.LayoutParams mParams;
    private TextView mUnReadMsgTextView;

    public onUnReadMsgTouchListener(Context context) {
        mManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mGooView = new GooView(context);
        mGooView.setonGooViewChangeListener(this);
        mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.format = PixelFormat.TRANSLUCENT;//类型是透明

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        // View v 是当前点击的 条目上的未读数量
        // 通过v 获取到 recyclerView的条目根布局，然后让根布局告诉 根布局的父亲说，不要抢事件
        v.getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 让当前的 未读数量 TextView 消失
                mUnReadMsgTextView = (TextView) v;
                mUnReadMsgTextView.setVisibility(View.GONE);
                mGooView.initDownPoints(event.getRawX() ,event.getRawY());
                mGooView.setText(mUnReadMsgTextView.getText().toString());

                mManager.addView(mGooView, mParams);

                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mGooView.onTouchEvent(event);
        return true;
    }

    @Override
    public void onDisappear() {
        mManager.removeView(mGooView);
    }

    @Override
    public void onReset() {
        //重置操作
        //1,移除GooView
        //WindowManager的addView方法是将GooView添加到root根部局中
        //removeView是将GooView从root根部局中移除,当移除后的GooView再次尝试从root中移除就会抛出
        //View not attached to window manager这样的异常
        //是由已经被WindowManager移除的视图,再此被移除
        //如果已经添加到root上的GooView会有一个Parent(父视图),判断父视图是否为空,就可以规避这个bug
        if (mGooView.getParent() != null) {
            mManager.removeView(mGooView);
            mUnReadMsgTextView.setVisibility(View.VISIBLE);
        }
    }
}
