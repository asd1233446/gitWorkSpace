package com.jessieray.api.model;

import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;

/**
 * 消费统计
 */
public class CostStatistical implements Serializable {
	/**
	 * 消费时间
	 */
	private String minute;
	/**
	 * 消费金额
	 */
	private String precent;
	/**
	 * 击败人数
	 */
	private String money;
	public String getMinute() {
		return minute;
	}
	public void setMinute(String minute) {
		this.minute = minute;
	}
	public String getPrecent() {
		return precent;
	}
	public void setPrecent(String precent) {
		this.precent = precent;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}

}