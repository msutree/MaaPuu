package com.maapuu.mereca.bean;

import java.io.Serializable;

/**
 * Created by dell on 2018/3/30.
 */

public class ArticlerBean implements Serializable {
    private String uid;
    private String avatar;
    private String nick_name;
    private String signature;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
