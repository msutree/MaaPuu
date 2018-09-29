package com.maapuu.mereca.util;

import android.Manifest;
import android.app.Activity;

import com.maapuu.mereca.bean.ImageTextBean;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 相册选择工具类
 * Created by Jia on 2018/1/24 0024.
 */

public class PictureSelectUtil {

    /**
     * 打开相册
     */
    public static void openPhotos(Activity context, int requestCode) {

        PictureSelector.create(context)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                //.theme(R.style.picture_QQ_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(true)// 是否裁剪
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .isGif(false)// 是否显示gif图片
                //.selectionMedia(selectList)// 是否传入已选图片
                .forResult(requestCode);//结果回调onActivityResult code
    }

    /***
     * 从相册中取图片
     */
    public static void pickPhoto(Activity context,int requestCode, boolean isCrop, boolean isCompress) {
        PictureSelector.create(context)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(isCrop)// 是否裁剪
                .withAspectRatio(3,1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .isGif(false)// 是否显示gif图片
                //.selectionMedia(selectList)// 是否传入已选图片
                .compress(isCompress)// 是否压缩 true or false
                .forResult(requestCode);//结果回调onActivityResult code
    }

    public static void openPhotosMul(Activity context, int requestCode, int maxSelectNum) {

            PictureSelector.create(context)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    //.theme(R.style.picture_QQ_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(maxSelectNum)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                    .previewImage(true)// 是否可预览图片
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .enableCrop(false)// 是否裁剪
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .isGif(false)// 是否显示gif图片
                    //.selectionMedia(selectList)// 是否传入已选图片
                    .compress(true)// 是否压缩 true or false
                    .forResult(requestCode);//结果回调onActivityResult code

    }

    public static void openPhotosMul(Activity context, int requestCode) {

        PictureSelector.create(context)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                //.theme(R.style.picture_QQ_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                //.maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .isGif(false)// 是否显示gif图片
                //.selectionMedia(selectList)// 是否传入已选图片
                .compress(true)// 是否压缩 true or false
                .forResult(requestCode);//结果回调onActivityResult code

    }




    public static void clearCache(Activity activity){
        //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
        if (EasyPermissions.hasPermissions(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PictureFileUtils.deleteCacheDirFile(activity);
        }
    }

    public static List<LocalMedia> getShowList(List<ImageTextBean> list){
        List<LocalMedia> showList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).getContent_type().equals("2")){
                LocalMedia bean = new LocalMedia();
                bean.setPath(list.get(i).getContent());
                bean.setCutPath(list.get(i).getContent());
                bean.setCompressPath(list.get(i).getContent());
                bean.setPosition(i);
                showList.add(bean);
            }
        }
        return showList;
    }

    public static int getShowPos(int position,List<LocalMedia> list){
        int pos = -1;
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).getPosition() == position){
                pos = i;
                break;
            }
        }
        return pos;
    }

    public static void show(Activity activity,int position,List<ImageTextBean> list){
        if(getShowPos(position,getShowList(list)) != -1){
            PictureSelector.create(activity).externalPicturePreview(getShowPos(position,getShowList(list)),
                    getShowList(list));
        }
    }
}
