package org.shynee.detection;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;

import static org.shynee.utils.Constants.*;

public class BodyDetection {

    private HOGDescriptor hog;
    private Mat currentFrame;
    private Rect previousRect = null;

    public BodyDetection(){
        hog = new HOGDescriptor();
        hog.setSVMDetector(HOGDescriptor.getDefaultPeopleDetector());
        currentFrame = new Mat();
    }

    public boolean detectBody(Mat image){
        if (image == null) return false;

        Mat frame = new Mat();
        image.copyTo(frame);

        MatOfRect rectMat = new MatOfRect();
        MatOfDouble weights = new MatOfDouble();

        //Sets frame color to gray and scales frame down by 0.5
        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
        Imgproc.resize(frame, frame, frame.size(), 0.5, 0.5);

        //Performs body detection and stores values in rectMat and weights
        hog.detectMultiScale(frame, rectMat, weights, 0, WINDOW_SCALE, PADDING, SCALE);

        if (weights.empty() || rectMat.empty()) return false;

        if (previousRect == null) previousRect = rectMat.toArray()[0];

        Rect currentRect = calculateLeastDistance(rectMat.toArray(), previousRect);

        if (weights.toArray()[rectMat.toList().indexOf(currentRect)] > DETECTION_THRESHOLD){
            drawDetections(image, currentRect, COLOR);
            previousRect = currentRect;
        }
        currentFrame = image;

        return true;
    }

    public void drawDetections(Mat image, Rect rectangle, Scalar color){
        Imgproc.rectangle(image, new Point(rectangle.x, rectangle.y), new Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height), color, 2);
    }

    public Mat getCurrentFrame(){
        return currentFrame;
    }

    private Rect calculateLeastDistance(Rect[] rects, Rect prev){
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
