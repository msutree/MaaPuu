package com.maapuu.mereca.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.GridImageAdapter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.SearchAddress;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.FullyGridLayoutManager;
import com.maapuu.mereca.util.BitmapUtils;
import com.maapuu.mereca.util.DataCleanManager;
import com.maapuu.mereca.util.EmojiUtil;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.JumpPermissionManagement;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.iceteck.silicompressorr.VideoCompress;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import info.wangchen.simplehud.SimpleHUD;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by dell on 2018/3/1.
 * 发布动态
 */

public class PublishTrendsActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rv_img)
    RecyclerView rvImg;
    @BindView(R.id.ll_article)
    LinearLayout llArticle;
    @BindView(R.id.iv_article_pic)
    SimpleDraweeView ivArticlePic;
    @BindView(R.id.txt_artical_title)
    TextView txtArticleTitle;
    @BindView(R.id.txt_location)
    TextView txtLocation;
    @BindView(R.id.txt_kk_type)
    TextView txtKkType;
    @BindView(R.id.txt_kk_person)
    TextView txtKkPerson;
    @BindView(R.id.txt_sync)
    TextView txtSync;

    private AlertView alertView;
    private GridImageAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 9;

    private String is_staff = "";
    private boolean isShare;
    private int type = 0;//0照片   1视频
    private int open_type = 1; //公开方式：1公开；2好友可见；3私密
    private SearchAddress searchAddress;
    private String lat = "0";
    private String lng = "0";
    private String address_detail = "";
    private StringBuffer buffer = new StringBuffer();//文字、图片拼接json字符串
    private int imgListPos = 0;//上传的图片下标
    private String videoUploadImagePath;
    private String videoImagePath;

    private int trans_type;
    private String trans_id;
    private String trans_title = "";
    private String trans_img = "";

    private String outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()
            + File.separator + "VID_compress.mp4";
    private String inputDir="";

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_publish_trends);
    }

    @Override
    public void initView() {
        isShare = getIntent().getBooleanExtra("is_share",false);
//        is_staff = getIntent().getStringExtra("is_staff");
        trans_type = getIntent().getIntExtra("trans_type",0);
        if(isShare){
            llArticle.setVisibility(View.VISIBLE);rvImg.setVisibility(View.GONE);
            txtSync.setVisibility(View.GONE);
            trans_id = getIntent().getStringExtra("trans_id");
            trans_img = getIntent().getStringExtra("trans_img");
            if(StringUtils.isEmpty(trans_img)){
                ivArticlePic.setVisibility(View.GONE);
            }else {
                ivArticlePic.setVisibility(View.VISIBLE);
                ivArticlePic.setImageURI(Uri.parse(trans_img));
            }
            trans_title = getIntent().getStringExtra("trans_title");
            txtArticleTitle.setText(trans_title);
        }else {
            llArticle.setVisibility(View.GONE);rvImg.setVisibility(View.VISIBLE);
            txtSync.setVisibility(View.VISIBLE);
        }
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("发布动态");
        type = getIntent().getIntExtra("type",0);

        if(type == 0){
            maxSelectNum = 9;
        }else {
            maxSelectNum = 1;
        }
        initRv();
        buffer.delete(0,buffer.length());//先清除原来的数据
        buffer.append("[");
    }

    private void initRv() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
        rvImg.setLayoutManager(manager);
        rvImg.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL,10,
                getResources().getColor(R.color.white)));
        adapter = new GridImageAdapter(mContext, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum-selectList.size());
        rvImg.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(type == 0){
                    PictureSelector.create(PublishTrendsActivity.this).externalPicturePreview(position, adapter.getList());
                }else {
                    PictureSelector.create(PublishTrendsActivity.this).externalPictureVideo(adapter.getList().get(position).getPath());
                }
            }
        });
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2, UrlUtils.user_page_info_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),2),false);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                SimpleHUD.dismiss();
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext,object.optString("message"));
                        setResult(AppConfig.ACTIVITY_RESULTCODE);
                        finish();
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_2:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        is_staff = resultObj.optString("is_staff");
                        if(StringUtils.isEmpty(is_staff)){
                            txtSync.setVisibility(View.GONE);
                        }else {
                            if(is_staff.equals("1")){
                                txtSync.setVisibility(View.VISIBLE);
                            }else {
                                txtSync.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_IMG:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.has("src") && !object.optString("src").equals("")) {
                        JSONObject resultObj = object.optJSONObject("result");
                        videoImagePath = resultObj.optString("src")+"\",\"width\":\"" +
                                resultObj.optString("width") + "\",\"height\":\"" + resultObj.optString("height") + "\"";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                compress();
                break;
            case HttpModeBase.HTTP_REQUESTCODE_VIDEO:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.has("src") && !object.optString("src").equals("")) {
                        JSONObject resultObj = object.optJSONObject("result");
                        String str = "{\"type\":\"3\",\"content\":\""+resultObj.optString("src")+"\",\"duration\":\"" +
                                adapter.getList().get(0).getDuration() + "\",\"first_frame\":\"" + videoImagePath + "}";
                        buffer.append(str);
                    }
                    File mFile = new File(outputDir);
                    if (mFile.exists()) {
                        mFile.delete();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                publish(false);
                break;
            case HttpModeBase.HTTP_REQUESTCODE_IMG_MUL:
                if (msg.arg1 == 1) {
                    try {
                        JSONObject object = new JSONObject((String) msg.obj);
                        if (object.has("src") && !object.optString("src").equals("")) {
                            JSONObject resultObj = object.optJSONObject("result");
                            String str = "{\"type\":\"2\",\"content\":\""+resultObj.optString("src")+"\",\"width\":\"" +
                                    resultObj.optString("width") + "\",\"height\":\"" + resultObj.optString("height") + "\"},";
                            buffer.append(str);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(msg.arg2 == adapter.getList().size() - 1){
                    publish(false);
                }else {
                    imgListPos++;
                    image_save_set(adapter.getList().get(imgListPos).getCompressPath(), imgListPos, false);
                }
                break;
            case HttpModeBase.HTTP_ERROR:
                SimpleHUD.dismiss();
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
    }

    private void compress(){
        File mFile = new File(outputDir);
        if (mFile.exists()) {
            mFile.delete();
        }
        VideoCompress.compressVideoLow(inputDir, outputDir, new VideoCompress.CompressListener() {
            @Override
            public void onStart() {
                Log.i("compress","开始时间:"+System.currentTimeMillis());
            }

            @Override
            public void onSuccess() {
                Log.i("compress","结束时间:"+System.currentTimeMillis());
                Log.i("compress","压缩后大小 = "+ DataCleanManager.getFileSize(outputDir));
                uploadVideo(outputDir);
            }

            @Override
            public void onFail() {
                Log.i("compress","失败时间:"+System.currentTimeMillis());
                uploadVideo(inputDir);
            }

            @Override
            public void onProgress(float percent) {
                Log.i("compress",String.valueOf(percent) + "%");
            }
        });
    }

    private void uploadVideo(String compressUrl){
        mHttpModeBase.uploadVideo(HttpModeBase.HTTP_REQUESTCODE_VIDEO, compressUrl, false);
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_location,R.id.rl_kekan,R.id.txt_sync,R.id.txt_publish})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_location:
                if (EasyPermissions.hasPermissions(mContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    it = new Intent(mContext,LocateActivity.class);
                    startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
                }else {
                    alertView = new AlertView(null, "需开启定位权限", "取消", null, new String[]{"确定"}, mContext,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 0) {
                                JumpPermissionManagement.GoToSetting(PublishTrendsActivity.this);
                            }
                        }
                    });
                    alertView.show();
                }
                break;
            case R.id.rl_kekan:
                it = new Intent(mContext,WhoCanSeeActivity.class);
                it.putExtra("open_type",open_type);
                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
                break;
            case R.id.txt_sync:
                txtSync.setSelected(!txtSync.isSelected());
                break;
            case R.id.txt_publish:
                if(isShare){
                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.mo_publish_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                            EmojiUtil.stringToUtf8(etContent.getText().toString()).replace("+","%20"), "",lng,lat,address_detail,trans_type,
                            EmojiUtil.stringToUtf8(trans_title).replace("+","%20"),trans_img,trans_id,open_type,
                            txtSync.isSelected()?1:0),true);
                }else {
                    if(StringUtils.isEmpty(etContent.getText().toString()) && adapter.getList().size() == 0){
                        ToastUtil.show(mContext,"请输入发布内容");
                        return;
                    }
                    if(adapter.getList().size() > 0){
                        if(type == 0){
                            image_save_set(adapter.getList().get(imgListPos).getCompressPath(), imgListPos, true);
                        }else {
                            mHttpModeBase.uploadImage(HttpModeBase.HTTP_REQUESTCODE_IMG, videoUploadImagePath, true);
                        }
                    }else {
                        publish(true);
                    }
                }
                break;
        }
    }

    private void publish(boolean bool) {
        if(buffer.toString().endsWith(",")){
            buffer.deleteCharAt(buffer.length()-1);
        }
        buffer.append("]");
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.mo_publish_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                EmojiUtil.stringToUtf8(etContent.getText().toString()).replace("+","%20"),adapter.getList().size() == 0 ? "" : buffer.toString(),lng,lat,address_detail,
                trans_type,trans_title,trans_img,trans_id,open_type, txtSync.isSelected()?1:0),bool);
    }

    private void image_save_set(String imagePath, int position, Boolean isProgress) {
        mHttpModeBase.xutilsPostList(HttpModeBase.HTTP_REQUESTCODE_IMG_MUL, imagePath, position, isProgress);
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PictureSelector.create(PublishTrendsActivity.this)
                    .openGallery(type == 0 ? PictureMimeType.ofImage():PictureMimeType.ofVideo())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .maxSelectNum(maxSelectNum - adapter.getList().size())// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .withAspectRatio(3,2)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                    .previewImage(true)// 是否可预览图片
                    .previewVideo(true)
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .enableCrop(false)// 是否裁剪
                    .videoMaxSecond(16)// 显示多少秒以内的视频or音频也可适用 int
                    .recordVideoSecond(15)//视频秒数录制 默认60s int
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .isGif(false)// 是否显示gif图片
                    .enableCrop(false)// 是否裁剪
                    .compress(true)// 是否压缩 true or false
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                searchAddress = (SearchAddress) data.getSerializableExtra("searchAddress");
                lat = searchAddress.getLatitude();
                lng = searchAddress.getLongitude();
                address_detail = searchAddress.getName();
                txtLocation.setText(address_detail);
                break;
            case AppConfig.ACTIVITY_RESULTCODE_1:
                open_type = data.getIntExtra("open_type",1);
                if(open_type == 1){
                    txtKkType.setText("公开");txtKkPerson.setText("所有人可见");
                }else if(open_type == 2){
                    txtKkType.setText("好友可见");txtKkPerson.setText("所有好友可见");
                }else {
                    txtKkType.setText("私密");txtKkPerson.setText("仅自己可见");
                }
                break;
            case RESULT_OK:
                if (data != null && requestCode == PictureConfig.CHOOSE_REQUEST){
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    adapter.addList(selectList);
                    adapter.notifyDataSetChanged();
                    if(type == 1){
                        inputDir = adapter.getList().get(0).getPath();
                        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                        retriever.setDataSource(selectList.get(0).getPath());
                        Bitmap bitmap = retriever.getFrameAtTime();
                        videoUploadImagePath = BitmapUtils.saveImageToGallery(mContext, bitmap,"video.jpg");
                        bitmap.recycle();
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearCache();
    }

    private void clearCache(){
        //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PictureFileUtils.deleteCacheDirFile(mContext);
        }
    }

    @Override
    public void onBackPressed() {
        if(alertView != null && alertView.isShowing()){
            alertView.dismiss();
        }else {
            super.onBackPressed();
        }
    }
}
