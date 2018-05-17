package com.jiayang.customview.bean;

/**
 * @author ：张 奎
 * @date ：2018-05-17 10：45
 * 邮箱   ：JiaYang627@163.com
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
