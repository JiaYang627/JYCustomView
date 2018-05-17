package com.jiayang.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.jiayang.customview.customviewactivity.PieChartViewActivity;
import com.jiayang.customview.customviewactivity.TestCanvasViewActivity;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private String[] strings = new String[]{"TestCanvasView","PieChartView"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        mListView = findViewById(R.id.mListView);
        MyAdapter myAdapter = new MyAdapter();
        mListView.setAdapter(myAdapter);

    }
    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return strings.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            View view = null;
            ViewHolder viewHolder = new ViewHolder();
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item_adapter, null);
                viewHolder.button = (Button) view.findViewById(R.id.item);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.button.setText(strings[position]);
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    if (position == 0) {
                        intent.setClass(MainActivity.this, TestCanvasViewActivity.class);
                    } else if (position == 1) {
                        intent.setClass(MainActivity.this, PieChartViewActivity.class);
                    }

                    startActivity(intent);

                }
            });

            return view;
        }
    }

    public class ViewHolder {
        Button button;
    }
}
