package com.github.tiger.pay.dao;

import com.github.tiger.pay.common.dao.BaseDAO;
import com.github.tiger.pay.dao.model.PayTradeOrderDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 支付交易订单DAO类
 */
public interface PayTradeOrderDAO extends BaseDAO {

    PayTradeOrderDO getByMerchantOrderNoOrTradeNo(@Param("merchantOrderNo") String merchantOrderoNo,
                                                  @Param("tradeNo") String tradeNo);

    int updateByIdOrMerchantOrderNo(PayTradeOrderDO payTradeOrderDO);

    List<PayTradeOrderDO> listByMerchantNo(@Param("merchantNo") String merchantNo);

}
