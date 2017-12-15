package com.github.tiger.pay.test.pay.service;

import com.github.tiger.pay.test.ServiceJunitTest;
import com.github.tiger.pay.common.util.RandomStringUtil;
import com.github.tiger.pay.constant.wxpay.WxTradeStatusEnum;
import com.github.tiger.pay.dto.OutTradeNoDTO;
import com.github.tiger.pay.dto.TradeOrderDTO;
import com.github.tiger.pay.dto.TradeRecordDTO;
import com.github.tiger.pay.service.TradeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Set;

public class TradeServiceTest extends ServiceJunitTest {

    @Autowired
    private TradeService tradeService;

    String merchantOrderNo = "909809618381664";
    String tradeNo = "2017031775706013";

    @Test
    public void testWxUpdateTradeToComplete() throws Exception {
        String transactionId = RandomStringUtil.randomAlphabetic(16);
        TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
        tradeOrderDTO.setMerchantOrderNo(merchantOrderNo);
        tradeOrderDTO.setTradeStatus(WxTradeStatusEnum.SUCCESS.name());
        TradeRecordDTO tradeRecordDTO = new TradeRecordDTO();
        tradeRecordDTO.setTradeNo(tradeNo);
        tradeRecordDTO.setRemark("微信交易号:" + transactionId);
        int result = tradeService.updateTradeToComplete(tradeOrderDTO, tradeRecordDTO);
        print(result);
    }

    @Test
    public void testGetTradeOrder() throws Exception {
        try {
            TradeOrderDTO tradeOrderDTO = tradeService.getTradeOrder(null, tradeNo);
            print(tradeOrderDTO);
        } catch (ConstraintViolationException constraintViolationException) {
            Set<ConstraintViolation<?>> constraints = constraintViolationException.getConstraintViolations();
            constraints.forEach(constraintViolation ->
                    System.out.println(constraintViolation.getMessage())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveTrade() throws Exception {
        try {
            TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
            tradeOrderDTO.setTotalAmount(new BigDecimal(9999999.00));
            tradeOrderDTO.setProductName("fuck you");
            TradeRecordDTO tradeRecordDTO = new TradeRecordDTO();
            OutTradeNoDTO outTradeNoDTO = tradeService.saveTrade(tradeOrderDTO, tradeRecordDTO);
            print(outTradeNoDTO);
        } catch (ConstraintViolationException constraintViolationException) {
            Set<ConstraintViolation<?>> constraints = constraintViolationException.getConstraintViolations();
            constraints.forEach(constraintViolation ->
                    System.out.println(constraintViolation.getMessage())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
