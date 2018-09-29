package com.maapuu.mereca.background.shop.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.background.shop.bean.shopreport.BusinessPointBean;
import com.maapuu.mereca.background.shop.bean.shopreport.CardPointBean;
import com.maapuu.mereca.background.shop.bean.shopreport.CommodityPointBean;
import com.maapuu.mereca.background.shop.bean.shopreport.CustomPointBean;
import com.maapuu.mereca.background.shop.bean.shopreport.ProjectPointBean;
import com.maapuu.mereca.background.shop.bean.shopreport.ShopReportBean;
import com.maapuu.mereca.background.shop.dialog.ChooseShopFilter;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ShopBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LineChartManager1;
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

public class ShopReportActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_yyyj_total)
    TextView txtYyyjTotal;
    @BindView(R.id.line_chart)
    LineChart mLineChart;
    @BindView(R.id.txt_xmyj_total)
    TextView txtXmyjTotal;
    @BindView(R.id.list_view_project)
    MyListView listViewProject;
    @BindView(R.id.txt_spyj_total)
    TextView txtSpyjTotal;
    @BindView(R.id.list_view_goods)
    MyListView listViewGoods;
    @BindView(R.id.txt_hykyj_total)
    TextView txtHykyjTotal;
    @BindView(R.id.list_view_card)
    MyListView listViewCard;
    @BindView(R.id.txt_hdyj_total)
    TextView txtHdyjTotal;
    @BindView(R.id.txt_rcsz_total)
    TextView txtRcszTotal;
    @BindView(R.id.txt_project_income)
    TextView txtProjectIncome;
    @BindView(R.id.txt_goods_income)
    TextView txtGoodsIncome;
    @BindView(R.id.txt_outcome)
    TextView txtOutcome;
    @BindView(R.id.txt_xmks_total)
    TextView txtXmksTotal;
    @BindView(R.id.progress_bar1)
    RoundProgressBar progressBar1;
    @BindView(R.id.txt_male_num)
    TextView txtMaleNum;
    @BindView(R.id.progress_bar2)
    RoundProgressBar progressBar2;
    @BindView(R.id.txt_female_num)
    TextView txtFemaleNum;
    @BindView(R.id.progress_bar3)
    RoundProgressBar progressBar3;
    @BindView(R.id.txt_tourist_num)
    TextView txtTouristNum;
    @BindView(R.id.txt_spks)
    TextView txtSpks;
    @BindView(R.id.txt_hykkl)
    TextView txtHykkl;
    @BindView(R.id.txt_hdkl)
    TextView txtHdkl;
    @BindView(R.id.txt_shop_name)
    TextView txtShopName;
    @BindView(R.id.ao_choose_shop_lt)
    LinearLayout chooseShopLt;

    private LineChartManager1 lineChartManager;
    private ShopReportBean reportBean;
    private List<Integer> colours;
    private String shop_id;
    private List<ShopBean> shopList;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shop_report);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("店铺报表");
        lineChartManager = new LineChartManager1(mContext,mLineChart);
        colours = new ArrayList<>();
        colours.add(Color.parseColor("#F5524B"));
        shopList = new ArrayList<>();
