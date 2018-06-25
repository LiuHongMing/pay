package com.github.tiger.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA-非对称加密算法
 *
 * @author liuhongming
 */
public class RSAUtil {

    public enum RSAKey {
        PUBLIC, PRIVATE
    }

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 128;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 私钥加密
     *
     * @param data 加密数据
     * @param privateKey 私钥
     *
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        Key genKey = initial(privateKey, RSAKey.PRIVATE);
        byte[] result = handler(data, genKey, Cipher.ENCRYPT_MODE, MAX_ENCRYPT_BLOCK);
        return result;
    }

    /**
     * 公钥加密
     *
     * @param data 加密数据
     * @param publicKey 公钥
     *
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        Key genKey = initial(publicKey, RSAKey.PUBLIC);
        byte[] result = handler(data, genKey, Cipher.ENCRYPT_MODE, MAX_ENCRYPT_BLOCK);
        return result;
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     *
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        Key genKey = initial(privateKey, RSAKey.PRIVATE);
        byte[] result = handler(data, genKey, Cipher.DECRYPT_MODE, MAX_DECRYPT_BLOCK);
        return result;
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     *
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String publicKey) throws Exception {
        Key genKey = initial(publicKey, RSAKey.PUBLIC);
        byte[] result = handler(data, genKey, Cipher.DECRYPT_MODE, MAX_DECRYPT_BLOCK);
        return result;
    }

    /**
     * 初始化Key
     *
     * @param inputKey
     * @param rsaKey
     *
     * @return
     * @throws Exception
     */
    private static Key initial(String inputKey, RSAKey rsaKey) throws Exception {
        byte[] base64Src = Base64.decodeBase64(inputKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSASignature.KEY_ALGORITHM);
        KeySpec keySpec;
        Key genKey = null;
        switch (rsaKey) {
            case PRIVATE:
                keySpec = new PKCS8EncodedKeySpec(base64Src);
                genKey = keyFactory.generatePrivate(keySpec);
                break;
            case PUBLIC:
                keySpec = new X509EncodedKeySpec(base64Src);
                genKey = keyFactory.generatePublic(keySpec);
                break;
        }
        return genKey;
    }

    /**
     * 加密处理
     *
     * @param data
     * @param genKey
     * @param mode
     * @param maxBlock
     *
     * @return
     * @throws Exception
     */
    private static byte[] handler(byte[] data, Key genKey, int mode, int maxBlock) throws Exception {
        Cipher cipher = Cipher.getInstance(RSASignature.KEY_ALGORITHM);
        cipher.init(mode, genKey);

        int dataLength = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] cache;
        int offset = 0, i = 0;
        // 数据分段解密
        while (dataLength - offset > 0) {
            if (dataLength - offset > maxBlock) {
                cache = cipher.doFinal(data, offset, maxBlock);
            } else {
                int end = dataLength - offset;
                cache = cipher.doFinal(data, offset, end);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * maxBlock;
        }
        byte[] result = out.toByteArray();
        out.close();
        return result;
    }

}
