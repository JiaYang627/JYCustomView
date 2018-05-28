package com.jiayang.customview.customviewactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jiayang.customview.R;
import com.jiayang.customview.customview.MyPullToRefreshView;
import com.jiayang.customview.manager.SelfHeaderViewManager;

/**
 * @author ：张 奎
 * @date ：2018-05-28 13：19
 * 邮箱   ：JiaYang627@163.com
 * 下拉刷新Activity
 */
public class PullToRefreshViewActivity extends AppCompatActivity {

    private MyPullToRefreshView mPullToRefreshView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulltorefresh);
        initView();
    }

    private void initView() {
        mPullToRefreshView = findViewById(R.id.myPullToRefreshView);
        mPullToRefreshView.setSelfManager(new SelfHeaderViewManager(this));
    }
}
