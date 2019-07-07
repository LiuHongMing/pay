//package com.github.tiger.common.extractor.utils;
//
//import com.alibaba.fastjson.JSON;
//import com.github.tiger.common.extractor.HtmlMiningApi;
//import com.zpcampus.crawler.extraction.v1.HtmlMiningApi;
//import com.zpcampus.crawler.logic.model.PagingRulesModel;
//import com.zpcampus.platform.dataaccess.fetch.dao.FetchConfigDao;
//import com.zpcampus.platform.dataaccess.fetch.entity.FetchConfigEntity;
//import com.zpcampus.publicproject.http.NetHttpClient;
//import org.apache.commons.lang3.StringUtils;
//
//import java.io.IOException;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
///**
// * @author liuhongming
// */
//interface CheckCommand extends Runnable {
//
//    /**
//     * 探测位置
//     *
//     * @param detect
//     * @return
//     */
//    boolean tryDetect(int detect);
//
//}
//
///**
// * @author liuhongming
// */
//public class PageEndCheckService {
//
//    private static final int HAITOU_SITEID = 16;
//
//    private static final int YINJIESHENG_SITEID = 18;
//
//    /**
//     * 额外页数
//     */
//    private static final int PAGE_EXTRA = 3;
//
//    public static void main(String[] args) {
//        PageEndCheckService checkService = new PageEndCheckService();
//        /**
//         * 海投
//         */
//        checkService.check(HAITOU_SITEID, new HaitouCheckCommand());
//        /**
//         * 应届生
//         */
//        checkService.check(YINJIESHENG_SITEID, new YjsCheckCommand());
//    }
//
//    public void check(int siteId, PageEndCheckCommand command) {
//        List<PagingRulesModel> prmList;
//        List<FetchConfigEntity> fetchConfigEntityList = getFetchConfigEntitys(siteId);
//        for (FetchConfigEntity entity : fetchConfigEntityList) {
//
//            String pagingRules = entity.getPagingRules();
//            if (StringUtils.isEmpty(pagingRules)) {
//                continue;
//            }
//            prmList = JSON.parseArray(pagingRules,
//                    PagingRulesModel.class);
//
//            if (prmList != null) {
//                for (PagingRulesModel prmModel : prmList) {
//                    String pageUrl = prmModel.getUrlTemp();
//                    int end = prmModel.getPageEndNum();
//
//                    command.setPageUrl(pageUrl);
//                    command.setEnd(end);
//                    command.run();
//
//                    int pageEndNum = command.getEnd();
//                    if (pageEndNum > 0) {
//                        pageEndNum += PAGE_EXTRA;
//                    }
//                    prmModel.setPageEndNum(pageEndNum);
//                }
//            }
//
//            pagingRules = JSON.toJSONString(prmList);
//
//        }
//    }
//
//    public static class PageEndCheckCommand implements CheckCommand {
//        /**
//         * 校验基数
//         */
//        private static final int BASE = 10;
//        /**
//         * 最大位移
//         */
//        private static final int MAX_SHIFT = 1 << 3 + 1;
//        /**
//         * 页面url
//         */
//        String pageUrl;
//        /**
//         * 尾页数
//         */
//        int end;
//        /**
//         * 每次检查的阈值
//         */
//        int nextDetect = 1;
//        /**
//         * 探测计数
//         */
//        int detectCount;
//
//        public PageEndCheckCommand() {
//        }
//
//        public PageEndCheckCommand(String pageUrl) {
//            this.pageUrl = pageUrl;
//        }
//
//        private void init() {
//            this.nextDetect = 1;
//            this.detectCount = 0;
//        }
//
//        @Override
//        public void run() {
//            init();
//
//            boolean isKeep = true;
//            boolean detectOut  = false;
//
//            int base = (end == 0) ? BASE : end;
//
//            while (isKeep) {
//                if (!detectOut && tryDetect(nextDetect)) {
//                    resize(base, detectCount++);
//                    continue;
//                } else {
//                    detectOut = true;
//                }
//
//                if (detectCount == 0) {
//                    end = 0;
//                    break;
//                }
//
//                try {
//                    if (detectCount == 1) {
//                        acquireOne(base);
//                    } else {
//                        acquire(base, detectCount - 1);
//                    }
//                    isKeep = false;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        public void setPageUrl(String pageUrl) {
//            this.pageUrl = pageUrl;
//        }
//
//        public void setEnd(int end) {
//            this.end = end;
//        }
//
//        public int getEnd() {
//            return end;
//        }
//
//        @Override
//        public boolean tryDetect(int detect) {
//            if (StringUtils.isNoneEmpty(pageUrl)) {
//                String pageUrl = setPage(String.valueOf(detect));
//                NetHttpClient httpClient = new NetHttpClient();
//                try {
//                    String responseContent = httpClient.get(pageUrl);
//                    return proof(responseContent);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return false;
//        }
//
//        public boolean proof(String responseContent) {
//            HtmlMiningApi hm = new HtmlMiningApi();
//            Map<String, List<String>> pageExtract =
//                    hm.extract(responseContent, null);
//            Set<String> keySet = pageExtract.keySet();
//            Collection<List<String>> values = pageExtract.values();
//            if (keySet != null && keySet.size() > 0) {
//                if (values != null && values.size() > 0) {
//                    return true;
//                }
//            }
//            return false;
//        }
//
//        public String setPage(String page) {
//            return pageUrl.replaceAll("\\{page\\}", page);
//        }
//
//        public boolean resize(int base, int detectCount) {
//            if (detectCount < 0) {
//                throw new IllegalArgumentException(
//                        String.format("detectCount=%s invalid ...", detectCount));
//            }
//
//            if (detectCount > MAX_SHIFT) {
//                return false;
//            }
//
//            int resize = Math.min(1 << detectCount,
//                    1 << MAX_SHIFT);
//            nextDetect = base * resize;
//
//            return true;
//        }
//
//        public void acquireOne(int base) {
//            end = binaryDetect(1, base);
//        }
//
//        public void acquire(int base, int detectCount) {
//            if (detectCount <= 0) {
//                throw new IllegalArgumentException(
//                        String.format("detectCount=%s invalid ...", detectCount));
//            }
//            int low  = base * (1 << (detectCount - 1));
//            int high = base * (1 << detectCount);
//            end = binaryDetect(low, high);
//        }
//
//        public int binaryDetect(int low, int high) {
//            if (low < 0 || high < 0) {
//                return 0;
//            }
//
//            if (low + 1 == high) {
//                if (tryDetect(nextDetect + 1)) {
//                    nextDetect++;
//                }
//                return nextDetect;
//            }
//
//            int mid = (low + high) / 2;
//            if (tryDetect(mid)) {
//                nextDetect = mid;
//                return binaryDetect(mid + 1, high);
//            } else {
//                return binaryDetect(low, mid - 1);
//            }
//
//        }
//
//    }
//
//    /**
//     * 海投网
//     */
//    static class HaitouCheckCommand extends PageEndCheckCommand {
//
//        public HaitouCheckCommand() {
//        }
//
//        public HaitouCheckCommand(String pageUrl) {
//            super(pageUrl);
//        }
//
//        @Override
//        public String setPage(String page) {
//            if (Integer.valueOf(page) == 1) {
//                return pageUrl.replaceAll("\\{page\\}", "");
//            } else {
//                return super.setPage("page-" + page);
//            }
//        }
//    }
//
//    /**
//     * 应届生
//     */
//    static class YjsCheckCommand extends PageEndCheckCommand {
//
//        private String lastResponseText;
//
//        public YjsCheckCommand() {
//        }
//
//        public YjsCheckCommand(String pageUrl) {
//            super(pageUrl);
//        }
//
//        @Override
//        public boolean proof(String responseText) {
//
//            if (StringUtils.isNoneEmpty(responseText)
//                    && responseText.equals(lastResponseText)) {
//                return false;
//            }
//
//            boolean isProof = super.proof(responseText);
//
//            if (isProof) {
//                lastResponseText = responseText;
//            }
//
//            return isProof;
//        }
//    }
//
//}
