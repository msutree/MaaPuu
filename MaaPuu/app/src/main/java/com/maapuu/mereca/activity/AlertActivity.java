package com.maapuu.mereca.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.background.employee.activity.ServiceDetailActivity;
import com.maapuu.mereca.base.AlertBean;
import com.maapuu.mereca.base.BaseActivity;
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
 * Created by dell on 2018/3/1.
 */

public class AlertActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.iv_icon)
    SimpleDraweeView ivIcon;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_project_name)
    TextView txtProjectName;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.list_view)
    MyListView listView;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private List<AlertBean> list;
    private QuickAdapter<AlertBean> adapter;
    private String appoint_srv_id;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_alert);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("新的服务");
        appoint_srv_id = getIntent().getStringExtra("business_id");
        list = new ArrayList<>();
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.srv_new_detail_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),appoint_srv_id),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        JSONObject detailObj = resultObj.optJSONObject("order_detail");
                        ivIcon.setImageURI(Uri.parse(detailObj.optString("avatar")));
                        txtName.setText(detailObj.optString("nick_name"));
                        txtProjectName.setText(detailObj.optString("item_name"));
                        txtPrice.setText("¥"+detailObj.optString("pay_amount"));
                        if(resultObj.has("lst_srv") && !(resultObj.opt("lst_srv") instanceof  Boolean)){
                            list = FastJsonTools.getPersons(resultObj.optString("lst_srv"),AlertBean.class);
                        }
                        setAdapter();
                        if(resultObj.optInt("can_start") == 1){
                            llBottom.setVisibility(View.VISIBLE);
                        }else {
                            llBottom.setVisibility(View.GONE);
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
                        it = new Intent(mContext, ServiceDetailActivity.class);
                        it.putExtra("selectedIndex",0);
                        it.putExtra("appoint_srv_id",appoint_srv_id);
                        startActivity(it);
                        finish();
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
        adapter = new QuickAdapter<AlertBean>(mContext,R.layout.layout_alert_item,list) {
            @Override
            protected void convert(BaseAdapterHelper helper, AlertBean item) {
                helper.setSelected(R.id.txt,item.getIs_current_srv().equals("1"));
                switch (item.getIs_current_srv()){
                    case "1":
                        if("1".equals(item.getAssign_type())){
                            helper.setText(R.id.txt,"用户指定你为TA进行"+item.getSrv_name()+"服务");
                        }else {
                            helper.setText(R.id.txt,"系统指定你为TA进行"+item.getSrv_name()+"服务");
                        }
                        break;
                    case "2":
                        helper.setText(R.id.txt,item.getStaff_name()+"已为TA进行"+item.getSrv_name()+"服务");
                        break;
                    case "3":
                        if(StringUtils.isEmpty(item.getSrv_name())){
                            helper.setText(R.id.txt,"为TA"+item.getSrv_name());
                        }else {
                            helper.setText(R.id.txt,"已预约"+item.getStaff_name()+"为TA"+item.getSrv_name());
                        }
                        break;
                }
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_start})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_start:
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.start_srv_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),appoint_srv_id),true);
                break;
        }
    }
}
