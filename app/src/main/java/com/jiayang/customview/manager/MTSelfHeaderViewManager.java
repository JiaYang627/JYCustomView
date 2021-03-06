package com.jiayang.customview.manager;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jiayang.customview.R;

/**
 * @author ：张 奎
 * @date ：2018-05-30 15：05
 * 邮箱   ：JiaYang627@163.com
 */
public class MTSelfHeaderViewManager extends SelfHeaderViewManager {

    private ImageView mPullDownImageView;
    private ImageView mReleaseRefresingImageView;
    private AnimationDrawable mReleaseRefreshDrawable;
    private AnimationDrawable mRefreshingDrawable;

    public MTSelfHeaderViewManager(Context context) {
        this.mContext = context;
    }

    @Override
    public View getSelfHeaderView() {

        if (mSelfHeaderView == null) {
            mSelfHeaderView = View.inflate(mContext, R.layout.view_refresh_header_meituan, null);
            mSelfHeaderView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            mPullDownImageView = mSelfHeaderView.findViewById(R.id.iv_meituan_pull_down);
            mReleaseRefresingImageView = mSelfHeaderView.findViewById(R.id.iv_meituan_release_refreshing);
        }
        return mSelfHeaderView;
    }

    @Override
    public void changeToIdle() {

        if (mReleaseRefreshDrawable != null) {
            mReleaseRefreshDrawable.stop();
        }
        if (mRefreshingDrawable != null) {
            mRefreshingDrawable.stop();
        }
    }

    @Override
    public void changeToPullDown() {
        mPullDownImageView.setVisibility(View.VISIBLE);
        mReleaseRefresingImageView.setVisibility(View.GONE);
    }

    @Override
    public void changeToReleaseRefresh() {
        mPullDownImageView.setVisibility(View.GONE);
        mReleaseRefresingImageView.setImageResource(R.drawable.release_refresh);
        mReleaseRefreshDrawable = (AnimationDrawable) mReleaseRefresingImageView.getDrawable();
        mReleaseRefreshDrawable.start();
        mReleaseRefresingImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void changeToRefreshing() {
        mReleaseRefresingImageView.setImageResource(R.drawable.refresh_mt_refreshing);
        mRefreshingDrawable = (AnimationDrawable) mReleaseRefresingImageView.getDrawable();
        mRefreshingDrawable.start();
    }

    @Override
    public void onRefreshEnd() {
        mPullDownImageView.setVisibility(View.VISIBLE);
        mReleaseRefresingImageView.setVisibility(View.GONE);
    }

    @Override
    public void handleScale(float scale) {
        mPullDownImageView.setScaleX(scale);
        mPullDownImageView.setScaleY(scale);
    }
}
