package com.senyint.pay.dao;

import com.senyint.common.dao.BaseDAO;
import com.senyint.pay.dao.model.PayTradeOrderDO;
import com.senyint.pay.dao.model.PayTradeRecordDO;
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
