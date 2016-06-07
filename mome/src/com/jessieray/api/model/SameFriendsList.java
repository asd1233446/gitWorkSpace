package com.jessieray.api.model;

import java.io.Serializable;
import java.util.List;

public class SameFriendsList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//同场观影列表
	private List<Friend> samescene;
	//看过此片的人
	
	private List<Friend> samemovie;
	
	private List<Friend> samefavor;
	
	private List<Friend>  recommand;
	
	private List<Friend> lastjoin;
	
	
    
	
	
	public List<Friend> getLastjoin() {
		return lastjoin;
	}

	public void setLastjoin(List<Friend> lastjoin) {
		this.lastjoin = lastjoin;
	}

	public List<Friend> getRecommand() {
		return recommand;
	}

	public void setRecommand(List<Friend> recommand) {
		this.recommand = recommand;
	}

	public List<Friend> getSamemovie() {
		return samemovie;
	}

	public void setSamemovie(List<Friend> samemovie) {
		this.samemovie = samemovie;
	}

	public List<Friend> getSamefavor() {
		return samefavor;
	}

	public void setSamefavor(List<Friend> samefavor) {
		this.samefavor = samefavor;
	}

	public List<Friend> getSamescene() {
		return samescene;
	}

	public void setSamescene(List<Friend> samescene) {
		this.samescene = samescene;
	}
	

}
