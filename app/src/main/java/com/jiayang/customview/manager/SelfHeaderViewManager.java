package com.jiayang.customview.manager;

import android.content.Context;
import android.view.View;

/**
 * @author ：张 奎
 * @date ：2018-05-28 14：04
 * 邮箱   ：JiaYang627@163.com
 */
public abstract class SelfHeaderViewManager {

    protected Context mContext;
    protected View mSelfHeaderView;



    public abstract View getSelfHeaderView();

    public int getSelfHeaderViewHeight() {
        // 此时 视图还是初始化阶段，并没有到测量阶段，如果此时想获取到视图的宽高，只需要调用measure方法，
        // 并传入 0,0 即可
        mSelfHeaderView.measure(0, 0);
        return mSelfHeaderView.getMeasuredHeight();
    }

    public void changeToIdle() {

    }

    public abstract void changeToPullDown();

    public abstract void changeToReleaseRefresh();

    public abstract void changeToRefreshing();

    public abstract void onRefreshEnd();
}
