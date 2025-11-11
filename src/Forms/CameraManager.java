package Forms;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;

/**
 * CameraManager — improved OpenCV load and life cycle handling
 *
 * Behavior changes:
 * - QR detection no longer performs DB or UI work on the EDT.
 * - When a QR is detected we spawn a background worker thread that:
 *     1) obtains the current schedule info from MainPageInst (off-EDT),
 *     2) calls MainPageInst.processAttendanceSync(qrText) (off-EDT),
 *     3) posts UI updates (log label / result dialog) on the EDT.
 */
public class CameraManager extends Thread {
    private VideoCapture camera;
    private JPanel cameraPanel;
    private JLabel cameraLabel;
    private JLabel logLabel;
    private MainPageInst parentFrame;
    private volatile boolean running = false;

    private Set<String> scannedQRCodes = new HashSet<>();

    static {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            System.out.println("OpenCV loaded via System.loadLibrary: " + Core.NATIVE_LIBRARY_NAME);
        } catch (UnsatisfiedLinkError e) {
            System.err.println("System.loadLibrary failed: " + e.getMessage());
            try {
                String path = DBConfig.getOpenCvDllPath();
                if (path != null && !path.isEmpty()) {
                    System.load(path);
                    System.out.println("OpenCV loaded via absolute path: " + path);
                }
            } catch (Throwable ex) {
                System.err.println("Failed to load OpenCV native library from DBConfig path: " + ex.getMessage());
            }
        }
    }

    /**
     * Constructor explicitly accepts MainPageInst (no casting needed).
     *
     * @param cameraPanel panel where frames will be shown
     * @param logLabel label to display scan messages
     * @param parent the MainPageInst that owns the camera UI
     */
    public CameraManager(JPanel cameraPanel, JLabel logLabel, MainPageInst parent) {
        this.cameraPanel = cameraPanel;
        this.logLabel = logLabel;
        this.parentFrame = parent;

        cameraLabel = new JLabel();
        cameraPanel.setLayout(new BorderLayout());
        cameraPanel.add(cameraLabel, BorderLayout.CENTER);

        camera = new VideoCapture();
        setName("CameraManager");
    }

    @Override
    public void run() {
        running = true;
        Mat frame = new Mat();

        if (!camera.isOpened()) {
            camera.open(0);
        }

        while (running) {
            if (camera.isOpened() && camera.read(frame)) {
                BufferedImage image = matToBufferedImage(frame);

                // Detect QR code
                try {
                    LuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Result result = new com.google.zxing.qrcode.QRCodeReader().decode(bitmap);

                    if (result != null) {
                        String qrText = result.getText().trim();

                        if (!scannedQRCodes.contains(qrText)) {
                            scannedQRCodes.add(qrText);

                            // Do DB work off the EDT to avoid UI freeze
                            new Thread(() -> {
                                // 1) Get active class info (off-EDT)
                                String activeClass = parentFrame.getCurrentScheduleInfo();

                                // 2) Process attendance synchronously (DB work off-EDT).
                                MainPageInst.AttendanceResult res = parentFrame.processAttendanceSync(qrText);

                                // 3) Update UI on EDT: set log label and show a dialog if needed.
                                SwingUtilities.invokeLater(() -> {
                                    logLabel.setText("Scanned: " + qrText + " | Class: " + activeClass);
                                    if (res != null && res.message != null && !res.message.isEmpty()) {
                                        JOptionPane.showMessageDialog(parentFrame, res.message, "Scan Result", res.messageType);
                                    }
                                });
                            }, "Camera-QR-Worker").start();
                        }
                    }
                } catch (NotFoundException | ChecksumException | FormatException ignored) {}

                ImageIcon icon = new ImageIcon(image);

                // Only UI update for preview icon on EDT
                SwingUtilities.invokeLater(() -> {
                    cameraLabel.setIcon(icon);
                    parentFrame.hideCameraLabel();
                });
            }

            try { Thread.sleep(33); } catch (InterruptedException ignored) {}
        }

        // Ensure camera released when thread ends
        if (camera != null && camera.isOpened()) {
            camera.release();
        }
    }

    public void startCamera() {
        if (!running) {
            if (!camera.isOpened()) camera.open(0);
            this.start();
        }
    }

    public void stopCamera() {
        running = false;
        scannedQRCodes.clear();
        // thread will exit loop and release camera in run()
    }

    public void releaseCamera() {
        if (camera != null && camera.isOpened()) {
            camera.release();
        }
    }

    private BufferedImage matToBufferedImage(Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            Mat mat2 = new Mat();
            Imgproc.cvtColor(mat, mat2, Imgproc.COLOR_BGR2RGB);
            mat = mat2;
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        byte[] b = new byte[mat.channels() * mat.cols() * mat.rows()];
        mat.get(0, 0, b);
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), b);
        return image;
    }
}