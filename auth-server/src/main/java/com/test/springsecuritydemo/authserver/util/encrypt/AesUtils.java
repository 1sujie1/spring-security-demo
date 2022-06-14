package com.test.springsecuritydemo.authserver.util.encrypt;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.UUID;

/**
 * 对称加密
 * AES加密工具类
 */
public class AesUtils {

    /**
     * 算法/加密模式/填充方式
     */
    private static final String AES_PKCS5P = "AES/ECB/PKCS5Padding";

    /**
     * 算法/加密模式/填充方式
     */
    private static final String AES_PKCS7P = "AES/ECB/PKCS7Padding";

    /**
     * 密钥长度
     */
    private static final int Secret_key_size = 16;

    /**
     * 编码
     */
    private static final String Encode = "UTF-8";

    static {
        //BouncyCastle是一个开源的加解密解决方案，主页在http://www.bouncycastle.org/
        Security.addProvider(new BouncyCastleProvider());
    }

    //生成随机密钥
    public static String getRandomKey() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    /**
     * 加密
     *
     * @param str 需要加密的字符串
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static String encrypt(String str, String key) {
        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("key不能为空");
        }
        try {
            if (str == null) {
                return null;
            }
            // 判断Key是否为16位
            if (key.length() != Secret_key_size) {
                return null;
            }
            byte[] raw = key.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance(AES_PKCS5P);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(str.getBytes("UTF-8"));
            // 此处使用BASE64做转码功能，同时能起到2次加密的作用。
            return new BASE64Encoder().encode(encrypted);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 解密
     *
     * @param str 需要解密的字符串
     * @param key 密钥
     * @return
     */
    public static String decrypt(String str, String key) {
        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("key不能为空");
        }
        try {
            if (str == null) {
                return null;
            }
            // 判断Key是否为16位
            if (key.length() != Secret_key_size) {
                return null;
            }
            byte[] raw = key.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(AES_PKCS5P);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            // 先用base64解密
            byte[] encrypted = new BASE64Decoder().decodeBuffer(str);
            try {
                byte[] original = cipher.doFinal(encrypted);
                String originalString = new String(original, "UTF-8");
                return originalString;
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public static String encodePKCS7(String content, String aesKey) throws Exception {
        try {
            Security.addProvider(new BouncyCastleProvider());

            byte[] raw = aesKey.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance(AES_PKCS7P);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes("UTF-8"));
            // 此处使用BASE64做转码功能，同时能起到2次加密的作用。
            return new BASE64Encoder().encode(encrypted);
        } catch (Exception e) {
            throw new Exception("AES加密失败：" + e.getMessage(), e);
        }
    }

    public static String decodePKCS7(String content, String aesKey) throws Exception {
        try {
            byte[] raw = aesKey.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(AES_PKCS7P);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            // 先用base64解密
            byte[] encrypted = new BASE64Decoder().decodeBuffer(content);
            try {
                byte[] original = cipher.doFinal(encrypted);
                String originalString = new String(original, "UTF-8");
                return originalString;
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("AES解密失败：" + e.getMessage(), e);
        }
    }

}
