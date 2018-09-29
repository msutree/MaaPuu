package com.maapuu.mereca.background.shop.bean;

import java.io.Serializable;

/**
 * Created by dell on 2018/8/6.
 */

public class CutBean implements Serializable {
    private String fullcut_amount;
    private String cut_amount;

    public String getFullcut_amount() {
        return fullcut_amount;
    }

    public void setFullcut_amount(String fullcut_amount) {
        this.fullcut_amount = fullcut_amount;
    }

    public String getCut_amount() {
        return cut_amount;
    }

    public void setCut_amount(String cut_amount) {
        this.cut_amount = cut_amount;
    }
}
