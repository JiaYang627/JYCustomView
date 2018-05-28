package com.jiayang.customview.manager;

import android.content.Context;
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
public class SelfHeaderViewManager {

    private final Context mContext;
    private View mSelfHeaderView;
    private RotateAnimation mRotateAnimationForUp;
    private RotateAnimation mRotateAnimationForDown;
    private TextView mTextView;
    private ImageView mImageView;

    public SelfHeaderViewManager(Context context) {
        mContext = context;
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
            mTextView = (TextView) mSelfHeaderView.findViewById(R.id.tv_normal_refresh_header_status);
            mImageView = (ImageView) mSelfHeaderView.findViewById(R.id.iv_normal_refresh_header_arrow);
            mSelfHeaderView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        return mSelfHeaderView;
    }

    public int getSelfHeaderViewHeight() {
        // 此时 视图还是初始化阶段，并没有到测量阶段，如果此时想获取到视图的宽高，只需要调用measure方法，
        // 并传入 0,0 即可
        mSelfHeaderView.measure(0, 0);
        return mSelfHeaderView.getMeasuredHeight();
    }

    public void changeToIdle() {

    }

    public void changeToPullDown() {
        mTextView.setText("下拉刷新");
        mImageView.startAnimation(mRotateAnimationForDown);
    }

    public void changeToRefreshing() {
        mTextView.setText("释放刷新");
        mImageView.startAnimation(mRotateAnimationForUp);
    }

    public void changeToRefreshEnd() {

    }
}
