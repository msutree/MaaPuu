package com.maapuu.mereca.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.maapuu.mereca.bean.ShareBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import info.wangchen.simplehud.SimpleHUD;

/**
 * Created by dell on 2018/4/27.
 */

public class ShareUtil implements UMShareListener {
    private Context mContext;

    public ShareUtil(Activity activity, SHARE_MEDIA share_media, ShareBean shareBean) {
        this.mContext = activity.getBaseContext();
        UMImage image = new UMImage(mContext, shareBean.getLogo());
        UMWeb web = new UMWeb(shareBean.getUrl());
        web.setTitle(shareBean.getTitle());//标题
        web.setThumb(image);  //缩略图
        web.setDescription(shareBean.getDesc());//描述
        new ShareAction(activity).withMedia(web)
                .setPlatform(share_media)
                .setCallback(this).share();
    }

    public ShareUtil(Activity activity, SHARE_MEDIA share_media, Bitmap bitmap) {
        this.mContext = activity.getBaseContext();
        UMImage image = new UMImage(mContext, bitmap);
        new ShareAction(activity).withMedia(image)
                .setPlatform(share_media)
                .setCallback(this).share();
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        SimpleHUD.dismiss();
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        ToastUtil.show(mContext,"分享成功");
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        ToastUtil.show(mContext,"分享失败");
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        ToastUtil.show(mContext,"分享取消");
    }
}
