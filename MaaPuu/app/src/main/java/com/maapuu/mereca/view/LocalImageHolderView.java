package com.maapuu.mereca.view;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.bean.BannerBean;

import java.util.List;

/**
 * Created by dell on 2017/7/31.
 */

public class LocalImageHolderView implements Holder<BannerBean> {
    private SimpleDraweeView imageView;
    private Context context;
    private List<BannerBean> advs;

    public LocalImageHolderView(Context context, List<BannerBean> advs) {
        this.context = context;
        this.advs = advs;
    }

    @Override
    public View createView(Context context) {
        imageView = new SimpleDraweeView(context);
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setFailureImage(context.getResources().getDrawable(R.mipmap.nopic))
                .setFailureImageScaleType(ScalingUtils.ScaleType.CENTER)
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                .build();
        imageView.setHierarchy(hierarchy);
        imageView.setAspectRatio(3);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, BannerBean data) {
        imageView.setImageURI(Uri.parse(data.getAdv_img()));
    }
}
