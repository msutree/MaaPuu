package com.maapuu.mereca.util;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.maapuu.mereca.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/5/24.
 */

public class LineChartManager1 {
    private Context mContext;
    private LineChart lineChart;
    private YAxis leftAxis;   //左边Y轴
    private YAxis rightAxis;  //右边Y轴
    private XAxis xAxis;      //X轴

    public LineChartManager1(Context mContext, LineChart mLineChart) {
        this.mContext = mContext;
        this.lineChart = mLineChart;
        leftAxis = lineChart.getAxisLeft();
        rightAxis = lineChart.getAxisRight();
        xAxis = lineChart.getXAxis();
    }

    /**
     * 初始化LineChart
     */
    private void initLineChart(List<String> xAxisValues) {
        lineChart.setTouchEnabled(true);

        // 可拖曳
        lineChart.setDragEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setDoubleTapToZoomEnabled(false);
        Legend l = lineChart.getLegend();
        l.setEnabled(false);

        //设置描述文字
        lineChart.setDescription(null);
        XAxis xAxis = lineChart.getXAxis();

        //设置X轴的文字在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTextSize(18f);
        xAxis.setTextColor(mContext.getResources().getColor(R.color.text_99));
        xAxis.setGridColor(mContext.getResources().getColor(R.color.text_52));
        xAxis.setAxisLineColor(mContext.getResources().getColor(R.color.text_99));
//        xAxis.setAxisLineWidth(2f);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new StringAxisValueFormatter(xAxisValues));

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setEnabled(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setTextColor(Color.WHITE);
//        leftAxis.setAxisMaxValue(60); //设置该轴的最大值。 如果设置了，这个值将不会是根据提供的数据计算出来的。
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisLineColor(mContext.getResources().getColor(R.color.text_52));
        leftAxis.setGridColor(mContext.getResources().getColor(R.color.text_52));
        leftAxis.setStartAtZero(true);
//        leftAxis.setAxisLineWidth(2f);
//        leftAxis.setTextSize(18f);
        leftAxis.setLabelCount(6, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(20f);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    public class StringAxisValueFormatter implements IAxisValueFormatter {
        private List<String> xValues;
        public StringAxisValueFormatter(List<String> xValues) {
            this.xValues = xValues;
        }

        @Override
        public String getFormattedValue(float v, AxisBase axisBase) {
            if (v < 0 || v > (xValues.size() - 1)){//使得两侧柱子完全显示
                return "";
            }
            return xValues.get((int)v);
        }
    }

    /**
     * 初始化曲线 每一个LineDataSet代表一条线
     *
     * @param lineDataSet
     * @param color
     * @param mode        折线图是否填充
     */
    private void initLineDataSet(LineDataSet lineDataSet, int color, boolean mode) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(9f);
        lineDataSet.setValueTextColor(mContext.getResources().getColor(R.color.text_99));
        //设置折线图填充
        lineDataSet.setDrawFilled(mode);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        //线模式为圆滑曲线（默认折线）
//        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
    }

    /**
     * 展示折线图(一条)
     *
     * @param xAxisValues
     * @param yAxisValues
     * @param label
     * @param color
     */
    public void showLineChart(List<String> xAxisValues, List<Float> yAxisValues, String label, int color) {
        initLineChart(xAxisValues);
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < xAxisValues.size(); i++) {
            entries.add(new Entry(i, yAxisValues.get(i)));
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, label);
        initLineDataSet(lineDataSet, color, true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);
        //设置X轴的刻度数
        xAxis.setLabelCount(xAxisValues.size(), true);
        lineChart.setData(data);
    }

    /**
     * 展示线性图(多条)
     *
     * @param xAxisValues
     * @param yAxisValues 多条曲线Y轴数据集合的集合
     * @param colours
     */
    public void showLineChart(List<String> xAxisValues, List<List<Float>> yAxisValues,List<Integer> colours) {
        initLineChart(xAxisValues);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < yAxisValues.size(); i++) {
            ArrayList<Entry> entries = new ArrayList<>();
            for (int j = 0; j < yAxisValues.get(i).size(); j++) {
                if (j >= xAxisValues.size()) {
                    j = xAxisValues.size() - 1;
                }
                entries.add(new Entry(j, yAxisValues.get(i).get(j)));
            }
            LineDataSet lineDataSet = new LineDataSet(entries, "");

            initLineDataSet(lineDataSet, colours.get(i), false);
            dataSets.add(lineDataSet);
        }
        LineData data = new LineData(dataSets);
        xAxis.setLabelCount(xAxisValues.size(), true);
        lineChart.setData(data);
    }

    /**
     * 设置Y轴值
     *
     * @param max
     * @param min
     * @param labelCount
     */
    public void setYAxis(float max, float min, int labelCount) {
        if (max < min) {
            return;
        }
        leftAxis.setAxisMaximum(max);
        leftAxis.setAxisMinimum(min);
        leftAxis.setLabelCount(labelCount, false);

        rightAxis.setAxisMaximum(max);
        rightAxis.setAxisMinimum(min);
        rightAxis.setLabelCount(labelCount, false);
        lineChart.invalidate();
    }

    /**
     * 设置X轴的值
     *
     * @param max
     * @param min
     * @param labelCount
     */
    public void setXAxis(float max, float min, int labelCount) {
        xAxis.setAxisMaximum(max);
        xAxis.setAxisMinimum(min);
        xAxis.setLabelCount(labelCount, true);

        lineChart.invalidate();
    }

    /**
     * 设置高限制线
     *
     * @param high
     * @param name
     */
    public void setHightLimitLine(float high, String name, int color) {
        if (name == null) {
            name = "高限制线";
        }
        LimitLine hightLimit = new LimitLine(high, name);
        hightLimit.setLineWidth(2f);
        hightLimit.setTextSize(10f);
        hightLimit.setLineColor(color);
        hightLimit.setTextColor(color);
        leftAxis.addLimitLine(hightLimit);
        lineChart.invalidate();
    }

    /**
     * 设置低限制线
     *
     * @param low
     * @param name
     */
    public void setLowLimitLine(int low, String name) {
        if (name == null) {
            name = "低限制线";
        }
        LimitLine hightLimit = new LimitLine(low, name);
        hightLimit.setLineWidth(4f);
        hightLimit.setTextSize(10f);
        leftAxis.addLimitLine(hightLimit);
        lineChart.invalidate();
    }

    /**
     * 设置描述信息
     *
     * @param str
     */
    public void setDescription(String str) {
        Description description = new Description();
        description.setText(str);
        lineChart.setDescription(description);
        lineChart.invalidate();
    }
}
