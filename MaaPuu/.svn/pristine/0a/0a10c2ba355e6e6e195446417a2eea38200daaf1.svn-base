package com.maapuu.mereca.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.RedBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.service.UpdataService;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.JumpPermissionManagement;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.VersionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by dell on 2018/3/1.
 */

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_version)
    TextView txtVersion;
    @BindView(R.id.txt_website)
    TextView txtWebsite;

    private View popView;
    private PopupWindow popupWindow;
    public static final int PERMISSIONCODE = 0x0100;//权限

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about_us);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("关于我们");
        txtVersion.setText("V"+ VersionUtil.getAppVersionName());
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        txtWebsite.setText("Copyright  © 2017-"+year);
    }

    @Override
    public void initData() {
        checkUpdate();
    }

    // 检测-更新
    public void checkUpdate() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.app_update_get(VersionUtil.getAppVersionName(), LoginUtil.getInfo("uid")),false);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(!StringUtils.isEmpty(object.optString("result"))){
                            JSONObject resultObj = object.optJSONObject("result");
                            showPopupWindow(resultObj.optInt("update_type"),resultObj.optString("content"),resultObj.optString("download_url"));
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

    private void showPopupWindow(int update_type, String content, final String apkUrl) {
        popView = LayoutInflater.from(mContext).inflate(R.layout.pop_update, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        LinearLayout llClose = (LinearLayout) popView.findViewById(R.id.ll_close);
        if(update_type == 2){
            llClose.setVisibility(View.GONE);
        }else {
            llClose.setVisibility(View.VISIBLE);
        }
        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        LinearLayout ll = (LinearLayout) popView.findViewById(R.id.ll);
        RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams) ll.getLayoutParams();
        linearParams.width=((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DisplayUtil.getWidthDP(mContext)*4/5, getResources().getDisplayMetrics()));
        linearParams.height=linearParams.width*5/3;
        ll.setLayoutParams(linearParams);
        View btn_update = popView.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EasyPermissions.hasPermissions(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Intent it = new Intent(mContext,UpdataService.class);
                    it.putExtra("url",apkUrl);
                    startService(it);
                }else {
                    ToastUtil.show(mContext,"请打开存储权限");
                    JumpPermissionManagement.GoToSetting(AboutUsActivity.this);
                }
            }
        });
        TextView update_info = (TextView) popView.findViewById(R.id.update_info);
        update_info.setText(content);

        // 设置好参数之后再show
        popupWindow.showAtLocation(popView, Gravity.CENTER,0,0);
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
