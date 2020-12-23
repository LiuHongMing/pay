package com.github.tiger.test.netty;

import io.netty.util.concurrent.*;
import lombok.Data;

/**
 * @author liuhongming
 * @className: PromiseTest
 * @description: TODO
 * @date 2020/12/10
 */
public class PromiseTest {

    public static void main(String[] args) throws Exception {
        String url = "http://xxx.yyy.zzz";
        EventExecutor executor = GlobalEventExecutor.INSTANCE;
        Promise<DownloadResult> promise = new DefaultPromise<>(executor);
        promise.addListener(new DownloadResultListener());
        Thread thread = new Thread(() -> {
            try {
                System.out.println("开始下载资源,url:" + url);
                long start = System.currentTimeMillis();
                // 模拟下载耗时
                Thread.sleep(2000);
                String location = "/xxx/yyy/z.md";
                long cost = System.currentTimeMillis() - start;
                System.out.println(String.format("下载资源成功,url:%s,保存到:%s,耗时:%d ms", url, location, cost));
                DownloadResult result = new DownloadResult();
                result.setUrl(url);
                result.setFileDiskLocation(location);
                result.setCost(cost);
                // 通知结果
                promise.setSuccess(result);
            } catch (Exception ignore) {

            }
        }, "Download-Thread");
        thread.start();
        Thread.sleep(Long.MAX_VALUE);
    }

    @Data
    private static class DownloadResult {

        private String url;

        private String fileDiskLocation;

        private long cost;
    }

    private static class DownloadResultListener implements GenericFutureListener<Future<DownloadResult>> {

        @Override
        public void operationComplete(Future<DownloadResult> future) throws Exception {
            if (future.isSuccess()) {
                DownloadResult downloadResult = future.getNow();
                System.out.println(String.format("下载完成通知,url:%s,文件磁盘路径:%s,耗时:%d ms", downloadResult.getUrl(),
                    downloadResult.getFileDiskLocation(), downloadResult.getCost()));
            }
        }
    }

}
