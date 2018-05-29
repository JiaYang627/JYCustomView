package com.jiayang.customview.manager;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiayang.customview.R;

/**
 * @author ：张 奎
 * @date ：2018-05-28 14：04
 * 邮箱   ：JiaYang627@163.com
 */
public class NormalSelfHeaderViewManager extends SelfHeaderViewManager{

    private RotateAnimation mRotateAnimationForUp;
    private RotateAnimation mRotateAnimationForDown;
    private TextView mTextView;
    private ImageView mImageView;
    private ImageView mImageViewAnimation;
    private AnimationDrawable mMImageViewDrawable;

    public NormalSelfHeaderViewManager(Context context) {
        this.mContext = context;
        initAnimation();
    }

    private void initAnimation() {
        mRotateAnimationForUp = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimationForUp.setDuration(100);
        mRotateAnimationForUp.setFillAfter(true);

        mRotateAnimationForDown = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimationForDown.setDuration(100);
        mRotateAnimationForDown.setFillAfter(true);
    }

    public View getSelfHeaderView() {
        if (mSelfHeaderView == null) {
            mSelfHeaderView = View.inflate(mContext, R.layout.view_refresh_header_normal, null);
            mTextView =  mSelfHeaderView.findViewById(R.id.tv_normal_refresh_header_status);
            mImageView =  mSelfHeaderView.findViewById(R.id.iv_normal_refresh_header_arrow);
            mImageViewAnimation =  mSelfHeaderView.findViewById(R.id.iv_normal_refresh_header_chrysanthemum);
            mMImageViewDrawable = (AnimationDrawable) mImageViewAnimation.getDrawable();
            mSelfHeaderView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        return mSelfHeaderView;
    }


    public void changeToIdle() {

    }

    public void changeToPullDown() {
        mTextView.setText("下拉刷新");
        mImageView.startAnimation(mRotateAnimationForDown);
    }

    public void changeToReleaseRefresh() {
        mTextView.setText("释放刷新");
        mImageView.startAnimation(mRotateAnimationForUp);

    }

    public void changeToRefreshing() {
        mTextView.setText("加载中......");
        mImageView.clearAnimation();
        mImageView.setVisibility(View.GONE);
        mImageViewAnimation.setVisibility(View.VISIBLE);
        mMImageViewDrawable.start();
    }

    public void onRefreshEnd() {
        mTextView.setText("下拉刷新");
        mRotateAnimationForDown.setDuration(0);
        mImageView.startAnimation(mRotateAnimationForDown);
        mImageView.setVisibility(View.VISIBLE);
        mImageViewAnimation.setVisibility(View.GONE);

    }
}
