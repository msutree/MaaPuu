package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.bean.WageBean;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;
import com.maapuu.mereca.view.NestedRecyclerView;
import com.luck.picture.lib.decoration.RecycleViewDivider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 编辑工资 （提成设置）
 * Created by Jia on 2018/3/15.
 */

public class EditWageActivity extends BaseActivity {

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    @BindView(R.id.txt_tab_1)
    TextView txtTab1;
    @BindView(R.id.txt_tab_2)
    TextView txtTab2;

    @BindView(R.id.cal_type_1)
    ImageView calTypeIv1;
    @BindView(R.id.cal_type_2)
    ImageView calTypeIv2;

    @BindView(R.id.ew_post_tv)
    TextView postTv;
    @BindView(R.id.ew_srv_name_tv)
    TextView srvNameTv;

    @BindView(R.id.recycler_view)
    NestedRecyclerView recyclerView;
    @BindView(R.id.ew_delete_model_iv)
    ImageView deleteIv;

    private BaseRecyclerAdapter<WageBean.WageDetailBean> adapter;

    private TextView[] tvs;
    private int tabPosition = 0;
    String post_id = "";
    String jobName = "";

    int numType = 1;//计费方式: 1按次数计费，2按比例计费
    int calType = 1;//计算方式：1累进计算；2累计计算
    WageBean wageBean;
    String wage_id = "";
    String srv_id = "";
    List<WageBean.WageDetailBean> list;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_edit_wage);
    }

    @Override
    public void initView() {
        getBundle();
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("提成设置");
        tvs = new TextView[]{txtTab1, txtTab2};
        list = new ArrayList<>();
        if(wageBean.getWage_detail() != null){
            list.addAll(wageBean.getWage_detail());
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setNestedScrollingEnabled(false);
        //分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL,
                UIUtils.dip2px(mContext,10),
                getResources().getColor(R.color.background)));

        setInitData();
        setAdapter();
    }

    private void setInitData() {
        if(wageBean != null){
            numType = wageBean.getIs_num();
            tabPosition = numType == 1?0:1;
            calType = wageBean.getCalc_type();
            postTv.setText(jobName);
            srvNameTv.setText(wageBean.getSrv_name());

            wage_id = wageBean.getWage_id();
            srv_id = wageBean.getSrv_id();
        }

        setHead(tabPosition);
        calTypeIv1.setSelected(calType == 1);
        calTypeIv2.setSelected(calType == 2);
//        List<WageBean.WageDetailBean> list = wageBean.getWage_detail();
//        if(list != null){
//            adapter.clear();
//            adapter.addList(list);
//        }
    }

    private void setAdapter() {
        adapter = new BaseRecyclerAdapter<WageBean.WageDetailBean>(mContext, list,R.layout.shop_item_add_model) {
            @Override
            public void convert(BaseRecyclerHolder holder, final WageBean.WageDetailBean bean, int position, boolean isScrolling) {
                holder.setText(R.id.am_level_name_tv,position+1+"级阶梯");
                final EditText amountEt = holder.getView(R.id.am_amount_et);
                TextView amountUnitTv = holder.getView(R.id.am_amount_unit_tv);
                final EditText moneyEt = holder.getView(R.id.am_money_et);
                TextView moneyUnitTv = holder.getView(R.id.am_money_unit_tv);

                if(tabPosition == 0){
                    amountEt.setHint("请填写次数");
                    amountUnitTv.setText("次");
                    moneyEt.setHint("请填写金额");
                    moneyUnitTv.setText("元");
                } else {
                    amountEt.setHint("请填写金额");
                    amountUnitTv.setText("元");
                    moneyEt.setHint("请填写比例");
                    moneyUnitTv.setText("%");

                }

                amountEt.setText(bean.getCondition());
                moneyEt.setText(bean.getCommission());
//                if(numType != 0 && numType == wageBean.getIs_num()){
//                    if(!TextUtils.isEmpty(bean.getCondition())) amountEt.setSelection(bean.getCondition().length());
//                    if(!TextUtils.isEmpty(bean.getCommission())) moneyEt.setSelection(bean.getCommission().length());
//                } else {
//                    bean.setCondition("");
//                    bean.setCommission("");
//                    amountEt.setText(bean.getCondition());
//                    moneyEt.setText(bean.getCommission());
//                }

                amountEt.setTag(position);
                moneyEt.setTag(position);

                amountEt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        int pos = (int) amountEt.getTag();
                        getList().get(pos).setCondition(s.toString());
                    }
                });

                moneyEt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        int pos = (int) moneyEt.getTag();
                        getList().get(pos).setCommission(s.toString());
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            post_id = bundle.getString("post_id","");
            jobName = bundle.getString("jobName","");
            wageBean = (WageBean) bundle.getSerializable("WageBean");
        }
    }

    @Override
    public void initData() {
    }

    private void  setWageDetail(String wage_id,String srv_id,int is_num,int calc_type,String detail){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_post_detail_wage_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        post_id,wage_id,srv_id,is_num,calc_type,detail),true);
    }

    private void setHead(int position) {
        for (int i = 0; i < tvs.length; i++){
            if(i == position){
                tvs[position].setSelected(true);
            } else {
                tvs[i].setSelected(false);
            }
        }
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_tab_1,R.id.txt_tab_2,R.id.ew_save_tv,R.id.ew_add_model_tv,R.id.ew_delete_model_iv,
            R.id.cal_type_1,R.id.cal_type_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;

            case R.id.ew_save_tv:
                //完成
                setWageDetail(wage_id,srv_id,numType,calType,getDetail(adapter.getList()));

                break;

            case R.id.txt_tab_1:
                tabPosition = 0;setHead(tabPosition);numType = 1;
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }

                break;

            case R.id.txt_tab_2:
                tabPosition = 1;setHead(tabPosition);numType = 2;
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }

                break;

            case R.id.ew_add_model_tv:{
                //添加
                if (adapter != null) {
//                    List<WageBean.WageDetailBean> list = adapter.getList();
//                    if(list == null) list = new ArrayList<>();
                    list.add(new WageBean.WageDetailBean());
                    adapter.notifyDataSetChanged();
                    //adapter.insert(new WageBean.WageDetailBean(),adapter.getList().size());
                }
            }

                break;

            case R.id.ew_delete_model_iv:
