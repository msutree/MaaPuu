package com.maapuu.mereca.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.mobileim.YWAPI;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.kyleduo.switchbutton.SwitchButton;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/5.
 */

public class ChatDetailActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.image)
    SimpleDraweeView image;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_set_remark)
    TextView txtSetRemark;
    @BindView(R.id.txt_signture)
    TextView txtSignture;
    @BindView(R.id.sb_not_see_he)
    SwitchButton sbNotSeeHe;
    @BindView(R.id.sb_not_see_me)
    SwitchButton sbNotSeeMe;
    @BindView(R.id.sb_no_message)
    SwitchButton sbNoMessage;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.txt_delete)
    TextView txtDelete;
    @BindView(R.id.txt_refuse)
    TextView txtRefuse;
    @BindView(R.id.txt_send_message)
    TextView txtSendMessage;

    private String friend_uid;
    private String share_code = "";
    private String remark;
    private String is_friend;
    private String msg_id;
    private String is_staff;
    private boolean isVerify = false;
    private boolean isSet = false;
    private boolean isAgree = false;
    private boolean isFirst = true;
    private boolean isFirst1 = true;
    private AlertView alertView;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_chat_detail);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("聊天详情");
        isVerify = getIntent().getBooleanExtra("isVerify",false);
        friend_uid = getIntent().getStringExtra("friend_uid");
        share_code = getIntent().getStringExtra("share_code");
        msg_id = getIntent().getStringExtra("msg_id");

        sbNotSeeHe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isFirst){
                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3, UrlUtils.friend_not_see_he_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                            friend_uid,isChecked?"1":"0"),false);
                }
            }
        });
        sbNotSeeMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isFirst1){
                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3, UrlUtils.friend_not_see_me_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                            friend_uid,isChecked?"1":"0"),false);
                }
            }
        });
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.friend_detail_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),friend_uid,share_code),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        if(!StringUtils.isEmpty(resultObj.optString("avatar"))){
                            image.setImageURI(Uri.parse(resultObj.optString("avatar")));
                        }
                        txtName.setText(resultObj.optString("nick_name"));
