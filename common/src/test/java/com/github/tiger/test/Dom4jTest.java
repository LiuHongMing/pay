package com.github.tiger.test;

import com.github.tiger.pay.common.util.Dom4jUtil;
import org.dom4j.*;
import org.junit.Test;

import java.util.Map;

public class Dom4jTest {

    @Test
    public void testParse2Map() throws DocumentException {
        String wxXml =
                "<xml>\n" +
                        "  <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n" +
                        "  <attach><![CDATA[支付测试]]></attach>\n" +
                        "  <bank_type><![CDATA[CFT]]></bank_type>\n" +
                        "  <fee_type><![CDATA[CNY]]></fee_type>\n" +
                        "  <is_subscribe><![CDATA[Y]]></is_subscribe>\n" +
                        "  <mch_id><![CDATA[10000100]]></mch_id>\n" +
                        "  <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>\n" +
                        "  <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>\n" +
                        "  <out_trade_no><![CDATA[909809618381664_2017031775706013]]></out_trade_no>\n" +
                        "  <result_code><![CDATA[SUCCESS]]></result_code>\n" +
                        "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                        "  <sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>\n" +
                        "  <sub_mch_id><![CDATA[10000100]]></sub_mch_id>\n" +
                        "  <time_end><![CDATA[20140903131540]]></time_end>\n" +
                        "  <total_fee>1</total_fee>\n" +
                        "  <trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                        "  <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>\n" +
                        "</xml>";
        Map result = Dom4jUtil.parse2Map(wxXml);
        System.out.println(result);
    }

    @Test
    public void testCreateDocument() {
        Document document = DocumentHelper.createDocument();

        DocumentFactory documentFactory = DocumentFactory.getInstance();
        Element root = documentFactory.createElement("root");
        document.setRootElement(root);

        Element body = documentFactory.createElement("body");
        QName type = documentFactory.createQName("type");
        body.addElement(type);

        root.add(body);

        System.out.println(document.asXML());
    }

}
