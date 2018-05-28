package com.jiayang.customview.manager;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.jiayang.customview.R;

/**
 * @author ：张 奎
 * @date ：2018-05-28 14：04
 * 邮箱   ：JiaYang627@163.com
 */
public class SelfHeaderViewManager {

    private final Context mContext;
    private View mSelfHeaderView;

    public SelfHeaderViewManager(Context context) {
        mContext = context;
    }

    public View getSelfHeaderView() {
        if (mSelfHeaderView == null) {
            mSelfHeaderView = View.inflate(mContext, R.layout.view_refresh_header_normal, null);

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
}
