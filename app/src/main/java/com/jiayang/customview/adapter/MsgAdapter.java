package com.jiayang.customview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayang.customview.R;
import com.jiayang.customview.bean.Msg;
import com.jiayang.customview.listener.onUnReadMsgTouchListener;

import java.util.List;

/**
 * Created by sszz on 2016/12/13.
 */

public class MsgAdapter extends Adapter<MsgAdapter.MyViewHolder> {
	private List<Msg> msgList;
	private final onUnReadMsgTouchListener mMsgTouchListener;

	public MsgAdapter(List<Msg> msgList, Context context) {
		mMsgTouchListener = new onUnReadMsgTouchListener(context);
		this.msgList = msgList;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rlv_item, parent,false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		holder.tv_title.setText(msgList.get(position).title);
		int unReadMsgCount = msgList.get(position).unReadMsgCount;
		if (unReadMsgCount == 0) {
			holder.tv_unReadMsgCount.setVisibility(View.INVISIBLE);
		} else {
			holder.tv_unReadMsgCount.setVisibility(View.VISIBLE);
			holder.tv_unReadMsgCount.setText(String.valueOf(unReadMsgCount));

		}

		holder.tv_unReadMsgCount.setOnTouchListener(mMsgTouchListener);
	}

	@Override
	public int getItemCount() {
		return msgList.size();
	}

	public static class MyViewHolder extends RecyclerView.ViewHolder {
		public TextView tv_title;
		public TextView tv_unReadMsgCount;

		public MyViewHolder(View itemView) {
			super(itemView);
			tv_title = (TextView) itemView.findViewById(R.id.tv_title);
			tv_unReadMsgCount = (TextView) itemView.findViewById(R.id.tv_unReadMsgCount);
		}
	}
}
