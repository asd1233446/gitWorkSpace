/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jessieray.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @deprecated查询好友关系
 */
public class RelationListByUserId implements Serializable{
	/***
	 * 查询好友关系
	 * 
	 */
	private ArrayList<Friend> relations;

	public ArrayList<Friend> getRelations() {
		return relations;
	}

	public void setRelations(ArrayList<Friend> relations) {
		this.relations = relations;
	}

	
	
	
   
}
