package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class PersonelMessage implements Serializable {
    /**
     * 动态详情信息
     */
    private java.util.List<com.jessieray.api.model.MessageInfo> messages;



    public void setMessages(java.util.List<com.jessieray.api.model.MessageInfo> messages) {
        this.messages = messages;
    }

    public java.util.List<com.jessieray.api.model.MessageInfo> getMessages() {
        return messages;
    }


}