package com.maapuu.mereca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.DateBean;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.view.MyGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * Created by dell on 2018/3/5.
 */

public class ChooseYuYueTimeActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.grid_view_1)
    MyGridView gridView1;
    @BindView(R.id.grid_view_2)
    MyGridView gridView2;
    @BindView(R.id.txt_no_time)
    TextView txtNoTime;

    private QuickAdapter<DateBean> adapter1;
    private QuickAdapter<String> adapter2;

    List<DateBean> srv_time;
    List<String> timeList;
    String selectedDate = "";
    String selectedTime = "";

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_choose_yuyue_time);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("选择时间");

        srv_time = (List<DateBean>) getIntent().getSerializableExtra("srv_time");
        if(srv_time != null && srv_time.size()>0){
            timeList = srv_time.get(0).getTime();
        }

        adapter1 = new QuickAdapter<DateBean>(mContext,R.layout.layout_choose_time_item_1,srv_time) {
            @Override
            protected void convert(BaseAdapterHelper helper, DateBean item) {
                final int position = helper.getPosition();
                TextView txt_week = helper.getView(R.id.txt_week);
                TextView txt_date = helper.getView(R.id.txt_date);
                txt_week.setText(srv_time.get(position).getWeek());
                txt_date.setText(srv_time.get(position).getDate_short());
                txt_week.setSelected(srv_time.get(position).isBool());
                txt_date.setSelected(srv_time.get(position).isBool());
                helper.setBackgroundRes(R.id.ll_item,srv_time.get(position).isBool()? R.drawable.bg_main_btn:R.drawable.bg_solid_33_stroke_ff);
                helper.setOnClickListener(R.id.ll_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //清除选中状态
                        txtNoTime.setSelected(false);
                        for (int i = 0; i < srv_time.size(); i++){
                            if(position == i){
                                srv_time.get(position).setBool(true);
                                selectedDate = srv_time.get(position).getDate();

                                setAdapter2(srv_time.get(position).getTime());
//                                timeList = srv_time.get(position).getTime();
//                                if(adapter2 != null){
//                                    adapter2.notifyDataSetChanged();
//                                }
                            } else {
                                srv_time.get(i).setBool(false);
                            }
                        }
                        notifyDataSetChanged();
                    }
                });
            }
        };
        gridView1.setAdapter(adapter1);

        setAdapter2(srv_time.get(0).getTime());
    }

    private void setAdapter2(List<String> list) {
        adapter2 = new QuickAdapter<String>(mContext,R.layout.layout_choose_time_item_2,list) {
            @Override
            protected void convert(BaseAdapterHelper helper, final String item) {

                TextView txt_time = helper.getView(R.id.txt_time);
                txt_time.setText(item);
                txt_time.setSelected(selectedTime.equals(item));
                txt_time.setBackgroundColor(selectedTime.equals(item)?getResources().getColor(R.color.main_color):getResources().getColor(R.color.white));
                txt_time.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //清除选中状态
                        txtNoTime.setSelected(false);
                        selectedTime = item;
                        notifyDataSetChanged();
                    }
                });
            }
        };
        gridView2.setAdapter(adapter2);
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_no_time,R.id.txt_confirm})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;

            case R.id.txt_no_time:
                setResult(AppConfig.ACTIVITY_RESULTCODE);
                finish();
//                txtNoTime.setSelected(!txtNoTime.isSelected());
//                if(txtNoTime.isSelected() && adapter1 != null && adapter2 != null){
//                    if(srv_time != null && srv_time.size()>0){
//                        for (int i = 0; i < srv_time.size(); i++){
//                            srv_time.get(i).setBool(false);
//                        }
//                    }
//                    adapter1.notifyDataSetChanged();
//                    selectedTime = "";
//                    adapter2.notifyDataSetChanged();
//                }
                break;

            case R.id.txt_confirm:
                //确定
                String time = selectedDate + " "+ selectedTime;
//                if(txtNoTime.isSelected()){
//                    setResult(AppConfig.ACTIVITY_RESULTCODE);
//                    finish();
//                } else {
                    if(TextUtils.isEmpty(selectedDate)){
                        ToastUtil.show(mContext,"请选择日期");
                        return;
                    }
                    if(TextUtils.isEmpty(selectedTime)){
                        ToastUtil.show(mContext,"请选择时间");
                        return;
                    }
                    Intent intent = new Intent();
                    intent.putExtra("time",time);
                    setResult(-1,intent);
                    finish();
//                }

                break;
        }
    }
}
