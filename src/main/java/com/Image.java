package com;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.highgui.HighGui;


public class Image {


    private double width;
    private double height;
    private double scale;
    private Mat image;


    public Mat getImage() {
        return image;
    }
    public double getScale() {
        return scale;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }


    public void getImage(String src) {
/*
        try {
            BufferedImage image = ImageIO.read(new File(src));


            width = image.getWidth();
            height = image.getHeight();
            scale = (float) image.getWidth() / (float)image.getHeight();


            int[][] rgb = new int[width][height];
            image.getRGB(0, 0, width, height, rgb , 0, width);
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
*/
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);


    }

    static {
        // 加载 OpenCV 本地库
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public Image(String src) {
        // 读取图像文件
        String imagePath = src;
        Mat image = Imgcodecs.imread(imagePath);
        this.image = image;
        Size si = image.size();
        this.height = si.height;
        this.width = si.width;
        this.scale = si.height/si.width;
    }
}
