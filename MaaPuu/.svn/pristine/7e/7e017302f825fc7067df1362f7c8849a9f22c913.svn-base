package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.dialog.ChooseShopFilter;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ShopBean;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 报告统计
 * Created by Jia on 2018/3/10.
 */

public class ReportCountActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_shop_name)
    TextView txtShopName;
    @BindView(R.id.ao_choose_shop_lt)
    LinearLayout chooseShopLt;

    private List<ShopBean> shopList;
    private String shop_id;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_report_count);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("报告统计");
    }

    @Override
    public void initData() {
//        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_select_shop_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid")),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            shopList = FastJsonTools.getPersons(object.optString("result"), ShopBean.class);
                            if(shopList != null && shopList.size() > 0){
                                txtShopName.setText(shopList.get(0).getShop_name());
                                shop_id = shopList.get(0).getShop_id();
                            }
                        }
                    } else {
                        ToastUtil.show(mContext, object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_ERROR:
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext, result_e);
                break;
        }
        return false;
    }

    @Override
    @OnClick({R.id.txt_left,R.id.ao_choose_shop_lt,R.id.em_wage_detail_rt,R.id.rl_shop_report,R.id.rl_employee_report,R.id.rl_employee_wages})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.ao_choose_shop_lt:
                ChooseShopFilter chooseShopFilter = new ChooseShopFilter(mContext, shopList, new ChooseShopFilter.ChooseCall() {
                    @Override
                    public void onChoose(ShopBean item) {
                        txtShopName.setText(item.getShop_name());
                        shop_id = item.getShop_id();
                    }
                });
                chooseShopFilter.createPopup();
                chooseShopFilter.showAsDropDown(chooseShopLt);
                break;
            case R.id.em_wage_detail_rt:
                it = new Intent(mContext,DataAnalysisActivity.class);
                it.putExtra("shop_id",shop_id);
                startActivity(it);
                break;
            case R.id.rl_shop_report:
                it = new Intent(mContext,ShopReportActivity.class);
                it.putExtra("shop_id",shop_id);
                startActivity(it);
                break;
            case R.id.rl_employee_report:
                it = new Intent(mContext,ShopEmployeeReportActivity.class);
                it.putExtra("shop_id",shop_id);
                startActivity(it);
                break;
            case R.id.rl_employee_wages:
                it = new Intent(mContext,EmployeeWagesActivity.class);
                it.putExtra("shop_id",shop_id);
                startActivity(it);
                break;
        }
    }

}
