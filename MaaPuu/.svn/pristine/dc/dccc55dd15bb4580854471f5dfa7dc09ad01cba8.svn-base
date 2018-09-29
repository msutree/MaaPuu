package com.maapuu.mereca.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.gyf.barlibrary.ImmersionBar;
import com.maapuu.mereca.R;
import com.maapuu.mereca.util.HttpModeBase;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener,Handler.Callback {
    public Context mContext;
    public Intent it;
    public Handler mHandler;
    public HttpModeBase mHttpModeBase;
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        initContentView(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.white).statusBarDarkFont(true,0f).init();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ButterKnife.bind(this);
        mContext = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mHandler = new Handler(mContext.getMainLooper(),this);
        mHttpModeBase = new HttpModeBase(mHandler, mContext);
        initView();
        initData();
    }

    public abstract void initContentView(Bundle savedInstanceState);

    public abstract void initView();

    public abstract void initData();

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }

    protected String getInstanceTag() {
        return this.getClass().getSimpleName();
    }
}
