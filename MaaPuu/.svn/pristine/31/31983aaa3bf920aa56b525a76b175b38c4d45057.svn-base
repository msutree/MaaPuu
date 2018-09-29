package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.BannerBean;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 轮播图详情
 * Created by Jia on 2018/3/13.
 */

public class BannerDetailActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.txt_type)
    TextView txtType;
    @BindView(R.id.txt_label)
    TextView txtLabel;
    @BindView(R.id.txt_value)
    TextView txtValue;

    private BannerBean banner;
    private String shop_id;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_banner_detail);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("轮播图详情");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setText("编辑");
        shop_id = getIntent().getStringExtra("shop_id");

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) image.getLayoutParams();
        lp.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DisplayUtil.getWidthDP(mContext)*2/5, mContext.getResources().getDisplayMetrics()));
        image.setLayoutParams(lp);

        banner = (BannerBean) getIntent().getSerializableExtra("banner");
        if(banner != null){
            UIUtils.loadImg(mContext,banner.getAdv_img(),image);
            switch (banner.getAdv_type()){
                case 1:
                    txtType.setText("发型师");
                    txtLabel.setText("选择发型师");
                    break;
                case 2:
                    txtType.setText("项目");
                    txtLabel.setText("选择项目");
                    break;
                case 3:
                    txtType.setText("商品");
                    txtLabel.setText("选择商品");
                    break;
                case 4:
                    txtType.setText("链接");
                    txtLabel.setText("链接地址");
                    break;
            }
            txtValue.setText(banner.getAdv_text());
        }
    }


    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_right:
                it = new Intent(mContext,AddBannerActivity.class);
                it.putExtra("banner",banner);
                it.putExtra("shop_id",shop_id);
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                setResult(AppConfig.ACTIVITY_RESULTCODE);
                finish();
                break;
        }
    }
}
