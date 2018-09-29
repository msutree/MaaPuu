package com.maapuu.mereca.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.callback.ImageLoadCallback;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

public class BitmapUtils {
    public static int max = 0;
    public static boolean act_bool = true;
    public static List<Bitmap> bmp = new ArrayList<Bitmap>();

    // 图片sd地址 上传服务器时把图片调用下面方法压缩后 保存到临时文件夹 图片压缩后小于100KB，失真度不明显
    public static List<String> drr = new ArrayList<String>();
    /**
     * 压缩图片
     *
     * @param picturePath
     * @param type        0相机图片，1相册图片
     * @return
     */
    public static String compressJPG(String picturePath, int type) {
        Options options = new Options();
        options.inSampleSize = 1;
        Bitmap bm = null;
        if (type == 1) {
            bm = BitmapFactory.decodeByteArray(
                    decodeBitmap(picturePath), 0,
                    decodeBitmap(picturePath).length);
            // Bitmap bm = byteToBitmap(decodeBitmap(picturePath));
        } else {
            bm = BitmapFactory.decodeFile(picturePath, options); // 原始bitmap
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            baos.toByteArray();
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            // 进行缩放
            Options newOpts = new Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);// 此时返回bm为空
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
            float hh = 800f;// 这里设置高度为800f
            float ww = 480f;// 这里设置宽度为480f
            // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;// be=1表示不缩放
            if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = 2;// 设置缩放比例
            // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            // bitmap = BitmapFactory.decodeStream(is, null, newOpts);
            bm = BitmapFactory.decodeByteArray(baos.toByteArray(), 0,
                    baos.toByteArray().length, newOpts);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_",
                Locale.CHINA).format(new Date());
        timeStamp = timeStamp + String.valueOf((int) (Math.random() * 1000));
        String nativePath = null;
        nativePath = saveToJPEG(baos.toByteArray(), timeStamp);

