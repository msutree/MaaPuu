package com.maapuu.mereca.bean;

import java.io.Serializable;

/**
 * Created by dell on 2018/3/29.
 */

public class MoCommentBean implements Serializable {
    private String mmt_comment_id;
    private String mo_id;
    private String comment_uid;
    private String cmm_nick_name;
    private String content;
    private String reply_uid;
    private String rp_nick_name;

    public String getMmt_comment_id() {
        return mmt_comment_id;
    }

    public void setMmt_comment_id(String mmt_comment_id) {
        this.mmt_comment_id = mmt_comment_id;
    }

    public String getMo_id() {
        return mo_id;
    }

    public void setMo_id(String mo_id) {
        this.mo_id = mo_id;
    }

    public String getComment_uid() {
        return comment_uid;
    }

    public void setComment_uid(String comment_uid) {
        this.comment_uid = comment_uid;
    }

    public String getCmm_nick_name() {
        return cmm_nick_name;
    }

    public void setCmm_nick_name(String cmm_nick_name) {
        this.cmm_nick_name = cmm_nick_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply_uid() {
        return reply_uid;
    }

    public void setReply_uid(String reply_uid) {
        this.reply_uid = reply_uid;
    }

    public String getRp_nick_name() {
        return rp_nick_name;
    }

    public void setRp_nick_name(String rp_nick_name) {
        this.rp_nick_name = rp_nick_name;
    }
}
