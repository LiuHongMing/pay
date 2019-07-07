//package com.github.tiger.common.extractor.utils;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.TypeReference;
//import com.github.tiger.common.extractor.HtmlMiningApi;
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.joda.time.DateTime;
//import org.joda.time.format.DateTimeFormatter;
//import org.joda.time.format.DateTimeFormatterBuilder;
//import org.joda.time.format.ISODateTimeFormat;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.util.*;
//
//interface ExtractVisitor<T, V> {
//
//    V transform(T target);
//}
//
///**
// * @author liuhongming
// */
//public class PageExtractService {
//
//    private static final Logger logger = LoggerFactory.getLogger(PageExtractService.class);
//
//    public static Map<String, ExampleResult> exampleResultCache = new HashMap<>();
//
//    private HtmlMiningApi htmlMiningApi = new HtmlMiningApi();
//
//    private FeatureSimilarUtil similarUtil = new FeatureSimilarUtil();
//
//    private ExtractVisitor visitor;
//
//    public static final int NATURES = 1;
//
//    public static final int NATURES_RULE = 2;
//
//    private static final Map<String, Map<String, Double>> historyMap;
//
//    static {
//        historyMap = analysisHistory("common", 1);
//    }
//
//    public static void main(String[] args) throws IOException {
//        URL url = Thread.currentThread().getContextClassLoader()
//                .getResource("yjbys");
//
//        String filePath = url.getFile();
//
//        List<String> htmls = new ArrayList<>();
//        for (int i = 1; i <= 3; i++) {
//            String content = Templates.loadContent(filePath + "/list" + i
//                    + ".html");
//            htmls.add(content);
//        }
//
//        String extractId = "common";
//        PageExtractService extractService = new PageExtractService();
//        XjhVisitor xjhVisitor = new XjhVisitor(extractService, false);
//        extractService.accept(xjhVisitor);
//
//        /**
//         * 扫描图片处理
//         */
////        extractService.isNeedImage(true);
//
//        ExampleResult exampleResult = extractService.dataProduce(extractId, htmls);
//
//        /**
//         * 共三条数据
//         */
//        String content = Templates.loadContent(filePath + "/list1.html");
//
//        List<Map<String, String>> exampleData = extractService
//                .extractData(extractId, content);
//        Map<String, String> matchResult = exampleResult.getMatchResult();
//
//        /**
//         * 提取数据图片处理
//         */
////        xjhVisitor.setNeedImage(true);
//
//        List<Map<String, String>> extractResult = extractService
//                .getExtractResult(exampleData, matchResult);
//    }
//
//    private boolean needImage = false;
//
//    public void isNeedImage(boolean needImage) {
//        this.needImage = needImage;
//    }
//
//    public boolean needImage() {
//        return needImage;
//    }
//
//    public ExtractVisitor getVisitor() {
//        return visitor;
//    }
//
//    public void setVisitor(ExtractVisitor visitor) {
//        this.visitor = visitor;
//    }
//
//    /**
//     * 提取数据转换类
//     */
//    public static class DataVisitor implements
//            ExtractVisitor<List<Map<String, String>>, List<Map<String, String>>> {
//
//        public static final String DATE_FIELD = "holdDate";
//        public static final String TIME_FIELD = "holdTime";
//
//        private static final String YYYY_MM_DD = "yyyy-MM-dd";
//        private static final String MM_DD = "MM-dd";
//        private static final String HH_MM = "HH:mm";
//        private static final String HH_MM_BETWEEN = "HH:mm-HH:mm";
//
//        private static final String TODAY = "今天";
//        private static final String TOMORROW = "明天";
//        private static final String AFTER_TOMORROW = "后天";
//
//        /**
//         * 日期格式化
//         * <p>
//         * yyyy-MM-dd
//         */
//        private DateTimeFormatter yearMonthDayFormatter = new DateTimeFormatterBuilder()
//                .appendYear(0, 4).appendLiteral("-").appendMonthOfYear(2).appendLiteral("-")
//                .appendDayOfMonth(2).toFormatter();
//        /**
//         * MM-dd
//         */
//        private DateTimeFormatter monthDayFormatter = new DateTimeFormatterBuilder()
//                .appendMonthOfYear(2).appendLiteral("-").appendDayOfMonth(2).toFormatter();
//        /**
//         * HH:mm-HH:mm
//         */
//        private DateTimeFormatter hourMinuteBetweenFormatter = new DateTimeFormatterBuilder()
//                .appendHourOfDay(2).appendLiteral(":").appendMinuteOfHour(2)
//                .appendLiteral('-')
//                .appendHourOfDay(2).appendLiteral(":").appendMinuteOfHour(2)
//                .toFormatter();
//        /**
//         * 日期掩码
//         */
//        private static final int FULL = 1 << 0;
//        private static final int HALF = 1 << 2;
//
//        @Override
//        public List<Map<String, String>> transform(List<Map<String, String>> target) {
//            for (Map<String, String> example : target) {
//                for (String field : example.keySet()) {
//                    String content = example.get(field);
//
//                    if (DATE_FIELD.equals(field)) {
//
//                        content = dateConvert(content);
//
//                        /**
//                         * \u0020（空格）
//                         * \u00A0（不间断空格）
//                         */
//                        String[] tokens = content.split("[\u0020\u00A0]");
//                        if (tokens.length > 1) {
//                            Map<String, String> dtMap = date(tokens);
//                            example.putAll(dtMap);
//                            continue;
//                        }
//
//                        String start = "(";
//                        String end = ")";
//                        if (content.contains(start) && content.contains(end)) {
//                            String useless = content.substring(content.indexOf(start),
//                                    content.indexOf(end) + 1);
//                            content = content.replace(useless, "");
//                        }
//                    }
//
//                    if (TIME_FIELD.equals(field)) {
//
//                        content = imageConvert(content);
//
//                        /**
//                         * \u0020（空格）
//                         * \u00A0（不间断空格）
//                         */
//                        String[] tokens = content.split("[\u0020\u00A0]");
//                        if (tokens.length > 1) {
//                            Map<String, String> dtMap = time(tokens);
//                            example.putAll(dtMap);
//                            continue;
//                        }
//
//                        if (content.length() > HH_MM.length()
//                                && content.length() > HH_MM_BETWEEN.length()) {
//                            example.put(field, "");
//                            continue;
//                        }
//                    }
//
//                    example.put(field, content);
//                }
//            }
//            return target;
//        }
//
//        public Map<String, String> date(String[] tokens) {
//            Map<String, String> ret = new HashMap<>(4);
//
//            for (String token : tokens) {
//                token = dateConvert(token);
//                int flag = isDate(token);
//                if (flag > 0) {
//                    if ((flag ^ HALF) == 0) {
//                        int year = DateTime.now().getYear();
//                        token = year + "-" + token;
//                    }
//                    ret.put(DATE_FIELD, token);
//                }
//            }
//
//            return ret;
//        }
//
//        public Map<String, String> time(String[] tokens) {
//            Map<String, String> ret = new HashMap<>(4);
//
//            for (String token : tokens) {
//                if (isTime(token)) {
//                    ret.put(TIME_FIELD, token);
//                }
//            }
//
//            return ret;
//        }
//
//        public String dateConvert(String token) {
//            DateTime dateTime = new DateTime();
//            if (token.contains(TODAY)) {
//                token = DateTime.now().toString(YYYY_MM_DD);
//            } else if (token.contains(TOMORROW)) {
//                token = dateTime.plusDays(1).toString(YYYY_MM_DD);
//            } else if (token.contains(AFTER_TOMORROW)) {
//                token = dateTime.plusDays(2).toString(YYYY_MM_DD);
//            }
//            return token;
//        }
//
//        public int isDate(String token) {
//            token = token.trim();
//
//            String delimiter = "[-/]";
//
//            int flag = 0;
//
//            String[] splits = token.split(delimiter);
//            if (splits != null && splits.length > 1) {
//                try {
//                    if (token.length() == YYYY_MM_DD.length()) {
//                        DateTime.parse(token, yearMonthDayFormatter);
//                        flag |= FULL;
//                    }
//                } catch (Exception e) {
//                    logger.error(e.getMessage(), e);
//                }
//
//                try {
//                    if (token.length() == MM_DD.length()) {
//                        DateTime.parse(token, monthDayFormatter);
//                        flag |= HALF;
//                    }
//                } catch (Exception e) {
//                    logger.error(e.getMessage(), e);
//                }
//            }
//            return flag;
//        }
//
//        public boolean isTime(String token) {
//            token = token.trim();
//
//            String delimiter = "[:-]";
//
//            int flag = 0;
//
//            String[] splits = token.split(delimiter);
//            if (splits != null && splits.length > 1) {
//                try {
//                    if (token.length() == HH_MM.length()) {
//                        DateTime.parse(token, ISODateTimeFormat.hourMinute());
//                        flag |= HALF;
//                    }
//                    if (token.length() == (HH_MM.length() * 2 + 1)) {
//                        DateTime.parse(token, hourMinuteBetweenFormatter);
//                        flag |= HALF;
//                    }
//                } catch (Exception e) {
//                    logger.error(e.getMessage(), e);
//                }
//            }
//            return flag > 0;
//        }
//
//        public String imageConvert(String imageUrl) {
//            return imageUrl;
//        }
//    }
//
//    public static class XjhVisitor extends DataVisitor {
//
//        private NetHttpClient netHttpClient = new NetHttpClient();
//
//        private boolean needImage;
//
//        public XjhVisitor(PageExtractService service, boolean needImage) {
//            this.needImage = needImage;
//            service.isNeedImage(needImage);
//        }
//
//        @Override
//        public String imageConvert(String imageUrl) {
//            if (isNeedImage()) {
//                try {
//                    imageUrl = XjhUtil.YJBYS_DOMAIN + imageUrl;
//                    InputStream inStream = netHttpClient.getResponseInputStream(imageUrl);
//                    String time = XjhUtil.getTime(inStream);
//                    String none = "-1";
//                    if (StringUtils.isNoneEmpty(time)
//                            && !none.equals(time)) {
//                        return time;
//                    } else {
//                        return "-1";
//                    }
//                } catch (IOException e) {
//                    logger.error(e.getMessage(), e);
//                }
//            }
//            return imageUrl;
//        }
//
//        public boolean isNeedImage() {
//            return needImage;
//        }
//
//        public void setNeedImage(boolean needImage) {
//            this.needImage = needImage;
//        }
//    }
//
//    public void accept(ExtractVisitor visitor) {
//        this.visitor = visitor;
//    }
//
//    public static class ExampleResult {
//
//        private String pageCssQuery;
//
//        private List<Map<String, String>> exampleData;
//
//        private Map<String, String> matchResult;
//
//        private String FEATURE_IMAGE_SRC = ".src";
//
////        private NetHttpClient httpClient = new NetHttpClient();
//
//        public ExampleResult() {
//        }
//
//        public ExampleResult(String pageCssQuery,
//                             List<Map<String, String>> exampleData,
//                             Map<String, String> matchResult) {
//            this.pageCssQuery = pageCssQuery;
//            this.exampleData = exampleData;
//            this.matchResult = matchResult;
//        }
//
//        public String getPageCssQuery() {
//            return pageCssQuery;
//        }
//
//        public void setPageCssQuery(String pageCssQuery) {
//            this.pageCssQuery = pageCssQuery;
//        }
//
//        public List<Map<String, String>> getExampleData() {
//            return exampleData;
//        }
//
//        public void setExampleData(List<Map<String, String>> exampleData) {
//            this.exampleData = exampleData;
//        }
//
//        public Map<String, String> getMatchResult() {
//            return matchResult;
//        }
//
//        public void setMatchResult(Map<String, String> matchResult) {
//            this.matchResult = matchResult;
//        }
//
//        public void scanImage() {
//            for (Map<String, String> dataMap : exampleData) {
//                for (String fieldKey : dataMap.keySet()) {
//                    if (fieldKey.contains(FEATURE_IMAGE_SRC)) {
//                        String imageUrl = dataMap.get(fieldKey);
////                        try {
////                            imageUrl = XjhUtil.YJBYS_DOMAIN + imageUrl;
////                            InputStream inStream = httpClient.getResponseInputStream(imageUrl);
////                            String time = XjhUtil.getTime(inStream);
////                            String none = "-1";
////                            if (StringUtils.isNoneEmpty(time)
////                                    && !none.equals(time)) {
////                                dataMap.put(fieldKey, time);
////                            } else {
////                                dataMap.put(fieldKey, "-1");
////                            }
////                        } catch (IOException e) {
////                            logger.error(e.getMessage(), e);
////                        }
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * 样本数据处理，缓存样本处理结果
//     *
//     * @param extractId
//     * @param exampleHtmls
//     * @return
//     */
//    public ExampleResult dataProduce(String extractId, List<String> exampleHtmls) {
//
//        ExampleResult exampleResult = new ExampleResult();
//
//        List<Map<String, String>> exampleData = new ArrayList<>();
//        for (String html : exampleHtmls) {
//            Map<String, List<String>> extractResult = extractComplete(html, null);
//
//            Collection<List<String>> pageList = extractResult.values();
//            for (List<String> pageItems : pageList) {
//                for (String data : pageItems) {
//                    Map<String, String> dataMap = JSON.parseObject(data,
//                            new TypeReference<Map<String, String>>() {
//                            });
//                    exampleData.add(dataMap);
//                }
//            }
//            Set<String> keySet = extractResult.keySet();
//            if (keySet != null && keySet.size() > 0) {
//                exampleResult.setPageCssQuery((String) keySet.toArray()[0]);
//            }
//        }
//
//        dataProduce(extractId, exampleData, exampleResult);
//
//        return exampleResult;
//    }
//
//    public ExampleResult dataProduce(
//            String extractId, List<Map<String, String>> exampleData, ExampleResult exampleResult) {
//
//        exampleResult.setExampleData(exampleData);
//        if (needImage()) {
//            exampleResult.scanImage();
//        }
//
//        Map<String, String> matchResult = getMatchResult(exampleData, NATURES);
//        exampleResult.setMatchResult(matchResult);
//
//        exampleResultCache.put(extractId, exampleResult);
//
//        return exampleResult;
//    }
//
//    /**
//     * 提取完整内容
//     *
//     * @param pageCssQuery
//     * @param html
//     * @return
//     */
//    private Map<String, List<String>> extractComplete(String html, String pageCssQuery) {
//        Map<String, List<String>> ret = new HashMap<>(16);
//
//        try {
//            Map<String, List<String>> pageList = htmlMiningApi.extract(html,
//                    pageCssQuery);
//
//            Set<String> keySet = pageList.keySet();
//            Collection<List<String>> values = pageList.values();
//            if (keySet != null && keySet.size() > 0) {
//                if (values != null && values.size() > 0) {
//                    for (String key : keySet) {
//                        pageCssQuery = key;
//                        pageList = htmlMiningApi.extract(html, pageCssQuery);
//                    }
//                    ret = pageList;
//                }
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
//
//        return ret;
//    }
//
//    /**
//     * 提取数据内容
//     *
//     * @param extractId
//     * @param html
//     * @return
//     */
//    public List<Map<String, String>> extractData(String extractId, String html) {
//        ExampleResult exampleResult = exampleResultCache.get(extractId);
//
//        if (exampleResult != null) {
//            List<Map<String, String>> exampleData = new ArrayList<>();
//
//            String pageCssQuery = exampleResult.getPageCssQuery();
//            Map<String, List<String>> extractResult = extractComplete(html, pageCssQuery);
//
//            Collection<List<String>> pageList = extractResult.values();
//            for (List<String> pageItems : pageList) {
//                for (String data : pageItems) {
//                    Map<String, String> dataMap = JSON.parseObject(data,
//                            new TypeReference<Map<String, String>>() {
//                            });
//                    exampleData.add(dataMap);
//                }
//            }
//
//            return exampleData;
//        }
//
//        return Collections.emptyList();
//    }
//
//    /**
//     * 获取提取结果
//     *
//     * @return
//     */
//    public List<Map<String, String>> getExtractResult(
//            List<Map<String, String>> exampleData, Map<String, String> matchResult) {
//
//        List<Map<String, String>> ret = new ArrayList<>();
//
//        try {
//
//            logger.info(JSON.toJSONString(exampleData));
//
//            Set<String> matchKeySet = matchResult.keySet();
//
//            for (Map<String, String> example : exampleData) {
//                Map<String, String> content = new HashMap<>(16);
//
//                for (String field : matchKeySet) {
//                    Set<String> cssSet = new HashSet<>();
//                    String exampleCssQuery = matchResult.get(field);
//                    String exampleContent = example.get(exampleCssQuery);
//                    if (exampleContent == null) {
//                        exampleContent = "";
//                    }
//                    /**
//                     * 获取子标签内容
//                     */
//                    for (String css : example.keySet()) {
//                        if (css.equals(exampleCssQuery)) {
//                            continue;
//                        }
//                        if (css.indexOf(exampleCssQuery) == 0
//                                && !css.contains(".href")) {
//                            exampleContent += example.get(css);
//                        }
//
//                    }
//                    /**
//                     * 如果属性没有匹配到数据
//                     *
//                     * 则查找其余相似数据进行匹配
//                     */
//                    if (StringUtils.isBlank(exampleContent)) {
//                        double maxScore = 0.3;
//                        for (String css : example.keySet()) {
//                            if (cssSet.contains(css)) {
//                                continue;
//                            }
//                            double score = otherMatch(example.get(css),
//                                    historyMap.get(field));
//                            if (score > maxScore) {
//                                maxScore = score;
//                                exampleContent = example.get(css);
//                            }
//                        }
//                    }
//                    content.put(field, exampleContent);
//                    cssSet.add(exampleCssQuery);
//                }
//
//                ret.add(content);
//            }
//
//            if (visitor != null) {
//                visitor.transform(ret);
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
//
//        logger.info(JSON.toJSONString(ret));
//
//        return ret;
//    }
//
//    /**
//     * 获取提取结果
//     *
//     * @return
//     */
//    public List<Map<String, String>> getExtractResult(String extractId, List<String> htmls) {
//
//        ExampleResult exampleResult = dataProduce(extractId, htmls);
//
//        List<Map<String, String>> exampleData = exampleResult.getExampleData();
//        Map<String, String> matchResult = exampleResult.getMatchResult();
//
//        List<Map<String, String>> ret = getExtractResult(exampleData, matchResult);
//
//        return ret;
//    }
//
//    private double otherMatch(String content, Map<String, Double> hisMap) {
//        List<String> natures = Analysis.getDicAnalysisNatures(content);
//        Map<String, Double> map = new HashMap<>(16);
//        for (String nature : natures) {
//            if (!map.containsKey(nature)) {
//                map.put(nature, 0d);
//            }
//            double count = map.get(nature) + 1;
//            map.put(nature, count);
//        }
//        return FeatureSimilarUtil.getCosineSimilarity(map, hisMap);
//    }
//
//    public Map<String, String> getMatchResult(List<Map<String, String>> exampleData, int matchMode) {
//
//        Map<String, Map<String, Double>> matchingMap = matching(exampleData, matchMode);
//
//        Map<String, String> ret = new HashMap<>(16);
//        for (String field : matchingMap.keySet()) {
//            Map<String, Double> exampleScoreMap = matchingMap.get(field);
//            for (String example : exampleScoreMap.keySet()) {
//                ret.put(field, example);
//            }
//        }
//
//        return ret;
//    }
//
//    /**
//     * 分析样本数据
//     *
//     * @return
//     */
//    public Map<String, Map<String, Double>> analysisExample(
//            List<Map<String, String>> exampleData, int matchMode) {
//        if (matchMode == NATURES) {
//            return similarUtil.getNaturesProbabiliMap(exampleData);
//        } else {
//            return similarUtil.getCombinationNaturesProbabiliMap(exampleData);
//        }
//    }
//
//    /**
//     * 分析历史数据
//     *
//     * @return
//     */
//    public static Map<String, Map<String, Double>> analysisHistory(
//            String historyFlag, int matchMode) {
//
//        Map<String, Map<String, Double>> ret = new HashMap<>(16);
//
//        final int minDataBytes = 512;
//
//        URL resourceURL = Thread.currentThread().getContextClassLoader()
//                .getResource("");
//        String resourcePath = resourceURL.getPath();
//
//        String historyDir = resourcePath + "/history";
//        try {
//            FileUtils.forceMkdir(new File(historyDir));
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//        }
//
//        String historyFile;
//        if (matchMode == NATURES) {
//            historyFile = String.format("%s/%s_nature.dat", historyDir, historyFlag);
//        } else {
//            historyFile = String.format("%s/%s_nature_rule.dat", historyDir,
//                    historyFlag);
//        }
//
//        if (FileUtil.fileExists(historyFile)) {
//            try {
//                byte[] historyBytes = IOUtils.toByteArray(new FileReader(
//                        historyFile));
//                if (historyBytes.length > minDataBytes) {
//                    ret = JSON.parseObject(new String(historyBytes),
//                            new TypeReference<Map<String, Map<String, Double>>>() {
//                            });
//                }
//            } catch (IOException e) {
//                logger.error(e.getMessage(), e);
//            }
//        }
//
//        return ret;
//    }
//
//    public void weight(Map<String, Map<String, Double>> map) {
//        for (String key : map.keySet()) {
//            for (String fkey : map.get(key).keySet()) {
//                if ("htime".equalsIgnoreCase(fkey)) {
//                    map.get(key).put(fkey, map.get(key).get(fkey) * 2);
//                }
//            }
//        }
//    }
//
//
//    /**
//     * 样本与历史数据相似度匹配
//     *
//     * @param exampleData
//     * @return
//     */
//    public Map<String, Map<String, Double>> matching(
//            List<Map<String, String>> exampleData, int matchMode) {
//        double scoreThreshold = 0.3;
//
//        Map<String, Map<String, Double>> exampleMap = analysisExample(
//                exampleData, matchMode);
//
//        weight(exampleMap);
//
//        Map<String, Map<String, Double>> newHistoryMap = new HashMap<>();
//        for (String key : historyMap.keySet()) {
//            Map<String, Double> newMap = new HashMap<String, Double>();
//            newMap.putAll(historyMap.get(key));
//            newHistoryMap.put(key, newMap);
//        }
//
//        weight(newHistoryMap);
//
//        Map<String, Map<String, Double>> matchingMap = new HashMap<>(16);
//        for (String example : exampleMap.keySet()) {
//            for (String history : newHistoryMap.keySet()) {
//                double score = FeatureSimilarUtil.getCosineSimilarity(
//                        exampleMap.get(example), newHistoryMap.get(history));
//                Map<String, Double> exampleScoreMap = matchingMap.get(history);
//                if (exampleScoreMap == null) {
//                    exampleScoreMap = new HashMap<>(16);
//                }
//                if (score > scoreThreshold) {
//                    exampleScoreMap.put(example, score);
//                    matchingMap.put(history, exampleScoreMap);
//                }
//            }
//        }
//
//        filter(matchingMap);
//
//        return matchingMap;
//    }
//
//    /**
//     * 筛选匹配最大项
//     *
//     * @param matchingMap
//     */
//    public static void filter(Map<String, Map<String, Double>> matchingMap) {
//        String tempExample = null;
//        double tempScore = -1;
//
//        for (String key : matchingMap.keySet()) {
//            Map<String, Double> exampleScoreMap = matchingMap.get(key);
//            for (String example : exampleScoreMap.keySet()) {
//                double score = exampleScoreMap.get(example);
//                if (score >= tempScore) {
//                    tempExample = example;
//                    tempScore = score;
//                }
//            }
//
//            Map<String, Double> res = new HashMap<>(16);
//            res.put(tempExample, tempScore);
//
//            matchingMap.put(key, res);
//
//            tempExample = null;
//            tempScore = -1;
//        }
//
//    }
//}
