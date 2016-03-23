package com.jessieray.api.request.base;

import java.io.Serializable;

public class ResponseResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	

//mome项目
	private T data;
	private long code;
	private String message;
	private long servertime;

	@SuppressWarnings({ "hiding", "unchecked" })
	public <T> T getModel() {
		return (T) data;
	}

	public void setModel(T model) {
		this.data = model;
	}

	/**
	 * @return the code
	 */
	public long getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(long code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public long getServertime() {
		return servertime;
	}

	public void setServertime(long servertime) {
		this.servertime = servertime;
	}

}
