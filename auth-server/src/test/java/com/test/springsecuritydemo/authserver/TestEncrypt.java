package com.test.springsecuritydemo.authserver;

import com.test.springsecuritydemo.authserver.util.encrypt.RSAUtil;
import com.test.springsecuritydemo.authserver.util.encrypt.RsaPkcsTransformer;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
public class TestEncrypt {

    //生成rsa密钥
    @Test
    public void init() {
        try {
            String publicKeyName = "rsa_public_key";
            String priavteKeyName = "rsa_private_key";
            Map<String, Object> map = RSAUtil.initKey();
            if (null == map) {
                return;
            }
            RSAPublicKey rsaPublicKey = (RSAPublicKey) map.get(publicKeyName);
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) map.get(priavteKeyName);
            String publicKey = Base64.encodeBase64String(rsaPublicKey.getEncoded());
            String privateKey = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
            String publicKeyPem = RsaPkcsTransformer.formatPkcs82Pkcs1(publicKey);
            String privateKeyPem = RsaPkcsTransformer.formatPkcs8ToPkcs1(privateKey);

            System.out.println("privateKey:" + privateKey);
            System.out.println("publicKey:" + publicKey);
            System.out.println("privateKeyPem:" + privateKeyPem);
            System.out.println("publicKeyPem:" + publicKeyPem);
        } catch (Exception e) {

        }
    }

    @Test
    public void convent() {
        try {
            String pem = "-----BEGIN RSA PRIVATE KEY-----\n" +
                    "MIICWwIBAAKBgQDq0m8npCD3W2/Ct/UR8MLabln2IvZv0JxS/POHeTbzhDw5R72p\n" +
                    "5qvO2U4/mjucaTBsCiVXbax9cywfuCQ9LwmsDVBPvmRDLg77ItpjV7Yg810H7Ax7\n" +
                    "6PbYczHERkV8UozPEcpBnQeJyEyy5VptMIdnrC4eTgWLH6k9a9ztzY3IlQIDAQAB\n" +
                    "AoGAGj4CJg/1jy8swjoErUiWvgpvC07E86IAllWmcZFeTsCMampWC9K3iJpIYFtA\n" +
                    "Ji6rNqe7nJWpHSvt1m2hvySuk9Dq0QrvIlfkSXzncbCxeRCq6KUNEdESyYBf5Fgq\n" +
                    "EkpYVMZKG/mMwF2SAK//Mop4qPRkKOSmhr9QrzmTitFZktECQQD4c8M8t1lnI6eK\n" +
                    "0UzzqfS8zr97OnnvLADOeIbZh1mM6QA5JZHn4/mCJXD9fZTM2016LRkobZeoLiEC\n" +
                    "nTANbCA/AkEA8fSrEI86WBgooRdCAhElAfyM83ye6Y2DgSl8Vtjk9aT1EFQS/NaZ\n" +
                    "R+NeW6II2LmLaH4b6IPWvYw4J7gbiOQiKwJANzZimMeWEpmVzf+dPjjyrlKzssrX\n" +
                    "9hKtSzT1orm1aN8OsaRrvECpXga8CVkzDCNgIFqNULKqFTHLHC2aVCFHOQJAeEc7\n" +
                    "nsDErTRdRi9speBl+EXWv9fMm6e4nsXDxNyPfTmihZp10fDQQ/dYWc8D/NDH54sB\n" +
                    "TqXzY0jIRZ51OajG/wJAc01yjVVkmZIacLuiA4kFGgr5G2jIS7uRUyidoM+J6eIx\n" +
                    "K0pSPbOucKwpEoOIcZ8XTptr3edcE+g9xBBA63eF7A==\n" +
                    "-----END RSA PRIVATE KEY-----";
            String privateKey = RsaPkcsTransformer.getPkcs1Pem2PKCS8PrivateKey(pem);

            String privateKeyPem = RsaPkcsTransformer.formatPkcs8ToPkcs1(privateKey);
            System.out.println(privateKey);

            System.out.println(privateKeyPem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void encrypt() {
        try {
            String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAOrSbyekIPdbb8K39RHwwtpuWfYi9m/QnFL884d5NvOEPDlHvanmq87ZTj+aO5xpMGwKJVdtrH1zLB+4JD0vCawNUE++ZEMuDvsi2mNXtiDzXQfsDHvo9thzMcRGRXxSjM8RykGdB4nITLLlWm0wh2esLh5OBYsfqT1r3O3NjciVAgMBAAECgYAaPgImD/WPLyzCOgStSJa+Cm8LTsTzogCWVaZxkV5OwIxqalYL0reImkhgW0AmLqs2p7uclakdK+3WbaG/JK6T0OrRCu8iV+RJfOdxsLF5EKropQ0R0RLJgF/kWCoSSlhUxkob+YzAXZIAr/8yinio9GQo5KaGv1CvOZOK0VmS0QJBAPhzwzy3WWcjp4rRTPOp9LzOv3s6ee8sAM54htmHWYzpADklkefj+YIlcP19lMzbTXotGShtl6guIQKdMA1sID8CQQDx9KsQjzpYGCihF0ICESUB/IzzfJ7pjYOBKXxW2OT1pPUQVBL81plH415bogjYuYtofhvog9a9jDgnuBuI5CIrAkA3NmKYx5YSmZXN/50+OPKuUrOyytf2Eq1LNPWiubVo3w6xpGu8QKleBrwJWTMMI2AgWo1QsqoVMcscLZpUIUc5AkB4RzuewMStNF1GL2yl4GX4Rda/18ybp7iexcPE3I99OaKFmnXR8NBD91hZzwP80MfniwFOpfNjSMhFnnU5qMb/AkBzTXKNVWSZkhpwu6IDiQUaCvkbaMhLu5FTKJ2gz4np4jErSlI9s65wrCkSg4hxnxdOm2vd51wT6D3EEEDrd4Xs";
            String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDq0m8npCD3W2/Ct/UR8MLabln2IvZv0JxS/POHeTbzhDw5R72p5qvO2U4/mjucaTBsCiVXbax9cywfuCQ9LwmsDVBPvmRDLg77ItpjV7Yg810H7Ax76PbYczHERkV8UozPEcpBnQeJyEyy5VptMIdnrC4eTgWLH6k9a9ztzY3IlQIDAQAB";

            String token = UUID.randomUUID().toString().replaceAll("-", "");
            System.out.println(token);

            //公钥加密
            String encryptStr = RSAUtil.encryptByPublicKey(token, publicKey);
            System.out.println(encryptStr);

            //私钥解密
            String decryptStr = RSAUtil.decryptByPrivateKey(encryptStr, privateKey);
            System.out.println(decryptStr);
        } catch (Exception e) {

        }
    }

    @Test
    public void encrypt1() {
        try {
            String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAOrSbyekIPdbb8K39RHwwtpuWfYi9m/QnFL884d5NvOEPDlHvanmq87ZTj+aO5xpMGwKJVdtrH1zLB+4JD0vCawNUE++ZEMuDvsi2mNXtiDzXQfsDHvo9thzMcRGRXxSjM8RykGdB4nITLLlWm0wh2esLh5OBYsfqT1r3O3NjciVAgMBAAECgYAaPgImD/WPLyzCOgStSJa+Cm8LTsTzogCWVaZxkV5OwIxqalYL0reImkhgW0AmLqs2p7uclakdK+3WbaG/JK6T0OrRCu8iV+RJfOdxsLF5EKropQ0R0RLJgF/kWCoSSlhUxkob+YzAXZIAr/8yinio9GQo5KaGv1CvOZOK0VmS0QJBAPhzwzy3WWcjp4rRTPOp9LzOv3s6ee8sAM54htmHWYzpADklkefj+YIlcP19lMzbTXotGShtl6guIQKdMA1sID8CQQDx9KsQjzpYGCihF0ICESUB/IzzfJ7pjYOBKXxW2OT1pPUQVBL81plH415bogjYuYtofhvog9a9jDgnuBuI5CIrAkA3NmKYx5YSmZXN/50+OPKuUrOyytf2Eq1LNPWiubVo3w6xpGu8QKleBrwJWTMMI2AgWo1QsqoVMcscLZpUIUc5AkB4RzuewMStNF1GL2yl4GX4Rda/18ybp7iexcPE3I99OaKFmnXR8NBD91hZzwP80MfniwFOpfNjSMhFnnU5qMb/AkBzTXKNVWSZkhpwu6IDiQUaCvkbaMhLu5FTKJ2gz4np4jErSlI9s65wrCkSg4hxnxdOm2vd51wT6D3EEEDrd4Xs";
            String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDq0m8npCD3W2/Ct/UR8MLabln2IvZv0JxS/POHeTbzhDw5R72p5qvO2U4/mjucaTBsCiVXbax9cywfuCQ9LwmsDVBPvmRDLg77ItpjV7Yg810H7Ax76PbYczHERkV8UozPEcpBnQeJyEyy5VptMIdnrC4eTgWLH6k9a9ztzY3IlQIDAQAB";

            String token = UUID.randomUUID().toString().replaceAll("-", "");
            System.out.println(token);

            //公钥加密
            String encryptStr = RSAUtil.encryptByPrivateKey(token, privateKey);
            System.out.println(encryptStr);

            //私钥解密
            String decryptStr = RSAUtil.decryptByPublicKey(encryptStr, publicKey);
            System.out.println(decryptStr);
        } catch (Exception e) {

        }
    }

}
