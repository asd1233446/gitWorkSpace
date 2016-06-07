package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class PhotoInfo implements Serializable {
    /**
     * 图片id
     */
    private String photoid;

    /**
     * 图片名称
     */
    private String title;

    /**
     * 图片地址
     */
    private String photourl;
    
    
    /**
     * 图片是否被选中
     */
    private boolean isCheck;
    
    
    


    public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	private String id;
    
    private String userid;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setPhotoid(String photoid) {
         if (photoid == null) {
            return;
         }
        this.photoid = photoid;
    }

    public String getPhotoid() {
        return photoid;
    }


    public void setTitle(String title) {
         if (title == null) {
            return;
         }
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public void setPhotourl(String photourl) {
         if (photourl == null) {
            return;
         }
        this.photourl = photourl;
    }

    public String getPhotourl() {
        return photourl;
    }


}