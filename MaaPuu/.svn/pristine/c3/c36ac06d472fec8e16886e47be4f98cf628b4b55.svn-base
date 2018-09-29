package com.maapuu.mereca.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.maapuu.mereca.util.HttpModeBase;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment implements OnClickListener ,Handler.Callback{
    public Context mContext;
    public Intent it;
    public View layoutView;
    public Handler mHandler;
    public HttpModeBase mHttpModeBase;
    protected Bundle mBundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        initBundle(mBundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutView = inflater.inflate(setContentViewById(), null);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutView.setLayoutParams(lp);
        ButterKnife.bind(this, layoutView);
        mHandler = new Handler(mContext.getMainLooper(),this);
        mHttpModeBase = new HttpModeBase(mHandler, mContext);
        initView(layoutView);
        initData();
        return layoutView;
    }

    protected void initBundle(Bundle bundle) {
    }

    protected abstract int setContentViewById();

    protected abstract void initView(View v);

    protected abstract void initData();

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    protected String getInstanceTag() {
        return this.getClass().getSimpleName();
    }

    public void loadData(){}
}
