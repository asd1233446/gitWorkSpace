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