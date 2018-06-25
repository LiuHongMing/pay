package com.github.tiger.common.util;

import com.google.common.collect.Maps;
import org.dom4j.*;

import java.util.Map;

/**
 * Dom4j工具类
 *
 * @author liuhongming
 */
public class Dom4jUtil {

    /**
     * 解析xml字符串，转换成map
     *
     * @param content xml字符串
     * @return
     * @throws DocumentException
     */
    public static Map parse2Map(String content) throws DocumentException {
        MapVisitor mapVisitor = new MapVisitor();
        Document document = parseText(content);
        document.accept(mapVisitor);
        return mapVisitor.getResult();
    }

    /**
     * 创建新的Document对象
     *
     * @return
     */
    public static Document newDocument() {
        return DocumentHelper.createDocument();
    }

    /**
     * 将xml字符串转换成Document对象
     *
     * @param content xml字符串
     * @return
     * @throws DocumentException
     */
    public static Document parseText(String content) throws DocumentException {
        return DocumentHelper.parseText(content);
    }

    /**
     * 采用访问者模式对Document进行访问，将内容转换成Map结构
     */
    private static class MapVisitor extends VisitorSupport {

        private Map<String, String> result = Maps.newHashMap();

        @Override
        public void visit(Element node) {
            if (!node.isRootElement()) {
                result.put(node.getName(), node.getText());
            }
        }

        public Map getResult() {
            return result;
        }
    }

}
