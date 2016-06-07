package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class GetMovieUserArticleByKeyWord implements Serializable {
	
	private int totoal;
	
	
	
    public int getTotoal() {
		return totoal;
	}

	public void setTotoal(int totoal) {
		this.totoal = totoal;
	}

	/**
     * 个人信息
     */
    private ArrayList<com.jessieray.api.model.UserInfo> users;
    
    
    /**
     * 动态信息
     */
    private ArrayList<com.jessieray.api.model.DynamicInfo> articles;
    
    /**
     * 电影信息
     */
    private ArrayList<com.jessieray.api.model.MovieInfo> movies;

	public ArrayList<com.jessieray.api.model.UserInfo> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<com.jessieray.api.model.UserInfo> users) {
		this.users = users;
	}

	public ArrayList<com.jessieray.api.model.DynamicInfo> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<com.jessieray.api.model.DynamicInfo> articles) {
		this.articles = articles;
	}

	public ArrayList<com.jessieray.api.model.MovieInfo> getMovies() {
		return movies;
	}

	public void setMovies(ArrayList<com.jessieray.api.model.MovieInfo> movies) {
		this.movies = movies;
	}
    
   



}