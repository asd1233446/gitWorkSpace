/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mome.main.business.userinfo;

import java.util.ArrayList;
import java.util.List;

import com.jessieray.api.model.Friend;
/**
 *
 * @deprecated好友种类
 */
public class FriendCategoryModel {
    
 //   private String section;
    private List<Friend> friendModelList = new ArrayList<Friend>();



    public List<Friend> getFriendModelList() {
		return friendModelList;
	}

	public void setFriendModelList(List<Friend> friendModelList) {
		this.friendModelList = friendModelList;
	}

//	public String getSection() {
//        return section;
//    }
//
//    public void setSection(String section) {
//        this.section = section;
//    }
    
}
