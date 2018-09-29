package com.maapuu.mereca.bean;

/**
 * 手机联系人
 * Created by Jia on 2018/5/18.
 */

public class PhoneContactBean {

    /**
     * phone : 18812345678
     * avatar : http://beauty.whhxrc.com/public/upload/noavatar_middle.jpg
     * nick_name : 18812345678
     * user_status : 3   用户状态：1已经注册，可以直接添加；2已经注册，已经是好友(显示已添加，不能做任何操作)；3未注册，可以邀请注册
     */

    private String phone;
    private String avatar;
    private String nick_name;
    private int user_status;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }
}
