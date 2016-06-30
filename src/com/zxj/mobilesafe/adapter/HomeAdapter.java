package com.zxj.mobilesafe.adapter;

import com.zxj.mobilesafe.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeAdapter extends BaseAdapter {
	int[] imageId = { R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app,
			R.drawable.taskmanager, R.drawable.netmanager, R.drawable.trojan,
			R.drawable.sysoptimize, R.drawable.atools, R.drawable.settings };
	String[] names = { "手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理",
			"高级工具", "设置中心" };
	
	private Context context;
	
	public HomeAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return names.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = null;
		
		if(convertView != null){
			view = convertView;
		}else {
			view = View.inflate(context, R.layout.item_home_menu, null);
		}
		
		ImageView iv_home_img = (ImageView) view.findViewById(R.id.iv_home_img);
		TextView tv_home_title = (TextView) view.findViewById(R.id.tv_home_title);
		
		iv_home_img.setImageResource(imageId[position]);
		tv_home_title.setText(names[position]);
		return view;
	}

}
