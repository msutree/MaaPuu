package com.maapuu.mereca.bean;

import java.io.Serializable;

/**
 * 服务数据bean
 * Created by Jia on 2018/4/8.
 */

public class SrvDataBean implements Serializable {
    /**
     * srv_id : 1
     * srv_name : 洗发
     * srv_duration : 10
     * staff_id : 0
     * staff_name :
     */

    private String srv_id;
    private String srv_name;
    private String srv_duration;
    private String staff_id;
    private String staff_name;

    private String appoint_srv_id;//预约服务id
    private String staff_avatar;
    private String srv_img;
    private String srv_status;
    private int can_appoint;//1可以指定，0不允许指定


    public String getSrv_id() {
        return srv_id;
    }

    public void setSrv_id(String srv_id) {
        this.srv_id = srv_id;
    }

    public String getSrv_name() {
        return srv_name;
    }

    public void setSrv_name(String srv_name) {
        this.srv_name = srv_name;
    }

    public String getSrv_duration() {
        return srv_duration;
    }

    public void setSrv_duration(String srv_duration) {
        this.srv_duration = srv_duration;
    }

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

    public String getAppoint_srv_id() {
        return appoint_srv_id;
    }

    public void setAppoint_srv_id(String appoint_srv_id) {
        this.appoint_srv_id = appoint_srv_id;
    }

    public String getStaff_avatar() {
        return staff_avatar;
    }

    public void setStaff_avatar(String staff_avatar) {
        this.staff_avatar = staff_avatar;
    }

    public String getSrv_img() {
        return srv_img;
    }

    public void setSrv_img(String srv_img) {
        this.srv_img = srv_img;
    }

    public String getSrv_status() {
        return srv_status;
    }

    public void setSrv_status(String srv_status) {
        this.srv_status = srv_status;
    }

    public int getCan_appoint() {
        return can_appoint;
    }

    public void setCan_appoint(int can_appoint) {
        this.can_appoint = can_appoint;
    }

}
