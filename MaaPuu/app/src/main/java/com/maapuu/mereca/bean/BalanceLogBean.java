package com.maapuu.mereca.bean;

import java.io.Serializable;

/**
 * Created by dell on 2018/4/17.
 */

public class BalanceLogBean implements Serializable {
    private String balance_log_id;
    private String amount;
    private String balance;
    private String create_time;
    private String business_text;

    public String getBalance_log_id() {
        return balance_log_id;
    }

    public void setBalance_log_id(String balance_log_id) {
        this.balance_log_id = balance_log_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getBusiness_text() {
        return business_text;
    }

    public void setBusiness_text(String business_text) {
        this.business_text = business_text;
    }
}
