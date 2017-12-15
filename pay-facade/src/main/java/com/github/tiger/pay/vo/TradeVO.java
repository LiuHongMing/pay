package com.github.tiger.pay.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 类名：TradeVO
 * 功能：交易参数类
 * 详细：用于在视图与服务之间的参数传递
 *
 * @author liuhongming
 */
public class TradeVO implements Serializable {

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 商户编号
     */
    private String merchantNo;

    /**
     * 商家名称
     */
    private String merchantName;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 订单金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单来源
     */
    private String orderFrom;

    /**
     * 支付方式编号
     */
    private String payWayCode;

    /**
     * 支付方式名称
     */
    private String payWayName;

    /**
     * 支付备注
     */
    private String remark;

    /**
     * 交易号
     */
    private String tradeNo;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 交易状态
     */
    private String tradeStatus;

    /**
     * 支付类型编号
     */
    private String payTypeCode;

    /**
     * 支付类型名称
     */
    private String payTypeName;

    /**
     * 通知url
     */
    private String notifyUrl;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getPayWayCode() {
        return payWayCode;
    }

    public void setPayWayCode(String payWayCode) {
        this.payWayCode = payWayCode;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getPayTypeCode() {
        return payTypeCode;
    }

    public void setPayTypeCode(String payTypeCode) {
        this.payTypeCode = payTypeCode;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    @Override
    public String toString() {
        return "TradeVO{" +
                "outTradeNo='" + outTradeNo + '\'' +
                ", productName='" + productName + '\'' +
                ", totalAmount=" + totalAmount +
                ", orderFrom='" + orderFrom + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchantNo='" + merchantNo + '\'' +
                ", payWayCode='" + payWayCode + '\'' +
                ", payWayName='" + payWayName + '\'' +
                ", remark='" + remark + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", tradeStatus='" + tradeStatus + '\'' +
                ", payTypeCode='" + payTypeCode + '\'' +
                ", payTypeName='" + payTypeName + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                '}';
    }
}
