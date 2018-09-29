package com.maapuu.mereca.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.maapuu.mereca.base.BaseApplication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by dell on 2016/11/16.
 */
public class VersionUtil {

    public static String read(InputStream instream) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = instream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        String ret = new String(bos.toByteArray());
        return ret;
    }

    // ============系统信息==================================
    // 软件-版本
    public static int getAppVersion() {
        int versionName = 0;
        Application app = BaseApplication.getInstance();
        try {
            PackageManager pkgMng = app.getPackageManager();
            PackageInfo pkgInfo = pkgMng
                    .getPackageInfo(app.getPackageName(), 0);
            versionName = pkgInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    // 软件-版本
    public static String getAppVersionName() {
        String versionName = "";
        Application app = BaseApplication.getInstance();
        try {
            PackageManager pkgMng = app.getPackageManager();
            PackageInfo pkgInfo = pkgMng
                    .getPackageInfo(app.getPackageName(), 0);
            versionName = pkgInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 检测是否安装微信
     *
     * @param context
     * @return
     */
    public static boolean isAppInstall(Context context,String packagename) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packagename)) {
                    return true;
                }
            }
        }
        return false;
    }
}
