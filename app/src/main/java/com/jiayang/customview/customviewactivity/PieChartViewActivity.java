package com.jiayang.customview.customviewactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jiayang.customview.R;
import com.jiayang.customview.bean.PieEntity;
import com.jiayang.customview.customview.MyPieChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：张 奎
 * @date ：2018-05-17 10：44
 * 邮箱   ：JiaYang627@163.com
 * 饼状图
 */
public class PieChartViewActivity extends AppCompatActivity {

    private MyPieChartView mPieChartView;
    private int[] colors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);
        initView();
        initData();
    }

    private void initView() {
        mPieChartView = findViewById(R.id.myPieChartView);
    }

    private void initData() {
        List<PieEntity> entities = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            entities.add(new PieEntity(i + 1, colors[i]));
        }
        mPieChartView.setDatas(entities);
    }
}
