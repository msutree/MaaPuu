package com.maapuu.mereca.background.employee.fragment;

import android.graphics.Color;
import android.os.Message;
import android.view.View;
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
import com.maapuu.mereca.background.shop.bean.report.IncomePointBean;
import com.maapuu.mereca.background.shop.bean.report.MonthBean;
import com.maapuu.mereca.background.shop.bean.report.ReportBean;
import com.maapuu.mereca.base.BaseFragment;
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
import com.willy.ratingbar.ScaleRatingBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 工作狂 数据
 * Created by Jia on 2018/2/28.
 */

public class WorkDataFragment extends BaseFragment{
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

    private ReportBean report;
    private LineChartManager lineChartManager;
    private BarChartManager barChartManager;
    private List<Integer> colours;

    @Override
    protected int setContentViewById() {
        return R.layout.em_fragment_work_data;
    }

    @Override
    protected void initView(View v) {
        txtIncomeWeek.setSelected(true);txtAchievementWeek.setSelected(true);txtCutsomerWeek.setSelected(true);
        colours = new ArrayList<>();
        colours.add(Color.parseColor("#09E092"));
        colours.add(Color.parseColor("#01FFFF"));
        colours.add(Color.parseColor("#FFFD01"));
        initChart();
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
    protected void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.staff_report_data_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid")),true);
    }

    public void refresh() {
        initData();
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
                            setPieChart(report.getIncome().getWeek().getCatalog());
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
    private void setPieChart(List<IncomePointBean> list) {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        if(list.size() == 0){
            pieChart.setVisibility(View.INVISIBLE);
        }else {
            pieChart.setVisibility(View.VISIBLE);
            for (int i = 0; i < list.size(); i++){
                if(Float.parseFloat(list.get(i).getAmount()) != 0) {
                    PieEntry pieEntry = new PieEntry(Float.parseFloat(list.get(i).getAmount()));
                    entries.add(pieEntry);
                    colors.add(Color.parseColor("#" + list.get(i).getColor()));
                }
            }
            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setColors(colors);
            PieData data = new PieData(dataSet);
            pieChart.setData(data);
            pieChart.invalidate();
        }

        gridView.setAdapter(new QuickAdapter<IncomePointBean>(mContext,R.layout.layout_income_analysis_grid_item,list) {
            @Override
            protected void convert(BaseAdapterHelper helper, IncomePointBean item) {
                helper.setBackgroundColor(R.id.view,Color.parseColor("#"+item.getColor()));
                helper.setText(R.id.txt,item.getCatalog_name());
                helper.setText(R.id.txt_percent, item.getAmount_percent()+"%");
            }
        });
        listView.setAdapter(new QuickAdapter<IncomePointBean>(mContext,R.layout.layout_income_analysis_list_item,list) {
            @Override
            protected void convert(BaseAdapterHelper helper, IncomePointBean item) {
                helper.setText(R.id.txt_title,item.getCatalog_name());
                helper.setText(R.id.txt_money, "CNY "+item.getAmount());
                helper.setText(R.id.txt_num, "客数"+item.getNum()+"人");
                helper.setText(R.id.txt_num_percent, "客数占比 "+item.getNum_percent()+"%");
                helper.setText(R.id.txt_percent, "收入占比 "+item.getAmount_percent()+"%");
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
    @OnClick({R.id.txt_income_week,R.id.txt_income_month,R.id.txt_income_year,R.id.txt_achievement_week,R.id.txt_achievement_month,
            R.id.txt_achievement_year,R.id.txt_customer_week,R.id.txt_customer_month,R.id.txt_customer_year})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_income_week:
                txtIncomeWeek.setSelected(true);txtIncomeMonth.setSelected(false);txtIncomeYear.setSelected(false);
                setPieChart(report.getIncome().getWeek().getCatalog());
                break;
            case R.id.txt_income_month:
                txtIncomeWeek.setSelected(false);txtIncomeMonth.setSelected(true);txtIncomeYear.setSelected(false);
                setPieChart(report.getIncome().getMonth().getCatalog());
                break;
            case R.id.txt_income_year:
                txtIncomeWeek.setSelected(false);txtIncomeMonth.setSelected(false);txtIncomeYear.setSelected(true);
                setPieChart(report.getIncome().getYear().getCatalog());
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
        }
    }
}
