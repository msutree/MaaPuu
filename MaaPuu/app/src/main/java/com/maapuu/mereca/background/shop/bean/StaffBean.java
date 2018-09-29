package com.maapuu.mereca.background.shop.bean;

import java.io.Serializable;

/**
 * Created by dell on 2018/4/12.
 */

public class StaffBean implements Serializable {
    private String staff_id;
    private String staff_name;
    private String staff_avatar;
    private String phone;
    private String sex;
    private String age;
    private String staff_no;
    private String post_name;
    private String amount;
    private String wage_total;
    private String wage_month;
    private String uid;
    private boolean bool;

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getStaff_avatar() {
        return staff_avatar;
    }

    public void setStaff_avatar(String staff_avatar) {
        this.staff_avatar = staff_avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStaff_no() {
        return staff_no;
    }

    public void setStaff_no(String staff_no) {
        this.staff_no = staff_no;
    }

    public String getPost_name() {
        return post_name;
    }

    public void setPost_name(String post_name) {
        this.post_name = post_name;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getWage_total() {
        return wage_total;
    }

    public void setWage_total(String wage_total) {
        this.wage_total = wage_total;
    }

    public String getWage_month() {
        return wage_month;
    }

    public void setWage_month(String wage_month) {
        this.wage_month = wage_month;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
