package com.xs.common.utils.rsa;

/**
 * Exmail模块常量类
 *
 * @author 18871430207@163.com
 */
class RsaConstants {

    /** RSA密钥生成key */
    static String defaultRsa = "RSA";
    /** RSA公钥文件 */
    static String publicKeyFile = "/publicKey.rsa";
    /** RSA私钥文件 */
    static String privateKeyFile = "/privateKey.rsa";
    /** RSA密钥大小 */
    static Integer keySize = 1024;
    /** 密钥非法 */
    static String InvalidKeySpec = "密钥非法";
    /** 密钥字符串解码失败 */
    static String Base64Decoding = "密钥字符串解码失败";
    /** 密钥为空, 请设置 */
    static String EmptyKey = "密钥为空, 请设置";
    /** 无此加密/解密算法 */
    static String NoSuchAlgorithm = "无此加密/解密算法";
    /** 无此调用 */
    static String NoSuchPadding = "无此调用";
    /** 加密/解密密钥非法,请检查 */
    static String InvalidKey = "加密/解密密钥非法,请检查";
    /** 明文/密文长度非法 */
    static String IllegalBlockSize = "明文/密文长度非法";
    /** 明文/密文数据已损坏 */
    static String BadPadding = "明文/密文数据已损坏";

    /** 签名算法 */
    static String signAlgorithms = "SHA1WithRSA";
    /** 签名失败 */
    static String SignatureError = "签名失败";
    /** 未知编码类型 */
    static String UnsupportedEncoding = "未知编码类型";

}
