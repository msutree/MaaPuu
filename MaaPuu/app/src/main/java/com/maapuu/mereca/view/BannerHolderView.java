package com.maapuu.mereca.view;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.bean.HairStylistBean;

import java.util.List;

/**
 * Created by dell on 2017/7/31.
 */

public class BannerHolderView implements Holder<HairStylistBean> {
    private View view;
    private LayoutInflater layoutInflater;
    private Context context;
    private List<HairStylistBean> advs;
    private TextView txtName;
    private TextView txtPosition;
    private TextView txtIntro;
    private SimpleDraweeView image;

    public BannerHolderView(Context context, List<HairStylistBean> advs) {
        this.context = context;
        this.advs = advs;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View createView(Context context) {
        view = layoutInflater.inflate(R.layout.layout_home_index_banner_item, null);
        txtName = (TextView) view.findViewById(R.id.txt_name);
        txtPosition = (TextView) view.findViewById(R.id.txt_position);
        txtIntro = (TextView) view.findViewById(R.id.txt_intro);
        image = (SimpleDraweeView) view.findViewById(R.id.image);
        return view;
    }

    @Override
    public void UpdateUI(Context context, final int position, HairStylistBean data) {
        txtName.setText(data.getStaff_name());
        txtPosition.setText(data.getPost_name());
        txtIntro.setText(data.getStaff_intro());
        image.setImageURI(Uri.parse(data.getStaff_avatar()));
    }
}
