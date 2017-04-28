package com.senyint.common.wx;

import com.google.common.collect.Maps;
import com.senyint.common.util.Dom4jUtil;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 微信工具类
 * <p>
 * 功能：验证微信签名
 *
 * @author liuhongming
 */
public class WxUtil {

    public static boolean checkSign(HttpServletRequest request, HttpServletResponse response,
                                    Map<String, String> notifyResult,
                                    String appId, String appSecret, String partnerKey) throws DocumentException {
        // 复制结果
        Map<String, String> resultMap = Maps.newHashMap(notifyResult);
        String resultSign = resultMap.get(WxConstants.SIGN);
        resultMap.remove(WxConstants.SIGN);
        // 参数排序
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.putAll(resultMap);

        RequestHandler reqHandler = new RequestHandler(request, response);
        reqHandler.init(appId, appSecret, partnerKey);
        String sign = reqHandler.createSign(packageParams);
        if (resultSign.equals(sign)) {
            return true;
        }

        return false;
    }

}
