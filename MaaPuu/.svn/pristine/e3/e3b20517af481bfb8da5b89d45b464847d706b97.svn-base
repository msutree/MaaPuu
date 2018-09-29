package com.maapuu.mereca.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.GridImageAdapter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyGridLayoutManager;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import info.wangchen.simplehud.SimpleHUD;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by dell on 2018/3/1.
 * 申请退款
 */

public class ApplyRefundActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_reason)
    TextView txtReason;
    @BindView(R.id.txt_refund_type)
    TextView txtRefundType;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rv_img)
    RecyclerView rvImg;

    private List<String> list;
    private List<String> reasonList;
    private GridImageAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 3;

    private StringBuffer buffer = new StringBuffer();//文字、图片拼接json字符串
    private int imgListPos = 0;//上传的图片下标
    private String order_detail_id;
    private String refund_amount;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_apply_refund);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("申请退款");
        list = new ArrayList<>();
        reasonList = new ArrayList<>();
        order_detail_id = getIntent().getStringExtra("order_detail_id");
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
                PictureSelector.create(ApplyRefundActivity.this).externalPicturePreview(position, adapter.getList());
            }
        });
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.order_refund_init_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                order_detail_id),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        if(resultObj.has("refund_type") && !StringUtils.isEmpty(resultObj.optString("refund_type"))){
                            list = FastJsonTools.getPersons(resultObj.optString("refund_type"),String.class);
                        }
                        if(resultObj.has("refund_reason") && !StringUtils.isEmpty(resultObj.optString("refund_reason"))){
                            reasonList = FastJsonTools.getPersons(resultObj.optString("refund_reason"),String.class);
                        }
                        refund_amount = resultObj.optString("refund_amount");
                        txtPrice.setText("¥"+resultObj.optString("refund_amount"));
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_2:
                SimpleHUD.dismiss();
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext,"申请提交成功");
                        setResult(AppConfig.ACTIVITY_RESULTCODE);
                        finish();
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_IMG:
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
                    image_save_set(adapter.getList().get(imgListPos).getCutPath(), imgListPos, false);
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

    @Override
    @OnClick({R.id.txt_left,R.id.rl_refund_type,R.id.rl_reason,R.id.txt_commit})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.rl_refund_type:
                if(list.size() > 0){
                    showDialog(0,list);
                }
                break;
            case R.id.rl_reason:
                if(reasonList.size() > 0){
                    showDialog(1,reasonList);
                }
                break;
            case R.id.txt_commit:
                if(StringUtils.isEmpty(txtRefundType.getText().toString())){
                    ToastUtil.show(mContext,"请选择退款类型");
                    return;
                }
                if(StringUtils.isEmpty(txtReason.getText().toString())){
                    ToastUtil.show(mContext,"请选择退款原因");
                    return;
                }
                if(adapter.getList().size() > 0){
                    image_save_set(adapter.getList().get(imgListPos).getCutPath(), imgListPos, true);
                }else {
                    publish(true);
                }
                break;
        }
    }

    private void publish(boolean bool) {
        if(buffer.toString().endsWith(",")){
            buffer.deleteCharAt(buffer.length()-1);
        }
        buffer.append("]");
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2, UrlUtils.order_refund_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),order_detail_id,
                txtRefundType.getText().toString(),txtReason.getText().toString(),refund_amount,etContent.getText().toString(),
                adapter.getList().size() == 0 ? "" : buffer.toString()),bool);
    }

    private void image_save_set(String imagePath, int position, Boolean isProgress) {
        mHttpModeBase.xutilsPostList(HttpModeBase.HTTP_REQUESTCODE_IMG, imagePath, position, isProgress);
    }

    private void showDialog(final int pos, final List<String> list) {
        NiceDialog.init().setLayoutId(R.layout.pop_bottom_menu)
                .setConvertListener(new ViewConvertListener() {

                    @Override
                    public void convertView(final ViewHolder holder, final BaseNiceDialog dialog) {
                        TextView cancelTv = holder.getView(R.id.pop_cancel_tv);
                        cancelTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        RecyclerView rv = holder.getView(R.id.pop_rv);
                        BaseRecyclerAdapter<String> adapter = new BaseRecyclerAdapter<String>(mContext,list,R.layout.pop_item_bottom_menu) {
                            @Override
                            public void convert(BaseRecyclerHolder baseHolder, final String item, int position, boolean isScrolling) {
                                final TextView menuTv = baseHolder.getView(R.id.bm_title);
                                menuTv.setText(item);
                                menuTv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(pos == 0){
                                            txtRefundType.setText(item);
                                        }else {
                                            txtReason.setText(item);
                                        }
                                        dialog.dismiss();
                                    }
                                });
                            }
                        };
                        rv.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL,2,
                                getResources().getColor(R.color.background)));
                        rv.setLayoutManager(new LinearLayoutManager(mContext));
                        rv.setAdapter(adapter);
                    }
                })
                .setOutCancel(true).setShowBottom(true)
                //.setHeight(270)
                .show(getSupportFragmentManager());
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PictureSelector.create(ApplyRefundActivity.this)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .maxSelectNum(maxSelectNum - adapter.getList().size())// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
//                    .withAspectRatio(3,2)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                    .freeStyleCropEnabled(true)
                    .previewImage(true)// 是否可预览图片
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .enableCrop(false)// 是否裁剪
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .isGif(false)// 是否显示gif图片
                    .enableCrop(true)// 是否裁剪
                    .compress(true)// 是否压缩 true or false
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST){
                // 图片选择结果回调
                selectList = PictureSelector.obtainMultipleResult(data);
                adapter.addList(selectList);
                adapter.notifyDataSetChanged();
            }
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
}
