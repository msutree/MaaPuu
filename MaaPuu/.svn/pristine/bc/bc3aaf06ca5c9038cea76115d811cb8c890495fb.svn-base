package com.maapuu.mereca.background.employee.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 工资明细
 */

public class SalaryBean implements Serializable {
    /**
     * wage_day_id : 1
     * amount : 10.00
     * wage_day : 2018-04-01
     * srv_list : [{"srv_name":"洗发","srv_begin_time_text":"2018-04-08 11:52","srv_charge":"0.00"},{"srv_name":"洗发","srv_begin_time_text":"2018-04-08 11:52","srv_charge":"0.00"}]
     */

    private String wage_day_id;
    private String amount;
    private String wage_day;
    private List<SrvListBean> srv_list;

    public String getWage_day_id() {
        return wage_day_id;
    }

    public void setWage_day_id(String wage_day_id) {
        this.wage_day_id = wage_day_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getWage_day() {
        return wage_day;
    }

    public void setWage_day(String wage_day) {
        this.wage_day = wage_day;
    }

    public List<SrvListBean> getSrv_list() {
        return srv_list;
    }

    public void setSrv_list(List<SrvListBean> srv_list) {
        this.srv_list = srv_list;
    }

    public static class SrvListBean {
        /**
         * srv_name : 洗发
         * srv_begin_time_text : 2018-04-08 11:52
         * srv_charge : 0.00
         */

        private String srv_name;
        private String srv_begin_time_text;
        private String srv_end_time_text;
        private String srv_charge;

        public String getSrv_name() {
            return srv_name;
        }

        public void setSrv_name(String srv_name) {
            this.srv_name = srv_name;
        }

        public String getSrv_begin_time_text() {
            return srv_begin_time_text;
        }

        public void setSrv_begin_time_text(String srv_begin_time_text) {
            this.srv_begin_time_text = srv_begin_time_text;
        }

        public String getSrv_charge() {
            return srv_charge;
        }

        public void setSrv_charge(String srv_charge) {
            this.srv_charge = srv_charge;
        }

        public String getSrv_end_time_text() {
            return srv_end_time_text;
        }

        public void setSrv_end_time_text(String srv_end_time_text) {
            this.srv_end_time_text = srv_end_time_text;
        }
    }

}
