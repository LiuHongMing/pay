package com.github.tiger.pay.dto.factory;

import com.github.tiger.pay.dto.OutTradeNoDTO;

/**
 * OutTradeNo工厂类
 *
 * @author liuhongming
 */
public class OutTradeNoFactory {

    /**
     * 生成新OutTradeNo实例
     *
     * @param merchantOrderNo
     * @param tradeNo
     * @return
     */
    public static OutTradeNoDTO newInstance(String merchantOrderNo, String tradeNo) {
        return new OutTradeNoDTO(merchantOrderNo, tradeNo);
    }
}
