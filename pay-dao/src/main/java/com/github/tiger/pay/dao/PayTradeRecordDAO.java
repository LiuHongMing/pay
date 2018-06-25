package com.github.tiger.pay.dao;

import com.github.tiger.common.dao.BaseDAO;
import com.github.tiger.pay.dao.model.PayTradeRecordDO;
import com.github.tiger.pay.dao.model.PayTradeOrderDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 支付交易记录DAO类
 */
public interface PayTradeRecordDAO extends BaseDAO {

    PayTradeRecordDO getPayTradeRecord(PayTradeRecordDO payTradeRecordDO);

    int updateByIdOrTradeNo(PayTradeRecordDO payTradeRecordDO);

    List<PayTradeOrderDO> listByMerchantNo(@Param("merchantNo") String merchantNo);

}
