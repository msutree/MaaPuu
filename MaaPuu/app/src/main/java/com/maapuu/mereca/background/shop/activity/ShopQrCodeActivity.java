package com.maapuu.mereca.background.shop.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.util.BitmapUtils;
import com.maapuu.mereca.util.QRCodeUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 店铺二维码
 * Created by Jia on 2018/3/13.
 */

public class ShopQrCodeActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.rl_view)
    RelativeLayout rlView;
    @BindView(R.id.iv_shop)
    SimpleDraweeView ivShop;
    @BindView(R.id.txt_shop_name)
    TextView txtShopName;
    @BindView(R.id.txt_fans_num)
    TextView txtFansNum;
    @BindView(R.id.shop_qr_code_iv)
    ImageView qrCodeIv;

    private String filePath;
    private String shop_code;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_shop_qr_code);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("店铺二维码");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight.setText("\ue64e");//&#xe64e;
        txtRight.setTextSize(4);
        ivShop.setImageURI(Uri.parse(getIntent().getStringExtra("shop_logo")));
        txtShopName.setText(getIntent().getStringExtra("shop_name"));
        txtFansNum.setText("粉丝数"+getIntent().getStringExtra("attention_num"));
        shop_code = getIntent().getStringExtra("shop_code");
    }

    @Override
    public void initData() {

        filePath = getFileRoot(mContext) + File.separator + "shop_qr_" + System.currentTimeMillis() + ".jpg";

        //二维码图片较大时，生成图片、保存文件的时间可能较长，因此放在新线程中
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean success = QRCodeUtil.createQRImage(shop_code, 480, 480,null,filePath);
                if (success) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            qrCodeIv.setImageBitmap(BitmapFactory.decodeFile(filePath));
                        }
                    });
                }
            }
        }).start();

    }

    @Override
    @OnClick({R.id.txt_left, R.id.txt_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;

            case R.id.txt_right:
                NiceDialog.init().setLayoutId(R.layout.pop_ewm_more)
                        .setConvertListener(new ViewConvertListener() {
                            @Override
                            public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                                TextView txtCancel = holder.getView(R.id.txt_cancel);
                                TextView txtSave = holder.getView(R.id.txt_save_pic);
                                txtSave.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        saveCurrentImage();
                                    }
                                });
                                txtCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                            }
                        })
                        .setOutCancel(true).setShowBottom(true).setHeight(155)
                        .show(getSupportFragmentManager());
                break;

        }
    }

    //这种方法状态栏是空白，显示不了状态栏的信息
    private void saveCurrentImage() {
        //获取当前屏幕的大小
        int width = rlView.getWidth();
        int height = rlView.getHeight();
        //生成相同大小的图片
        Bitmap bitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );
        View dView = rlView;
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        if (bitmap != null) {
            BitmapUtils.saveImageToGallery(mContext,bitmap, "screenshot.png");
            ToastUtil.show(mContext,"已成功保存到相册");
        }
    }

    //文件存储根目录
    private String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }
        return context.getFilesDir().getAbsolutePath();
    }

}
