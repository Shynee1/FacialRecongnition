package org.example.utils;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static BufferedImage matToBufferedImage(Mat mat) throws IOException {

        MatOfByte mb = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, mb);

        byte[] imageBytes = mb.toArray();

        InputStream in = new ByteArrayInputStream(imageBytes);
        return ImageIO.read(in);
    }
}
