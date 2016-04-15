package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
import java.util.List;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class GetMovieUserArticleByKeyWord implements Serializable {
    /**
     * 个人信息
     */
    private List<com.jessieray.api.model.UserInfo> users;
    
    
    /**
     * 动态信息
     */
    private List<com.jessieray.api.model.DynamicInfo> articles;
    
    /**
     * 电影信息
     */
    private List<com.jessieray.api.model.MovieInfo> movies;

	public List<com.jessieray.api.model.UserInfo> getUsers() {
		return users;
	}

	public void setUsers(List<com.jessieray.api.model.UserInfo> users) {
		this.users = users;
	}

	public List<com.jessieray.api.model.DynamicInfo> getArticles() {
		return articles;
	}

	public void setArticles(List<com.jessieray.api.model.DynamicInfo> articles) {
		this.articles = articles;
	}

	public List<com.jessieray.api.model.MovieInfo> getMovies() {
		return movies;
	}

	public void setMovies(List<com.jessieray.api.model.MovieInfo> movies) {
		this.movies = movies;
	}
    
   



}