//                        if(StringUtils.isEmpty(resultObj.optString("friend_remark"))){
//                            txtName.setText(resultObj.optString("nick_name"));
//                        }else {
//                            txtName.setText(resultObj.optString("friend_remark"));
//                        }
                        share_code = resultObj.optString("share_code");
                        txtSignture.setText(resultObj.optString("signature"));
                        sbNotSeeHe.setChecked(resultObj.optInt("not_see_he") == 1);
                        sbNotSeeMe.setChecked(resultObj.optInt("not_see_me") == 1);
                        friend_uid = resultObj.optString("friend_uid");
                        is_staff = resultObj.optString("is_staff");
                        is_friend = resultObj.optString("is_friend");
                        if(is_friend.equals("1")){
                            ll.setVisibility(View.VISIBLE);
                            txtDelete.setVisibility(View.VISIBLE);
                            txtSendMessage.setVisibility(View.VISIBLE);
                            txtRefuse.setVisibility(View.GONE);
                            txtDelete.setText("删除好友");
                            txtDelete.setBackground(getResources().getDrawable(R.drawable.bg_refuse_btn));
                        }else {
                            ll.setVisibility(View.GONE);
                            txtSendMessage.setVisibility(View.GONE);
                            txtDelete.setVisibility(View.VISIBLE);
                            if(isVerify){//扫一扫添加好友 进入不显示
                                txtDelete.setText("通过验证");
                                txtRefuse.setVisibility(View.VISIBLE);
                                txtRefuse.setText("拒绝添加");
                            }else {
                                txtDelete.setText("添加好友");
                            }
                        }
                        isFirst = false;
                        isFirst1 = false;
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
                        ToastUtil.show(mContext,"设置成功");
                        isSet = true;
                        txtName.setText(remark);
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_3:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext,"设置成功");
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_4:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext,"删除成功");
                        setResult(AppConfig.ACTIVITY_RESULTCODE);
                        finish();
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_5:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(!TextUtils.isEmpty(msg_id)){
                            ToastUtil.show(mContext,object.optString("message"));
                            isSet = true;
                            if(isAgree){
                                txtSetRemark.setVisibility(View.VISIBLE);
                            }
                            initData();
                        }else {
                            ToastUtil.show(mContext,"申请已提交，等待好友同意");
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
    @OnClick({R.id.txt_left,R.id.txt_homepage,R.id.txt_set_remark,R.id.txt_delete,R.id.txt_refuse,R.id.txt_send_message})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                if(isSet){
                    setResult(AppConfig.ACTIVITY_RESULTCODE);
                }
                finish();
                break;
            case R.id.txt_homepage:
                it = new Intent(mContext, PersonalHomepageActivity.class);
                it.putExtra("userpage_uid",friend_uid);
                it.putExtra("is_staff",is_staff);
                startActivity(it);
                break;
            case R.id.txt_set_remark:
                NiceDialog.init().setLayoutId(R.layout.pop_set_remark)
                        .setConvertListener(new ViewConvertListener() {
                            @Override
                            public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                                final EditText etInput = holder.getView(R.id.et_input);
                                etInput.setText(txtName.getText().toString().trim());
                                if(!TextUtils.isEmpty(txtName.getText().toString().trim())){
                                    etInput.setSelection(txtName.getText().toString().trim().length());
                                }
                                TextView txtCommit = holder.getView(R.id.txt_commit);
                                txtCommit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(StringUtils.isEmpty(etInput.getText().toString())){
                                            ToastUtil.show(mContext,"请输入备注名");
                                            return;
                                        }
                                        dialog.dismiss();
                                        remark = etInput.getText().toString();
                                        setRemark(remark);
                                    }
                                });
                            }
                        })
                        .setOutCancel(true).setShowBottom(false).setHeight(220).setWidth(250)
                        .show(getSupportFragmentManager());
                break;
            case R.id.txt_delete:
                if(is_friend.equals("1")){
                    alertView = new AlertView(null, "确定删除吗？", "取消", null, new String[]{"确定"}, mContext,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int pos) {
                            if (pos == 0) {
                                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_4, UrlUtils.friend_delete_set(LoginUtil.getInfo("token"),
                                        LoginUtil.getInfo("uid"),friend_uid),true);
                            }
                        }
                    });
                    alertView.show();
                }else {
                    isAgree = true;
                    if(!TextUtils.isEmpty(msg_id)){
                        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_5,UrlUtils.friend_new_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),msg_id,"1"),true);
                    } else { //扫一扫添加好友
                        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_5,
                                UrlUtils.friend_add_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),share_code),true);
                    }
                }
                break;
            case R.id.txt_refuse:
                isAgree = false;
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_5,UrlUtils.friend_new_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),msg_id,"2"),true);
                break;
            case R.id.txt_send_message:
                if(LoginUtil.getLoginState()){
                    if(AppConfig.mIMKit == null){
                        AppConfig.mIMKit = YWAPI.getIMKitInstance(LoginUtil.getInfo("uid"), AppConfig.APP_KEY);
                    }
                    Intent chat = AppConfig.mIMKit.getChattingActivityIntent(friend_uid);
                    chat.putExtra("uid",friend_uid);
                    startActivity(chat);
                }else {
                    it = new Intent(mContext, LoginActivity.class);
                    startActivity(it);
                }
                break;
        }
    }

    private void setRemark(String remark){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2, UrlUtils.friend_remark_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),friend_uid,remark),true);
    }

    @Override
    public void onBackPressed() {
        if(alertView != null && alertView.isShowing()){
            alertView.dismiss();
        }else {
            if(isSet){
                setResult(AppConfig.ACTIVITY_RESULTCODE);
            }
            finish();
            super.onBackPressed();
        }
    }
}
