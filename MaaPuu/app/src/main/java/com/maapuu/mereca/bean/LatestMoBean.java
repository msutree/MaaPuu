package com.maapuu.mereca.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dell on 2018/3/27.
 */

public class LatestMoBean implements Serializable {
    private String uid;
    private String mo_id;
    private String time_text;
    private String avatar;
    private String nick_name;
    private int is_praise;
    private String content;
    private String moment_content;
    private String create_time;
    private List<ImageTextBean> detail;
    private List<PraiseBean> praise_users;
    private List<MoCommentBean> comments;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMo_id() {
        return mo_id;
    }

    public void setMo_id(String mo_id) {
        this.mo_id = mo_id;
    }

    public String getTime_text() {
        return time_text;
    }

    public void setTime_text(String time_text) {
        this.time_text = time_text;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getIs_praise() {
        return is_praise;
    }

    public void setIs_praise(int is_praise) {
        this.is_praise = is_praise;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMoment_content() {
        return moment_content;
    }

    public void setMoment_content(String moment_content) {
        this.moment_content = moment_content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public List<ImageTextBean> getDetail() {
        return detail;
    }

    public void setDetail(List<ImageTextBean> detail) {
        this.detail = detail;
    }

    public List<PraiseBean> getPraise_users() {
        return praise_users;
    }

    public void setPraise_users(List<PraiseBean> praise_users) {
        this.praise_users = praise_users;
    }

    public List<MoCommentBean> getComments() {
        return comments;
    }

    public void setComments(List<MoCommentBean> comments) {
        this.comments = comments;
    }
}
