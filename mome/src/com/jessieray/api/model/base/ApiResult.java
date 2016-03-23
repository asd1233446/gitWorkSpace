package com.jessieray.api.model.base;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * return wrapper from proxy service
 * 
 * @param <T>
 */
// @JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Data<T> data;

	private long code = -1;

	private String message;

	private List<Integer> whiteList = Arrays.asList(new Integer[] {
			50, 1, 52, 3, 4, 51, 2,// 数字彩
			202, 20, 560, 544, 200, 22, 23,// 高频彩
			80, 85,// 足彩
			71, 73, 70,// 篮彩
			30,// 北单
			7, 8 // 胜负彩
	});

	private Date serverTime = new Date();
	
	private long serverTimeMillis = System.currentTimeMillis();//4.0.0后用此属性，为解决时区问题

	private UpdateInfo updateInfo;// 升级信息

	// ============================ 便捷方法
	// @JsonIgnore
	public T firstModel() {
		if (this.allModel() == null || this.allModel().size() == 0) {
			return null;
		}
		return this.allModel().get(0);
	}

	// @JsonIgnore
	public List<T> allModel() {
		if (this.getData() == null) {
			return null;
		}
		return this.getData().getModelList();
	}

	public ApiResult<T> setSessionId(String sessionId) {
		this.data.setSessionId(sessionId);
		return this;
	}

	// ============================ getter and setter
	public Data<T> getData() {
		return data;
	}

	public void setData(Data<T> data) {
		this.data = data;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Integer> getWhiteList() {
		return whiteList;
	}

	public void setWhiteList(List<Integer> whiteList) {
		this.whiteList = whiteList;
	}

	public Date getServerTime() {
		return serverTime;
	}

	public void setServerTime(Date serverTime) {
		this.serverTime = serverTime;
	}
	
	public long getServerTimeMillis() {
		return serverTimeMillis;
	}

	public void setServerTimeMillis(long serverTimeMillis) {
		this.serverTimeMillis = serverTimeMillis;
	}

	public UpdateInfo getUpdateInfo() {
		return updateInfo;
	}

	public void setUpdateInfo(UpdateInfo updateInfo) {
		this.updateInfo = updateInfo;
	}

}
