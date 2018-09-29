package com.maapuu.mereca.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ImageTextBean;
import com.maapuu.mereca.bean.SrvBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.PictureSelectUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;
import com.maapuu.mereca.view.MyListView;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/4/9.
 */

public class SrvDetailActivity extends BaseActivity{
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.ll_has_address)
    LinearLayout ll;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.txt_name_1)
    TextView txtName1;
    @BindView(R.id.txt_project_1)
    TextView txtProject1;
    @BindView(R.id.txt_price_1)
    TextView txtPrice1;
    @BindView(R.id.txt_card_desc_1)
    TextView txtCardDesc1;
    @BindView(R.id.txt_card_name_1)
    TextView txtCardName1;
    @BindView(R.id.iv_icon)
    SimpleDraweeView ivIcon;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_goods_name)
    TextView txtGoodsName;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_order_no)
    TextView txtOrderNo;
    @BindView(R.id.txt_pay_type)
    TextView txtPayType;
    @BindView(R.id.txt_order_time)
    TextView txtOrderTime;
    @BindView(R.id.list_view)
    MyListView listView;
    @BindView(R.id.ll_start_service)
    LinearLayout startServiceLt;
    @BindView(R.id.tv_start_service)
    TextView startServiceTv;

    private String code2d;
    private String code2d_id;
    private List<SrvBean> list;
    private QuickAdapter<SrvBean> adapter;
    private boolean isScan;
    private String image;

    String appoint_srv_id = "";//当前选中的预约id
    boolean isShowProgress = true;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_srv_detail);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("服务单详情");
        //isScan = getIntent().getBooleanExtra("isScan",false);
        code2d_id = getIntent().getStringExtra("code2d_id");

//        if(isScan){
//            code2d = getIntent().getStringExtra("code2d");
//        } else {
//            code2d_id = getIntent().getStringExtra("code2d_id");
//        }

//        list = new ArrayList<>();
        adapter = new QuickAdapter<SrvBean>(mContext,R.layout.layout_srv_detail_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, final SrvBean item) {
                helper.setSimpViewImageUri(R.id.iv_left,Uri.parse(item.getSrv_img()));
                helper.setText(R.id.txt_left,item.getSrv_name());
                helper.setText(R.id.txt_right,item.getStaff_name());
                helper.setText(R.id.txt_status,item.getSrv_status());
                if(item.getSrv_status().equals("未预约")){
                    helper.setVisible(R.id.txt_btn,true);
                    helper.setVisible(R.id.iv_right,false);
                    helper.setSelected(R.id.iv_srv,false);
                    helper.setText(R.id.txt_btn,"为TA"+item.getSrv_name());
                }else {
                    helper.setSimpViewImageUri(R.id.iv_right,Uri.parse(item.getStaff_avatar()));
                    helper.setVisible(R.id.txt_btn,false);
                    helper.setVisible(R.id.iv_right,true);
                    helper.setSelected(R.id.iv_srv,true);
                }

                helper.setOnClickListener(R.id.txt_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        appoint_srv_id = item.getAppoint_srv_id();
                        //选择服务人员
                        Intent intent = new Intent(mContext,ChooseHairstylistActivity.class);
                        intent.putExtra("srv_id",item.getSrv_id());
                        intent.putExtra("appoint_srv_id",appoint_srv_id);
                        startActivityForResult(intent, AppConfig.ACTIVITY_REQUESTCODE);
                    }
                });

                helper.setOnClickListener(R.id.iv_right, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        appoint_srv_id = item.getAppoint_srv_id();
                        if(item.getSrv_status().equals("未预约") || item.getSrv_status().equals("已预约")){
                            //选择服务人员
                            Intent intent = new Intent(mContext,ChooseHairstylistActivity.class);
                            intent.putExtra("srv_id",item.getSrv_id());
                            intent.putExtra("appoint_srv_id",appoint_srv_id);
                            startActivityForResult(intent, AppConfig.ACTIVITY_REQUESTCODE);
                        }
                    }
                });
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.srv_detail_get(LoginUtil.getInfo("token"),
                        LoginUtil.getInfo("uid"),code2d_id),isShowProgress);

