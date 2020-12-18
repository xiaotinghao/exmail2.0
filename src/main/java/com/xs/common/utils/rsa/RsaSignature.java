package com.xs.common.utils.rsa;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static com.xs.common.utils.rsa.RsaConstants.*;

/**
 * RSA签名验签类
 *
 * @author 18871430207@163.com
 */
public class RsaSignature {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(RsaSignature.class);
    /**
     * 签名对象
     */
    private static Signature signature;
    /**
     * 密钥工厂
     */
    private static KeyFactory keyFactory;

    static {
        if (signature == null) {
            try {
                signature = Signature.getInstance(signAlgorithms);
            } catch (NoSuchAlgorithmException e) {
                logger.error(NoSuchAlgorithm);
                e.printStackTrace();
            }
        }
        if (keyFactory == null) {
            try {
                keyFactory = KeyFactory.getInstance(defaultRsa);
            } catch (NoSuchAlgorithmException e) {
                logger.error(NoSuchAlgorithm);
                e.printStackTrace();
            }
        }
    }

    /**
     * RSA签名
     *
     * @param content    待签名数据
     * @param privateKey 商户私钥
     * @param encode     字符集编码
     * @return 签名值
     */
    public static String sign(String content, String privateKey, String encode) {
        byte[] signed = null;
        try {
            byte[] encodedKey = Base64.decode(privateKey);
            PrivateKey priKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
            signature.initSign(priKey);
            if (encode == null) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(encode));
            }
            signed = signature.sign();
        } catch (Base64DecodingException e) {
            logger.error(Base64Decoding);
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            logger.error(InvalidKeySpec);
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            logger.error(InvalidKey);
            e.printStackTrace();
        } catch (SignatureException e) {
            logger.error(SignatureError);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            logger.error(UnsupportedEncoding);
            e.printStackTrace();
        }
        return Base64.encode(signed);
    }

    /**
     * RSA签名
     *
     * @param content    待签名数据
     * @param privateKey 商户私钥
     * @return 签名值
     */
    public static String sign(String content, String privateKey) {
        return sign(content, privateKey, null);
    }

    /**
     * RSA验签名检查
     *
     * @param content   待签名数据
     * @param sign      签名值
     * @param publicKey 分配给开发商公钥
     * @param encode    字符集编码
     * @return 检验结果，布尔值
     */
    public static boolean doCheck(String content, String sign, String publicKey, String encode) {
        try {
            byte[] encodedKey = Base64.decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            signature.initVerify(pubKey);
            if (encode == null) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(encode));
            }
            return signature.verify(Base64.decode(sign));
        } catch (Base64DecodingException e) {
            logger.error(Base64Decoding);
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            logger.error(InvalidKeySpec);
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            logger.error(InvalidKey);
            e.printStackTrace();
        } catch (SignatureException e) {
            logger.error(SignatureError);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            logger.error(UnsupportedEncoding);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * RSA验签名检查
     *
     * @param content   待签名数据
     * @param sign      签名值
     * @param publicKey 分配给开发商公钥
     * @return 检验结果，布尔值
     */
    public static boolean doCheck(String content, String sign, String publicKey) {
        return doCheck(content, sign, publicKey, null);
    }

}
