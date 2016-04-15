package com.mome.db;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * intent����Я���Parcel��ݣ���Ҫʵ��������� . 1��describeContents()����0�Ϳ���.
 * 2������Ҫ�����д��Parcel�У���ܵ�����������������. 3����д�ⲿ�෴���л�����ʱ���õķ���.
 * 
 * @author wangdan
 * 
 */
public class Contact implements Parcelable {

	/**
	 * ��user������intent��ʱ��key
	 */
	public static final String userKey = "lovesong_user";

	private String name;
	private String JID;
	private String status;
	private String from;
	private String groupName;
	/**
	 * �û�״̬��Ӧ��ͼƬ
	 */
	private int imgId;
	/**
	 * group��size
	 */
	private int size;
	private boolean available;

	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJID() {
		return JID;
	}

	public void setJID(String jID) {
		JID = jID;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(JID);
		dest.writeString(name);
		dest.writeString(from);
		dest.writeString(status);
		dest.writeInt(available ? 1 : 0);
	}

	public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {

		@Override
		public Contact createFromParcel(Parcel source) {
			Contact u = new Contact();
			u.JID = source.readString();
			u.name = source.readString();
			u.from = source.readString();
			u.status = source.readString();
			u.available = source.readInt() == 1 ? true : false;
			return u;
		}

		@Override
		public Contact[] newArray(int size) {
			return new Contact[size];
		}

	};

	public Contact clone() {
		Contact user = new Contact();
		user.setAvailable(Contact.this.available);
		user.setFrom(Contact.this.from);
		user.setGroupName(Contact.this.groupName);
		user.setImgId(Contact.this.imgId);
		user.setJID(Contact.this.JID);
		user.setName(Contact.this.name);
		user.setSize(Contact.this.size);
		user.setStatus(Contact.this.status);
		return user;
	}

}
