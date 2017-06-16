package com.senyint.pay.rpc.api;

import com.senyint.pay.query.TradeQUERY;
import com.senyint.pay.rpc.Result;
import com.senyint.pay.vo.TradeVO;

/**
 * 交易RPC接口，定义了交易业务方法
 *
 * @author liuhongming
 */
public interface TradeRpcService {

    /**
     * 创建交易
     */
    Result<TradeVO> createTrade(TradeVO tradeVO) throws Exception;

    /**
     * 查询交易
     */
    Result<TradeVO> queryTrade(TradeQUERY tradeQUERY) throws Exception;

    /**
     * 更新交易
     */
    Result<String> updateTrade(TradeVO tradeVO) throws Exception;

}
