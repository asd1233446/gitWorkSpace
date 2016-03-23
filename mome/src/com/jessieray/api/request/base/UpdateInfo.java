package com.jessieray.api.request.base;

import java.io.Serializable;

/**
 * This file is auto generated, do not modify it by hand
 *
 */

public class UpdateInfo implements Serializable{
    /**
     * 新版本号
     */
    private String new_version;

    /**
     * 更新日志
     */
    private String update_log;

    /**
     * 更新策略
     */
    private String update_rule;

    /**
     * 下载地址
     */
    private String download_url;



    public void setNew_version(String new_version) {
         if (new_version == null) {
            return;
         }
        this.new_version = new_version;
    }

    public String getNew_version() {
        return new_version;
    }


    public void setUpdate_log(String update_log) {
         if (update_log == null) {
            return;
         }
        this.update_log = update_log;
    }

    public String getUpdate_log() {
        return update_log;
    }


    public void setUpdate_rule(String update_rule) {
         if (update_rule == null) {
            return;
         }
        this.update_rule = update_rule;
    }

    public String getUpdate_rule() {
        return update_rule;
    }


    public void setDownload_url(String download_url) {
         if (download_url == null) {
            return;
         }
        this.download_url = download_url;
    }

    public String getDownload_url() {
        return download_url;
    }


}