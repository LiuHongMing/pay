package com.github.tiger.pay.rpc.api;

import com.github.tiger.pay.rpc.Result;
import com.github.tiger.pay.query.TradeQUERY;
import com.github.tiger.pay.vo.TradeVO;

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
