package com.jessieray.api.model;

import java.io.Serializable;

public class DouMovieInfo implements Serializable {

	/**
	 * 条目id
	 * */
	private String id;
	/**
	 * 中文名
	 * */
	private String title;
	/**
	 * 原名
	 * */
	private String original_title;
	/**
	 * 条目URL
	 * */
	private String alt;
	/**
	 * 海报
	 * */
	private Image images;

	/**
	 * 评分
	 * */
	private rating rating;

	/**
	 * 日期
	 * */
	private String pubdates;
	/**
	 * 年代
	 * */
	private String year;

	/**
	 * 类型
	 * */
	private String subtype;

	
	
	public Image getImages() {
		return images;
	}

	public void setImages(Image images) {
		this.images = images;
	}

	public rating getRating() {
		return rating;
	}

	public void setRating(rating rating) {
		this.rating = rating;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOriginal_title() {
		return original_title;
	}

	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getPubdates() {
		return pubdates;
	}

	public void setPubdates(String pubdates) {
		this.pubdates = pubdates;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

public	class Image {
		private String small;

		private String large;

		public String getSmall() {
			return small;
		}

		public void setSmall(String small) {
			this.small = small;
		}

		public String getLarge() {
			return large;
		}

		public void setLarge(String large) {
			this.large = large;
		}
	}

	class rating {
		private String average;

		private String stars;

		private String min;
		private String max;

		public String getMax() {
			return max;
		}

		public void setMax(String max) {
			this.max = max;
		}

		public String getAverage() {
			return average;
		}

		public void setAverage(String average) {
			this.average = average;
		}

		public String getStars() {
			return stars;
		}

		public void setStars(String stars) {
			this.stars = stars;
		}

		public String getMin() {
			return min;
		}

		public void setMin(String min) {
			this.min = min;
		}
	}

}
