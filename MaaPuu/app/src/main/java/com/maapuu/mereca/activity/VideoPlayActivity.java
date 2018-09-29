package com.maapuu.mereca.activity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.barlibrary.ImmersionBar;
import com.maapuu.mereca.R;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ImageTextBean;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoPlayActivity extends BaseActivity {

    @BindView(R.id.vp_content_video)
    NiceVideoPlayer mNiceVideoPlayer;

    ImageTextBean entity;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_video_play);
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.text_33).init();
        entity = (ImageTextBean) getIntent().getSerializableExtra("entity");
    }

    @Override
    public void initView() {
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_NATIVE); // or NiceVideoPlayer.TYPE_NATIVE
        mNiceVideoPlayer.setUp(entity.getContent(), null);

        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("");
//        List<Clarity> clarities = new ArrayList<>();
//        clarities.add(new Clarity("高清", "480P", entity.getContent()));
//        controller.setClarity(clarities,0);
//        controller.imageView().setImageURI(Uri.parse(entity.getFirst_frame()));
        RequestOptions options = new RequestOptions().error(R.mipmap.nopic);
        Glide.with(mContext).load(entity.getFirst_frame()).apply(options).into(controller.imageView());
        mNiceVideoPlayer.setController(controller);
        mNiceVideoPlayer.start();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

    @Override
    @OnClick({R.id.iv_left})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
        }
    }
}
