package com.maapuu.mereca.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.maapuu.mereca.event.EventEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * EventBus基类
 * Created by dell on 2017/7/29.
 */

public abstract class BaseEventFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doEventBus(EventEntity event){
        if(event == null) return;
    }

}
