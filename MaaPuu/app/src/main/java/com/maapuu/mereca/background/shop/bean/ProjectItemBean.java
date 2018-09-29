package com.maapuu.mereca.background.shop.bean;

import java.io.Serializable;

/**
 * Created by dell on 2018/4/13.
 */

public class ProjectItemBean implements Serializable {
    private String item_id;
    private String item_name;
    private int shelf_status;
    private String promotion_end_time;
    private int is_top;
    private String pack_type;
    private String end_time;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getShelf_status() {
        return shelf_status;
    }

    public void setShelf_status(int shelf_status) {
        this.shelf_status = shelf_status;
    }

    public String getPromotion_end_time() {
        return promotion_end_time;
    }

    public void setPromotion_end_time(String promotion_end_time) {
        this.promotion_end_time = promotion_end_time;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public String getPack_type() {
        return pack_type;
    }

    public void setPack_type(String pack_type) {
        this.pack_type = pack_type;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
