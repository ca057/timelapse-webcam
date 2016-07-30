package appl.camera.impl;

import appl.camera.Camera;
import appl.camera.exceptions.CameraException;
import com.github.sarxos.webcam.Webcam;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;

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
            // TODO
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
        // TODO check if it is needed to close the camera first
        if (this.webcam.isOpen()) {
            this.webcam.close();
        }
        this.webcam = webcam;
    }

    @Override
    public boolean makeItReady() throws CameraException {
        if (webcam == null) {
            throw new CameraException("The camera which should get opened is not set.");
        }
        return webcam.isOpen() || webcam.open();
    }

    @Override
    public void shouldListenForWebcams(boolean shouldListen) {
        if (shouldListen) {
            if (!Webcam.getDiscoveryService().isRunning()) {
                Webcam.getDiscoveryService().start();
            }
        } else if (Webcam.getDiscoveryService().isRunning()) {
            Webcam.getDiscoveryService().stop();
        }
    }
}
