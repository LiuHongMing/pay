package com.github.tiger.test.imageio;

import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 图像分割
 */
public class ImageIoTest {

    static String imgPath = "temp/xjhimg.png";

    static class Coordinator {

        private int pos;

        private int offset;

        public Coordinator(int pos) {
            this.pos = pos;
        }

        public void shift() {
            offset += 1;
        }
    }

    /**
     * 阈值化分割
     */
    @Test()
    public void testCutByThrehold() throws IOException {
        InputStream is = openStream(imgPath);
        BufferedImage bi = ImageIO.read(is);
        int threhold = -1;
        /**
         * 二值化矩阵
         */
        int[][] binaryMatrix = binarization(bi, threhold);
        int width = bi.getWidth();
        int height = bi.getHeight();
        /**
         * 划分区域
         */
        int pos = 0, offset = 0;
        Coordinator coordinator = null;
        List<Coordinator> dots = new ArrayList<>();
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                int dot = binaryMatrix[row][col];
                if (dot == 1) {
                    pos = col;
                    offset += 1;
                    break;
                }
            }
            if (pos == col) {
                if (offset == 1) {
                    coordinator = new Coordinator(pos);
                    dots.add(coordinator);
                } else if (offset > 1) {
                    coordinator.shift();
                }
            } else {
                offset = 0;
            }
        }

        for (int i = 0; i < dots.size(); i++) {
            Coordinator cdr = dots.get(i);
            int x = cdr.pos;
            int y = 0;
            int w = cdr.offset + 1;
            int h = height;
            cut(bi, i + ".png", x, y, w, h);
        }
    }

    /**
     * 图片切割
     *
     * @param bi - 原图片：BufferedImage
     * @param dest - 输出图片文件
     * @param x - 指定矩形区域左上角的 X 坐标
     * @param y - 指定矩形区域左上角的 Y 坐标
     * @param w - 指定矩形区域的宽度
     * @param h - 指定矩形区域的高度
     */
    public void cut(BufferedImage bi, String dest,
                    int x, int y, int w, int h) {
        BufferedImage image = bi.getSubimage(x, y, w, h);
        try {
            ImageIO.write(image, "PNG", new File(dest));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 二值化矩阵
     */
    public int[][] binarization(BufferedImage bufferedImage, int threhold) {
        int width = bufferedImage.getWidth(), height = bufferedImage.getHeight();
        int[][] matrix = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = bufferedImage.getRGB(j, i);
                if (rgb == threhold) {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] = 1;
                }
            }
        }
        return matrix;
    }

    public InputStream openStream(String resource) {
        InputStream inStream = ClassLoader.getSystemResourceAsStream(resource);
        return inStream;
    }

}
