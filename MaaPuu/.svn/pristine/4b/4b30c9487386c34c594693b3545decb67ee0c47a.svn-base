package com.maapuu.mereca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.SrvDataBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * Created by dell on 2018/3/5.
 */

public class ZhiDingFaXingShiActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.list_view)
    ListView listView;

    private QuickAdapter<SrvDataBean> adapter;
    String oid = "";
    String code2d_id = "";


    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_zdfxs);
    }

    @Override
    public void initView() {
        oid = getIntent().getStringExtra("oid");
        code2d_id = getIntent().getStringExtra("code2d_id");
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("指定发型师");
        adapter = new QuickAdapter<SrvDataBean>(mContext,R.layout.layout_zdfxs_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, SrvDataBean item) {
                helper.setText(R.id.tv_srv_name,item.getSrv_name());
                if(!TextUtils.isEmpty(item.getSrv_id())){
                    helper.setText(R.id.tv_srv_staff_name,item.getStaff_name());
                }
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SrvDataBean bean = adapter.getItem(position);
                if(bean.getCan_appoint() == 1){//1可以指定，0不允许指定
                    Intent intent = new Intent(mContext,ChooseHairstylistActivity.class);
                    intent.putExtra("srv_id",bean.getSrv_id());
                    intent.putExtra("appoint_srv_id",bean.getAppoint_srv_id());
                    startActivityForResult(intent,AppConfig.ACTIVITY_REQUESTCODE);
                }else {
                    ToastUtil.show(mContext,"该服务项目正在进行或已完成");
                }
            }
        });
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.code2d_srv_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),code2d_id),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:

                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(!StringUtils.isEmpty(object.optString("result")) && object.optJSONArray("result").length() > 0){
                            List<SrvDataBean> list = FastJsonTools.getPersons(object.optString("result"),SrvDataBean.class);
                            if(list != null){
                                adapter.clear();
                                adapter.addAll(list);
                            }
                            //page ++ ;//没有分页
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
                        Intent intent = new Intent();//空intent,保证预约完成界面刷新数据
                        setResult(-1,intent);

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
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_confirm})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;

            case R.id.txt_confirm:
                //确定
                setAppointStaff(getSrvData(adapter.getList()));

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1 && data != null){
           switch (requestCode){
               case AppConfig.ACTIVITY_REQUESTCODE:
                   String srv_id = data.getStringExtra("srv_id");//用于区分是哪个服务选项
                   String staff_id = data.getStringExtra("staff_id");
                   String staff_name = data.getStringExtra("staff_name");

                   List<SrvDataBean> list = adapter.getList();
                   for (int i=0;i<list.size();i++){
                       if(!TextUtils.isEmpty(srv_id) && srv_id.equals(list.get(i).getSrv_id())){
                           list.get(i).setStaff_id(staff_id);
                           list.get(i).setStaff_name(staff_name);
                       }
                   }
                   adapter.notifyDataSetChanged();

                   break;
           }
        }

    }

    private void setAppointStaff(String srv_data) {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2, UrlUtils.appoint_staff_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),oid,code2d_id,srv_data),true);
    }

    //获取指定发型师的数据 可以为空
    private String getSrvData(List<SrvDataBean> list){
        String jsonString ="";
        if(list != null && list.size()>0){
            for (SrvDataBean bean : list){
                if(!TextUtils.isEmpty(bean.getStaff_id()) && bean.getCan_appoint() == 1){//1可以指定，0不允许指定
                    jsonString += "{" + "\"" + "srv_id" + "\"" + ":" + "\"" + bean.getSrv_id()+ "\"" + ","
                            + "\"" + "staff_id" + "\"" + ":" + "\"" + bean.getStaff_id() + "\"" + "}" + ",";
                }
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