//        if(isScan){
//            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.order2d_detail_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),code2d),isShowProgress);
//        }else {
//            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.srv_detail_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),code2d_id),isShowProgress);
//        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                isShowProgress = false;
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        if(resultObj.has("order_detail") && !StringUtils.isEmpty(resultObj.optString("order_detail"))){
                            JSONObject detailObj = resultObj.optJSONObject("order_detail");
                            if(StringUtils.isEmpty(detailObj.optString("card_type")) || "3".equals(detailObj.optString("card_type"))){
                                ll.setVisibility(View.VISIBLE);
                                ll1.setVisibility(View.GONE);
                                ivIcon.setImageURI(Uri.parse(detailObj.optString("avatar")));
                                txtName.setText(detailObj.optString("nick_name"));
                                txtGoodsName.setText(detailObj.optString("item_name"));
                                txtPrice.setText("¥"+detailObj.optString("pay_amount"));
                            }else {
                                ll.setVisibility(View.GONE);
                                ll1.setVisibility(View.VISIBLE);
                                image = detailObj.optString("avatar");
                                UIUtils.loadImg(mContext,detailObj.optString("avatar"),ivImage,true,R.mipmap.morentouxiang);
                                txtName1.setText(detailObj.optString("nick_name"));
                                txtProject1.setText(detailObj.optString("item_name"));
                                txtPrice1.setText("¥"+detailObj.optString("pay_amount"));
                                switch (detailObj.optInt("card_type")){
                                    case 1:
                                        txtCardDesc1.setText("会籍");
                                        break;
                                    case 2:
                                        txtCardDesc1.setText("项目卡");
                                        break;
                                    case 3:
                                        txtCardDesc1.setText("充值卡");
                                        break;
                                }
                                txtCardName1.setText(detailObj.optString("card_name"));
                            }

                            txtOrderNo.setText("订单编号："+detailObj.optString("order_no"));
                            switch (detailObj.optInt("pay_type")){
                                case 1:
                                    txtPayType.setText("支付方式：支付宝支付");
                                    break;
                                case 2:
                                    txtPayType.setText("支付方式：微信支付");
                                    break;
                                case 3:
                                    txtPayType.setText("支付方式：余额支付");
                                    break;
                            }
                            txtOrderTime.setText("下单时间："+detailObj.optString("create_time_text"));
                            // 为2时才显示开始服务按钮，为3显示下一项服务，其他状态不显示可以关闭；
                            int code_status = detailObj.optInt("code_status");
                            if(code_status == 2){
                                txtRight.setText("拒绝服务");
                                txtRight.setVisibility(View.GONE);
                                startServiceLt.setVisibility(View.VISIBLE);
                                startServiceTv.setText("开始服务");
                            } else if(code_status == 3){
                                txtRight.setVisibility(View.GONE);
                                startServiceLt.setVisibility(View.VISIBLE);
                                startServiceTv.setText("下一项服务");
                            } else {
                                txtRight.setVisibility(View.GONE);
                                startServiceLt.setVisibility(View.GONE);
                            }
                        }
                        if(resultObj.has("lst_srv") && !StringUtils.isEmpty(resultObj.optString("lst_srv")) && resultObj.optJSONArray("lst_srv").length() > 0){
                            list = FastJsonTools.getPersons(resultObj.optString("lst_srv"),SrvBean.class);
                            adapter.clear();
                            adapter.addAll(list);
                        }
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case HttpModeBase.HTTP_REQUESTCODE_2:
                //设置服务人员
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        //刷新界面数据
                        initData();

                    } else {
                        ToastUtil.show(mContext, object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case HttpModeBase.HTTP_REQUESTCODE_3:
                //开始服务
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        //刷新界面数据
                        finish();
                    }
                    ToastUtil.show(mContext, object.optString("message"));
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
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.iv_image,R.id.tv_start_service})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_left:
            case R.id.txt_right:
                finish();
                break;
            case R.id.iv_image:
                List<ImageTextBean> list = new ArrayList<>();
                ImageTextBean bean = new ImageTextBean();
                bean.setContent_type("2");
                bean.setContent(image);
                list.add(bean);
                PictureSelectUtil.show((Activity)mContext,0,list);
                break;
            case R.id.tv_start_service:
                //开始服务
                showConfirmDialog("确定开始服务吗？");

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1 && data != null){
            switch (requestCode){
                case AppConfig.ACTIVITY_REQUESTCODE:
                    //String srv_id = data.getStringExtra("srv_id");//用于区分是哪个服务选项
                    String staff_id = data.getStringExtra("staff_id");
                    //String staff_name = data.getStringExtra("staff_name");

                    setStaff(appoint_srv_id,staff_id);
                    break;
            }
        }

    }

    //设置服务人员
    private void setStaff(String appoint_srv_id,String staff_id){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2, UrlUtils.order_staff_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),appoint_srv_id,staff_id),true);
    }

    //开始服务 在服务单详情页面开始服务，只是通知
    private void startService() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3,
                UrlUtils.appoint_start_set(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"), code2d_id), true);
    }

    //开始下一个服务，只是通知下一个服务人员
    private void startNextService() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3,
                UrlUtils.appoint_next_set(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"), code2d_id), true);
    }

    private void showConfirmDialog(final String msg) {
        NiceDialog.init()
                .setLayoutId(R.layout.nd_layout_confirm)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(com.othershe.nicedialog.ViewHolder holder, final BaseNiceDialog dialog) {
                        holder.setText(R.id.title,"提示");
                        holder.setText(R.id.message,msg);

                        holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //消失
                                dialog.dismiss();
                            }
                        });

                        holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if("开始服务".equals(startServiceTv.getText().toString())){
                                    startService();
                                } else if("下一项服务".equals(startServiceTv.getText().toString())){
                                    startNextService();
                                }

                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setWidth(270)//setMargin()和setWidth()选择一个即可
                //.setMargin(60)
                .setOutCancel(true)
                //.setAnimStyle(R.style.EnterExitAnimation)
                .show(getSupportFragmentManager());
    }

}
