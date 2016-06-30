package com.zxj.mobilesafe.view;

import com.zxj.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ItemSystemSetView extends RelativeLayout {

	private TextView tv_systemset_title;
	private TextView tv_systemset_des;
	private CheckBox cb_systemset_checkbox;

	public ItemSystemSetView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ItemSystemSetView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ItemSystemSetView(Context context) {
		super(context);
		init();
	}
	
	/**
	 * 初始化控件
	 */
	private void init(){
		//添加布局文件对象
		View view = View.inflate(getContext(), R.layout.item_systemset_view, this);
		
		tv_systemset_title = (TextView) view.findViewById(R.id.tv_systemset_title);
		tv_systemset_des = (TextView) view.findViewById(R.id.tv_systemset_des);
		cb_systemset_checkbox = (CheckBox) view.findViewById(R.id.cb_systemset_checkbox);
	}
	
	/**
	 * 设置子控件标题
	 * @param title
	 */
	public void setTitle(String title){
		tv_systemset_title.setText(title);
	}
	
	/**
	 * 设置子控件介绍
	 * @param des
	 */
	public void setDes(String des){
		tv_systemset_des.setText(des);
	}
	
	/**
	 * 设置子控件CheckBox状态
	 * @param checked
	 */
	public void setCheckBox(Boolean checked){
		cb_systemset_checkbox.setChecked(checked);
	}
	
	/**
	 * 获取CheckBox状态
	 * @return
	 */
	public Boolean getCheckBox(){
		return cb_systemset_checkbox.isChecked();
	}
}
