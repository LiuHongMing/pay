package com.senyint.test.dubbo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.senyint.pay.query.TradeQUERY;
import com.senyint.pay.rpc.RpcResult;
import com.senyint.pay.rpc.api.TradeRpcService;
import com.senyint.pay.vo.TradeVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com/senyint/pay/dubbo/pay-consumer.xml"})
public class PayConsumerTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testCreateTrade() throws Exception {
        TradeRpcService tradeRpcApi = applicationContext.getBean(TradeRpcService.class);
        TradeVO tradeVO = new TradeVO();
        //tradeVO.setTotalAmount(new BigDecimal(99.99));
        try {
            RpcResult<TradeVO> rpcResult = tradeRpcApi.createTrade(tradeVO);
            System.out.println(JSON.toJSONString(rpcResult, SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQueryTrade() throws Exception {
        TradeRpcService tradeRpcApi = applicationContext.getBean(TradeRpcService.class);
        TradeQUERY tradeQUERY = new TradeQUERY();
        tradeQUERY.setMerchantOrderNO("909809618381664");
        tradeQUERY.setTradeNo("2017031775706013");
        try {
            RpcResult<TradeVO> rpcResult = tradeRpcApi.queryTrade(tradeQUERY);
            System.out.println(JSON.toJSONString(rpcResult, SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetResult() throws Exception {
        TradeRpcService tradeRpcApi = applicationContext.getBean(TradeRpcService.class);
        try {
            String inStr = "hello world";
            RpcResult<String> rpcResult = tradeRpcApi.getResult(inStr);
            System.out.println(rpcResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
