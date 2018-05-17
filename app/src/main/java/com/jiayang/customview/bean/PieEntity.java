package com.jiayang.customview.bean;

/**
 * Created by sszz on 2016/12/9.
 */

public class PieEntity {
	//对应数据占用的份额
	public float value;
	//对应数据的颜色
	public int color;

	public PieEntity(float value, int color) {
		this.value = value;
		this.color = color;
	}
}