        return nativePath;
    }

    /**
     * 压缩图片
     *
     * @param picturePath
     * @return
     */
    public static String compressJPG(String picturePath) {
        Options options = new Options();
//        options.inSampleSize = 1;
        options.inSampleSize =computeSampleSize(options, -1, 1024 * 1000);
        Bitmap bm = null;
        try {
            bm = BitmapFactory.decodeByteArray(decodeBitmap(picturePath), 0, decodeBitmap(picturePath).length);
        } catch (Exception e) {
            bm = BitmapFactory.decodeFile(picturePath, options); // 原始bitmap
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            baos.toByteArray();
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            // 进行缩放
            Options newOpts = new Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);// 此时返回bm为空
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
            float hh = 800f;// 这里设置高度为800f
            float ww = 480f;// 这里设置宽度为480f
            // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;// be=1表示不缩放
            if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize =computeSampleSize(newOpts, -1, 1024 * 1000);// 设置缩放比例
            // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            // bitmap = BitmapFactory.decodeStream(is, null, newOpts);
            bm = BitmapFactory.decodeByteArray(baos.toByteArray(), 0,
                    baos.toByteArray().length, newOpts);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_",
                Locale.CHINA).format(new Date());
        timeStamp = timeStamp + String.valueOf((int) (Math.random() * 1000));
        String nativePath = null;
        nativePath = saveToJPEG(baos.toByteArray(), timeStamp);

        return nativePath;
    }

    private static String saveToJPEG(byte[] data, String timeStamp) {
        String nativePath = "";
        // 保存图片到本地
        if (data != null) {
            data = compressImage(data);
            File file = new File(Environment.getExternalStorageDirectory(),
                    AppConfig.APP_NAME);
            if (!file.exists())
                file.mkdirs();
            String picturePath = file.getPath() + "/zx_" + timeStamp + ".jpg";
            File pictureFile = new File(picturePath);
            nativePath = picturePath;

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(null, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(null, "Error accessing file: " + e.getMessage());
            }
        }
        return nativePath;
    }

    // 压缩本地图片到1M以下
    private static byte[] compressImage(byte[] data) {
        Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 512) { // 循环判断如果压缩后图片是否大于512kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 5;// 每次都减少10
        }
        /*
		 * ByteArrayInputStream isBm = new
		 * ByteArrayInputStream(baos.toByteArray());//
		 * 把压缩后的数据baos存放到ByteArrayInputStream中 Bitmap bitmap =
		 * BitmapFactory.decodeStream(isBm, null, null);//
		 * 把ByteArrayInputStream数据生成图片
		 */
        return baos.toByteArray();
    }

    // //避免内存溢出////////////////////////////

    /**
     * @param path
     * @return
     */
    public static byte[] decodeBitmap(String path) {
        Options opts = new Options();
        opts.inJustDecodeBounds = true;// 设置成了true,不占用内存，只获取bitmap宽高
        BitmapFactory.decodeFile(path, opts);
        opts.inSampleSize = computeSampleSize(opts, -1, 1024 * 1000);
        opts.inJustDecodeBounds = false;// 这里一定要将其设置回false，因为之前我们将其设置成了true
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inDither = false;
        opts.inPurgeable = true;
        opts.inTempStorage = new byte[300 * 1024];
        File file = new File(path);
        FileInputStream is = null;
        Bitmap bmp = null;
        ByteArrayOutputStream baos = null;
        try {
            is = new FileInputStream(file);
            if (is != null)
                bmp = BitmapFactory
                        .decodeFileDescriptor(is.getFD(), null, opts);
            double scale = getScaling(opts.outWidth * opts.outHeight, 1024 * 1000);
            Bitmap bmp2 = Bitmap.createScaledBitmap(bmp,
                    (int) (opts.outWidth * scale),
                    (int) (opts.outHeight * scale), true);
            bmp.recycle();
            baos = new ByteArrayOutputStream();
            bmp2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            bmp2.recycle();
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.gc();
        }
        return baos.toByteArray();
    }

    private static double getScaling(int src, int des) {
        /**
         * 48 目标尺寸÷原尺寸 sqrt开方，得出宽高百分比 49
         */
        double scale = Math.sqrt((double) des / (double) src);
        return scale;
    }

    public static int computeSampleSize(Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static Bitmap byteToBitmap(byte[] paramArrayOfByte) {
        Options localOptions = new Options();
        localOptions.inSampleSize = 8;
        ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(
                paramArrayOfByte);
        Bitmap localBitmap = (Bitmap) new SoftReference(
                BitmapFactory.decodeStream(localByteArrayInputStream, null,
                        localOptions)).get();
        if ((paramArrayOfByte == null) || (localByteArrayInputStream != null))
            ;
        try {
            localByteArrayInputStream.close();
            return localBitmap;
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
        return localBitmap;
    }

    public static Bitmap getBitmapFromUri(Uri uri, Context mContext) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存到本地且在图库显示
     *
     * @param context
     * @param bmp
     * @return
     */
    public static String saveImageToGallery(Context context,Bitmap bmp,String fileName) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),AppConfig.APP_NAME);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String filePath = null;
        File file = new File(appDir, fileName);
        filePath = file.getPath();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        return file.getPath();
    }

    /**
     * @param b Bitmap
     * @return 图片存储的位置
     * @throws FileNotFoundException
     * @imageType 图片类型
     */
    public static String saveBTMToPath(Bitmap b, String name, String imageType) throws Exception {
        File file = new File(Environment.getExternalStorageDirectory(),
                AppConfig.APP_NAME);
        if (!file.exists())
            file.mkdirs();
        String picturePath = file.getPath() + "/zx_" + name + imageType;
        File mediaFile = new File(picturePath);
        mediaFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(mediaFile);
        b.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();
        b.recycle();
        b = null;
        System.gc();
        return mediaFile.getPath();
    }

    /**
     * 判断角度
     */

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     */

    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * 压缩图片，处理某些手机拍照角度旋转的问题
     *
     * @param context   上下文
     * @param filePath  文件路径
     * @param fileName  保存名称
     * @param imageType .jpg
     * @param q         压缩比例(100);
     */
    public static String compressImage(Context context, String filePath, String fileName, String imageType, int q) throws FileNotFoundException {

        Bitmap bm = getSmallBitmap(filePath);

        int degree = readPictureDegree(filePath);

        if (degree != 0) {//旋转照片角度
            bm = rotateBitmap(bm, degree);
        }

        File imageDir = new File(Environment.getExternalStorageDirectory(),
                AppConfig.APP_NAME);
        if (!imageDir.exists())
            imageDir.mkdirs();
        File outputFile = new File(imageDir, fileName);

        FileOutputStream out = new FileOutputStream(outputFile);

        bm.compress(Bitmap.CompressFormat.JPEG, q, out);

        return outputFile.getPath();
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片的缩放值
     */
    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static File scal(Uri fileUri) throws IOException {
        String path = fileUri.getPath();
        File outputFile = new File(path);
        long fileSize = outputFile.length();
        final long fileMaxSize = 200 * 1024;
        if (fileSize >= fileMaxSize) {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(outputFile));
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            int i = 0;
            Bitmap bitmap = null;
            while (true) {
                if ((options.outWidth >> i <= 1080) && (options.outHeight >> i <= 1920)) {
                    in = new BufferedInputStream(
                            new FileInputStream(new File(path)));
                    options.inSampleSize = (int) Math.pow(2.0D, i);
                    options.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeStream(in, null, options);
                    break;
                }
                i += 1;
            }
            outputFile = new File(createImageFile().getPath());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(outputFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }else{
                File tempFile = outputFile;
                outputFile = new File(createImageFile().getPath());
                copyFileUsingFileChannels(tempFile, outputFile);
            }
        }
        return outputFile;

    }

    public static Uri createImageFile(){
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_",Locale.CHINA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        timeStamp = timeStamp + String.valueOf((int) (Math.random() * 1000));
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(image);
    }
    public static void copyFileUsingFileChannels(File source, File dest){
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            try {
                inputChannel = new FileInputStream(source).getChannel();
                outputChannel = new FileOutputStream(dest).getChannel();
                outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                inputChannel.close();
                outputChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000)
                    && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    /**
     * 根据路径删除图片
     *
     * @param path
     */
    public static void deleteTempFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 添加到图库
     */
    public static void galleryAddPic(Context context, String path) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(path);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    /**
     * 获取保存图片的目录
     *
     * @return
     */
    public static File getAlbumDir() {
        File dir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getAlbumName());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 获取保存 隐患检查的图片文件夹名称
     *
     * @return
     */
    public static String getAlbumName() {
        return AppConfig.APP_NAME;
    }

    /**
     * 把bitmap转换成String
     *
     * @param filePath
     * @return
     */
    public static String bitmapToString(String filePath) {
        Bitmap bm = getSmallBitmap(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);

    }

    /**
     * 把网络图片保存到本地
     *
     * @param filePath
     * @return
     */
    public static String savaHttpImgToSDCark(final Activity activity, String filePath) {
        final String[] imageNewPath = {null};
        new GetBitmapResControl(activity).doGetBitmap(filePath, new ImageLoadCallback<Bitmap>() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                if (bitmap != null) {
                    ToastUtil.show(activity.getBaseContext(),"图片保存成功");
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_",
                            Locale.CHINA).format(new Date());
                    timeStamp = timeStamp + String.valueOf((int) (Math.random() * 1000));
                    try {
                        imageNewPath[0] =saveBTMToPath(bitmap,timeStamp,"png");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onError(String paramString) {
                ToastUtil.show(activity.getBaseContext(),"图片保存失败");
            }
        });
        return imageNewPath[0];
    }

    /**
     * 通过uri获取图片并进行压缩
     *
     * @param uri
     */
    public static Bitmap getBitmapFormUri(Activity ac, Uri uri) throws FileNotFoundException, IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        Options onlyBoundsOptions = new Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        Options bitmapOptions = new Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return compressImage(bitmap);//再进行质量压缩
    }
    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    private static Bitmap getScaleLogo(Bitmap logo,int w,int h){
        if(logo == null)return null;
        Matrix matrix = new Matrix();
        float scaleFactor = Math.min(w * 1.0f / 5 / logo.getWidth(), h * 1.0f / 5 /logo.getHeight());
        matrix.postScale(scaleFactor,scaleFactor);
        Bitmap result = Bitmap.createBitmap(logo, 0, 0, logo.getWidth(),   logo.getHeight(), matrix, true);
        return result;
    }

    /**
     * 生成二维码图片
     * @param text
     * @param w
     * @param h
     * @param logo
     * @return
     */
    public static Bitmap createImage(String text,int w,int h,Bitmap logo) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        try {
            Bitmap scaleLogo = getScaleLogo(logo,w,h);

            int offsetX = w / 2;
            int offsetY = h / 2;

            int scaleWidth = 0;
            int scaleHeight = 0;
            if (scaleLogo != null) {
                scaleWidth = scaleLogo.getWidth();
                scaleHeight = scaleLogo.getHeight();
                offsetX = (w - scaleWidth) / 2;
                offsetY = (h - scaleHeight) / 2;
            }
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            //设置空白边距的宽度
            hints.put(EncodeHintType.MARGIN, 0);
            BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, w, h, hints);
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if(x >= offsetX && x < offsetX + scaleWidth && y>= offsetY && y < offsetY + scaleHeight){
                        int pixel = scaleLogo.getPixel(x-offsetX,y-offsetY);
                        if(pixel == 0){
                            if(bitMatrix.get(x, y)){
                                pixel = 0xff000000;
                            }else{
                                pixel = 0xffffffff;
                            }
                        }
                        pixels[y * w + x] = pixel;
                    }else{
                        if (bitMatrix.get(x, y)) {
                            pixels[y * w + x] = 0xff000000;
                        } else {
                            pixels[y * w + x] = 0xffffffff;
                        }
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(w, h,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
