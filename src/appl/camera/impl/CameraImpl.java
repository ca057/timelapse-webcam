package appl.camera.impl;

import appl.camera.Camera;
import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by ca on 16/07/16.
 */
public class CameraImpl implements Camera {

    private Webcam webcam;

    // private ImageWriter writer;

    public CameraImpl() {
        webcam = Webcam.getDefault();
        // writer = ImageIO.getImageWritersByFormatName("jpg").next();
    }

    @Override
    public void run() {
        if (!webcam.isOpen()) {
            System.err.println("Webcam is not open!");
        }
        String fileName = new Date().toString().replace(" ", "_").concat(".jpg");
        System.out.println("Camera takes an image: " + fileName);

        try {
            ImageIO.write(webcam.getImage(), "JPG", new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Webcam> getAvailableWebcams() {
        return Webcam.getWebcams();
    }

    @Override
    public void setWebcam(Webcam webcam) {
        if (webcam == null) {
            throw new IllegalArgumentException("Passed webcam is null.");
        }
        if (webcam.isOpen()) {
            webcam.close();
        }
        this.webcam = webcam;
    }

    @Override
    public boolean makeItReady() {
        if (webcam == null) {
            // TODO
        }
        return webcam.open();
    }
}
