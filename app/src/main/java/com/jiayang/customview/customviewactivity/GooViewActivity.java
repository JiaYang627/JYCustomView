package com.jiayang.customview.customviewactivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jiayang.customview.R;
import com.jiayang.customview.adapter.MsgAdapter;
import com.jiayang.customview.bean.Msg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：张 奎
 * @date ：2018-05-22 17：32
 * 邮箱   ：JiaYang627@163.com
 */
public class GooViewActivity extends Activity {

    private RecyclerView mRecyclerView;
    private List<Msg> mMsgList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goo);
        initData();
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MsgAdapter(mMsgList));


    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            mMsgList.add(new Msg("这是标题" + i, i));
        }
    }
}
