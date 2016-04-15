package com.jessieray.api.model;

import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This file is auto generated, do not modify it by hand
 * 
 */

public class UserAlbum implements Serializable {
	 /**
	 * 总数
	 */
	 private int total;
	
	 /**
	 * 昵称
	 */
	 private String nickname;
	
	 /**
	 * 最近动态
	 */
	 private java.util.List<com.jessieray.api.model.PhotoInfo> photos;
	
	
	
	 public void setTotal(int total) {
	 this.total = total;
	 }
	
	 public int getTotal() {
	 return total;
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
	
	
	 public void setPhotos(java.util.List<com.jessieray.api.model.PhotoInfo>
	 photos) {
	 this.photos = photos;
	 }
	
	 public java.util.List<com.jessieray.api.model.PhotoInfo> getPhotos() {
	 return photos;
	 }
	

//测试	
//	int totalNum;
//	
//    String tag1;
//    String tag2;
//    int start_index;
//    
//    int return_number;
//	public String getTag1() {
//		return tag1;
//	}
//
//	public void setTag1(String tag1) {
//		this.tag1 = tag1;
//	}
//
//	public String getTag2() {
//		return tag2;
//	}
//
//	public void setTag2(String tag2) {
//		this.tag2 = tag2;
//	}
//
//	public int getStart_index() {
//		return start_index;
//	}
//
//	public void setStart_index(int start_index) {
//		this.start_index = start_index;
//	}
//
//	public int getReturn_number() {
//		return return_number;
//	}
//
//	public void setReturn_number(int return_number) {
//		this.return_number = return_number;
//	}
//
//	public int getTotalNum() {
//		return totalNum;
//	}
//
//	public void setTotalNum(int totalNum) {
//		this.totalNum = totalNum;
//	}
//
//	ArrayList<BaiduImage> data;
//	public ArrayList<BaiduImage> getData() {
//		return data;
//	}
//
//	public void setData(ArrayList<BaiduImage> data) {
//		this.data = data;
//	}
//
//	public static class BaiduImage implements Serializable  {
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = 1L;
//		String id, abs, desc, tag, date, image_url;
//
//		public String getId() {
//			return id;
//		}
//
//		public void setId(String id) {
//			this.id = id;
//		}
//
//		public String getAbs() {
//			return abs;
//		}
//
//		public void setAbs(String abs) {
//			this.abs = abs;
//		}
//
//		public String getDesc() {
//			return desc;
//		}
//
//		public void setDesc(String desc) {
//			this.desc = desc;
//		}
//
//		public String getTag() {
//			return tag;
//		}
//
//		public void setTag(String tag) {
//			this.tag = tag;
//		}
//
//		public String getDate() {
//			return date;
//		}
//
//		public void setDate(String date) {
//			this.date = date;
//		}
//
//		public String getImage_url() {
//			return image_url;
//		}
//
//		public void setImage_url(String image_url) {
//			this.image_url = image_url;
//		}
//	}

}