package com.zxj.mobilesafe.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 流转换工具类
 * @author Administrator
 *
 */
public class StreamUtil {

	/**
	 * 流转换JSON
	 * @param inputStream
	 * @return
	 */
	public static JSONObject streamToJSON(InputStream inputStream) {
		
		JSONObject jsonObject = null;
		
		//创建字符读取流
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		//创建写入字符流
		StringWriter writer = new StringWriter();
		//创建数据缓冲区
		String str = null;
		
		try {
			if((str = bufferedReader.readLine()) != null){
				writer.write(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				bufferedReader.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			jsonObject = new JSONObject(str);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}

}
