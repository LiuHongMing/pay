package com.github.tiger.test;

import com.github.tiger.common.context.CustomClassLoader;
import com.github.tiger.common.context.PatchContext;
import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;

import java.net.URL;
import java.util.Scanner;

/**
 * 监听目录，通过自定义类加载器加载jar包
 */
public class HotspotTest {

    private CustomClassLoader customClassLoader;

    public CustomClassLoader getCustomClassLoader() {
        return customClassLoader;
    }

    public void setCustomClassLoader(CustomClassLoader customClassLoader) {
        this.customClassLoader = customClassLoader;
    }

    public void doListen(String dir) throws FileSystemException {
        FileObject listenDir = VFS.getManager().resolveFile(dir);

        FileListener listener = new FileListener() {
            @Override
            public void fileCreated(FileChangeEvent event) throws Exception {
                String extension = event.getFile().getName().getExtension();
                if ("jar".equals(extension)) {
                    URL url = event.getFile().getURL();
                    customClassLoader = new CustomClassLoader(new URL[]{url});
                }
            }

            @Override
            public void fileDeleted(FileChangeEvent event) throws Exception {

            }

            @Override
            public void fileChanged(FileChangeEvent event) throws Exception {

            }
        };

        DefaultFileMonitor fileMonitor = new DefaultFileMonitor(listener);
        fileMonitor.setRecursive(true);
        fileMonitor.addFile(listenDir);
        fileMonitor.start();
    }

    public static void main(String[] args) throws FileSystemException {
        String dir = "f://patch";

        HotspotTest vfs = new HotspotTest();
        vfs.doListen(dir);

        PatchContext patchContext = null;

        ClassLoader classLoader;

        Scanner scanner = new Scanner(System.in);
        for (; ; ) {
            String message = scanner.nextLine();
            if ("exit".equals(message)) {
                System.exit(0);
            }

            String className = message;
            Class targetClass = null;
            try {
                // 通过Class.forName获取Class对象
                targetClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            classLoader = vfs.getCustomClassLoader();
            if (classLoader != null) {
                try {
                    // 通过自定义类加载器加载类
                    targetClass = classLoader.loadClass(className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    // 通过Class.forName获取Class对象
                    targetClass = Class.forName(className, true, classLoader);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            // 判断PatchService是否是该Class的父接口
            if (targetClass != null && PatchContext.class.isAssignableFrom(targetClass)) {
                try {
                    patchContext = (PatchContext) targetClass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (patchContext != null) {
                System.out.println(patchContext.getResource());
            }
        }
    }
}
