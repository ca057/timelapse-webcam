package appl.camera.impl;

import appl.camera.Camera;
import appl.camera.exceptions.CameraException;
import appl.util.Checker;
import com.github.sarxos.webcam.Webcam;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Created by ca on 16/07/16.
 */
public class CameraImpl implements Camera {

    private Webcam webcam;

    private Path directory;

    // private ImageWriter writer;
    public CameraImpl() {
        webcam = Webcam.getDefault();
        // writer = ImageIO.getImageWritersByFormatName("jpg").next();
    }

    @Override
    public void run() {
        if (!webcam.isOpen() && Checker.isCorrectDirectory.negate().test(directory)) {
            System.err.println("Webcam is not open or directory is not set.");
            // TODO improve exception handling
            return;
        }
        String fileName = new Date().toString().replace(" ", "_").concat(".jpg");
        System.out.println("Camera takes an image: " + fileName);

        try {
            File file = new File(directory.toFile(), fileName);
            ImageIO.write(webcam.getImage(), "JPG", file);
        } catch (IOException ignore) {
            // TODO ignore it but log it in debug view
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

    @Override
    public void saveTo(Path path) {
        if (Checker.isCorrectDirectory.negate().test(path)) {
            throw new IllegalArgumentException("The passed path is either null or does not lead to an directory.");
        }
        this.directory = path;
    }
}
