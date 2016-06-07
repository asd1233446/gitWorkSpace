package com.jessieray.api.model;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {

	private List<MessageInfo> messages;

	private int total;

	

	public int getTotoal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<MessageInfo> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageInfo> messages) {
		this.messages = messages;
	}

}
