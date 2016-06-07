package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class MessageInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//昵称
	private String nickname;
	
	//最新消息
	private String lastwords;
	
	//用户id
	private String userid;
	
	//关联用户id
	private String activeid;
	
	//未读数
	
	private String unread;
	
	//最新信息事件
	
	private String lastDate;
	
	
	//消息分类1 系统消息，2用户关联消息
	private int classification;
	
	//消息类型id
	
	private int typeid;
	//消息类型1为用户头像跳转到用户主页；2为用户头像跳转至动态正文；3为跳转到电影详情，4为跳转到回忆录详情
	private int type;
	
	

    public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLastwords() {
		return lastwords;
	}

	public void setLastwords(String lastwords) {
		this.lastwords = lastwords;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getActiveid() {
		return activeid;
	}

	public void setActiveid(String activeid) {
		this.activeid = activeid;
	}

	public String getUnread() {
		return unread;
	}

	public void setUnread(String unread) {
		this.unread = unread;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public int getClassification() {
		return classification;
	}

	public void setClassification(int classification) {
		this.classification = classification;
	}

	public int getTypeid() {
		return typeid;
	}

	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	/**
     * 消息id
     */
    private int messageid;

    /**
     * 对方头像地址
     */
    private String avatar;

    /**
     * 发送方id
     */
    private String fromid;

    /**
     * 接收方id
     */
    private String toid;

    /**
     * 消息内容
     */
    private String breif;

    /**
     * 发送时间
     */
    private String createtime;



    public void setMessageid(int messageid) {
        this.messageid = messageid;
    }

    public int getMessageid() {
        return messageid;
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


    public void setFromid(String fromid) {
         if (fromid == null) {
            return;
         }
        this.fromid = fromid;
    }

    public String getFromid() {
        return fromid;
    }


    public void setToid(String toid) {
         if (toid == null) {
            return;
         }
        this.toid = toid;
    }

    public String getToid() {
        return toid;
    }


    public void setBreif(String breif) {
         if (breif == null) {
            return;
         }
        this.breif = breif;
    }

    public String getBreif() {
        return breif;
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


}