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

        /**
         * 表示x坐标位置
         */
        private int xAxis;

        /**
         * 表示x坐标上的偏移量
         */
        private int xOffset;

        public Coordinator(int x) {
            this.xAxis = x;
        }

        public void shiftX() {
            xOffset += 1;
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
        Coordinator coor = null;
        List<Coordinator> blocks = new ArrayList<>();
        int pos = 0, offset = 0;
        /**
         * 列
         */
        for (int col = 0; col < width; col++) {
            /**
             * 行
             */
            for (int row = 0; row < height; row++) {
                int spot = binaryMatrix[row][col];
                /**
                 * 遇阈值时，标记区域
                 */
                if (spot == 1) {
                    pos = col;
                    offset += 1;
                    break;
                }
            }
            if (pos == col) {
                if (offset == 1) {
                    coor = new Coordinator(pos);
                    blocks.add(coor);
                } else if (offset > 1) {
                    coor.shiftX();
                }
            } else {
                offset = 0;
            }
        }

        for (int i = 0; i < blocks.size(); i++) {
            Coordinator block = blocks.get(i);
            int x = block.xAxis;
            int y = 0;
            int w = block.xOffset + 1;
            int h = height;
            cut(bi, i + ".png", "png", x, y, w, h);
        }
    }

    /**
     * 图片切割
     *
     * @param bi     - 原图片：BufferedImage
     * @param dest   - 输出图片文件
     * @param format - 输出图片格式
     * @param x      - 指定矩形区域左上角的 X 坐标
     * @param y      - 指定矩形区域左上角的 Y 坐标
     * @param w      - 指定矩形区域的宽度
     * @param h      - 指定矩形区域的高度
     */
    public void cut(BufferedImage bi, String dest, String format,
                    int x, int y, int w, int h) {
        BufferedImage image = bi.getSubimage(x, y, w, h);
        try {
            ImageIO.write(image, format, new File(dest));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cut2() {

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
