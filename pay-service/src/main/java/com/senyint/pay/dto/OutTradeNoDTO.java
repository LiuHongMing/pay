package com.senyint.pay.dto;

public class OutTradeNoDTO {

    private String merchantOrderNo;

    private String tradeNo;

    public OutTradeNoDTO(String merchantOrderNo, String tradeNo) {
        this.merchantOrderNo = merchantOrderNo;
        this.tradeNo = tradeNo;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Override
    public String toString() {
        return merchantOrderNo + "_" + tradeNo;
    }
}
