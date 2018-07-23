package com.github.tiger.pay.service;

import com.github.tiger.pay.dto.OutTradeNoDTO;
import com.github.tiger.pay.dto.TradeOrderDTO;
import com.github.tiger.pay.dto.TradeRecordDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 交易订单服务
 *
 * @author liuhongming
 */
public interface TradeService {

    /**
     * 保存交易过程的交易数据，包括交易订单、交易记录
     * <p>
     * 并返回商户订单号(out_trade_no)，由订单号 + "_" + 交易号组成
     */
    OutTradeNoDTO saveTrade(@Valid TradeOrderDTO tradeOrderDTO,
                            TradeRecordDTO tradeRecordDTO) throws Exception;

    /**
     * 交易完成，更新交易数据
     */
    int updateTradeToComplete(TradeOrderDTO tradeOrderDTO,
                              TradeRecordDTO tradeRecordDTO) throws Exception;

    /**
     * 获取交易订单的基本信息
     *
     * @param merchantOrderNo
     * @param tradeNo
     * @return
     * @throws Exception
     */
    TradeOrderDTO getTradeOrder(
            @NotNull(message = "{trade.merchantorderno.null}") String merchantOrderNo,
            String tradeNo) throws Exception;

    /**
     * 订单支付完成，更新订单信息
     * <p>
     * 返回状态 -> 失败：0，成功：> 0
     */
    int updateTradeOrderToComplete(TradeOrderDTO tradeOrderDTO) throws Exception;

    /**
     * 交易完成，更新交易记录
     * <p>
     * 返回状态 -> 失败：0，成功：> 0
     */
    int updateTradeRecordToComplete(TradeRecordDTO tradeRecordDTO) throws Exception;
}
