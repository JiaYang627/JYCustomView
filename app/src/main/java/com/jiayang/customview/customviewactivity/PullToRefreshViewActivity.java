package com.jiayang.customview.customviewactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jiayang.customview.R;
import com.jiayang.customview.adapter.MyAdapter;
import com.jiayang.customview.customview.MyPullToRefreshView;
import com.jiayang.customview.manager.NormalSelfHeaderViewManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：张 奎
 * @date ：2018-05-28 13：19
 * 邮箱   ：JiaYang627@163.com
 * 下拉刷新Activity
 */
public class PullToRefreshViewActivity extends AppCompatActivity {

    private MyPullToRefreshView mPullToRefreshView;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulltorefresh);
        initView();
        initListener();
        initRecyclerView();
    }

    private void initView() {
        mPullToRefreshView = findViewById(R.id.myPullToRefreshView);
        mRecyclerView = findViewById(R.id.recyclerView);
        mPullToRefreshView.setSelfManager(new NormalSelfHeaderViewManager(this));
    }

    private void initListener() {
        mPullToRefreshView.setOnRefreshEndListener(new MyPullToRefreshView.onRefreshEndListener() {
            @Override
            public void endRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.onRefreshEnd();
                    }
                }, 2000);
            }
        });
    }

    private void initRecyclerView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            datas.add("条目" + i);
        }
        MyAdapter adapter = new MyAdapter(datas);
        mRecyclerView.setAdapter(adapter);
    }



}
