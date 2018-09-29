package com.maapuu.mereca.background.employee.fragment;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.employee.activity.MyAppointmentActivity;
import com.maapuu.mereca.background.employee.activity.MyServiceActivity;
import com.maapuu.mereca.background.employee.activity.SalaryDetailActivity;
import com.maapuu.mereca.background.employee.activity.TotalIncomeActivity;
import com.maapuu.mereca.background.shop.fragment.ShopHomeFragment;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.camera.CaptureActivity;
import com.maapuu.mereca.util.JumpPermissionManagement;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 工作狂 主页
 * Created by Jia on 2018/2/28.
 */

public class WorkHomeFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks{

    @BindView(R.id.em_my_service_arrow)
    TextView myServiceArrow;
    @BindView(R.id.em_yuyue_arrow)
    TextView yuyueArrow;

    @BindView(R.id.em_total_income_arrow)
    TextView totalIncomeArrow;
    @BindView(R.id.em_wage_detail_arrow)
    TextView wageDetailArrow;
    @BindView(R.id.em_free_arrow)
    TextView freeArrow;
    @BindView(R.id.em_entry_arrow)
    TextView entryArrow;


    @Override
    protected int setContentViewById() {
        return R.layout.em_fragment_work_home;
    }

    @Override
    protected void initView(View v) {
        myServiceArrow.setTypeface(StringUtils.getFont(mContext));
        yuyueArrow.setTypeface(StringUtils.getFont(mContext));
        totalIncomeArrow.setTypeface(StringUtils.getFont(mContext));
        wageDetailArrow.setTypeface(StringUtils.getFont(mContext));
        freeArrow.setTypeface(StringUtils.getFont(mContext));
        entryArrow.setTypeface(StringUtils.getFont(mContext));


    }

    @Override
    protected void initData() {

    }

    @Override
    @OnClick({R.id.em_my_service_rt,R.id.em_yuyue_rt,R.id.em_total_income_rt,R.id.em_wage_detail_rt,R.id.em_entry,R.id.em_free_rt})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.em_my_service_rt:
                UIUtils.startActivity(getActivity(),MyServiceActivity.class);
                break;
            case R.id.em_yuyue_rt:
                UIUtils.startActivity(getActivity(),MyAppointmentActivity.class);
                break;
            case R.id.em_total_income_rt:
                UIUtils.startActivity(getActivity(),TotalIncomeActivity.class);
                break;
            case R.id.em_wage_detail_rt:
                it = new Intent(mContext, SalaryDetailActivity.class);
                it.putExtra("uid", LoginUtil.getInfo("uid"));
                startActivity(it);
//                UIUtils.startActivity(getActivity(),SalaryDetailActivity.class);
                break;
            case R.id.em_entry:
                requestLocationPermission();
                break;
            case R.id.em_free_rt://自由身
                getActivity().finish();
                break;
        }
    }

    @AfterPermissionGranted(AppConfig.PERMISSIONCODE)
    private void requestLocationPermission() {
        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.CAMERA)) {
            it = new Intent(mContext, CaptureActivity.class);
            it.putExtra("isAddFriends",false);
            it.putExtra("isEntry",true);
            startActivity(it);
        }else {
            EasyPermissions.requestPermissions(this,"您需要开启相机权限", AppConfig.PERMISSIONCODE,
                    Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        new AlertView(null, "使用扫一扫功能，需打开相机权限？", "取消", null, new String[]{"确定"}, mContext,
                AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position == 0) {
                    JumpPermissionManagement.GoToSetting(getActivity());
                }
            }
        }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults,WorkHomeFragment.this);
    }
}
