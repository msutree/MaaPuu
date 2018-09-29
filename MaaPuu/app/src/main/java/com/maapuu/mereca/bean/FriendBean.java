package com.maapuu.mereca.bean;

import java.io.Serializable;

/**
 * Created by dell on 2018/3/27.
 */

public class FriendBean implements Serializable {
    private String friend_uid;
    private String uid;
    private String f_leter;
    private String nick_name;
    private String avatar;
    private String signature;
    private String is_friend;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFriend_uid() {
        return friend_uid;
    }

    public void setFriend_uid(String friend_uid) {
        this.friend_uid = friend_uid;
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getF_leter() {
        return f_leter;
    }

    public void setF_leter(String f_leter) {
        this.f_leter = f_leter;
    }

    public String getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(String is_friend) {
        this.is_friend = is_friend;
    }
}
