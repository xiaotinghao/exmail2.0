package com.xs.common.utils.rsa;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.xs.common.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static com.xs.common.utils.rsa.RsaConstants.*;

/**
 * RSA加密解密类
 *
 * @author 18871430207@163.com
 */
public class RsaEncrypt {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(RsaEncrypt.class);
    /**
     * 密钥对象
     */
    private static Cipher cipher;
    /**
     * KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
     */
    private static KeyPairGenerator keyPairGen;

    static {
        if (cipher == null) {
            try {
                // 使用默认RSA初始化密钥对象
                cipher = Cipher.getInstance(defaultRsa);
            } catch (NoSuchAlgorithmException e) {
                logger.error(NoSuchAlgorithm);
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                logger.error(NoSuchPadding);
                e.printStackTrace();
            }
        }
        if (keyPairGen == null) {
            try {
                // 使用默认RSA初始化密钥生成器
                keyPairGen = KeyPairGenerator.getInstance(defaultRsa);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 随机生成密钥对
     */
    public static void genKeyPair(String filePath) {
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(keySize, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 得到公钥字符串
        String publicKeyString = Base64.encode(publicKey.getEncoded());
        // 得到私钥字符串
        String privateKeyString = Base64.encode(privateKey.getEncoded());
        // 将公钥对写入到文件
        FileUtils.writeText(filePath + publicKeyFile, publicKeyString);
        // 将私钥对写入到文件
        FileUtils.writeText(filePath + privateKeyFile, privateKeyString);
    }

    /**
     * 从文件中加载公钥
     *
     * @param path 公钥文件路径
     * @return 公钥字符串
     */
    public static String loadPublicKeyByFile(String path) {
        return FileUtils.loadFileContent(path + publicKeyFile);
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @return 公钥对象
     */
    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr) {
        try {
            byte[] buffer = Base64.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(defaultRsa);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            logger.error(NoSuchAlgorithm);
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            logger.error(InvalidKeySpec);
            e.printStackTrace();
        } catch (NullPointerException e) {
            logger.error(EmptyKey);
            e.printStackTrace();
        } catch (Base64DecodingException e) {
            logger.error(Base64Decoding);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从文件中加载私钥
     *
     * @param path 私钥文件名路径
     * @return 私钥字符串
     */
    public static String loadPrivateKeyByFile(String path) {
        return FileUtils.loadFileContent(path + privateKeyFile);
    }

    /**
     * 从字符串中加载私钥
     *
     * @param privateKeyStr 私钥数据字符串
     * @return 私钥对象
     */
    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr) {
        try {
            byte[] buffer = Base64.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(defaultRsa);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            logger.error(NoSuchAlgorithm);
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            logger.error(InvalidKeySpec);
            e.printStackTrace();
        } catch (NullPointerException e) {
            logger.error(EmptyKey);
            e.printStackTrace();
        } catch (Base64DecodingException e) {
            logger.error(Base64Decoding);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 密钥加密过程
     *
     * @param rsaKey        密钥
     * @param plainTextData 明文数据
     * @return 密文
     */
    public static byte[] encrypt(Key rsaKey, byte[] plainTextData) {
        try {
            // 使用默认RSA
            cipher.init(Cipher.ENCRYPT_MODE, rsaKey);
            return cipher.doFinal(plainTextData);
        } catch (InvalidKeyException e) {
            logger.error(InvalidKey);
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            logger.error(IllegalBlockSize);
            e.printStackTrace();
        } catch (NullPointerException e) {
            logger.error(EmptyKey);
            e.printStackTrace();
        } catch (BadPaddingException e) {
            logger.error(BadPadding);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 密钥解密过程
     *
     * @param rsaKey     密钥
     * @param cipherData 密文数据
     * @return 明文
     */
    public static byte[] decrypt(Key rsaKey, byte[] cipherData) {
        try {
            // 使用默认RSA
            cipher.init(Cipher.DECRYPT_MODE, rsaKey);
            return cipher.doFinal(cipherData);
        } catch (InvalidKeyException e) {
            logger.error(InvalidKey);
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            logger.error(IllegalBlockSize);
            e.printStackTrace();
        } catch (NullPointerException e) {
            logger.error(EmptyKey);
            e.printStackTrace();
        } catch (BadPaddingException e) {
            logger.error(BadPadding);
            e.printStackTrace();
        }
        return null;
    }

}
