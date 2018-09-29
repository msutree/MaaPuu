package com.maapuu.mereca.background.shop.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.background.shop.bean.StaffBean;
import com.maapuu.mereca.background.shop.dialog.ChooseShopFilter;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ShopBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.view.MyListView;
import com.maapuu.mereca.view.RoundProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/25.
 */

public class ShopEmployeeReportActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_little_yes)
    TextView txtLittleYes;
    @BindView(R.id.txt_little_no)
    TextView txtLittleNo;
    @BindView(R.id.txt_big_yes)
    TextView txtBigYes;
    @BindView(R.id.txt_big_no)
    TextView txtBigNo;
    @BindView(R.id.txt_project_yes)
    TextView txtProjectYes;
    @BindView(R.id.txt_project_no)
    TextView txtProjectNo;
    @BindView(R.id.txt_ldyj)
    TextView txtLdyj;
    @BindView(R.id.txt_hyyj)
    TextView txtHyyj;
    @BindView(R.id.txt_skyj)
    TextView txtSkyj;
    @BindView(R.id.txt_spyj)
    TextView txtSpyj;
    @BindView(R.id.txt_hdyj)
    TextView txtHdyj;
    @BindView(R.id.txt_kkyj)
    TextView txtKkyj;
    @BindView(R.id.txt_ckyj)
    TextView txtCkyj;
    @BindView(R.id.txt_cikyj)
    TextView txtCikyj;
    @BindView(R.id.txt_xmyj)
    TextView txtXmyj;
    @BindView(R.id.txt_xmtc)
    TextView txtXmtc;
    @BindView(R.id.txt_zhiding_num)
    TextView txtZhidingNum;
    @BindView(R.id.txt_month_num)
    TextView txtMonthNum;
    @BindView(R.id.progress_bar)
    RoundProgressBar progressBar;
    @BindView(R.id.list_view)
    MyListView listView;
    @BindView(R.id.txt_shop_name)
    TextView txtShopName;
    @BindView(R.id.ao_choose_shop_lt)
    LinearLayout chooseShopLt;

    private String shop_id;
    private List<StaffBean> list;
    private List<ShopBean> shopList;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shop_employee_report);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("员工报表");
//        shop_id = getIntent().getStringExtra("shop_id");
        list = new ArrayList<>();
        shopList = new ArrayList<>();
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_5, UrlUtils.s_select_shop_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),0),false);
    }

    private void initReport(){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.shop_staff_report_data_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),shop_id),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        txtLittleYes.setText(resultObj.optString("small_project_assign_num"));
                        txtLittleNo.setText(resultObj.optString("small_project_notassign_num"));
                        txtBigYes.setText(resultObj.optString("big_project_assign_num"));
                        txtBigNo.setText(resultObj.optString("big_project_notassign_num"));
                        txtProjectYes.setText(resultObj.optString("custom_project_assign_num"));
                        txtProjectNo.setText(resultObj.optString("custom_project_notassign_num"));
                        txtLdyj.setText("¥ "+resultObj.optString("ld_income"));
                        txtHyyj.setText("¥ "+resultObj.optString("member_income"));
                        txtSkyj.setText("¥ "+resultObj.optString("sanke_income"));
                        txtSpyj.setText("¥ "+resultObj.optString("commodity_income"));
                        txtHdyj.setText("¥ "+resultObj.optString("action_income"));
                        txtKkyj.setText("¥ "+resultObj.optString("kaika_income"));
                        txtCkyj.setText("¥ "+resultObj.optString("chongka_income"));
                        txtCikyj.setText("¥ "+resultObj.optString("cika_income"));
                        txtXmyj.setText("¥ "+resultObj.optString("project_income"));
                        txtXmtc.setText("¥ "+resultObj.optString("commission_income"));
                        txtZhidingNum.setText(resultObj.optString("assign_num"));
                        txtMonthNum.setText(resultObj.optString("assign_total_num"));
                        progressBar.setProgress(new Double(resultObj.optString("assign_percent")).intValue());
                        if(resultObj.has("staff_income") && !(resultObj.opt("staff_income") instanceof Boolean)){
                            list = FastJsonTools.getPersons(resultObj.optString("staff_income"),StaffBean.class);
                        }
                        setAdapter();
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_5:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            shopList = FastJsonTools.getPersons(object.optString("result"), ShopBean.class);
                            if(shopList != null && shopList.size() > 0){
                                txtShopName.setText(shopList.get(0).getShop_name());
                                shop_id = shopList.get(0).getShop_id();
                                initReport();
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
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
    }

    private void setAdapter() {
        listView.setAdapter(new QuickAdapter<StaffBean>(mContext,R.layout.layout_shop_employee_report_item1,list) {
            @Override
            protected void convert(BaseAdapterHelper helper, StaffBean item) {
                int position = helper.getPosition();
                switch (position){
                    case 0:
                        helper.setVisible(R.id.iv_range,true);
                        helper.setVisible(R.id.txt_range,false);
                        helper.setImageResource(R.id.iv_range,R.mipmap.diyiming);
                        break;
                    case 1:
                        helper.setVisible(R.id.iv_range,true);
                        helper.setVisible(R.id.txt_range,false);
                        helper.setImageResource(R.id.iv_range,R.mipmap.dierming);
                        break;
                    case 2:
                        helper.setVisible(R.id.iv_range,true);
                        helper.setVisible(R.id.txt_range,false);
                        helper.setImageResource(R.id.iv_range,R.mipmap.disanming);
                        break;
                    default:
                        helper.setVisible(R.id.iv_range,false);
                        helper.setVisible(R.id.txt_range,true);
                        helper.setText(R.id.txt_range,String.valueOf(position+1));
                        break;
                }
                helper.setSimpViewImageUri(R.id.image, Uri.parse(item.getStaff_avatar()));
                helper.setText(R.id.txt_name,item.getStaff_name());
                helper.setText(R.id.txt_price,"¥ "+item.getAmount());
            }
        });
    }

    @Override
    @OnClick({R.id.txt_left,R.id.ao_choose_shop_lt})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.ao_choose_shop_lt:
                ChooseShopFilter chooseShopFilter = new ChooseShopFilter(mContext, shopList, new ChooseShopFilter.ChooseCall() {
                    @Override
                    public void onChoose(ShopBean item) {
                        txtShopName.setText(item.getShop_name());
                        shop_id = item.getShop_id();
                        initReport();
                    }
                });
                chooseShopFilter.createPopup();
                chooseShopFilter.showAsDropDown(chooseShopLt);
                break;
        }
    }
}
