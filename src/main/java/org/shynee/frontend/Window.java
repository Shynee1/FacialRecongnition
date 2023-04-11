package org.shynee.frontend;

import org.shynee.detection.BodyDetection;
import org.shynee.detection.FacialDetection;
import org.shynee.utils.Utils;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.shynee.utils.Constants.SCREEN_HEIGHT;
import static org.shynee.utils.Constants.SCREEN_WIDTH;

public class Window extends JFrame implements Runnable{

    private boolean isRunning;
    private Image doubleBufferImage = null;
    private Graphics doubleBufferGraphics;
    private BufferedImage currentFrame;
    private Graphics windowGraphics;
    private VideoCapture capture;
    private final FacialDetection facialDetection;
    private final BodyDetection bodyDetection;

    public Window(){
        this.setTitle("Output");
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.facialDetection = new FacialDetection();
        this.bodyDetection = new BodyDetection();

        isRunning = true;

        startCamera();

        this.setVisible(true);
    }

    private void draw(BufferedImage currentFrame){
        if (doubleBufferImage == null) doubleBufferImage = createImage(SCREEN_WIDTH, SCREEN_HEIGHT);
        doubleBufferGraphics = doubleBufferImage.getGraphics();
        doubleBufferGraphics.drawImage(currentFrame, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
        windowGraphics.drawImage(doubleBufferImage, 0, 0, getWidth(), getHeight(), null);
    }

    private void update() {
        if (!isRunning) return;

        windowGraphics = getGraphics();

        Mat frame = new Mat();
        if (!capture.read(frame)) stopCamera();

        Mat currentMat;
        if (facialDetection.detectFace(frame)) currentMat = facialDetection.getCurrentFrame();
        else if (bodyDetection.detectBody(frame)) currentMat = bodyDetection.getCurrentFrame();
        else currentMat = frame;

        try {
            currentFrame = Utils.matToBufferedImage(currentMat);
        } catch (IOException e) {
            stopCamera();
            throw new RuntimeException(e);
        }

        draw(currentFrame);
    }

    private void startCamera(){
        if (!isRunning) return;
        capture = new VideoCapture(1);

        if (!capture.isOpened()) throw new RuntimeException("Could not open capture");

        capture.read(new Mat());
    }

    private void stopCamera(){
        if (!isRunning) return;
        isRunning = false;

        capture.release();

        if (capture.isOpened()) throw new RuntimeException("could not close capture");
    }

    @Override
    public void run() {
        while(isRunning){
            update();
        }
    }
}
