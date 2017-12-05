package com.senyint.pay.web.controller;

import com.google.common.collect.Maps;
import com.senyint.common.notify.NotifyManager;
import com.senyint.common.util.Dom4jUtil;
import com.senyint.common.util.MapBuilder;
import com.senyint.common.wx.WxConstants;
import com.senyint.common.wx.WxUtil;
import com.senyint.pay.dto.TradeOrderDTO;
import com.senyint.pay.dto.TradeRecordDTO;
import com.senyint.pay.constant.wxpay.WxTradeStatusEnum;
import com.senyint.pay.service.TradeService;
import org.apache.commons.io.IOUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * 提供交易接口
 *
 * @author liuhongming
 */
//@RestController
//@RequestMapping("/trades")
public class TradeController extends PlatoController {

    private static final Logger logger = LoggerFactory.getLogger(TradeController.class);

    @Autowired
    @Qualifier("yamlProperties")
    private Properties yamlProperties;

    @Autowired
    private TradeService tradeService;

    private static final NotifyManager notifyManager = new NotifyManager();

    /**
     * 微信支付通知
     */
    @RequestMapping("/notify/wx")
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> notifyResult = Maps.newHashMap();
        try {
            ServletInputStream in = request.getInputStream();
            if (in != null) {
                byte[] inBytes = IOUtils.toByteArray(in);
                String notifyXml = new String(inBytes, "UTF-8");
                logger.info("微信支付结果内容\n{}", notifyXml);
                notifyResult = Dom4jUtil.parse2Map(notifyXml);

                String appId = notifyResult.get(WxConstants.APP_ID);
                String appSecret = yamlProperties.getProperty(appId + "." + WxConstants.APP_SECRET);
                String partnerKey = yamlProperties.getProperty(appId + "." + WxConstants.PARTNER_KEY);
                boolean checkSign = WxUtil.checkSign(request, response, notifyResult,
                        appId, appSecret, partnerKey);
                logger.info("验证签名结果：{}", checkSign);
                if (!checkSign) {
                    return;
                }
            }
        } catch (IOException e) {
            logger.error("读取微信支付结果异常：" + e.getMessage(), e);
        } catch (DocumentException e) {
            logger.error("解析微信支付结果异常：" + e.getMessage(), e);
        }

        String success = "<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>";
        String failure = "<xml><return_code><![CDATA[FAIL]]></return_code></xml>";
        String msg = failure;

        // 业务代码
        String returnCode = null;
        if (notifyResult != null) {
            returnCode = notifyResult.get("return_code");
        }
        String outTradeNo = notifyResult.get("out_trade_no");
        String merchantOrderNo = null, tradeNo = null;
        try {
            if (WxTradeStatusEnum.SUCCESS.name().equals(returnCode)) {
                if (!StringUtils.isEmpty(outTradeNo)) {
                    String[] ids = outTradeNo.split("_");
                    if (!ObjectUtils.isEmpty(ids)) {
                        if (ids.length > 0)
                            merchantOrderNo = ids[0];
                        if (ids.length > 1)
                            tradeNo = ids[1];
                    }
                    TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
                    tradeOrderDTO.setMerchantOrderNo(merchantOrderNo);
                    tradeOrderDTO.setTradeStatus(WxTradeStatusEnum.SUCCESS.name());
                    TradeRecordDTO tradeRecordDTO = new TradeRecordDTO();
                    tradeRecordDTO.setTradeNo(tradeNo);
                    tradeRecordDTO.setRemark(String.format("微信支付结果通知: %s", notifyResult.toString()));
                    tradeService.updateTradeToComplete(tradeOrderDTO, tradeRecordDTO);

                    msg = success;
                    if (logger.isInfoEnabled()) {
                        logger.info("微信支付成功：out_trade_no：{}", outTradeNo);
                    }
                }
            } else {
                if (logger.isInfoEnabled()) {
                    logger.info("微信支付失败：out_trade_no：{}", outTradeNo);
                }
            }
        } catch (Exception e) {
            msg = failure;
            logger.error("微信支付结果异常：" + e.getMessage(), e);
        }
        response.setContentType("text/html;charset=UTF-8");
        response.getOutputStream().write(msg.getBytes());
        response.getOutputStream().flush();

        try {
            if (WxTradeStatusEnum.SUCCESS.name().equals(returnCode)) {
                // 商户通知
                TradeOrderDTO tradeOrderDTO = tradeService.getTradeOrder(merchantOrderNo, tradeNo);
                if (tradeOrderDTO != null) {
                    String notifyUrl = tradeOrderDTO.getNotifyUrl();
                    if (notifyUrl != null) {
                        Map<String, Object> params = MapBuilder.newMap("out_trade_no", outTradeNo);
                        boolean flag = notifyManager.notify(notifyUrl, params);
                        if (flag) {
                            logger.info("商户通知成功：notifyUrl：{}, params：{}", notifyUrl, params);
                        } else {
                            logger.info("商户通知失败：notifyUrl：{}, params：{}", notifyUrl, params);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("商户通知异常：" + e.getMessage(), e);
        }
    }


}
