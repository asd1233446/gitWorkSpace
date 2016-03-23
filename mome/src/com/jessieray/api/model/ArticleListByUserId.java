package com.jessieray.api.model;

import java.io.Serializable;

public class ArticleListByUserId  implements Serializable{
	
	 private java.util.List<com.jessieray.api.model.DynamicInfo> articles;
	 
	 private int total;

	

	public java.util.List<com.jessieray.api.model.DynamicInfo> getArticles() {
		return articles;
	}

	public void setArticles(
			java.util.List<com.jessieray.api.model.DynamicInfo> articles) {
		this.articles = articles;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	
	

}
