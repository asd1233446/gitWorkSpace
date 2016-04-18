package com.jessieray.api.model;

import java.util.List;

public class RecallDetail {
/**
 * 照片留念
 * */
	private List<PhotoInfo> photos;
	
	/**
	 * 观影同伴
	 * */	
	 private List<UserInfo> friends;
	/**
	 * 评论列表
	 * */
	 private List<DynamicInfo> articles;
	/**
	 * 同场人数
	 * */
	private String samescene;
	/**
	 * 回忆录信息
	 * */
	private MemoirsInfo base;
	/**
	 * 影片信息
	 * */
	
	private MovieInfo movie;
	public List<PhotoInfo> getPhotos() {
		return photos;
	}
	public void setPhotos(List<PhotoInfo> photos) {
		this.photos = photos;
	}
	public List<UserInfo> getFriends() {
		return friends;
	}
	public void setFriends(List<UserInfo> friends) {
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
	public MovieInfo getMovie() {
		return movie;
	}
	public void setMovie(MovieInfo movie) {
		this.movie = movie;
	}
	
}
