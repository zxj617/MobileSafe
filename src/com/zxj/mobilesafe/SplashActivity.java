package com.zxj.mobilesafe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zxj.mobilesafe.activity.HomeActivity;
import com.zxj.mobilesafe.utils.StreamUtil;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;


public class SplashActivity extends ActionBarActivity {

    protected static final int MSG_ENTER_HOME = 1;
	protected static final int MSG_UPDATE_DIALOG = 2;
	protected static final int MSG_ERROR_CONNECTION = 3;
	private TextView tv_splash_version;					//显示版本信息控件
	private TextView tv_splash_update_progress;			//显示下载进度信息控件
	private String versionName1;						//系统版本号
	private String des;									//服务器更新提示信息		
	private String apkurl;								//服务器更新包下载地址
	private String versionName2;						//服务器版本号
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        //相应控件
        tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
        tv_splash_update_progress = (TextView) findViewById(R.id.tv_splash_update_progress);
        
        //获取系统版本信息
        PackageManager packageManager = getPackageManager();
        try {
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			versionName1 = packageInfo.versionName;
			
			//设置版本信息控件显示系统版本
			tv_splash_version.setText(versionName1);
			
			SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
			if(sharedPreferences.getBoolean("update", true)){
				//获取软件更新信息
				systemUpdate();
			}else {
				new Thread(){
					public void run() {
						SystemClock.sleep(2000);
						enterHome();
					}
				}.start();
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
    }
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_ENTER_HOME://进入HOME界面
				enterHome();
				break;

			case MSG_UPDATE_DIALOG://提示更新
				showUpdateDialog("升级提示",des);			
				break;
				
			case MSG_ERROR_CONNECTION://连接失败
				Toast.makeText(getApplicationContext(), "联网失败", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			}
		};
	};

	/**
	 * 获取系统版本更新信息
	 */
	private void systemUpdate() {
		new Thread(){
				private Message msg = Message.obtain();
				private int startTime;	
				public void run() {
					startTime = (int) System.currentTimeMillis();
					try {
						URL url = new URL("http://192.168.0.101:8080/itheima74/json.html");
						HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();
						openConnection.setRequestMethod("GET");
						openConnection.setConnectTimeout(5000);
						
						int code = openConnection.getResponseCode();
						if(code == 200){
							//获取服务器版本信息
							InputStream inputStream = openConnection.getInputStream();
							JSONObject jsonObject = StreamUtil.streamToJSON(inputStream);
							
							versionName2 = jsonObject.getString("code");
							apkurl = jsonObject.getString("apkurl");
							des = jsonObject.getString("des");
							
							//判断是否需要更新
							if(versionName2.equals(versionName1)){
								//不更新
								msg.what = MSG_ENTER_HOME;
							}else {
								//提示更新
								msg.what = MSG_UPDATE_DIALOG;
							}
							inputStream.close();
						}else {
							//获取服务器信息失败
							msg.what = MSG_ERROR_CONNECTION;
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
						msg.what = MSG_ERROR_CONNECTION;
					} catch (IOException e) {
						e.printStackTrace();
						msg.what = MSG_ERROR_CONNECTION;
					} catch (JSONException e) {
						e.printStackTrace();
						msg.what = MSG_ERROR_CONNECTION;
					}
					finally{
						int endTime = (int) System.currentTimeMillis();
						if((endTime - startTime) < 2000){
							SystemClock.sleep(2000 - (endTime - startTime));
						}
						handler.sendMessage(msg);
					}
				}
			}.start();
	}
	
	/**
	 * 显示提示升级对话框
	 * @param string
	 * @param des2
	 */
	protected void showUpdateDialog(String string, String des2) {
		AlertDialog.Builder builder = new Builder(SplashActivity.this);
		builder.setTitle(string);
		builder.setMessage(des2);
		
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				enterHome();
			}
		});
		
		builder.setPositiveButton("升级", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				download(apkurl);
			}
		});
		
		builder.show();
	}
	
	/**
	 * 下载程序
	 * @param apkurl2
	 */
	protected void download(String apkurl2) {
		HttpUtils httpUtils = new HttpUtils();
		
		httpUtils.download(apkurl2, Environment.getExternalStorageDirectory().getPath()+"/mobilesafe.apk", new RequestCallBack<File>() {
			
			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath()+"/mobilesafe.apk")),"application/vnd.android.package-archive");
				startActivity(intent);
				finish();
			}
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(getApplicationContext(), "下载失败", Toast.LENGTH_SHORT).show();
				enterHome();
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				tv_splash_update_progress.setText(current+"/"+total);
			}

			@Override
			public void onStart() {
				tv_splash_update_progress.setVisibility(View.VISIBLE);
			}
		});
	}

	/**
	 * 进入HOME页面
	 */
	protected void enterHome() {
		Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
		startActivity(intent);
		finish();
	}
}
