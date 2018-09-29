package com.maapuu.mereca.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.StartAdapter;
import com.maapuu.mereca.base.AppConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 引导页
 */
public class StartActivity extends Activity implements EasyPermissions.PermissionCallbacks{

    private Context mContext;

    private Intent intent;

    private SharedPreferences.Editor edit;

    private SharedPreferences sp;

    private boolean isFirst;

    private ArrayList<ImageView> views;

    private StartAdapter adapter;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sp = getSharedPreferences("data", MODE_PRIVATE);
        edit = sp.edit();
        isFirst = sp.getBoolean("isFirst", true);
        // 应用首次启动
        if (isFirst == false) {
            // 应用非首次启动
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        mContext = this;
        requestLocationPermission();
        initView();
    }

    private void initView() {

        views = new ArrayList<>();

        ImageView view1 = new ImageView(mContext);
        view1.setImageResource(R.mipmap.ydt1);
        ImageView view2 = new ImageView(mContext);
        view2.setImageResource(R.mipmap.ydt2);
        ImageView view3 = new ImageView(mContext);
        view3.setImageResource(R.mipmap.ydt3);
//        ImageView view4 = new ImageView(mContext);
//        view4.setImageResource(R.mipmap.launch_d);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        view1.setLayoutParams(layoutParams);
        view2.setLayoutParams(layoutParams);
        view3.setLayoutParams(layoutParams);
//        view4.setLayoutParams(layoutParams);


        view1.setScaleType(ImageView.ScaleType.FIT_XY);
        view2.setScaleType(ImageView.ScaleType.FIT_XY);
        view3.setScaleType(ImageView.ScaleType.FIT_XY);
//        view4.setScaleType(ImageView.ScaleType.FIT_XY);

        views.add(view1);
        views.add(view2);
        views.add(view3);
//        views.add(view4);

        adapter = new StartAdapter(views);

        viewPager.setAdapter(adapter);

        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.putBoolean("isFirst", false);
                edit.commit();//改变状态后一定要提交
                intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @AfterPermissionGranted(AppConfig.PERMISSIONCODE)
    private void requestLocationPermission() {
        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE)) {
        } else {
            EasyPermissions.requestPermissions(StartActivity.this,"您需要开启定位权限", AppConfig.PERMISSIONCODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {}

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults,this);
    }
}
