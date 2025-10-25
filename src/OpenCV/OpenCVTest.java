/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OpenCV;

/**
 *
 * @author Jobelle
 */
import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;

public class OpenCVTest {
    public static void main(String[] args) {
        System.out.println("java.library.path = " + System.getProperty("java.library.path"));
        System.out.println("Core.NATIVE_LIBRARY_NAME = " + Core.NATIVE_LIBRARY_NAME);

        // Try to load via System.loadLibrary first (works if -Djava.library.path is set)
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            System.out.println("Loaded via System.loadLibrary: " + Core.NATIVE_LIBRARY_NAME);
        } catch (UnsatisfiedLinkError e) {
            System.err.println("System.loadLibrary failed: " + e.getMessage());
            // Fallback: load absolute path to DLL (use the exact path on your disk)
            // Use forward slashes to avoid backslash escaping issues
            System.load("C:/Users/Jobelle/Downloads/opencv/build/java/x64/opencv_java4120.dll");
            System.out.println("Loaded via System.load absolute path");
        }

        // quick test: try opening the default camera
        VideoCapture cap = new VideoCapture(0);
        System.out.println("VideoCapture opened? " + cap.isOpened());
        if (cap.isOpened()) cap.release();
    }
}