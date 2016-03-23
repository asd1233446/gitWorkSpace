package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class MessageInfo implements Serializable {
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