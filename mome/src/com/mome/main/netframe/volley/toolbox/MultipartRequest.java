package com.mome.main.netframe.volley.toolbox;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;

import com.mome.main.netframe.volley.AuthFailureError;
import com.mome.main.netframe.volley.NetworkResponse;
import com.mome.main.netframe.volley.Request;
import com.mome.main.netframe.volley.Response;
import com.mome.main.netframe.volley.Response.ErrorListener;
import com.mome.main.netframe.volley.Response.Listener;
import com.sina.weibo.sdk.utils.LogUtil;

public class MultipartRequest extends Request<String> {  
    private static final String TAG = "MultipartRequest";
    private final Listener<String> mListener;  
    private MultipartRequestParams params = null;  
    private HttpEntity httpEntity = null;  
      
    public MultipartRequest(String url, MultipartRequestParams params,Listener<String> listener,
            ErrorListener errorListener) {  
    	 super(Method.POST,url, errorListener);
         mListener = listener;  
         this.params = params; 
    }  
    @Override  
    public byte[] getBody() throws AuthFailureError {  
        // TODO Auto-generated method stub  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        if(params != null) {  
            httpEntity = params.getEntity();   
            try {  
                httpEntity.writeTo(baos);  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
                LogUtil.d(TAG, "IOException writing to ByteArrayOutputStream");
            }  
            String str = new String(baos.toByteArray());  
            LogUtil.d(TAG, "bodyString is :" + str);  
        }  
        return baos.toByteArray();  
    }  
      
    @Override  
    protected Response<String> parseNetworkResponse(NetworkResponse response) {  
        // TODO Auto-generated method stub  
    	LogUtil.e("parseNetworkResponse", response.data+"");
    	 String parsed;
         try {
             parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
         } catch (UnsupportedEncodingException e) {
             parsed = new String(response.data);
         }
         return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }  
  
      
    @Override  
    public String getBodyContentType() {  
        // TODO Auto-generated method stub  
        String str = httpEntity.getContentType().getValue();  
		LogUtil.e("getBodyContentType", str);

        return httpEntity.getContentType().getValue();  
    }  
	@Override
	protected void deliverResponse(String response) {
		// TODO Auto-generated method stub
		LogUtil.e("responsedeliverResponse", response);
		mListener.onResponse(response);
	}  
}