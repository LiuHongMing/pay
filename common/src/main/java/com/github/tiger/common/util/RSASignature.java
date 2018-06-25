package com.github.tiger.common.util;

import com.google.common.collect.Maps;
import org.apache.commons.codec.binary.Base64;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

/**
 * @author liuhongming
 */
public class RSASignature {

    // 数字签名，密钥算法
    public static final String KEY_ALGORITHM = "RSA";

    // 数字签名，签名-验证算法
    private static final String SIGNATURE_ALGORITHM_MD5 = "MD5withRSA";

    private static final String SIGNATURE_ALGORITHM_SHA = "SHA1withRSA";

    // 密钥长度
    private static final int KEY_SIZE = 512;

    // 公钥
    private static final String PUBLIC_KEY = "RSAPublicKey";

    // 私钥
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 生成密钥对(公钥和私钥)
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, Object> createKey() throws Exception {
        //密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥长度
        keyPairGenerator.initialize(KEY_SIZE);
        //密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> map = Maps.newHashMap();
        map.put(PUBLIC_KEY, publicKey);
        map.put(PRIVATE_KEY, privateKey);
        return map;
    }

    /**
     * 数字签名
     *
     * @param data
     * @param privateKey
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static byte[] sign(byte[] data, String privateKey) throws Exception {
        byte[] base64Src = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(base64Src);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_MD5);
        signature.initSign(priKey);
        signature.update(data);
        return signature.sign();
    }

    /**
     * 校验数字签名
     *
     * @param data
     * @param publicKey
     * @param sign
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verify(byte[] data, String publicKey, byte[] sign) throws Exception {
        byte[] base64Src = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(base64Src);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_MD5);
        signature.initVerify(pubKey);
        signature.update(data);
        return signature.verify(sign);
    }

    public static String getPrivateKey(Map<String, Object> map) {
        PrivateKey privateKey = (PrivateKey) map.get(PRIVATE_KEY);
        return Base64.encodeBase64String(privateKey.getEncoded());
    }

    public static String getPublicKey(Map<String, Object> map) {
        PublicKey publicKey = (PublicKey) map.get(PUBLIC_KEY);
        return Base64.encodeBase64String(publicKey.getEncoded());
    }

}
