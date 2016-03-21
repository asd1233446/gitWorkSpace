package com.mome.main.core.net;

import java.lang.reflect.Type;

import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;

public class GetFromCacheCallback implements ResponseCallback {
	
	public void error(ResponseError paramResponseError) {
	}

	public <T> void sucess(Type paramType, ResponseResult<T> paramResponseResult) {
	}

	@Override
	public boolean isRefreshNeeded() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean isResponseFromCache() {
		return true;
	}
}
