package com.mome.db;

import java.io.Serializable;

import android.graphics.Bitmap;


public class ChatItem implements Serializable{
	public static final int CHAT = 0; 
	public static final int GROUP_CHAT = 1;
	public static final int NOTI = 2;
	
	public int chatType;   // 0 chat  1 groupChat 2 noti
	public String chatName;  //Ⱥ�ĵĻ���username��һ��
	public String username;  //�Է����ǳ�
	public String head;
	public String msg;
	public String sendDate;
	public int inOrOut; //0我发出的 1代表回复的
	public String whos;
	public String imageUrl;
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	//ѡ��
	public Bitmap headBitmap;

	public ChatItem() {
		super();
	}

	public ChatItem(int chatType, String chatName,String username, String head, String msg, String sendDate,
			int inOrOut) {
		super();
		this.chatName = chatName;
		this.username = username;
		this.head = head;
		this.msg = msg;
		this.sendDate = sendDate;
		this.inOrOut = inOrOut;
		this.chatType=chatType;
	//	this.imageUrl=imageUrl;
	}
}
