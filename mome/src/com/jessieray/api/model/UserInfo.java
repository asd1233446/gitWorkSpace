package com.jessieray.api.model;

import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;

/**
 * This file is auto generated, do not modify it by hand
 * 
 */

public class UserInfo implements Serializable {
	/**
	 * 用户id
	 */
	private String userid;

	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 生日
	 */
	private String brithday;
	
	public String getBrithday() {
		return brithday;
	}

	public void setBrithday(String brithday) {
		this.brithday = brithday;
	}
	/**
	 * 性别
	 */
	private String sex;
	
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 用户头像
	 */
	private String avatar;

	/**
	 * 用户评论
	 */
	private String brief;

	/**
	 * 提交时间
	 */
	private String createtime;

	/**
	 * MOME号
	 */
	private String momeid;

	/**
	 * 未读系统消息数
	 */
	private String systemmessages;

	/**
	 * 未读用户消息数
	 */
	private String custommessages;

	/**
	 * 我的电影院
	 */
	private String cinema;

	/**
	 * 喜欢电影类型
	 * 
	 */
	private String movietype;

	/**
	 * 粉丝
	 * 
	 */
	private String fans;

	/**
	 * 关注
	 * 
	 */
	private String attention;

	/**
	 * 签名
	 * 
	 */
	private String signature;
	/**
	 * 观影人数
	 * 
	 */
	private String watchtotal;

	public boolean isIsattention() {
		return isattention;
	}

	public void setIsattention(boolean isattention) {
		this.isattention = isattention;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * 是否已经关注此用户
	 */
	private boolean isattention;

	/**
	 * 地点
	 */
	private String location;

	public String getMovietype() {
		return movietype;
	}

	public void setMovietype(String movietype) {
		this.movietype = movietype;
	}

	public String getFans() {
		return fans;
	}

	public void setFans(String fans) {
		this.fans = fans;
	}

	public String getAttention() {
		return attention;
	}

	public void setAttention(String attention) {
		this.attention = attention;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getWatchtotal() {
		return watchtotal;
	}

	public void setWatchtotal(String watchtotal) {
		this.watchtotal = watchtotal;
	}

	public void setUserid(String userid) {
		if (userid == null) {
			return;
		}
		this.userid = userid;
	}

	public String getUserid() {
		return userid;
	}

	public void setNickname(String nickname) {
		if (nickname == null) {
			return;
		}
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setAvatar(String avatar) {
		if (avatar == null) {
			return;
		}
		this.avatar = avatar;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setBrief(String brief) {
		if (brief == null) {
			return;
		}
		this.brief = brief;
	}

	public String getBrief() {
		return brief;
	}

	public void setCreatetime(String createtime) {
		if (createtime == null) {
			return;
		}
		this.createtime = createtime;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setMomeid(String momeid) {
		if (momeid == null) {
			return;
		}
		this.momeid = momeid;
	}

	public String getMomeid() {
		return momeid;
	}

	public void setSystemmessages(String systemmessages) {
		if (systemmessages == null) {
			return;
		}
		this.systemmessages = systemmessages;
	}

	public String getSystemmessages() {
		return systemmessages;
	}

	public void setCustommessages(String custommessages) {
		if (custommessages == null) {
			return;
		}
		this.custommessages = custommessages;
	}

	public String getCustommessages() {
		return custommessages;
	}

	public void setCinema(String cinema) {
		if (cinema == null) {
			return;
		}
		this.cinema = cinema;
	}

	public String getCinema() {
		return cinema;
	}

//	/**
//	 * 最近动态
//	 */
//	private java.util.List<com.jessieray.api.model.DynamicInfo> lastarticle;
//
//	public void setLastarticle(
//			java.util.List<com.jessieray.api.model.DynamicInfo> lastarticle) {
//		this.lastarticle = lastarticle;
//	}
//
//	public java.util.List<com.jessieray.api.model.DynamicInfo> getLastarticle() {
//		return lastarticle;
//	}
 private DynamicInfo lastarticle;

public DynamicInfo getLastarticle() {
	return lastarticle;
}

public void setLastarticle(DynamicInfo lastarticle) {
	this.lastarticle = lastarticle;
}

}