package com.jessieray.api.model;

import java.io.Serializable;
import java.util.List;

public class PlatformList implements Serializable{
	
	public List<Platform> platform;
	
	
	
	public List<Platform> getPlatform() {
		return platform;
	}



	public void setPlatform(List<Platform> platform) {
		this.platform = platform;
	}



	public class Platform implements Serializable{
		private String title;
		
		private String cinemaid;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getCinemaid() {
			return cinemaid;
		}

		public void setCinemaid(String cinemaid) {
			this.cinemaid = cinemaid;
		}
		
		
	}

}
