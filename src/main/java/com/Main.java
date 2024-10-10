package com;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Main {
    public static void main(String[] args) {
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);

        // 读取前景图像 (RGBA 格式)
        BufferedImage image_f = readImage("F:/Desktop/out.png");
        // 读取背景图像 (RGBA 格式)
        BufferedImage image_b = readImage("F:/Desktop/ins.png");

        int w_min = Math.min(image_f.getWidth(), image_b.getWidth());
        int h_min = Math.min(image_f.getHeight(), image_b.getHeight());

        double a = 12.0; // 亮度修正参数
        double b = 7.0;

        // 创建存储处理结果
        Mat new_image = new Mat(w_min,h_min, CvType.CV_32SC4, new Scalar(0, 0, 0, 255));

        for (int j = 0; j < h_min; j++) {
            for (int i = 0; i < w_min; i++) {

                // 获取前景图像的像素
                Color color_f = new Color(image_f.getRGB(i, j), true);
                int R_f = color_f.getRed();
                int G_f = color_f.getGreen();
                int B_f = color_f.getBlue();

                // 获取背景图像的像素
                Color color_b = new Color(image_b.getRGB(i, j), true);
                int R_b = color_b.getRed();
                int G_b = color_b.getGreen();
                int B_b = color_b.getBlue();

                // 对亮度信息进行修正
                R_f = (int) (R_f * a / 10);
                R_b = (int) (R_b * b / 10);
                G_f = (int) (G_f * a / 10);
                G_b = (int) (G_b * b / 10);
                B_f = (int) (B_f * a / 10);
                B_b = (int) (B_b * b / 10);

                // 计算 delta 和系数,系数变量及结果通过LAB颜色空间求颜色近似度得到,详情见详情见https://blog.csdn.net/qq_16564093/article/details/80698479
                int delta_r = R_b - R_f;
                int delta_g = G_b - G_f;
                int delta_b = B_b - B_f;

                double coe_a = 8 + 255 / 256.0 + (delta_r - delta_b) / 256.0;
                double coe_b = 4 * delta_r + 8 * delta_g + 6 * delta_b + ((delta_r - delta_b) * (R_b + R_f)) / 256.0 + (Math.pow(delta_r, 2) - Math.pow(delta_b, 2)) / 512.0;

                int A_new = (int) (255 + coe_b / (2 * coe_a));

                // 修正 A_new
                int R_new, G_new, B_new;
                if (A_new <= 0) {
                    A_new = 0;
                    R_new = 0;
                    G_new = 0;
                    B_new = 0;
                } else if (A_new >= 255) {
                    A_new = 255;
                    R_new = (int) ((255.0 * R_b * b / 10) / A_new);
                    G_new = (int) ((255.0 * G_b * b / 10) / A_new);
                    B_new = (int) ((255.0 * B_b * b / 10) / A_new);
                } else {
                    R_new = (int) ((255.0 * R_b * b / 10) / A_new);
                    G_new = (int) ((255.0 * G_b * b / 10) / A_new);
                    B_new = (int) ((255.0 * B_b * b / 10) / A_new);
                }

                // 设置新的像素
                new_image.put(i,j,new int[]{B_new,G_new,R_new,A_new});
            }
        }

        // 保存处理后的图像
        Imgcodecs.imwrite( "output_image.png",new_image);
    }

    //
    public static BufferedImage readImage(String filePath) {
        try {
            return javax.imageio.ImageIO.read(new java.io.File(filePath));
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}


