/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OpenCV;

/**
 *
 * @author Jobelle
 */
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public class IDRecognizer {

    private static String lastRecognizedID = "";

    // Main function to detect ID card & recognize ID number
    public static Mat detectIDCard(Mat frame) {
        Mat gray = new Mat();
        Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(gray, gray, new Size(5,5), 0);
        Mat edges = new Mat();
        Imgproc.Canny(gray, edges, 75, 200);

        // TODO: Find largest rectangle contour → crop ID area
        // For now, return original frame
        // Later: perform template matching or KNN for character recognition

        lastRecognizedID = "AB1234-XYZ"; // placeholder, replace with real recognition
        return frame;
    }

    public static String getLastRecognizedID() {
        return lastRecognizedID;
    }
}

