package com.maapuu.mereca.background.shop.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * 物流公司bean
 * Created by Jia on 2018/4/25.
 */

public class LogisticsCompanyBean implements IPickerViewData {

    /**
     * logistics_company_id : 2
     * company_name : 申通快递
     */

    private String logistics_company_id;
    private String company_name;

    public String getLogistics_company_id() {
        return logistics_company_id;
    }

    public void setLogistics_company_id(String logistics_company_id) {
        this.logistics_company_id = logistics_company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    @Override
    public String getPickerViewText() {
        return company_name;
    }
}
