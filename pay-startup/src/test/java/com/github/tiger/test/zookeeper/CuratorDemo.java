package com.github.tiger.test.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public class CuratorDemo {

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        String connectString = "192.168.66.11:2181";
        CuratorFramework curator = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(3000)
                .retryPolicy(retryPolicy)
                .build();
        curator.start();

        List<String> childNodes = curator.getChildren().forPath("/dubbo");
        for (String childNode : childNodes) {
            System.out.println("childNode=" + childNode);
        }

        Stat stat = curator.checkExists().forPath("/jason");
        if (stat == null) {
            curator.create().withMode(CreateMode.PERSISTENT).forPath("/jason");
        }
        curator.setData().forPath("/jason", "666888".getBytes());
    }
}
