package com.maapuu.mereca.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dell on 2018/3/26.
 */

public class EvlBean implements Serializable {
    private String evl_id;
    private String evl_level;
    private String evl_content;
    private String create_time;
    private String avatar;
    private String nick_name;
    private String is_evl_praise;
    private String praise_num;
    private String is_reply;
    private String reply_content;
    private String evl_reply_id;
    private String order_title;
    private List<ImageTextBean> detail;

    public String getEvl_id() {
        return evl_id;
    }

    public void setEvl_id(String evl_id) {
        this.evl_id = evl_id;
    }

    public String getEvl_level() {
        return evl_level;
    }

    public void setEvl_level(String evl_level) {
        this.evl_level = evl_level;
    }

    public String getEvl_content() {
        return evl_content;
    }

    public void setEvl_content(String evl_content) {
        this.evl_content = evl_content;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public List<ImageTextBean> getDetail() {
        return detail;
    }

    public void setDetail(List<ImageTextBean> detail) {
        this.detail = detail;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIs_evl_praise() {
        return is_evl_praise;
    }

    public void setIs_evl_praise(String is_evl_praise) {
        this.is_evl_praise = is_evl_praise;
    }

    public String getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(String praise_num) {
        this.praise_num = praise_num;
    }

    public String getIs_reply() {
        return is_reply;
    }

    public void setIs_reply(String is_reply) {
        this.is_reply = is_reply;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public String getEvl_reply_id() {
        return evl_reply_id;
    }

    public void setEvl_reply_id(String evl_reply_id) {
        this.evl_reply_id = evl_reply_id;
    }

    public String getOrder_title() {
        return order_title;
    }

    public void setOrder_title(String order_title) {
        this.order_title = order_title;
    }
}
