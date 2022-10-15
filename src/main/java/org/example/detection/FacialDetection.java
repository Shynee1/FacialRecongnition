package org.example.detection;

import org.example.utils.Constants;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FacialDetection {

    private CascadeClassifier cc;
    private Mat currentFrame;

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
        cc.detectMultiScale(frame, faces, Constants.SCALE,10, 0 , new Size(1, 1), new Size(75, 75));

        if (faces.toArray().length == 0) return false;

        drawDetection(image, faces.toArray(), Constants.COLOR);
        currentFrame = image;

        return true;
    }

    public void drawDetection(Mat image, Rect[] rectangles, Scalar color){
        for (Rect rectangle : rectangles) {
            Imgproc.rectangle(image, new Point(rectangle.x, rectangle.y), new Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height), color, 2);
        }
    }

    public Mat getCurrentFrame(){
        return currentFrame;
    }

}
