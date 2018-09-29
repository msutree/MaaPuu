package com.maapuu.mereca.background.employee.bean;

import java.util.List;

/**
 * 总收入
 * Created by Jia on 2018/4/25.
 */

public class TotalIncomeBean {

    /**
     * balance : 25000.00
     * wage_month : {"wage_total":"1500.00","last_day_amount":"50.00","last_week_amount":"160.00"}
     * balance_list : [{"amount":"200.00","balance":"1200.00","create_time_text":"2018-04-02 17:10","oid":"11"},{"amount":"100.00","balance":"13000.00","create_time_text":"2018-04-02 17:10","oid":"11"},{"amount":"200.00","balance":"1500.00","create_time_text":"2018-04-01 17:10","oid":"11"},{"amount":"100.00","balance":"16000.00","create_time_text":"2018-04-01 17:10","oid":"11"}]
     */

    private String balance;
    private WageMonthBean wage_month;
    private List<BalanceListBean> balance_list;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public WageMonthBean getWage_month() {
        return wage_month;
    }

    public void setWage_month(WageMonthBean wage_month) {
        this.wage_month = wage_month;
    }

    public List<BalanceListBean> getBalance_list() {
        return balance_list;
    }

    public void setBalance_list(List<BalanceListBean> balance_list) {
        this.balance_list = balance_list;
    }

    public static class WageMonthBean {
        /**
         * wage_total : 1500.00
         * last_day_amount : 50.00
         * last_week_amount : 160.00
         */

        private String wage_total;
        private String last_day_amount;
        private String last_week_amount;

        public String getWage_total() {
            return wage_total;
        }

        public void setWage_total(String wage_total) {
            this.wage_total = wage_total;
        }

        public String getLast_day_amount() {
            return last_day_amount;
        }

        public void setLast_day_amount(String last_day_amount) {
            this.last_day_amount = last_day_amount;
        }

        public String getLast_week_amount() {
            return last_week_amount;
        }

        public void setLast_week_amount(String last_week_amount) {
            this.last_week_amount = last_week_amount;
        }
    }

    public static class BalanceListBean {
        /**
         * amount : 200.00
         * balance : 1200.00
         * create_time_text : 2018-04-02 17:10
         * oid : 11
         */

        private String amount;
        private String balance;
        private String create_time_text;
        private String oid;

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

        public String getCreate_time_text() {
            return create_time_text;
        }

        public void setCreate_time_text(String create_time_text) {
            this.create_time_text = create_time_text;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }
    }
}
