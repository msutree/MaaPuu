package com.maapuu.mereca.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.WuliuBean;
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

public class WuLiuActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.txt_company_name)
    TextView txtCompanyName;
    @BindView(R.id.txt_tel)
    TextView txtTel;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.list_view)
    MyListView listView;

    private List<WuliuBean> list;
    private QuickAdapter<WuliuBean> adapter;

    private String oid;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_wuliu);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("查看物流");
        oid = getIntent().getStringExtra("oid");
        list = new ArrayList<>();
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.order_logistics_get(LoginUtil.getInfo("token"),
                LoginUtil.getInfo("uid"), oid), true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        txtStatus.setText("订单编号："+resultObj.optString("order_no"));
                        txtCompanyName.setText(resultObj.optString("company_name")+"："+resultObj.optString("logistics_no"));
                        txtTel.setText("官方电话："+resultObj.optString("company_tel"));
                        txtAddress.setText("配送至："+resultObj.optString("address_detail"));
                        if(resultObj.has("detail") && !StringUtils.isEmpty(resultObj.optString("detail"))){
                            list = FastJsonTools.getPersons(resultObj.optString("detail"),WuliuBean.class);
                        }
                        setAdapter();
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
        adapter = new QuickAdapter<WuliuBean>(mContext,R.layout.layout_wuliu_item,list) {
            @Override
            protected void convert(BaseAdapterHelper helper, WuliuBean item) {
                int position = helper.getPosition();
                helper.setText(R.id.txt_content,item.getLogistics_desc());
                helper.setText(R.id.txt_time,item.getLogistics_time());
                if(position == 0){
                    helper.setSelected(R.id.iv_choose,true);
                    helper.setSelected(R.id.txt_content,true);
                    helper.setSelected(R.id.txt_time,true);
                }else {
                    helper.setSelected(R.id.iv_choose,false);
                    helper.setSelected(R.id.txt_content,false);
                    helper.setSelected(R.id.txt_time,false);
                }

                if(position == list.size() - 1){
                    helper.setVisible(R.id.line,false);
                }else {
                    helper.setVisible(R.id.line,true);
                }
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    @OnClick({R.id.txt_left})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
        }
    }
}
