package com.jessieray.api.request.base;

import java.lang.reflect.Type;

public interface ResponseCallback {

	/**
	 * 返回成功
	 * @param claszz
	 */
	<T> void sucess(Type type, ResponseResult<T> claszz);
	
	/**
	 * 返回失败
	 * @param error
	 */
	void error(ResponseError error);

    /**
     * 是否刷新
     * @return
     */
    boolean isRefreshNeeded();


}
