package com.maapuu.mereca.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.AppointOrderBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.view.MyListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/5.
 */

public class MyOrderActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_project_dfk_num)
    TextView txtProjectDfkNum;
    @BindView(R.id.txt_project_dsy_num)
    TextView txtProjectDsyNum;
    @BindView(R.id.txt_project_dpj_num)
    TextView txtProjectDpjNum;
    @BindView(R.id.txt_goods_dfk_num)
    TextView txtGoodsDfkNum;
    @BindView(R.id.txt_goods_dfh_num)
    TextView txtGoodsDfhNum;
    @BindView(R.id.txt_goods_dsh_num)
    TextView txtGoodsDshNum;
    @BindView(R.id.txt_goods_dpj_num)
    TextView txtGoodsDpjNum;
    @BindView(R.id.list_view)
    MyListView listView;
    @BindView(R.id.ll_has)
    LinearLayout llHas;

    private List<AppointOrderBean> list;
    private QuickAdapter<AppointOrderBean> adapter;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_order);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("我的订单");
        list = new ArrayList<>();
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.my_order_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid")),true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.my_order_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid")),false);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        if(resultObj.has("project_order") && !StringUtils.isEmpty(resultObj.optString("project_order"))){
                            JSONObject projectObj = resultObj.optJSONObject("project_order");
                            if(projectObj.optInt("dfk") > 0){
                                txtProjectDfkNum.setVisibility(View.VISIBLE);
                                txtProjectDfkNum.setText(projectObj.optString("dfk"));
                            }else {
                                txtProjectDfkNum.setVisibility(View.GONE);
                            }
                            if(projectObj.optInt("dsy") > 0){
                                txtProjectDsyNum.setVisibility(View.VISIBLE);
                                txtProjectDsyNum.setText(projectObj.optString("dsy"));
                            }else {
                                txtProjectDsyNum.setVisibility(View.GONE);
                            }
                            if(projectObj.optInt("dpj") > 0){
                                txtProjectDpjNum.setVisibility(View.VISIBLE);
                                txtProjectDpjNum.setText(projectObj.optString("dpj"));
                            }else {
                                txtProjectDpjNum.setVisibility(View.GONE);
                            }
                        }
                        if(resultObj.has("commodity_order") && !StringUtils.isEmpty(resultObj.optString("commodity_order"))){
                            JSONObject goodsObj = resultObj.optJSONObject("commodity_order");
                            if(goodsObj.optInt("dfk") > 0){
                                txtGoodsDfkNum.setVisibility(View.VISIBLE);
                                txtGoodsDfkNum.setText(goodsObj.optString("dfk"));
                            }else {
                                txtGoodsDfkNum.setVisibility(View.GONE);
                            }
                            if(goodsObj.optInt("dfh") > 0){
                                txtGoodsDfhNum.setVisibility(View.VISIBLE);
                                txtGoodsDfhNum.setText(goodsObj.optString("dfh"));
                            }else {
                                txtGoodsDfhNum.setVisibility(View.GONE);
                            }
                            if(goodsObj.optInt("dsh") > 0){
                                txtGoodsDshNum.setVisibility(View.VISIBLE);
                                txtGoodsDshNum.setText(goodsObj.optString("dsh"));
                            }else {
                                txtGoodsDshNum.setVisibility(View.GONE);
                            }
                            if(goodsObj.optInt("dpj") > 0){
                                txtGoodsDpjNum.setVisibility(View.VISIBLE);
                                txtGoodsDpjNum.setText(goodsObj.optString("dpj"));
                            }else {
                                txtGoodsDpjNum.setVisibility(View.GONE);
                            }
                        }
                        if(resultObj.has("appoint_order") && resultObj.optJSONArray("appoint_order").length() > 0){
                            llHas.setVisibility(View.GONE);
                            list = FastJsonTools.getPersons(resultObj.optString("appoint_order"),AppointOrderBean.class);
                            setAdapter();
                        }else {
                            llHas.setVisibility(View.VISIBLE);
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

    private void setAdapter() {
        adapter = new QuickAdapter<AppointOrderBean>(mContext,R.layout.layout_yuyue_order_item,list) {
            @Override
            protected void convert(BaseAdapterHelper helper, AppointOrderBean item) {
                helper.setSimpViewImageUri(R.id.iv_order, Uri.parse(item.getItem_img()));
                helper.setText(R.id.txt_goods_name,item.getItem_name());
                helper.setText(R.id.txt_price,"¥"+item.getPay_amount());
                helper.setText(R.id.txt_shop_name,item.getShop_name());
                helper.setText(R.id.txt_yy_time,"预约时间："+item.getAppoint_time_text());
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                it = new Intent(mContext,YuYueSuccessActivity.class);
                it.putExtra("oid",adapter.getList().get(position).getOid());
                it.putExtra("code2d_id",adapter.getList().get(position).getCode2d_id());
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
            }
        });
    }

    @Override
    @OnClick({R.id.txt_left,R.id.rl_project_order,R.id.ll_project_dfk,R.id.ll_project_dsy,R.id.ll_project_dpj,
            R.id.rl_goods_order,R.id.ll_goods_dfk,R.id.ll_goods_dfh,R.id.ll_goods_dsh,R.id.ll_goods_dpj})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.rl_project_order:
                it = new Intent(mContext,OrderProjectActivity.class);
                it.putExtra("tabPosition",0);
                startActivity(it);
                break;
            case R.id.ll_project_dfk:
                it = new Intent(mContext,OrderProjectActivity.class);
                it.putExtra("tabPosition",1);
                startActivity(it);
                break;
            case R.id.ll_project_dsy:
                it = new Intent(mContext,OrderProjectActivity.class);
                it.putExtra("tabPosition",2);
                startActivity(it);
                break;
            case R.id.ll_project_dpj:
                it = new Intent(mContext,OrderProjectActivity.class);
                it.putExtra("tabPosition",3);
                startActivity(it);
                break;
            case R.id.rl_goods_order:
                it = new Intent(mContext,OrderGoodsActivity.class);
                it.putExtra("tabPosition",0);
                startActivity(it);
                break;
            case R.id.ll_goods_dfk:
                it = new Intent(mContext,OrderGoodsActivity.class);
                it.putExtra("tabPosition",1);
                startActivity(it);
                break;
            case R.id.ll_goods_dfh:
                it = new Intent(mContext,OrderGoodsActivity.class);
                it.putExtra("tabPosition",2);
                startActivity(it);
                break;
            case R.id.ll_goods_dsh:
                it = new Intent(mContext,OrderGoodsActivity.class);
                it.putExtra("tabPosition",3);
                startActivity(it);
                break;
            case R.id.ll_goods_dpj:
                it = new Intent(mContext,OrderGoodsActivity.class);
                it.putExtra("tabPosition",4);
                startActivity(it);
                break;
        }
    }
}
