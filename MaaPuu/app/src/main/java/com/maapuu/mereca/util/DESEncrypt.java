package com.maapuu.mereca.util;

import android.util.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESEncrypt {
    private static String SEED_MFT="7%8$_GytgyTuJ__^^jJJ...YnuMklzT";	//美发通-加密种子

    /**
     * 加密函数
     * @return 返回加密后的数据
     */
    public static String encrypt(String clearStr) {
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(SEED_MFT.getBytes());
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);
            // using DES in ECB mode
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
            // 执行加密操作
            byte encryptedData[] = cipher.doFinal(clearStr.getBytes());
            return new String(Base64.encode(encryptedData, Base64.DEFAULT));
        } catch (Exception e) {
            System.err.println("DES算法，加密数据出错!");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密函数
     * @return 返回解密后的数据
     */
    public static String decrypt(String encrypted) {
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            // byte rawKeyData[] = /* 用某种方法获取原始密匙数据 */;
            // 从原始密匙数据创建一个DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(SEED_MFT.getBytes());
            // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);
            // using DES in ECB mode
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
            // 正式执行解密操作
            byte decryptedData[] = cipher.doFinal(Base64.decode(encrypted.getBytes(),Base64.DEFAULT));
            return new String(decryptedData);
        } catch (Exception e) {
            System.err.println("DES算法，解密出错。");
            e.printStackTrace();
        }
        return null;
    }
}