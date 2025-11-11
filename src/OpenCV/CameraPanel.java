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
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CameraPanel extends JPanel {
    private VideoCapture capture;
    private Mat frame;
    private BufferedImage image;
    private boolean running = false;

    public CameraPanel() {
        this.setPreferredSize(new Dimension(640, 420));
    }

    public void startCamera() {
        capture = new VideoCapture(0); // default webcam
        frame = new Mat();
        running = true;

        new Thread(() -> {
            while (running) {
                if (capture.read(frame)) {
                    // Detect & recognize ID
                    Mat processed = IDRecognizer.detectIDCard(frame);

                    image = matToBufferedImage(processed);
                    repaint();

                    try { Thread.sleep(30); } catch (Exception e) {}
                }
            }
            capture.release();
        }).start();
    }

    public void stopCamera() {
        running = false;
    }

    private BufferedImage matToBufferedImage(Mat mat) {
        Mat tmp = new Mat();
        Imgproc.cvtColor(mat, tmp, Imgproc.COLOR_BGR2RGB);
        byte[] data = new byte[tmp.rows() * tmp.cols() * (int)tmp.elemSize()];
        tmp.get(0, 0, data);
        BufferedImage img = new BufferedImage(tmp.cols(), tmp.rows(), BufferedImage.TYPE_3BYTE_BGR);
        img.getRaster().setDataElements(0, 0, tmp.cols(), tmp.rows(), data);
        return img;
    }
}