//                List<WageBean.WageDetailBean> list = adapter.getList();
//                if (list!= null && list.size()>0) {
//                    adapter.delete(list.size()-1);
//                }

                if(list.size() == 0){
                    return;
                }
                list.remove(list.size()-1);
                adapter.notifyDataSetChanged();
//                adapter.notifyItemRemoved(list.size()-1);

                break;

            case R.id.cal_type_1:
            case R.id.cal_type_2:
                if(calType == 1){
                    calType = 2;
                } else {
                    calType = 1;
                }
                calTypeIv1.setSelected(calType == 1);
                calTypeIv2.setSelected(calType == 2);

                break;

        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        setResult(-1,new Intent());//通知刷新
                        finish();
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

    //获取指定发型师的数据 可以为空
    private String getDetail(List<WageBean.WageDetailBean> list){
        String jsonString ="";
        if(list != null && list.size()>0){
            for (WageBean.WageDetailBean bean : list){
                jsonString += "{" + "\"" + "condition" + "\"" + ":" + "\"" + bean.getCondition()+ "\"" + ","
                        + "\"" + "commission" + "\"" + ":" + "\"" + bean.getCommission() + "\"" + "}" + ",";
            }
            if(jsonString.endsWith(",")){
                jsonString = "[" + jsonString.substring(0,jsonString.length()-1) +"]";
            } else {
                jsonString = "";
            }
        } else {
            jsonString ="";
        }

        return jsonString;
    }

}
