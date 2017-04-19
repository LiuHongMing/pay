package com.senyint.pay.dao;

import com.senyint.common.dao.BaseDAO;
import com.senyint.pay.dao.model.PayTradeOrderDO;
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
