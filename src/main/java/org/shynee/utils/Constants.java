package org.shynee.utils;

import org.opencv.core.Scalar;
import org.opencv.core.Size;

public class Constants {

    //Detection
    public static final Size WINDOW_SCALE = new Size(8, 8);
    public static final Size PADDING = new Size(4, 4);
    public static final float SCALE = 1.05f;
    public static final double DETECTION_THRESHOLD = 0.35;
    public static final Scalar COLOR = new Scalar(0, 0, 255);
    public static final String CASCADE_FILEPATH = "C:\\Users\\jackb\\Downloads\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_default.xml";

    //Window
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
}
