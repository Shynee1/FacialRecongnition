package org.shynee.detection;

import org.shynee.utils.Constants;
import org.shynee.utils.Utils;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FacialDetection {

    private final CascadeClassifier cc;
    private Mat currentFrame;
    private Rect previousRect;

    public FacialDetection(){
        cc = new CascadeClassifier(Constants.CASCADE_FILEPATH);
        currentFrame = new Mat();
    }

    public boolean detectFace(Mat image){

        if (image == null) return false;

        //Create a dummy mat to edit color without changing original frame
        Mat frame = new Mat();
        image.copyTo(frame);

        //Convert frame to grayscale
        Imgproc.cvtColor(image, frame, Imgproc.COLOR_BGR2GRAY);

        //Perform detection
        MatOfRect faces = new MatOfRect();
        cc.detectMultiScale(frame, faces, Constants.SCALE,6, 0 , new Size(25, 25), new Size(150, 150));

        if (faces.toArray().length == 0) return false;

        if (previousRect == null) previousRect = faces.toArray()[0];

        Rect currentRect = Utils.calculateLeastDistance(faces.toArray(), previousRect);

        drawDetection(image, currentRect, Constants.COLOR);
        currentFrame = image;

        return true;
    }

    public void drawDetection(Mat image, Rect rectangle, Scalar color){
        Imgproc.rectangle(image, new Point(rectangle.x, rectangle.y), new Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height), color, 2);
    }

    public Mat getCurrentFrame(){
        return currentFrame;
    }

}
