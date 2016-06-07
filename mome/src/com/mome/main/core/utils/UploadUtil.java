package com.mome.main.core.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jessieray.api.model.Me;
import com.jessieray.api.model.savePicture;
import com.jessieray.api.request.base.Request;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.mome.main.core.utils.Tools.CallBack;
import com.mome.main.netframe.volley.RequestQueue;
import com.mome.main.netframe.volley.Response.ErrorListener;
import com.mome.main.netframe.volley.Response.Listener;
import com.mome.main.netframe.volley.VolleyError;
import com.mome.main.netframe.volley.toolbox.MultipartRequest;
import com.mome.main.netframe.volley.toolbox.MultipartRequestParams;
import com.mome.main.netframe.volley.toolbox.Volley;
import com.mome.view.LoadingDialog;

import android.content.Context;
import android.util.Log;

/**
 * 上传头像
 * 
 * @author Administrator
 * 
 */
public class UploadUtil {
	private static final int TIME_OUT = 10 * 1000; // 超时时间

	private static final String CHARSET = "utf-8"; // 设置编码

	public static void httpUtil(String RequestURL,
			Map<String, String> signParams, final String RequestMethod,
			final CallBack callBack) {
		final String spanUrl;
		final StringBuilder strbuf = new StringBuilder();
		for (Map.Entry<String, String> paramEntry : signParams.entrySet()) {
			if (strbuf.length() > 0) {
				strbuf.append("&");
			}
			strbuf.append(paramEntry.getKey()).append("=")
					.append(paramEntry.getValue());
		}
		if ("GET".equals(RequestMethod)&&signParams.size()>0) {
			spanUrl = strbuf.insert(0, "?").insert(0, RequestURL).toString();
		} else {
			spanUrl = RequestURL;
		}
		Tools.Log("网络请求url:" + spanUrl);

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result = "";
				try {
					URL url = new URL(spanUrl);
					final HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setReadTimeout(TIME_OUT);
					conn.setConnectTimeout(TIME_OUT);
					conn.setUseCaches(false); // 不允许使用缓存
					conn.setRequestMethod(RequestMethod); // 请求方式
					conn.setRequestProperty("Charset", CHARSET); // 设置编码
					conn.setRequestProperty("connection", "keep-alive");
					conn.setReadTimeout(8000);
					conn.setConnectTimeout(3000);
					conn.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded; charset=utf-8");
					if ("POST".equals(RequestMethod)) {
						conn.setDoInput(true); // 允许输入流
						conn.setDoOutput(true); // 允许输出流
						byte[] bypes = strbuf.toString().getBytes();
						conn.getOutputStream().write(bypes);// 输入参数
					}
					int res = conn.getResponseCode();
					Log.e("=============", "res" + res);
					if (res == 200) {
						InputStream input = conn.getInputStream();
						BufferedReader br = new BufferedReader(
								new InputStreamReader(input, "utf-8"));
						StringBuffer sb1 = new StringBuffer();
						int ss;
						while ((ss = br.read()) != -1) {
							sb1.append((char) ss);
						}
						result = sb1.toString();
						callBack.Back(result);

					}

				} catch (Exception e) {
					e.printStackTrace();
					callBack.Back(result);

				}

			}
		}).start();

	}

	public static void upload(String imageUrl, MultipartRequestParams params,
			final ResponseCallback callback) {
		MultipartRequest multiPartRequest = new MultipartRequest(AppConfig.url
				+ imageUrl, params, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				ResponseResult result = getObject(response);
				if (result.getCode() == AppConfig.REQUEST_CODE_SUCCESS) {
					callback.sucess(
							new com.google.gson.reflect.TypeToken<ResponseResult<Object>>() {
							}.getType(), result);
				} else {
					ResponseError error = new ResponseError();
					error.setMessage(result.getMessage());
					callback.error(error);
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				ResponseError responseError = new ResponseError();
				responseError.setMessage(error.getMessage());
				callback.error(responseError);

			}

		});

		RequestQueue requestQueue = Volley.newRequestQueue(AppConfig.context);
		requestQueue.start();
		requestQueue.add(multiPartRequest);
	}

	public static <T> ResponseResult<?> getObject(String jsonString) {
		Log.e("上传图片返回的结果", jsonString);
		Gson gson = new Gson();
		ResponseResult<?> response = (ResponseResult<?>) gson
				.fromJson(
						jsonString,
						new com.google.gson.reflect.TypeToken<ResponseResult<savePicture>>() {
						}.getType());
		return response;
	}

}