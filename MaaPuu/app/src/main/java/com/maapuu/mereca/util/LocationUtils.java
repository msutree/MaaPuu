package com.maapuu.mereca.util;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by dell on 2016/5/13.
 * <p/>
 * 高德定位
 */
public class LocationUtils implements AMapLocationListener {

    private Callback mCallback;

    private Context context;

    private AMapLocationClient mLocationClient = null;

    private AMapLocationClientOption mLocationOption = null;

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        mCallback.click(aMapLocation);
    }

    public interface Callback {
        void click(AMapLocation aMapLocation);
    }

    public LocationUtils(Context context, Callback mCallback) {
        this.context = context;
        this.mCallback = mCallback;
        initLocation();
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        mLocationClient = new AMapLocationClient(context);
        mLocationOption = new AMapLocationClientOption();
        mLocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }
}
