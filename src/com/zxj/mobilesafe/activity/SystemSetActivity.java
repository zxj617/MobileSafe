package com.zxj.mobilesafe.activity;

import com.zxj.mobilesafe.R;
import com.zxj.mobilesafe.view.ItemSystemSetView;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SystemSetActivity extends Activity {
	
	private ItemSystemSetView iv_systemset_item;
	private SharedPreferences sharedPreferences;
	private Editor edit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_systemset);
		
		sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		edit = sharedPreferences.edit();
		
		iv_systemset_item = (ItemSystemSetView) findViewById(R.id.iv_systemset_item);
		
		iv_systemset_item.setTitle("提示更新");
		if(sharedPreferences.getBoolean("update", true)){
			iv_systemset_item.setDes("打开提示更新");
			iv_systemset_item.setCheckBox(true);
		}else {
			iv_systemset_item.setDes("关闭提示更新");
			iv_systemset_item.setCheckBox(false);
		}
		
		iv_systemset_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!iv_systemset_item.getCheckBox()){
					edit.putBoolean("update", true);
					iv_systemset_item.setDes("打开提示更新");
					iv_systemset_item.setCheckBox(true);
				}else {
					edit.putBoolean("update", false);
					iv_systemset_item.setDes("关闭提示更新");
					iv_systemset_item.setCheckBox(false);
				}
				
				edit.commit();
			}
		});
	}
}
