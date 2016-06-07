package com.jessieray.api.model;

import java.io.Serializable;
import java.util.List;

public class RecallDetail implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
 * 照片留念
 * */
	private List<PhotoInfo> photos;
	
	/**
	 * 观影同伴
	 * */	
	 private List<Friend> friends;
	/**
	 * 评论列表
	 * */
	 private List<DynamicInfo> articles;
	/**
	 * 同场人数
	 * */
	private String samescene;
	
	/**
	 * 同场id
	 * */
	private String  sceneid;
	/**
	 * 回忆录信息
	 * */
	private MemoirsInfo base;
	/**
	 * 影片信息
	 * */
	
	private MemoirsInfo movie;
	
	
	public String getSceneid() {
		return sceneid;
	}
	public void setSceneid(String sceneid) {
		this.sceneid = sceneid;
	}
	public List<PhotoInfo> getPhotos() {
		return photos;
	}
	public void setPhotos(List<PhotoInfo> photos) {
		this.photos = photos;
	}
	public List<Friend> getFriends() {
		return friends;
	}
	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}
	public List<DynamicInfo> getArticles() {
		return articles;
	}
	public void setArticles(List<DynamicInfo> articles) {
		this.articles = articles;
	}
	public String getSamescene() {
		return samescene;
	}
	public void setSamescene(String samescene) {
		this.samescene = samescene;
	}
	public MemoirsInfo  getBase() {
		return base;
	}
	public void setBase(MemoirsInfo base) {
		this.base = base;
	}
	public MemoirsInfo getMovie() {
		return movie;
	}
	public void setMovie(MemoirsInfo movie) {
		this.movie = movie;
	}

	
}
