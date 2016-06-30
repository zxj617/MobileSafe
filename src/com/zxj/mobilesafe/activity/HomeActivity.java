package com.zxj.mobilesafe.activity;

import com.zxj.mobilesafe.R;
import com.zxj.mobilesafe.adapter.HomeAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

		gv_home_menu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				switch (position) {
					case 0:
						break;
					case 1:
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						break;
					case 5:
						break;
					case 6:
						break;
					case 7:
						break;
					case 8:
						Intent intent = new Intent(getApplicationContext(), SystemSetActivity.class);
						startActivity(intent);
						break;
				}
			}
		});
	}
}
