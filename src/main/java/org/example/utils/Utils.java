package org.example.utils;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Rect;
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

    public static Rect calculateLeastDistance(Rect[] rects, Rect prev){
        double shortestDistance = 0.0;
        Rect shortestDistanceRect = rects[0];

        for (Rect r : rects){
            double distance = Math.sqrt(Math.pow(prev.x - r.x, 2) + Math.pow(prev.y-r.y, 2));

            if (shortestDistance == 0 || distance < shortestDistance) {
                shortestDistance = distance;
                shortestDistanceRect = r;
            }
        }

        return shortestDistanceRect;
    }
}
