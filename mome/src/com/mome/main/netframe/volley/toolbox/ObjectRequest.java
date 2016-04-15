package com.mome.main.netframe.volley.toolbox;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jessieray.api.model.UserAlbum;
import com.jessieray.api.request.base.ResponseResult;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.netframe.volley.NetworkResponse;
import com.mome.main.netframe.volley.Request;
import com.mome.main.netframe.volley.Response;
import com.mome.main.netframe.volley.Response.BaseModelListener;
import com.mome.main.netframe.volley.Response.ErrorListener;

import android.text.TextUtils;
import android.util.Log;


public abstract class ObjectRequest extends Request<String>{
	
	private BaseModelListener<ResponseResult<?>> mListener;
	/**
	 * cache save key
	 */
	private String cacheKey = "";
    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param params Map<String, String> to post
     * @param listener BaseModelListener to receive the BaseModel
     * @param errorListener Error listener, or null to ignore errors
     */
    public ObjectRequest(int method, String url, BaseModelListener<ResponseResult<?>> listener,
            ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    /**
     * Creates a new GET request.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public ObjectRequest(String url, BaseModelListener<ResponseResult<?>> listener, ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return (Response<String>) Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
	}

	protected abstract Type getClassType();
	
	@Override
	protected void deliverResponse(String response) {
	    GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
	    Log.e("返回数据＝＝＝＝＝＝", response);
	    Gson gson = gsonBuilder.create();
	    ResponseResult<?> resultModel = gson.fromJson(response, getClassType());
	    mListener.onResponse(resultModel);
	}
	

	@Override
	public String getCacheKey() {
		return this.cacheKey;
	}

	/**
	 * cache key
	 * @param url
	 */
	public void setCacheKey(String url) {
		if(!TextUtils.isEmpty(url)) {
			this.cacheKey = url;
		}
	}
}
