package com.jessieray.api.model.base;

import java.io.Serializable;

public class UpdateInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 升级策略
	 * 
	 * <pre>
	 * 0:不升级
	 * 1:强制升级
	 * 2:建议升级
	 * </pre>
	 */
	private int policy;
	/**
	 * 新版本号
	 */
	private String newVersion;
	/**
	 * 更新日志
	 */
	private String updateLog;
	/**
	 * 下载地址
	 */
	private String downloadUrl;

	public UpdateInfo() {
	}

	public UpdateInfo(int policy, String newVersion, String updateLog, String downloadUrl) {
		this.policy = policy;
		this.newVersion = newVersion;
		this.updateLog = updateLog;
		this.downloadUrl = downloadUrl;
	}

	public int getPolicy() {
		return policy;
	}

	public void setPolicy(int policy) {
		this.policy = policy;
	}

	public String getNewVersion() {
		return newVersion;
	}

	public void setNewVersion(String newVersion) {
		this.newVersion = newVersion;
	}

	public String getUpdateLog() {
		return updateLog;
	}

	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

}
