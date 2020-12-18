package com.xs.common.utils.authenticate;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.xs.common.utils.rsa.RsaEncrypt;
import com.xs.common.utils.rsa.RsaSignature;

/**
 * RSA测试类
 *
 * @author 18871430207@163.com
 */
public class RsaTest {

    public static void main(String[] args) throws Exception {
        String filepath = "E:/tmp/";
        // 生成公钥和私钥文件
        RsaEncrypt.genKeyPair(filepath);


        System.out.println("--------------公钥加密私钥解密过程-------------------");
        String plainText = "rsa_公钥加密私钥解密";
        // 公钥加密过程
        byte[] cipherData = RsaEncrypt.encrypt(RsaEncrypt.loadPublicKeyByStr(RsaEncrypt.loadPublicKeyByFile(filepath)), plainText.getBytes());
        String cipher = Base64.encode(cipherData);
        // 私钥解密过程
        byte[] res = RsaEncrypt.decrypt(RsaEncrypt.loadPrivateKeyByStr(RsaEncrypt.loadPrivateKeyByFile(filepath)), Base64.decode(cipher));
        System.out.println("原文：" + plainText);
        System.out.println("加密：" + cipher);
        if (res != null) {
            System.out.println("解密：" + new String(res));
        }
        System.out.println();


        System.out.println("--------------私钥加密公钥解密过程-------------------");
        plainText = "rsa_私钥加密公钥解密";
        // 私钥加密过程
        cipherData = RsaEncrypt.encrypt(RsaEncrypt.loadPrivateKeyByStr(RsaEncrypt.loadPrivateKeyByFile(filepath)), plainText.getBytes());
        cipher = Base64.encode(cipherData);
        // 公钥解密过程
        res = RsaEncrypt.decrypt(RsaEncrypt.loadPublicKeyByStr(RsaEncrypt.loadPublicKeyByFile(filepath)), Base64.decode(cipher));
        System.out.println("原文：" + plainText);
        System.out.println("加密：" + cipher);
        if (res != null) {
            System.out.println("解密：" + new String(res));
        }
        System.out.println();


        System.out.println("---------------私钥签名过程------------------");
        String content = "rsa_这是用于签名的原始数据";
        String signStr = RsaSignature.sign(content, RsaEncrypt.loadPrivateKeyByFile(filepath));
        String signStr2 = RsaSignature.sign(content, RsaEncrypt.loadPrivateKeyByFile(filepath), "GBK");
        System.out.println("签名原串：" + content);
        System.out.println("签名串：" + signStr);
        System.out.println("签名串：" + signStr2);
        System.out.println();


        System.out.println("---------------公钥校验签名------------------");
        System.out.println("签名原串：" + content);
        System.out.println("签名串：" + signStr);
        System.out.println("验签结果：" + RsaSignature.doCheck(content, signStr, RsaEncrypt.loadPublicKeyByFile(filepath)));
        System.out.println("验签结果2：" + RsaSignature.doCheck(content, signStr, RsaEncrypt.loadPublicKeyByFile(filepath), "GBK"));
        System.out.println();
    }

}
