package com.maapuu.mereca.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.AppManager;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.AppointFinishBean;
import com.maapuu.mereca.bean.SrvDataBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.event.EventEntity;
import com.maapuu.mereca.util.BitmapUtils;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.view.MyListView;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * Created by dell on 2018/3/5.
 */

public class YuYueSuccessActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.list_view)
    MyListView listView;

    @BindView(R.id.remark)
    TextView tvRemark;
    @BindView(R.id.item_name)
    TextView tvItemName;
    @BindView(R.id.tv_pay_amount)
    TextView tvPayAmount;
    @BindView(R.id.total_srv_duration)
    TextView tvDuration;
    @BindView(R.id.appoint_name)
    TextView tvAppointName;
    //@BindView(R.id.appoint_phone)
    //TextView tvAppointPhone;


//    private List<SrvDataBean> list;
    private QuickAdapter<SrvDataBean> adapter;

    String oid = "";
    String code2d_id = "0";
    private String code2d;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_yuyue_success);
    }

    @Override
    public void initView() {
        getBundle();
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("预约完成");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setText("验证码");

        adapter = new QuickAdapter<SrvDataBean>(mContext, R.layout.layout_yy_success_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, SrvDataBean item) {
                helper.setText(R.id.tv_srv_name,item.getSrv_name());
                helper.setText(R.id.tv_staff_name,item.getStaff_name());
            }
        };
        listView.setAdapter(adapter);
    }

    private void setUI(AppointFinishBean bean) {
        AppointFinishBean.AppointDataBean dataBean = bean.getAppoint_data();
        if (dataBean != null) {
            tvRemark.setText("请您于" + dataBean.getAppoint_time_text() + "\n到" + dataBean.getShop_name() + "体验服务项目");
            tvItemName.setText(dataBean.getItem_name());
            tvPayAmount.setText("¥" + dataBean.getPay_amount());
            tvDuration.setText("服务时长：" + dataBean.getTotal_srv_duration());
            tvAppointName.setText("预约人："+dataBean.getAppoint_name() +"  " +dataBean.getAppoint_phone());
            //tvAppointPhone.setText(dataBean.getAppoint_phone());
        }

        List<SrvDataBean> list = bean.getSrv_data();
        adapter.clear();
        adapter.addAll(list);
    }

    @Override
    public void initData() {
        if(TextUtils.isEmpty(oid)){
            ToastUtil.show(mContext,"未获取到订单信息");
            return;
        }
        //code2d_id;//消费码id，订单详情里面查看预约详情时传0，取最近的一次预约
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.appoint_code2d_detail_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),oid,code2d_id),true);
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            oid = bundle.getString("oid");
            code2d_id = bundle.getString("code2d_id","0");
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(!StringUtils.isEmpty(object.optString("result"))){
                            AppointFinishBean bean = FastJsonTools.getPerson(object.optString("result"),AppointFinishBean.class);
                            if(bean != null){
                                code2d = bean.getAppoint_data().getCode2d();
                                setUI(bean);
                            }
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

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.txt_zdfxs,R.id.tv_see_order,R.id.tv_go_home})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_right:
                popEwm(code2d);
                break;

            case R.id.txt_zdfxs:
                it = new Intent(mContext,ZhiDingFaXingShiActivity.class);
                it.putExtra("oid",oid);
                it.putExtra("code2d_id",code2d_id);
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                break;

            case R.id.tv_see_order:
                it = new Intent(mContext,OrderProjectDetailActivity.class);
                it.putExtra("oid",oid);
                startActivity(it);

                break;

            case R.id.tv_go_home:
                EventBus.getDefault().post(new EventEntity(0,0,null));
                AppManager.getAppManager().finishOtherActivity();
                break;
        }
    }

    //二维码
    private void popEwm(final String code) {
        NiceDialog.init().setLayoutId(R.layout.pop_ewm)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        ImageView ivCode = holder.getView(R.id.iv_code);
                        holder.setText(R.id.tv_code,code);
                        Bitmap bitmap = BitmapUtils.createImage(code, DisplayUtil.dip2px(mContext,50), DisplayUtil.dip2px(mContext,50), null);
                        ivCode.setImageBitmap(bitmap);
                    }
                })
                .setOutCancel(true).setShowBottom(false).setHeight(320).setWidth(300)
                .show(getSupportFragmentManager());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            if(requestCode == AppConfig.ACTIVITY_REQUESTCODE){
                //刷新数据
                initData();
            }
        }
    }
}
