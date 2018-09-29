package com.maapuu.mereca.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseEventActivity;
import com.maapuu.mereca.bean.CodeDetailBean;
import com.maapuu.mereca.bean.CodeItemBean;
import com.maapuu.mereca.bean.ConsumeCodeBean;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * Created by dell on 2018/3/5.
 */

public class ConsumeCodeDetailActivity extends BaseEventActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.list_view)
    MyListView listView;

    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.srv_num_tv)
    TextView srvNumTv;
    @BindView(R.id.shop_name_tv)
    TextView shopNameTv;

    private List<CodeItemBean> list;
    private QuickAdapter<CodeItemBean> adapter;
    String oid ="";//订单id
    private String image,name,address,distance;
    private String appoint_name,appoint_phone;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_consume_code_detail);
    }

    @Override
    public void initView() {
        oid = getIntent().getStringExtra("oid");
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("验证码");

        list = new ArrayList<>();
        adapter = new QuickAdapter<CodeItemBean>(mContext,R.layout.layout_consume_code_detail_item,list) {
            @Override
            protected void convert(BaseAdapterHelper helper, final CodeItemBean item) {
                final int position = helper.getPosition();
                helper.setText(R.id.code_content,item.getCode2d());
                final TextView txtBtn = helper.getView(R.id.txt_btn);
                txtBtn.setSelected(false);
                //code_status二维码状态：1 未预约；2 已预约；3 服务中；4 已使用 5 已完成
                //code_action_flag为1可以预约；为2可以取消预约；为0不能做任何操作
                txtBtn.setVisibility(View.VISIBLE);
                switch (item.getCode_status()){
                    case 1:
                        txtBtn.setText("未预约");
                        if(item.getCode_action_flag() == 1){
                            txtBtn.setText("预约");
                            txtBtn.setSelected(true);
                        }
                        break;
                    case 2:
                        txtBtn.setText("已预约");
                        if(item.getCode_action_flag() == 2){
                            txtBtn.setText("取消预约");
                            txtBtn.setSelected(true);
                            //可取消预约
                        }
                        break;
                    case 3:
                        txtBtn.setText("服务中");
                        break;
                    case 4:
                        //txtBtn.setText("已使用");
                        txtBtn.setText("评价");
                        txtBtn.setSelected(true);
                        break;
                    case 5:
                        txtBtn.setText("已完成");
                        break;
                    default:
                        txtBtn.setVisibility(View.INVISIBLE);
                        break;
                }
                helper.setOnClickListener(R.id.iv_ewm, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popEwm(item.getCode2d());
                    }
                });
                helper.setOnClickListener(R.id.txt_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (txtBtn.getText().toString()){
                            case "预约":
                                popYuYueBaseinfo();
                                break;
                            case "取消预约":
                                //弹框取消预约
                                showCancelDialog("你确定取消预约吗？",item.getCode2d_id());
                                break;
                            case "评价":
                                //跳转到评价
                                it = new Intent(mContext,PublishProjectCommentActivity.class);
                                it.putExtra("oid",oid);
                                it.putExtra("code2d_id",list.get(position).getCode2d_id());
                                it.putExtra("image",image);
                                it.putExtra("name",name);
                                it.putExtra("address",address);
                                it.putExtra("distance",distance);
                                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
                                break;
                        }

                        //测试跳转
//                        it = new Intent(mContext,YuYueSuccessActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("code2d_id", "0");
//                        bundle.putString("oid", oid);
//                        it.putExtras(bundle);
//                        startActivity(it);

                    }
                });
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(list.get(position).getCode_status() != 1){
                    it = new Intent(mContext, YuYueSuccessActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("code2d_id", list.get(position).getCode2d_id());
                    bundle.putString("oid", oid);
                    it.putExtras(bundle);
                    startActivity(it);
                }else {
                    popYuYueBaseinfo();
                }
            }
        });
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
    //基本信息
    private void popYuYueBaseinfo() {
        NiceDialog.init().setLayoutId(R.layout.pop_yuyue_base_info)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {

                        ImageView ivClose = holder.getView(R.id.iv_close);
                        TextView txtNext = holder.getView(R.id.txt_next);
                        final EditText etName = holder.getView(R.id.et_name);
                        etName.setText(appoint_name);
                        etName.setSelection(appoint_name.length());
                        final EditText etPhone = holder.getView(R.id.et_phone);
                        etPhone.setText(appoint_phone);
                        ivClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        txtNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = etName.getText().toString().trim();
                                String phone = etPhone.getText().toString().trim();
                                if(TextUtils.isEmpty(name)){
                                    ToastUtil.show(mContext,"请输入姓名");
                                    return;
                                }
                                if(TextUtils.isEmpty(phone)){
                                    ToastUtil.show(mContext,"请输入电话");
                                    return;
                                }
                                dialog.dismiss();
                                Intent intent = new Intent(mContext,YuYueActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("name",name);
                                bundle.putString("phone",phone);
                                bundle.putString("oid",oid);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                })
                .setOutCancel(false).setShowBottom(false).setHeight(300).setWidth(300)
                .show(getSupportFragmentManager());
    }

    @Override
    public void initData() {
        if(TextUtils.isEmpty(oid)){
            ToastUtil.show(mContext,"未获取到订单信息");
            return;
        }
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.order_code2d_detail_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),oid,
                AppConfig.LAT.equals("未开启")?"0":AppConfig.LAT,AppConfig.LNG.equals("未开启")?"0":AppConfig.LNG),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(!StringUtils.isEmpty(object.optString("result"))){
                            CodeDetailBean bean = FastJsonTools.getPerson(object.optString("result"),CodeDetailBean.class);
                            if(bean != null){
                                image = bean.getCode2d_detail().getShop_logo();
                                name = bean.getCode2d_detail().getShop_name();
                                address = bean.getCode2d_detail().getAddress_detail();
                                distance = bean.getCode2d_detail().getDistance();
                                appoint_name = bean.getCode2d_detail().getAppoint_name() == null ?"":bean.getCode2d_detail().getAppoint_name();
                                appoint_phone = bean.getCode2d_detail().getAppoint_phone() == null ?"":bean.getCode2d_detail().getAppoint_phone();
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

            case HttpModeBase.HTTP_REQUESTCODE_2:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        //刷新数据
                        initData();

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

    private void setUI(CodeDetailBean bean) {
        ConsumeCodeBean consumeCodeBean = bean.getCode2d_detail();
        if(consumeCodeBean != null){
            nameTv.setText(consumeCodeBean.getItem_name());
            srvNumTv.setText(consumeCodeBean.getSrv_num()+"次");
            shopNameTv.setText(consumeCodeBean.getShop_name());
        }

        list = bean.getCode2d_list();
        if(list != null && list.size()>0 && adapter != null){
            adapter.clear();
            adapter.addAll(list);
        }
        //page ++ ;//没有分页
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

    @Override
    public void doEventBus(EventEntity event) {
        super.doEventBus(event);
        switch(event.getMsg()){
            case AppConfig.refresh_in_ConsumeCodeDetailActivity:
                initData();
                break;
        }
    }

    /*
    * 弹框取消预约
    */
    private void showCancelDialog(final String msg, final String code2d_id) {
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
                                //
                                cancelAppoint(code2d_id);
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

    //取消预约
    private void cancelAppoint(String code2d_id) {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2, UrlUtils.appoint_cancel_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),oid,code2d_id),true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                initData();
                break;
        }
    }
}
