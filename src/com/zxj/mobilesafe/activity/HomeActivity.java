package com.zxj.mobilesafe.activity;

import com.zxj.mobilesafe.R;
import com.zxj.mobilesafe.adapter.HomeAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class HomeActivity extends Activity {
	private GridView gv_home_menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		gv_home_menu = (GridView) findViewById(R.id.gv_home_menu);
		
		HomeAdapter adapter = new HomeAdapter(getApplicationContext());
		
		gv_home_menu.setAdapter(adapter);
	}
}