//        shop_id = getIntent().getStringExtra("shop_id");
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_5, UrlUtils.s_select_shop_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),0),false);
    }

    private void initReport(){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.inshop_report_data_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),shop_id),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(!StringUtils.isEmpty(object.optString("result"))){
                            reportBean = FastJsonTools.getPerson(object.optString("result"),ShopReportBean.class);
                            txtYyyjTotal.setText("¥ "+reportBean.getBusiness_total().getIncome());
                            txtXmyjTotal.setText("¥ "+reportBean.getProject_income().getTotal_income());
                            txtSpyjTotal.setText("¥ "+reportBean.getCommodity_income().getTotal_income());
                            txtHykyjTotal.setText("¥ "+reportBean.getCard_income().getTotal_income());
                            txtHdyjTotal.setText("¥ "+reportBean.getCustome_action_amount());
                            txtRcszTotal.setText("¥ "+reportBean.getCommon_amount().getTotal_amount());
                            txtProjectIncome.setText("+ "+reportBean.getCommon_amount().getProject_income());
                            txtGoodsIncome.setText("+ "+reportBean.getCommon_amount().getCommodity_income());
                            txtOutcome.setText(reportBean.getCommon_amount().getCash_amount());
                            txtXmksTotal.setText(reportBean.getCustom_project().getTotal_num()+"人");
                            if(reportBean.getCustom_project().getList_total() != null && reportBean.getCustom_project().getList_total().size() >= 3){
                                CustomPointBean bean1 = null;//男
                                CustomPointBean bean2 = null;//女
                                CustomPointBean bean3 = null;//散客
                                for (int i = 0; i < reportBean.getCustom_project().getList_total().size(); i++){
                                    if(reportBean.getCustom_project().getList_total().get(i).getSex().equals("1")){
                                        bean1 = reportBean.getCustom_project().getList_total().get(i);
                                        continue;
                                    }
                                    if(reportBean.getCustom_project().getList_total().get(i).getSex().equals("2")){
                                        bean2 = reportBean.getCustom_project().getList_total().get(i);
                                        continue;
                                    }
                                    if(reportBean.getCustom_project().getList_total().get(i).getSex().equals("0")){
                                        bean3 = reportBean.getCustom_project().getList_total().get(i);
                                    }
                                }
                                progressBar1.setProgress(new Double(bean1.getPercent()).intValue());
                                progressBar2.setProgress(new Double(bean2.getPercent()).intValue());
                                progressBar3.setProgress(new Double(bean3.getPercent()).intValue());
                                txtMaleNum.setText(bean1.getNum());
                                txtFemaleNum.setText(bean2.getNum());
                                txtTouristNum.setText(bean3.getNum());
                            }
                            txtSpks.setText(reportBean.getCustome_commodity_num()+"人");
                            txtHykkl.setText(reportBean.getCustome_member_num()+"人");
                            txtHdkl.setText(reportBean.getCustome_action_num()+"人");

                            setChart(reportBean.getBusiness_total().getMonth_income());
                            setAdapter();
                        }
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

    private void setChart(List<BusinessPointBean> list) {
        //设置x轴的数据
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            xValues.add(list.get(i).getDate());
        }
        //设置y轴的数据()
        List<List<Float>> yValues = new ArrayList<>();
        //第一条线
        List<Float> yValue = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            yValue.add(Float.parseFloat(list.get(j).getAmount()));
        }
        yValues.add(yValue);

        if(xValues.size() > 0 && yValues.size() > 0){
            mLineChart.setVisibility(View.VISIBLE);
            lineChartManager.showLineChart(xValues, yValues, colours);
            mLineChart.invalidate();
        }else {
            mLineChart.setVisibility(View.INVISIBLE);
        }
    }

    private void setAdapter(){
        listViewProject.setAdapter(new QuickAdapter<ProjectPointBean>(mContext,R.layout.layout_shop_report_item,reportBean.getProject_income().getList_income()) {
            @Override
            protected void convert(BaseAdapterHelper helper, ProjectPointBean item) {
                helper.setText(R.id.txt_name,item.getCatalog_name());
                helper.setText(R.id.txt_price,item.getAmount());
                ProgressBar progressBar = helper.getView(R.id.progress_bar);
                progressBar.setProgressDrawable(DisplayUtil.setProgressColor("#"+item.getColor()));
                progressBar.setProgress(new Double(item.getPercent()).intValue());
            }
        });
        listViewGoods.setAdapter(new QuickAdapter<CommodityPointBean>(mContext,R.layout.layout_shop_report_item1,reportBean.getCommodity_income().getList_income()) {
            @Override
            protected void convert(BaseAdapterHelper helper, CommodityPointBean item) {
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
                helper.setText(R.id.txt_name,item.getItem_name());
                helper.setText(R.id.txt_price,item.getAmount());
            }
        });
        listViewCard.setAdapter(new QuickAdapter<CardPointBean>(mContext,R.layout.layout_shop_report_item,reportBean.getCard_income().getList_income()) {
            @Override
            protected void convert(BaseAdapterHelper helper, CardPointBean item) {
                int position = helper.getPosition();
                ProgressBar progressBar = helper.getView(R.id.progress_bar);
                progressBar.setProgress(new Double(item.getPercent()).intValue());
                helper.setText(R.id.txt_price,item.getAmount());
                switch (position){
                    case 0:
                        helper.setText(R.id.txt_name,"开卡");
                        progressBar.setProgressDrawable(DisplayUtil.setProgressColor("#ED708D"));
                        break;
                    case 1:
                        helper.setText(R.id.txt_name,"充值卡");
                        progressBar.setProgressDrawable(DisplayUtil.setProgressColor("#EAC90E"));
                        break;
                    case 2:
                        helper.setText(R.id.txt_name,"次卡");
                        progressBar.setProgressDrawable(DisplayUtil.setProgressColor("#50C854"));
                        break;
                }
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
