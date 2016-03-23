package com.jessieray.api.request.base;

public class ResponseError {

	/**
	 * 状态码
	 */
	private long code;
	
	/**
	 * 错误提示
	 */
	private String msg;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return msg;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.msg = message;
	}

	/**
	 * @return the code
	 */
	public long getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(long code) {
		this.code = code;
	}
}
