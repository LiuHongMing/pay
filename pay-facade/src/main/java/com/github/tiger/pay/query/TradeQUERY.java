package com.github.tiger.pay.query;

import java.io.Serializable;

public class TradeQUERY implements Serializable {

    private String merchantNo;

    private String merchantOrderNO;

    private String tradeNo;

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getMerchantOrderNO() {
        return merchantOrderNO;
    }

    public void setMerchantOrderNO(String merchantOrderNO) {
        this.merchantOrderNO = merchantOrderNO;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Override
    public String toString() {
        return "TradeQUERY{" +
                "merchantNo='" + merchantNo + '\'' +
                ", merchantOrderNO='" + merchantOrderNO + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                '}';
    }
}
