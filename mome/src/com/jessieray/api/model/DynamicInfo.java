package com.jessieray.api.model;


import com.jessieray.api.model.base.ApiResult;

import java.io.Serializable;
/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class DynamicInfo implements Serializable {
    /**
     * 动态id
     */
    private int articleid,id;

    /**
     * 动态内容
     */
    private String brief,key_brief;

    /**
     * 电影id
     */
    private int movieid;

    /**
     * 电影名
     */
    private String title;

    /**
     * 电影海报地址
     */
    private String imageSrc;

    /**
     * 用户id
     */
    private int userid;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户头像地址
     */
    private String avatar;

    /**
     * 评分
     */
    private float mark;

    /**
     * 电影回忆录id
     */
    private int recallid;

    /**
     * 本人是否点赞
     */
    private boolean isgood;

    /**
     * 点赞总数
     */
    private int goods;

    /**
     * 评论总数
     */
    private int comments;

    /**
     * 提交时间
     */
    private String createtime;
    
    
    /**
     * 排序类型
     */
   
     private int orderType;

    public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public void setArticleid(int articleid) {
        this.articleid = articleid;
    }

    public int getArticleid() {
        return articleid;
    }


    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey_brief() {
		return key_brief;
	}

	public void setKey_brief(String key_brief) {
		this.key_brief = key_brief;
	}

	public void setBrief(String brief) {
         if (brief == null) {
            return;
         }
        this.brief = brief;
    }

    public String getBrief() {
        return brief;
    }


    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public int getMovieid() {
        return movieid;
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


    public void setImageSrc(String imageSrc) {
         if (imageSrc == null) {
            return;
         }
        this.imageSrc = imageSrc;
    }

    public String getImageSrc() {
        return imageSrc;
    }


    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUserid() {
        return userid;
    }


    public void setNickname(String nickname) {
         if (nickname == null) {
            return;
         }
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }


    public void setUsername(String username) {
         if (username == null) {
            return;
         }
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


    public void setAvatar(String avatar) {
         if (avatar == null) {
            return;
         }
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }


    public void setMark(float mark) {
        this.mark = mark;
    }

    public float getMark() {
        return mark;
    }


    public void setRecallid(int recallid) {
        this.recallid = recallid;
    }

    public int getRecallid() {
        return recallid;
    }


    public void setIsgood(boolean isgood) {
        this.isgood = isgood;
    }

    public boolean getIsgood() {
        return isgood;
    }


    public void setGoods(int goods) {
        this.goods = goods;
    }

    public int getGoods() {
        return goods;
    }


    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getComments() {
        return comments;
    }


    public void setCreatetime(String createtime) {
         if (createtime == null) {
            return;
         }
        this.createtime = createtime;
    }

    public String getCreatetime() {
        return createtime;
    }


}