package com;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.*;

/*
public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        Image imgout = new Image("F:/desktop/out.jpg");
        Image imgins = new Image("F:/desktop/ins.jpg");

        int out = 12;
        int ins = 7;


        double imgwidth = imgins.getWidth();
        double imgheight = imgins.getHeight();

        Mat rgbaImage = new Mat((int) imgwidth, (int) imgheight, CvType.CV_8UC4, new Scalar(0, 0, 0, 255));

        for (int col = 0; col < rgbaImage.rows(); col++) {
            for (int row = 0; row < rgbaImage.cols(); row++) {
                Mat rgbImageout = new Mat((int) imgwidth, (int) imgheight, CvType.CV_8UC4);
                Imgproc.cvtColor(imgout.getImage(), rgbImageout, Imgproc.COLOR_BGR2RGB);
                Mat rgbImageins = new Mat((int) imgwidth, (int) imgheight, CvType.CV_8UC4);
                Imgproc.cvtColor(imgins.getImage(), rgbImageins, Imgproc.COLOR_BGR2RGB);

                double[] rgbValuesout = rgbImageout.get(row, col);
                double[] rgbValuesins = rgbImageins.get(row, col);

                //System.out.println(rgbValuesout[0] + "," + rgbValuesins[0]);
                //System.out.println(rgbValuesout[1] + "," + rgbValuesins[1]);
                //System.out.println(rgbValuesout[2] + "," + rgbValuesins[2]);
                double a = out;
                double b = ins;
                double R_f =  rgbValuesout[0];
                double G_f =  rgbValuesout[1];
                double B_f =  rgbValuesout[2];
                double R_b =  rgbValuesins[0];
                double G_b =  rgbValuesins[1];
                double B_b =  rgbValuesins[2];
                R_f *= a/10;
                R_b *= b/10;
                G_f *= a/10;
                G_b *= b/10;
                B_f *= a/10;
                B_b *= b/10;

                double delta_r = R_b - R_f;
                double delta_g = G_b - G_f;
                double delta_b = B_b - B_f;
                double coe_a = 8+255/256+(delta_r - delta_b)/256;
                double coe_b = 4*delta_r + 8*delta_g + 6*delta_b + ((delta_r - delta_b)*(R_b+R_f))/256 + (Math.pow(delta_r,delta_r) - Math.pow(delta_b,delta_b))/512;
                double A_new = 255 + coe_b/(2*coe_a);
                int A_newnew = (int) A_new;

                int R_new;
                int G_new;
                int B_new;

                if (A_newnew<=0)
                {
                    A_newnew = 0;
                    R_new = 0;
                    G_new = 0;
                    B_new = 0;
                }
                else if (A_newnew>=255)
                {
                    A_newnew = 255;
                    R_new =(int)((255 * (R_b) * b / 10) / A_newnew);
                    G_new =(int)((255 * (G_b) * b / 10) / A_newnew);
                    B_new =(int)((255 * (B_b) * b / 10) / A_newnew);
                }
                else
                {
                    R_new =(int)((255 * (R_b) * b / 10) / A_newnew);
                    G_new =(int)((255 * (G_b) * b / 10) / A_newnew);
                    B_new =(int)((255 * (B_b) * b / 10) / A_newnew);

                }
                int []pixel_new = new int[4];
                pixel_new[0] = R_new;
                pixel_new[1] = G_new;
                pixel_new[2] = B_new;
                pixel_new[3] = A_newnew;

                rgbaImage.put(row, col, new byte[]{(byte)R_new, (byte) G_new, (byte) B_new, (byte)A_newnew}); // RGBA = (255, 0, 0, 128);

            }
        }

        double a = out;
        double b = ins;


        for (int i = 0; i < imgwidth; i++) {
            for (int j = 0; j < imgheight; j++) {
                // 获取前景图像的像素
                Color color_f = new Color(imgout.getRGB(i, j), true);
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

                // 计算 delta 和系数
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
                Color pixel_new = new Color(R_new, G_new, B_new, A_new);
                rgbaImage.setRGB(i, j, pixel_new.getRGB());
            }
        }

        // 保存处理后的图像
    
        HighGui.imshow("test",rgbaImage);
        String outputPath = "F:/Desktop/rgba_image.png";
        Imgcodecs.imwrite(outputPath, rgbaImage);

        System.out.println("RGBA 图像已保存到: " + outputPath);
    }
}*/

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


