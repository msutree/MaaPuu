package com.maapuu.mereca.bean;

import java.io.Serializable;

/**
 * Created by dell on 2018/3/24.
 */

public class TeamBean implements Serializable {
    private String staff_id;
    private String uid;
    private String staff_name;
    private String staff_intro;
    private String staff_avatar;
    private String fans_num;
    private String appoint_num;
    private String evl_level;
    private String evl_num;
    private String works_num;
    private String distance;
    private String city;

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getStaff_intro() {
        return staff_intro;
    }

    public void setStaff_intro(String staff_intro) {
        this.staff_intro = staff_intro;
    }

    public String getStaff_avatar() {
        return staff_avatar;
    }

    public void setStaff_avatar(String staff_avatar) {
        this.staff_avatar = staff_avatar;
    }

    public String getFans_num() {
        return fans_num;
    }

    public void setFans_num(String fans_num) {
        this.fans_num = fans_num;
    }

    public String getAppoint_num() {
        return appoint_num;
    }

    public void setAppoint_num(String appoint_num) {
        this.appoint_num = appoint_num;
    }

    public String getEvl_level() {
        return evl_level;
    }

    public void setEvl_level(String evl_level) {
        this.evl_level = evl_level;
    }

    public String getEvl_num() {
        return evl_num;
    }

    public void setEvl_num(String evl_num) {
        this.evl_num = evl_num;
    }

    public String getWorks_num() {
        return works_num;
    }

    public void setWorks_num(String works_num) {
        this.works_num = works_num;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
