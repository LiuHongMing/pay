package com.github.tiger.pay.test;

import com.github.tiger.pay.common.util.RSASignature;
import com.github.tiger.pay.common.util.RSAUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class RSATest {

    String privateKey;
    String publicKey;

    @Before
    public void setUp() throws Exception {
        Map<String, Object> keyMap = RSASignature.createKey();
        publicKey = RSASignature.getPublicKey(keyMap);
        privateKey = RSASignature.getPrivateKey(keyMap);
        System.out.println("公钥：\n" + publicKey);
        System.out.println("私钥：\n" + privateKey);
    }

    @Test
    public void testEnPublicDePrivate() throws Exception {
        System.out.println("***公钥加密-私钥解密***");
        String source = "用RSA加密的原数据&100";
        System.out.println("加密前：" + source);
        byte[] data = source.getBytes();
        byte[] encodeData = RSAUtil.encryptByPublicKey(data, publicKey);
        System.out.print("加密后：");
        System.out.println(new String(encodeData));
        byte[] decodeData = RSAUtil.decryptByPrivateKey(encodeData, privateKey);
        System.out.println("解密后：" + new String(decodeData));
    }

    @Test
    public void testSign() throws Exception {
        System.out.println("***私钥加密-公钥解密***");
        String source = "用RSA数字签名的原数据&200";
        System.out.println("加密前：" + source);
        byte[] data = source.getBytes();
        byte[] encodeData = RSAUtil.encryptByPrivateKey(data, privateKey);
        System.out.print("加密后：");
        System.out.println(new String(encodeData));
        byte[] decodeData = RSAUtil.decryptByPublicKey(encodeData, publicKey);
        System.out.println("解密后：" + new String(decodeData));

        System.out.println("***私钥签名-公钥验证签名***");
        byte[] sign = RSASignature.sign(encodeData, privateKey);
        System.out.println("签名：" + new String(sign));
        boolean status = RSASignature.verify(encodeData, publicKey, sign);
        System.out.println("验证：" + status);
    }

}
