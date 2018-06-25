package com.github.tiger.pay.dao.model;

import com.github.tiger.common.dao.model.BaseDO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付交易记录数据表类
 */
public class PayTradeRecordDO extends BaseDO {
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商户订单号
     */
    private String merchantOrderNo;
    /**
     * 交易号
     */
    private String tradeNo;
    /**
     * 商家名称
     */
    private String merchantName;
    /**
     * 商家编号
     */
    private String merchantNo;
    /**
     * 付款人编号
     */
    private String payerNo;
    /**
     * 付款人名称
     */
    private String payerName;
    /**
     * 付款方支付金额
     */
    private BigDecimal payerPayAmount;
    /**
     * 付款方手续费
     */
    private BigDecimal payerFee;
    /**
     * 付款方账户类型
     */
    private String payerAccountType;
    /**
     * 收款人编号
     */
    private String receiverNo;
    /**
     * 收款人名称
     */
    private String receiverName;
    /**
     * 收款方支付金额
     */
    private BigDecimal receiverPayAmount;
    /**
     * 收款方手续费
     */
    private BigDecimal receiverFee;
    /**
     * 收款方账户类型
     */
    private String receiverAccountType;
    /**
     * 订单金额
     */
    private BigDecimal totalAmount;
    /**
     * 支付方式编号
     */
    private String payWayCode;
    /**
     * 支付方式名称
     */
    private String payWayName;
    /**
     * 支付成功时间
     */
    private Date paySuccessTime;
    /**
     * 完成时间
     */
    private Date completeTime;
    /**
     * 交易业务类型
     */
    private String tradeType;
    /**
     * 订单来源
     */
    private String orderFrom;
    /**
     * 支付类型编号
     */
    private String payTypeCode;
    /**
     * 支付类型名称
     */
    private String payTypeName;
    /**
     * 备注
     */
    private String remark;

    @Override
    public String toString() {
        return super.toString() + "PayTradeRecord{" +
                "productName='" + productName + '\'' +
                ", merchantOrderNo='" + merchantOrderNo + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchantNo='" + merchantNo + '\'' +
                ", payerNo='" + payerNo + '\'' +
                ", payerName='" + payerName + '\'' +
                ", payerPayAmount=" + payerPayAmount +
                ", payerFee=" + payerFee +
                ", payerAccountType='" + payerAccountType + '\'' +
                ", receiverNo='" + receiverNo + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPayAmount=" + receiverPayAmount +
                ", receiverFee=" + receiverFee +
                ", receiverAccountType='" + receiverAccountType + '\'' +
                ", totalAmount=" + totalAmount +
                ", payWayCode='" + payWayCode + '\'' +
                ", payWayName='" + payWayName + '\'' +
                ", paySuccessTime=" + paySuccessTime +
                ", completeTime=" + completeTime +
                ", tradeType='" + tradeType + '\'' +
                ", orderFrom='" + orderFrom + '\'' +
                ", payTypeCode='" + payTypeCode + '\'' +
                ", payTypeName='" + payTypeName + '\'' +
                ", remark='" + remark + '\'' +
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

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
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

    public String getPayerNo() {
        return payerNo;
    }

    public void setPayerNo(String payerNo) {
        this.payerNo = payerNo;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public BigDecimal getPayerPayAmount() {
        return payerPayAmount;
    }

    public void setPayerPayAmount(BigDecimal payerPayAmount) {
        this.payerPayAmount = payerPayAmount;
    }

    public BigDecimal getPayerFee() {
        return payerFee;
    }

    public void setPayerFee(BigDecimal payerFee) {
        this.payerFee = payerFee;
    }

    public String getPayerAccountType() {
        return payerAccountType;
    }

    public void setPayerAccountType(String payerAccountType) {
        this.payerAccountType = payerAccountType;
    }

    public String getReceiverNo() {
        return receiverNo;
    }

    public void setReceiverNo(String receiverNo) {
        this.receiverNo = receiverNo;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public BigDecimal getReceiverPayAmount() {
        return receiverPayAmount;
    }

    public void setReceiverPayAmount(BigDecimal receiverPayAmount) {
        this.receiverPayAmount = receiverPayAmount;
    }

    public BigDecimal getReceiverFee() {
        return receiverFee;
    }

    public void setReceiverFee(BigDecimal receiverFee) {
        this.receiverFee = receiverFee;
    }

    public String getReceiverAccountType() {
        return receiverAccountType;
    }

    public void setReceiverAccountType(String receiverAccountType) {
        this.receiverAccountType = receiverAccountType;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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

    public Date getPaySuccessTime() {
        return paySuccessTime;
    }

    public void setPaySuccessTime(Date paySuccessTime) {
        this.paySuccessTime = paySuccessTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
