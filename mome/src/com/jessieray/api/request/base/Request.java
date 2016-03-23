package com.jessieray.api.request.base;

import java.lang.reflect.Type;
import java.util.Map;

public interface Request {
	
	/**
	 * 支持的请求方法
	 * @author sld
	 *
	 */
    interface Method {
        int DEPRECATED_GET_OR_POST = -1;
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
    }
    
    /**
     * 发送请求
     * @param url 接口请求的url
     * @param method 请求方式
     * @param params 请求参数
     * @param resultType 泛型用接口model类型
     * @param response 响应回调
     */
	void doRequest(String url,int method,Map<String,String> params, Type resultType, ResponseCallback response);
	
	/**
	 * 获取客户端url
	 * @return
	 */
	String getRequestUrl();
}
