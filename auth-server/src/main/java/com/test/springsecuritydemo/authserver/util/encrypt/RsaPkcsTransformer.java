package com.test.springsecuritydemo.authserver.util.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.StringWriter;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * RSA密钥格式转换工具
 */
public class RsaPkcsTransformer {

    private static final String COMMENT_BEGIN_FLAG = "-----";
    private static final String RETURN_FLAG_R = "\r";
    private static final String RETURN_FLAG_N = "\n";

    //format PKCS#8 to PKCS#1
    public static String formatPkcs8ToPkcs1(String rawKey) throws Exception {
        String result = null;
        //获取去首尾的pem字符串
        String validKey = extractFromPem(rawKey);
        if (!StringUtils.isEmpty(validKey)) {
            //将BASE64编码的私钥字符串进行解码
            byte[] encodeByte = Base64.decodeBase64(validKey);
            //==========
            //pkcs8Bytes contains PKCS#8 DER-encoded key as a byte[]
            PrivateKeyInfo pki = PrivateKeyInfo.getInstance(encodeByte);
            RSAPrivateKeyStructure pkcs1Key = RSAPrivateKeyStructure.getInstance(pki.getPrivateKey());
            byte[] pkcs1Bytes = pkcs1Key.getEncoded();//etc.
            //==========

            String type = "RSA PRIVATE KEY";
            result = format2PemString(type, pkcs1Bytes);
        }
        return result;
    }

    //format PKCS#1 to PKCS#8
    public static String formatPkcs1ToPkcs8(String rawKey) throws Exception {
        String result = null;
        //extract valid key content
        String validKey = extractFromPem(rawKey);
        if (!StringUtils.isEmpty(validKey)) {
            //将BASE64编码的私钥字符串进行解码
            byte[] encodeByte = Base64.decodeBase64(validKey);

            AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.pkcs8ShroudedKeyBag);    //PKCSObjectIdentifiers.pkcs8ShroudedKeyBag
            ASN1Object asn1Object = ASN1Object.fromByteArray(encodeByte);
            PrivateKeyInfo privKeyInfo = new PrivateKeyInfo(algorithmIdentifier, asn1Object);
            byte[] pkcs8Bytes = privKeyInfo.getEncoded();

            String type = "PRIVATE KEY";
            result = format2PemString(type, pkcs8Bytes);
        }
        return result;
    }

    //pkcs1格式PEM转pkcs8私钥
    public static String getPkcs1Pem2PKCS8PrivateKey(String rawKey) throws Exception {
        String result = null;
        //extract valid key content
        String validKey = extractFromPem(rawKey);
        if (!StringUtils.isEmpty(validKey)) {
            //将BASE64编码的私钥字符串进行解码
            byte[] encodeByte = Base64.decodeBase64(validKey);

            AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.pkcs8ShroudedKeyBag);    //PKCSObjectIdentifiers.pkcs8ShroudedKeyBag
            ASN1Object asn1Object = ASN1Object.fromByteArray(encodeByte);
            PrivateKeyInfo privKeyInfo = new PrivateKeyInfo(algorithmIdentifier, asn1Object);
            byte[] pkcs8Bytes = privKeyInfo.getEncoded();
            result = Base64.encodeBase64String(pkcs8Bytes);
        }
        return result;
    }

    //pkcs8格式PEM转pkcs1私钥
    public static String getPkcs8Pem2PKCS1PrivateKey(String rawKey) throws Exception {
        String result = null;
        //extract valid key content
        String validKey = extractFromPem(rawKey);
        if (!StringUtils.isEmpty(validKey)) {
            //将BASE64编码的私钥字符串进行解码
            byte[] encodeByte = Base64.decodeBase64(validKey);
            //pkcs8Bytes contains PKCS#8 DER-encoded key as a byte[]
            PrivateKeyInfo pki = PrivateKeyInfo.getInstance(encodeByte);
            RSAPrivateKeyStructure pkcs1Key = RSAPrivateKeyStructure.getInstance(pki.getPrivateKey());
            byte[] pkcs1Bytes = pkcs1Key.getEncoded();
            result = Base64.encodeBase64String(pkcs1Bytes);
        }
        return result;
    }

    // Write to pem file
    private static String format2PemString(String type, byte[] privateKeyPKCS1) throws Exception {
        PemObject pemObject = new PemObject(type, privateKeyPKCS1);
        StringWriter stringWriter = new StringWriter();
        PemWriter pemWriter = new PemWriter(stringWriter);
        pemWriter.writeObject(pemObject);
        pemWriter.close();
        String pemString = stringWriter.toString();
        return pemString;
    }

    private static String pkcs8Byte2Pem(byte[] pkcs8Bytes) throws Exception {
        String type = "PRIVATE KEY";
        String result = format2PemString(type, pkcs8Bytes);
        return result;
    }

    public static String extractFromPem(String privateKey) {
        String pkcs1_begin = "-----BEGIN RSA PRIVATE KEY-----";
        String pkcs1_end = "-----END RSA PRIVATE KEY-----";
        String pkcs8_begin = "-----BEGIN PRIVATE KEY-----";
        String pkcs8_end = "-----END PRIVATE KEY-----";
        String pkcs1_pub_begin = "-----BEGIN RSA PUBLIC KEY-----";
        String pkcs1_pub_end = "-----END RSA PUBLIC KEY-----";
        String pkcs8_pub_begin = "-----BEGIN PUBLIC KEY-----";
        String pkcs8_pub_end = "-----END PUBLIC KEY-----";
        privateKey = privateKey.replace(pkcs1_begin, "");
        privateKey = privateKey.replace(pkcs1_end, "");
        privateKey = privateKey.replace(pkcs8_begin, "");
        privateKey = privateKey.replace(pkcs8_end, "");
        privateKey = privateKey.replace(pkcs1_pub_begin, "");
        privateKey = privateKey.replace(pkcs1_pub_end, "");
        privateKey = privateKey.replace(pkcs8_pub_begin, "");
        privateKey = privateKey.replace(pkcs8_pub_end, "");
        //去换行符空格
        privateKey = privateKey.replaceAll("\\s+", "");
        return privateKey;
    }

    //PKCS8公钥top kcs1Pem
    public static String formatPkcs82Pkcs1(String publicKeyStr) throws Exception {
        byte[] pubBytes = Base64.decodeBase64(publicKeyStr);
        String type = "PUBLIC KEY";
        String pemString = format2PemString(type, pubBytes);
        return pemString;
    }

}
