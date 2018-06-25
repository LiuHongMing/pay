package com.github.tiger.pay.dao.model;

import com.github.tiger.common.dao.model.BaseDO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付交易订单数据表类
 */
public class PayTradeOrderDO extends BaseDO {

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 订单号
     */
    private String merchantOrderNo;

    /**
     * 订单金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单来源
     */
    private String orderFrom;

    /**
     * 商家名称
     */
    private String merchantName;

    /**
     * 商户编号
     */
    private String merchantNo;

    /**
     * 下单时间
     */
    private Date orderTime;

    /**
     * 订单有效期(单位分钟)
     */
    private Short orderPeriod;

    /**
     * 到期时间
     */
    private Date expireTime;

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

    @Override
    public String toString() {
        return super.toString() + ",PayTradeOrderEntity{" +
                "productName='" + productName + '\'' +
                ", merchantOrderNo='" + merchantOrderNo + '\'' +
                ", totalAmount=" + totalAmount +
                ", orderFrom='" + orderFrom + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchantNo='" + merchantNo + '\'' +
                ", orderTime=" + orderTime +
                ", orderPeriod=" + orderPeriod +
                ", expireTime=" + expireTime +
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
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

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Short getOrderPeriod() {
        return orderPeriod;
    }

    public void setOrderPeriod(Short orderPeriod) {
        this.orderPeriod = orderPeriod;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
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

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}