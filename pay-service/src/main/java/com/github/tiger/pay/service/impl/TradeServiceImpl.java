package com.github.tiger.pay.service.impl;

import com.github.tiger.pay.common.util.DateTimeUtil;
import com.github.tiger.pay.common.util.RandomStringUtil;
import com.github.tiger.pay.exception.TradeErrorCode;
import com.github.tiger.pay.biz.constant.BizConstants;
import com.github.tiger.pay.dao.PayTradeOrderDAO;
import com.github.tiger.pay.dao.PayTradeRecordDAO;
import com.github.tiger.pay.dao.model.PayTradeOrderDO;
import com.github.tiger.pay.dao.model.PayTradeRecordDO;
import com.github.tiger.pay.dto.OutTradeNoDTO;
import com.github.tiger.pay.dto.TradeOrderDTO;
import com.github.tiger.pay.dto.TradeRecordDTO;
import com.github.tiger.pay.dto.factory.OutTradeNoFactory;
import com.github.tiger.pay.exception.TradeException;
import com.github.tiger.pay.service.TradeService;
import com.github.tiger.pay.common.util.BeanFactoryUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 交易服务实现类
 *
 * @author liuhongming
 */
@Validated
@Service
public class TradeServiceImpl implements TradeService {

    private static final Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);

    @Autowired
    private PayTradeOrderDAO payTradeOrderDAO;

    @Autowired
    private PayTradeRecordDAO payTradeRecordDAO;

    @Override
    public OutTradeNoDTO saveTrade(TradeOrderDTO tradeOrderDTO, TradeRecordDTO tradeRecordDTO) throws Exception {

        String merchantOrderNo = RandomStringUtil.randomNumeric(15);
        String tradeNo = String.format("%1$tY%1$tm%1$td%2$s",
                new Date(), RandomStringUtil.randomNumeric(8));
        try {
            /**
             * 交易订单处理
             */
            PayTradeOrderDO payTradeOrderDO = BeanFactoryUtil.copyProperties(tradeOrderDTO, PayTradeOrderDO.class);
            payTradeOrderDO.setVersion(BizConstants.FIRST_VERSION);
            payTradeOrderDO.setGmtCreate(DateTimeUtil.now());
            // 商户订单号
            payTradeOrderDO.setMerchantOrderNo(merchantOrderNo);
            // 交易号
            payTradeOrderDO.setTradeNo(tradeNo);
            // 下单时间
            DateTime orderTime = DateTime.now();
            payTradeOrderDO.setOrderTime(orderTime.toDate());
            payTradeOrderDO.setExpireTime(orderTime.plus(TimeUnit.MINUTES.toMicros(BizConstants.PAY_PERIOD)).toDate());
            // 保存交易订单
            payTradeOrderDAO.save(payTradeOrderDO);

            /**
             * 交易记录处理
             */
            PayTradeRecordDO payTradeRecordDO = BeanFactoryUtil.copyProperties(tradeRecordDTO, PayTradeRecordDO.class);
            payTradeRecordDO.setVersion(BizConstants.FIRST_VERSION);
            payTradeRecordDO.setGmtCreate(DateTimeUtil.now());
            // 商户订单号
            payTradeRecordDO.setMerchantOrderNo(payTradeOrderDO.getMerchantOrderNo());
            // 交易号
            payTradeRecordDO.setTradeNo(tradeNo);
            // 保存交易记录
            payTradeRecordDAO.save(payTradeRecordDO);
        } catch (Exception e) {
            throw new TradeException(TradeErrorCode.SYSTEM_ERROR);
        }

        return OutTradeNoFactory.newInstance(merchantOrderNo, tradeNo);
    }

    @Override
    public int updateTradeToComplete(TradeOrderDTO tradeOrderDTO, TradeRecordDTO tradeRecordDTO) throws Exception {
        int result;
        try {
            result = updateTradeOrderToComplete(tradeOrderDTO);
            result &= updateTradeRecordToComplete(tradeRecordDTO);
        } catch (Exception e) {
            throw new TradeException(TradeErrorCode.SYSTEM_ERROR);
        }
        return result;
    }

    @Override
    public TradeOrderDTO getTradeOrder(String merchantOrderNo, String tradeNo) throws Exception {
        TradeOrderDTO tradeOrderDTO = null;

        PayTradeOrderDO payTradeOrderDO = payTradeOrderDAO.getByMerchantOrderNoOrTradeNo(merchantOrderNo, tradeNo);
        try {
            if (payTradeOrderDO != null) {
                tradeOrderDTO = BeanFactoryUtil.copyProperties(payTradeOrderDO, TradeOrderDTO.class);
            }
        } catch (Exception e) {
            throw new TradeException(TradeErrorCode.SYSTEM_ERROR);
        }

        return tradeOrderDTO;
    }

    @Override
    public int updateTradeOrderToComplete(TradeOrderDTO tradeOrderDTO) throws Exception {
        int result;
        try {
            PayTradeOrderDO payTradeOrderDO = BeanFactoryUtil.copyProperties(tradeOrderDTO, PayTradeOrderDO.class);
            payTradeOrderDO.setGmtModified(new Date());
            result = payTradeOrderDAO.updateByIdOrMerchantOrderNo(payTradeOrderDO);
        } catch (Exception e) {
            throw new TradeException(TradeErrorCode.SYSTEM_ERROR);
        }
        return result;
    }

    @Override
    public int updateTradeRecordToComplete(TradeRecordDTO tradeRecordDTO) {
        PayTradeRecordDO payTradeRecordDO = BeanFactoryUtil.copyProperties(tradeRecordDTO, PayTradeRecordDO.class);
        Date now = DateTimeUtil.now();
        payTradeRecordDO.setGmtModified(now);
        payTradeRecordDO.setCompleteTime(now);
        int result = payTradeRecordDAO.updateByIdOrTradeNo(payTradeRecordDO);
        return result;
    }
}
