package com.jiayang.customview.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiayang.customview.manager.SelfHeaderViewManager;

/**
 * @author ：张 奎
 * @date ：2018-05-28 13：22
 * 邮箱   ：JiaYang627@163.com
 */
public class MyPullToRefreshView extends LinearLayout {

    private LinearLayout mWholeHeaderView;
    private View selfHeaderView;
    private TextView mTvStatus;
    private ImageView mIvArrow;
    private SelfHeaderViewManager mSelfManager;
    // WholeHeaderView 距离顶部最小距离
    private int minWholeHeaderViewPaddingTop;
    // WholeHeaderView 下拉后距离顶部最大距离 = 头视图距离 * 系数
    private int maxWholeHeaderViewPaddingTop;
    // WholeHeaderView 控制下拉后距离顶部最大距离的系数
    private float maxWholeHeaderViewPaddingTopRadio = 0.3f;
    private float mDownY;
    private double dragRadio = 1.8f;

    //静止,下拉,释放刷新,刷新
    //枚举:
    //枚举不占用任何实际的值
    //枚举中的变量都是常量
    //枚举一旦定义完,只能使用枚举中的常量
    public enum RefreshStatus {
        IDLE, PULL_DOWN, RELEASE_REFRESH, REFRESHING
    }

    private RefreshStatus currentStatus = RefreshStatus.IDLE;

    public MyPullToRefreshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        initWholeHeaderView();
    }

    public void setSelfManager(SelfHeaderViewManager manager) {
        this.mSelfManager = manager;
        initSelfHeaderView();
    }

    private void initWholeHeaderView() {
        mWholeHeaderView = new LinearLayout(getContext());
        mWholeHeaderView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        mWholeHeaderView.setBackgroundColor(Color.parseColor("#FF4081"));
        addView(mWholeHeaderView);
    }

    private void initSelfHeaderView() {

        View selfHeaderView = mSelfManager.getSelfHeaderView();
        int selfHeaderViewHeight = mSelfManager.getSelfHeaderViewHeight();
        minWholeHeaderViewPaddingTop = -selfHeaderViewHeight;
        maxWholeHeaderViewPaddingTop = (int) (selfHeaderViewHeight * maxWholeHeaderViewPaddingTopRadio);
        mWholeHeaderView.setPadding(0, minWholeHeaderViewPaddingTop, 0, 0);
        mWholeHeaderView.addView(selfHeaderView);
    }

    private boolean handleActionMove(MotionEvent event) {
        float moveY = event.getY();
        float dY = moveY - mDownY;
        if (dY > 0) {
            //阻尼效果:就是类似弹簧的效果,随距离越来越长,拉动越来越难
            int paddingTop = (int) (dY / dragRadio + minWholeHeaderViewPaddingTop);
            if (paddingTop < 0 && currentStatus != RefreshStatus.PULL_DOWN) {
                currentStatus = RefreshStatus.PULL_DOWN;
                handleRefreshStatusChanged();
            } else if (paddingTop > 0 && currentStatus != RefreshStatus.RELEASE_REFRESH) {
                currentStatus = RefreshStatus.RELEASE_REFRESH;
                handleRefreshStatusChanged();
            }
            //判断如果paddingtop>maxWholeHeaderViewPaddingTop,就不能再滑动了
            paddingTop = Math.min(paddingTop, maxWholeHeaderViewPaddingTop);
            //不断的设置paddingTop,来达到将头部拉出的目的
            mWholeHeaderView.setPadding(0, paddingTop, 0, 0);

            return true;
        }


        return false;
    }

    /**
     * 刷新状态发生变化
     */
    private void handleRefreshStatusChanged() {
        switch (currentStatus) {
            case IDLE:
                mSelfManager.changeToIdle();
                break;
            case PULL_DOWN:
                mSelfManager.changeToPullDown();
                break;
            case RELEASE_REFRESH:
                mSelfManager.changeToReleaseRefresh();
                break;
            case REFRESHING:
                mSelfManager.changeToRefreshing();
                break;
        }

    }

    private boolean handleActionUp(MotionEvent event) {
        mDownY = 0;
        if (currentStatus == RefreshStatus.PULL_DOWN) {
            currentStatus = RefreshStatus.IDLE;
            hiddenRefreshHeaderView();
        } else if (currentStatus == RefreshStatus.RELEASE_REFRESH) {
            currentStatus = RefreshStatus.REFRESHING;
            changeRefreshHeaderViewPaddingToZero();
            handleRefreshStatusChanged();
        }

        return mWholeHeaderView.getPaddingTop() > minWholeHeaderViewPaddingTop;
    }

    private void hiddenRefreshHeaderView() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mWholeHeaderView.getPaddingTop(), minWholeHeaderViewPaddingTop);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentPaddingTop = (int) animation.getAnimatedValue();
                mWholeHeaderView.setPadding(0, currentPaddingTop, 0, 0);
            }
        });
        valueAnimator.start();

    }

    private void changeRefreshHeaderViewPaddingToZero() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mWholeHeaderView.getPaddingTop(), 0);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentPaddingTop = (int) animation.getAnimatedValue();
                mWholeHeaderView.setPadding(0, currentPaddingTop, 0, 0);
            }
        });
        valueAnimator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                if (handleActionMove(event)) {
                    // 满足条件后返回true ，说明 当前view 处理事件
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (handleActionUp(event)) {
                    return true;
                }
                break;
        }

        return super.onTouchEvent(event);
    }


}
