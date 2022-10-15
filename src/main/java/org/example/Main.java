package org.example;

import org.example.frontend.Window;
import org.opencv.core.Core;

public class Main {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Window window = new Window();
        Thread test = new Thread(window);
        test.start();
    }
}