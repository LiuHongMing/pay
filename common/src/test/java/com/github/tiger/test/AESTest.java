package com.github.tiger.test;

import com.github.tiger.pay.common.util.AESUtil;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class AESTest {

    @Test
    public void test() throws Exception {
        String str = "AES";
        System.out.println("原文：" + str);
        //初始化密钥
        byte[] key = AESUtil.createKey();
        System.out.println("密钥：" + Base64.encodeBase64String(key));
        //加密数据
        byte[] data = AESUtil.encrypt(str.getBytes(), key);
        System.out.println("加密后：" + Base64.encodeBase64String(data));
        //解密数据
        data = AESUtil.decrypt(data, key);
        System.out.println("解密后：" + new String(data));
    }

}
