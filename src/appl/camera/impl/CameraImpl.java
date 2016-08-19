package appl.camera.impl;

import appl.camera.Camera;
import appl.camera.exceptions.CameraException;
import appl.util.Checker;
import com.github.sarxos.webcam.Webcam;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.imageio.ImageIO;

/**
 * Created by ca on 16/07/16.
 */
public class CameraImpl implements Camera {

    private Webcam webcam;
    private final ObservableList<Webcam> cameras;
    private Path directory;

    private boolean turnOffAfterCapture = true;

    public CameraImpl() {
        this.cameras = FXCollections.observableArrayList(Webcam.getWebcams());
        this.webcam = Webcam.getDefault();
    }

    @Override
    public void run() {
        if (Checker.isCorrectDirectory.negate().test(directory)) {
            // TODO throw error
            System.err.println("Directory is not set.");
            return;
        }
        if (!webcam.isOpen()) {
            webcam.open();
        }
        String fileName = new Date().toString().replace(" ", "_").replace(":", "-").concat(".jpg");
        System.out.println("Camera takes an image: " + fileName);

        try {
            File file = new File(directory.toFile(), fileName);
            ImageIO.write(webcam.getImage(), "jpg", file);
            Files.setLastModifiedTime(Paths.get(file.getPath()), FileTime.fromMillis(System.currentTimeMillis()));
        } catch (IOException ignore) {
            System.err.println("IOException occured while writing the file: " + fileName + "; " + ignore.getMessage());
        } finally {
            if (turnOffAfterCapture) {
                webcam.close();
            }
        }
    }

    @Override
    public ObservableList<Webcam> getAvailableWebcams() {
        return cameras;
    }

    @Override
    public Webcam getCurrentWebcam() {
        return webcam;
    }

    @Override
    public void setWebcam(Webcam webcam) {
        if (webcam == null) {
            throw new IllegalArgumentException("Passed webcam is null.");
        }
        // TODO check if it is needed to close the current camera first
        if (this.webcam.isOpen()) {
            this.webcam.close();
        }
        this.webcam = webcam;
    }

    @Override
    public boolean turnCameraOn() throws CameraException {
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

    @Override
    public boolean turnCameraOff() {
        return this.webcam.close();
    }

    @Override
    public void turnCameraOffAfterCapture(boolean turnOff) {
        turnOffAfterCapture = turnOff;
    }

    public boolean getTurnCameraOffAfterCapture() {
        return turnOffAfterCapture;
    }
}
