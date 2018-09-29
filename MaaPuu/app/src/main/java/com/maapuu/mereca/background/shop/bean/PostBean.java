package com.maapuu.mereca.background.shop.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.io.Serializable;

/**
 * 岗位bean
 * Created by Jia on 2018/4/12.
 */

public class PostBean implements Serializable,IPickerViewData{

    /**
     * post_id : 1
     * post_name : 高级发型师
     * wage_base : 2000.00
     */

    private String post_id;
    private String post_name;
    private String wage_base;

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPost_name() {
        return post_name;
    }

    public void setPost_name(String post_name) {
        this.post_name = post_name;
    }

    public String getWage_base() {
        return wage_base;
    }

    public void setWage_base(String wage_base) {
        this.wage_base = wage_base;
    }

    @Override
    public String getPickerViewText() {
        return post_name;
    }
}
