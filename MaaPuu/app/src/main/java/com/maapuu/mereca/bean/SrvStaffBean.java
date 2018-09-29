package com.maapuu.mereca.bean;

import java.util.List;

/**
 * Created by Jia on 2018/4/9.
 */

public class SrvStaffBean {

    private List<StaffBean> free_data;
    private List<StaffBean> busy_data;

    public List<StaffBean> getFree_data() {
        return free_data;
    }

    public void setFree_data(List<StaffBean> free_data) {
        this.free_data = free_data;
    }

    public List<StaffBean> getBusy_data() {
        return busy_data;
    }

    public void setBusy_data(List<StaffBean> busy_data) {
        this.busy_data = busy_data;
    }
}
