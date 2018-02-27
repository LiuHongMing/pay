package com.github.tiger.test.dubbo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.tiger.pay.query.TradeQUERY;
import com.github.tiger.pay.rpc.Result;
import com.github.tiger.pay.rpc.api.TradeRpcService;
import com.github.tiger.pay.vo.TradeVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com/github/tiger/pay/dubbo/pay-consumer.xml"})
public class PayConsumerTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testCreateTrade() throws Exception {
        TradeRpcService tradeRpcApi = applicationContext.getBean(TradeRpcService.class);
        TradeVO tradeVO = new TradeVO();
        //tradeVO.setTotalAmount(new BigDecimal(99.99));
        try {
            Result<TradeVO> rpcResult = tradeRpcApi.createTrade(tradeVO);
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
            Result<TradeVO> rpcResult = tradeRpcApi.queryTrade(tradeQUERY);
            System.out.println(JSON.toJSONString(rpcResult, SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
