package com.jiayang.customview.customviewactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jiayang.customview.R;
import com.jiayang.customview.customview.MyCircleMenuView;

/**
 * @author ：张 奎
 * @date ：2018-05-18 13：07
 * 邮箱   ：JiaYang627@163.com
 * 仿建设银行圆形菜单
 */
public class CircleMenuViewActivity extends AppCompatActivity {

    private MyCircleMenuView mMyCircleMenuView;
    private String[] mStrings = new String[]{"安全中心 ", "特色服务", "投资理财",
            "转账汇款", "我的账户", "信用卡"};
    private int[] mImages = new int[]{R.drawable.home_mbank_1_normal,
            R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
            R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
            R.drawable.home_mbank_6_normal};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circlemenu);

        initView();
        initData();
    }

    private void initView() {
        mMyCircleMenuView = findViewById(R.id.myCircleMenuView);
    }

    private void initData() {
        mMyCircleMenuView.setDatas(mImages, mStrings);
    }
}
