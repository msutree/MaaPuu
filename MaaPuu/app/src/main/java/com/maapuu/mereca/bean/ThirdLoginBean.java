package com.maapuu.mereca.bean;

import java.io.Serializable;

/**
 * Created by dell on 2018/5/17.
 */

public class ThirdLoginBean implements Serializable {
    private int login_type;
    private String third_userid;
    private String nick_name;
    private String avatar;
    private String sex;
    private String device_tokens;

    public int getLogin_type() {
        return login_type;
    }

    public void setLogin_type(int login_type) {
        this.login_type = login_type;
    }

    public String getThird_userid() {
        return third_userid;
    }

    public void setThird_userid(String third_userid) {
        this.third_userid = third_userid;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDevice_tokens() {
        return device_tokens;
    }

    public void setDevice_tokens(String device_tokens) {
        this.device_tokens = device_tokens;
    }
}
