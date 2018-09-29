package com.maapuu.mereca.background.shop.activity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.background.shop.bean.report.AchieveChildBean;
import com.maapuu.mereca.background.shop.bean.report.CustomChildBean;
import com.maapuu.mereca.background.shop.bean.report.IncomeChildBean;
import com.maapuu.mereca.background.shop.bean.report.IncomePointBean;
import com.maapuu.mereca.background.shop.bean.report.IncomeStaffBean;
import com.maapuu.mereca.background.shop.bean.report.MonthBean;
import com.maapuu.mereca.background.shop.bean.report.ReportBean;
import com.maapuu.mereca.background.shop.dialog.ChooseShopFilter;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ShopBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.BarChartManager;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LineChartManager;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.view.FloatTextProgressBar;
import com.maapuu.mereca.view.MyGridView;
import com.maapuu.mereca.view.MyListView;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.willy.ratingbar.ScaleRatingBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/25.
 */

public class DataAnalysisActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.txt_month_turnover)
    TextView txtMonthTurnover;
    @BindView(R.id.progress_bar)
    FloatTextProgressBar progressBar;
    @BindView(R.id.txt_target_turnover)
    TextView txtTargetTurnover;
    @BindView(R.id.txt_rest_days)
    TextView txtRestDays;
    @BindView(R.id.txt_rest_turnover)
    TextView txtRestTurnover;
    @BindView(R.id.txt_day_turnover)
    TextView txtDayTurnover;
    @BindView(R.id.txt_today_label)
    TextView txtTodayLabel;
    @BindView(R.id.txt_today_income)
    TextView txtTodayIncome;
    @BindView(R.id.txt_target_percent)
    TextView txtTargetPercent;
    @BindView(R.id.txt_income_week)
    TextView txtIncomeWeek;
    @BindView(R.id.txt_income_month)
    TextView txtIncomeMonth;
    @BindView(R.id.txt_income_year)
    TextView txtIncomeYear;
    @BindView(R.id.pie_chart)
    PieChart pieChart;
    @BindView(R.id.grid_view)
    MyGridView gridView;
    @BindView(R.id.list_view)
    MyListView listView;
    @BindView(R.id.list_view_emploee)
    MyListView listViewEmploee;
    @BindView(R.id.txt_achievement_week)
    TextView txtAchievementWeek;
    @BindView(R.id.txt_achievement_month)
    TextView txtAchievementMonth;
    @BindView(R.id.txt_achievement_year)
    TextView txtAchievementYear;
    @BindView(R.id.line_chart)
    LineChart mLineChart;
    @BindView(R.id.txt_cash_performance)
    TextView txtCashPerformance;
    @BindView(R.id.txt_work_performance)
    TextView txtWorkPerformance;
    @BindView(R.id.txt_takeout_performance)
    TextView txtTakeoutPerformance;
    @BindView(R.id.txt_customer_week)
    TextView txtCutsomerWeek;
    @BindView(R.id.txt_customer_month)
    TextView txtCutsomerMonth;
    @BindView(R.id.txt_customer_year)
    TextView txtCutsomerYear;
    @BindView(R.id.txt_men_percent)
    TextView txtMenPercent;
    @BindView(R.id.txt_women_percent)
    TextView txtWomenPercent;
    @BindView(R.id.ratingbar_nan)
    ScaleRatingBar ratingBarNan;
    @BindView(R.id.ratingbar_nv)
    ScaleRatingBar ratingBarNv;
    @BindView(R.id.bar_chart)
    BarChart mBarChart;
    @BindView(R.id.txt_shop_name)
    TextView txtShopName;
    @BindView(R.id.ao_choose_shop_lt)
    LinearLayout chooseShopLt;

    private ReportBean report;
    private LineChartManager lineChartManager;
    private BarChartManager barChartManager;
    private List<Integer> colours;
    private String shop_id;
    private List<ShopBean> shopList;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_data_analysis);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("数据分析");
        txtRight.setText("设置目标");
        txtRight.setVisibility(View.VISIBLE);
        txtIncomeWeek.setSelected(true);txtAchievementWeek.setSelected(true);txtCutsomerWeek.setSelected(true);
        colours = new ArrayList<>();
        colours.add(Color.parseColor("#09E092"));
        colours.add(Color.parseColor("#01FFFF"));
        colours.add(Color.parseColor("#FFFD01"));
        initChart();
        shop_id = getIntent().getStringExtra("shop_id");
    }
    //图表初始化
    private void initChart() {
        //设置饼图上面的文字是否显示，不显示的话只显示百分比
        pieChart.setDrawEntryLabels(false);
        //设置饼图每一块的说明的字体颜色
        pieChart.setEntryLabelColor(Color.RED);
        //设置饼图每一块说明的字体大小
        pieChart.setEntryLabelTextSize(0f);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(0, 0, 0, 0);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(getResources().getColor(R.color.background));
        pieChart.setTransparentCircleRadius(0);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(58f);
        pieChart.setDrawCenterText(false);
        pieChart.setRotationAngle(0);
        pieChart.setDrawMarkerViews(false);
        // 触摸旋转
        pieChart.setRotationEnabled(false);
        pieChart.setHighlightPerTapEnabled(false);
        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setEnabled(false);

        lineChartManager = new LineChartManager(mContext,mLineChart);
        barChartManager = new BarChartManager(mContext,mBarChart);
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_5, UrlUtils.s_select_shop_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),0),false);
    }

    public void refresh() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.shop_report_data_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),"1"),true);
    }

    public String getShop_id() {
        return shop_id;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(!StringUtils.isEmpty(object.optString("result"))){
                            report = FastJsonTools.getPerson(object.optString("result"),ReportBean.class);
                            setHead(report.getMonth());
                            setPieChart(report.getIncome().getWeek());
                            setBarChart(report.getCustom().getWeek());
                            setLineChart(report.getAchieve().getWeek());
                        }
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_2:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext,"设置成功");
                        initData();
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
                                refresh();
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
    //头部
    private void setHead(MonthBean month) {
        DisplayUtil.changeTextSize(txtMonthTurnover,month.getMonth_wage()+" 元");
//        txtMonthTurnover.setText(month.getMonth_wage()+"元");
        txtTargetTurnover.setText("CNY "+month.getTarget_wage());
        progressBar.setProgress(new Double(month.getMonth_wage_percent()).intValue());
        DisplayUtil.changeTextSize(txtRestDays, month.getLess_day()+" 天");
        DisplayUtil.changeTextSize(txtRestTurnover, month.getLess_wage()+" 元");
        DisplayUtil.changeTextSize(txtDayTurnover, month.getAvg_day_wage()+" 元");
//        txtRestDays.setText(month.getLess_day()+"天");
//        txtRestTurnover.setText(month.getLess_wage()+"元");
//        txtDayTurnover.setText(month.getAvg_day_wage()+"元");
        txtTodayIncome.setText("当前已收入"+month.getToday_wage()+"元");
        txtTodayLabel.setText(month.getToday_text());
        txtTargetPercent.setText(month.getToday_wage_percent()+"%");
    }
    //饼状图
    private void setPieChart(IncomeChildBean bean) {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        if(bean.getCatalog()!= null && bean.getCatalog().size() > 0){
            pieChart.setVisibility(View.VISIBLE);
            for (int i = 0; i < bean.getCatalog().size(); i++){
                if(Float.parseFloat(bean.getCatalog().get(i).getAmount()) != 0){
                    PieEntry pieEntry = new PieEntry(Float.parseFloat(bean.getCatalog().get(i).getAmount()));
                    entries.add(pieEntry);
                    colors.add(Color.parseColor("#"+bean.getCatalog().get(i).getColor()));
                }
            }
            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setColors(colors);
            PieData data = new PieData(dataSet);
            pieChart.setData(data);
            pieChart.invalidate();
        }else {
            pieChart.setVisibility(View.INVISIBLE);
        }

        gridView.setAdapter(new QuickAdapter<IncomePointBean>(mContext,R.layout.layout_income_analysis_grid_item,bean.getCatalog()) {
            @Override
            protected void convert(BaseAdapterHelper helper, IncomePointBean item) {
                helper.setBackgroundColor(R.id.view,Color.parseColor("#"+item.getColor()));
                helper.setText(R.id.txt,item.getCatalog_name());
                helper.setText(R.id.txt_percent, item.getAmount_percent()+"%");
            }
        });
        listViewEmploee.setAdapter(new QuickAdapter<IncomeStaffBean>(mContext,R.layout.layout_emploee_analysis_list_item,bean.getStaff()) {
            @Override
            protected void convert(BaseAdapterHelper helper, IncomeStaffBean item) {
                helper.setSimpViewImageUri(R.id.iv_icon, Uri.parse(item.getStaff_avatar()));
                helper.setProgress(R.id.progress_bar_1,new Double(item.getXj_percent()).intValue());
                helper.setProgress(R.id.progress_bar_2,new Double(item.getLd_amount()).intValue());
            }
        });
    }
    //折线图
    private void setLineChart(AchieveChildBean bean){
        txtCashPerformance.setText(bean.getXj_total_amount());
        txtWorkPerformance.setText(bean.getLd_total_amount());
        //设置x轴的数据
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i < bean.getData().size(); i++){
            xValues.add(bean.getData().get(i).getDate());
        }
        //设置y轴的数据()
        List<List<Float>> yValues = new ArrayList<>();
        //第一条线
        List<Float> yValue1 = new ArrayList<>();
        for (int j = 0; j < bean.getData().size(); j++) {
            yValue1.add(Float.parseFloat(bean.getData().get(j).getXj_amount()));
        }
        yValues.add(yValue1);
        //第二条线
        List<Float> yValue2 = new ArrayList<>();
        for (int j = 0; j < bean.getData().size(); j++) {
            yValue2.add(Float.parseFloat(bean.getData().get(j).getLd_amount()));
        }
        yValues.add(yValue2);

        if(xValues.size() > 0 && yValues.size() > 0){
            mLineChart.setVisibility(View.VISIBLE);
            lineChartManager.showLineChart(xValues, yValues, colours);
            mLineChart.invalidate();
        }else {
            mLineChart.setVisibility(View.INVISIBLE);
        }
    }
    //柱状图
    private void setBarChart(CustomChildBean bean){
        txtMenPercent.setText(bean.getMan_percent()+"%");
        txtWomenPercent.setText(bean.getWoman_percent()+"%");
        float rate_nan = Float.parseFloat(bean.getMan_percent())/100;
        float rate_nv = Float.parseFloat(bean.getWoman_percent())/100;
        ratingBarNan.setRating(10*(1-rate_nan));
        ratingBarNv.setRating(10*rate_nv);
        //设置x轴的数据
        ArrayList<String> xBarValues = new ArrayList<>();
        //设置y轴的数据()
        List<Float> yBarValues = new ArrayList<>();
        for (int i = 0; i < bean.getAge().size(); i++){
            xBarValues.add(bean.getAge().get(i).getAge_text());
            yBarValues.add(Float.parseFloat(bean.getAge().get(i).getPercent()));
        }
        if(xBarValues.size() > 0 && yBarValues.size() > 0){
            mBarChart.setVisibility(View.VISIBLE);
            barChartManager.showBarChart(xBarValues, yBarValues);
            mBarChart.invalidate();
        }else {
            mBarChart.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.txt_income_week,R.id.txt_income_month,R.id.txt_income_year,R.id.txt_achievement_week,R.id.txt_achievement_month,
            R.id.txt_achievement_year,R.id.txt_customer_week,R.id.txt_customer_month,R.id.txt_customer_year,R.id.ao_choose_shop_lt})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_right:
                NiceDialog.init()
                        .setLayoutId(R.layout.pop_set_target)
                        .setConvertListener(new ViewConvertListener() {
                            @Override
                            public void convertView(com.othershe.nicedialog.ViewHolder holder, final BaseNiceDialog dialog) {
                                final EditText priceEt = holder.getView(R.id.et_price);
                                holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String raise_price = priceEt.getText().toString().trim();
                                        if(TextUtils.isEmpty(raise_price)){
                                            ToastUtil.show(mContext,"输入目标营业额");
                                            return;
                                        }
                                        dialog.dismiss();
                                        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2, UrlUtils.shop_report_target_set(LoginUtil.getInfo("token"),
                                                LoginUtil.getInfo("uid"),priceEt.getText().toString(),shop_id),true);
                                    }
                                });
                            }
                        })
                        .setWidth(270)
                        .setOutCancel(false)
                        .show(getSupportFragmentManager());
                break;
            case R.id.txt_income_week:
                txtIncomeWeek.setSelected(true);txtIncomeMonth.setSelected(false);txtIncomeYear.setSelected(false);
                setPieChart(report.getIncome().getWeek());
                break;
            case R.id.txt_income_month:
                txtIncomeWeek.setSelected(false);txtIncomeMonth.setSelected(true);txtIncomeYear.setSelected(false);
                setPieChart(report.getIncome().getMonth());
                break;
            case R.id.txt_income_year:
                txtIncomeWeek.setSelected(false);txtIncomeMonth.setSelected(false);txtIncomeYear.setSelected(true);
                setPieChart(report.getIncome().getYear());
                break;
            case R.id.txt_achievement_week:
                txtAchievementWeek.setSelected(true);txtAchievementMonth.setSelected(false);txtAchievementYear.setSelected(false);
                setLineChart(report.getAchieve().getWeek());
                break;
            case R.id.txt_achievement_month:
                txtAchievementWeek.setSelected(false);txtAchievementMonth.setSelected(true);txtAchievementYear.setSelected(false);
                setLineChart(report.getAchieve().getMonth());
                break;
            case R.id.txt_achievement_year:
                txtAchievementWeek.setSelected(false);txtAchievementMonth.setSelected(false);txtAchievementYear.setSelected(true);
                setLineChart(report.getAchieve().getYear());
                break;
            case R.id.txt_customer_week:
                txtCutsomerWeek.setSelected(true);txtCutsomerMonth.setSelected(false);txtCutsomerYear.setSelected(false);
                setBarChart(report.getCustom().getWeek());
                break;
            case R.id.txt_customer_month:
                txtCutsomerWeek.setSelected(false);txtCutsomerMonth.setSelected(true);txtCutsomerYear.setSelected(false);
                setBarChart(report.getCustom().getMonth());
                break;
            case R.id.txt_customer_year:
                txtCutsomerWeek.setSelected(false);txtCutsomerMonth.setSelected(false);txtCutsomerYear.setSelected(true);
                setBarChart(report.getCustom().getYear());
                break;
            case R.id.ao_choose_shop_lt:
                ChooseShopFilter chooseShopFilter = new ChooseShopFilter(mContext, shopList, new ChooseShopFilter.ChooseCall() {
                    @Override
                    public void onChoose(ShopBean item) {
                        txtShopName.setText(item.getShop_name());
                        shop_id = item.getShop_id();
                        refresh();
                    }
                });
                chooseShopFilter.createPopup();
                chooseShopFilter.showAsDropDown(chooseShopLt);
                break;
        }
    }
}
