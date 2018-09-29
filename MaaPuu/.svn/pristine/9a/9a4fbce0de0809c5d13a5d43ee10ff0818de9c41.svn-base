package com.maapuu.mereca.activity;

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
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.QRCodeUtil;
import com.maapuu.mereca.util.ShareUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/1.
 */

public class MyEwmActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.iv_icon_small)
    SimpleDraweeView ivIconSmall;
    @BindView(R.id.txt_nick_name)
    TextView txtNickName;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.ll_view)
    RelativeLayout llView;

    private String filePath;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_ewm);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("我的二维码");
        if(!StringUtils.isEmpty(LoginUtil.getInfo("avatar"))){
            ivIconSmall.setImageURI(Uri.parse(LoginUtil.getInfo("avatar")));
        }
        txtNickName.setText(LoginUtil.getInfo("nick_name"));

        filePath = getFileRoot(mContext) + File.separator + "qr_" + System.currentTimeMillis() + ".jpg";

        //二维码图片较大时，生成图片、保存文件的时间可能较长，因此放在新线程中
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean success = QRCodeUtil.createQRImage(LoginUtil.getInfo("share_code"), 480, 480,null,filePath);
                if (success) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivCode.setImageBitmap(BitmapFactory.decodeFile(filePath));
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.txt_left,R.id.iv_right})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.iv_right:
                NiceDialog.init().setLayoutId(R.layout.pop_ewm_more)
                        .setConvertListener(new ViewConvertListener() {
                            @Override
                            public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                                holder.setOnClickListener(R.id.txt_cancel, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                holder.setOnClickListener(R.id.txt_save_pic, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        saveCurrentImage();
                                    }
                                });
                                holder.setOnClickListener(R.id.txt_share, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        share();
                                    }
                                });
                            }
                        })
                        .setOutCancel(true).setShowBottom(true).setHeight(155)
                        .show(getSupportFragmentManager());
                break;
        }
    }

    private void share() {
        NiceDialog.init().setLayoutId(R.layout.pop_share)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        holder.setOnClickListener(R.id.txt_pyq, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new ShareUtil(MyEwmActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE,getScreenShot());
                                dialog.dismiss();
                            }
                        });
                        holder.setOnClickListener(R.id.txt_wx, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new ShareUtil(MyEwmActivity.this, SHARE_MEDIA.WEIXIN,getScreenShot());
                                dialog.dismiss();
                            }
                        });
                        holder.setOnClickListener(R.id.txt_qq, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new ShareUtil(MyEwmActivity.this, SHARE_MEDIA.QQ,getScreenShot());
                                dialog.dismiss();
                            }
                        });
                        holder.setOnClickListener(R.id.txt_wb, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new ShareUtil(MyEwmActivity.this, SHARE_MEDIA.SINA,getScreenShot());
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setOutCancel(true).setShowBottom(true)
                .show(getSupportFragmentManager());
    }

    //这种方法状态栏是空白，显示不了状态栏的信息
    private void saveCurrentImage() {
        //获取当前屏幕的大小
        int width = llView.getWidth();
        int height = llView.getHeight();
        //生成相同大小的图片
        Bitmap bitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );
        View dView = llView;
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        if (bitmap != null) {
            BitmapUtils.saveImageToGallery(mContext,bitmap,"screenshot.jpg");
            ToastUtil.show(mContext,"已成功保存到相册");
        }
    }
    private Bitmap getScreenShot() {
        //获取当前屏幕的大小
        int width = llView.getWidth();
        int height = llView.getHeight();
        //生成相同大小的图片
        Bitmap bitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );
        View dView = llView;
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        return bitmap;
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
