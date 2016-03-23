package com.jessieray.api.request.base;

public class RequestProxy {
	
	private static Request request;

	/**
	 * @return the request
	 */
	public static Request getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public static void setRequest(Request request) {
		RequestProxy.request = request;
	}
}