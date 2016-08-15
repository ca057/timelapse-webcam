package appl.timelapse.impl;

import appl.camera.Camera;
import appl.camera.exceptions.CameraException;
import appl.camera.impl.CameraImpl;
import appl.timelapse.Timelapse;
import appl.timelapse.exceptions.TimelapseException;
import com.github.sarxos.webcam.Webcam;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Created by ca on 16/07/16.
 */
public class TimelapseImpl implements Timelapse {

    private final Camera camera;
    private final ScheduledExecutorService executorService;
    private ScheduledFuture cameraTask;

    private final BooleanProperty isReady = new SimpleBooleanProperty();
    private final BooleanProperty isRunning = new SimpleBooleanProperty(false);

    public TimelapseImpl() {
        camera = new CameraImpl();
        executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public Camera getCameraModule() {
        return camera;
    }

    @Override
    public void setCamera(Webcam camera) throws TimelapseException {
        if (camera == null) {
            throw new IllegalArgumentException("The passed camera is null.");
        }
        this.camera.setWebcam(camera);
    }

    @Override
    public void start(long repetitionRate, Path targetDirectory) throws TimelapseException {
        if (repetitionRate <= 0 || targetDirectory == null || !Files.exists(targetDirectory) || !Files.isDirectory(targetDirectory)) {
            throw new IllegalArgumentException("One of the given arguments for starting the timelapse is not valid.");
        }
        if (makeCameraReady()) {
            isRunning.setValue(true);
            camera.shouldListenForWebcams(false);

            camera.saveTo(targetDirectory);
            cameraTask = executorService.scheduleWithFixedDelay(camera, 0, repetitionRate, TimeUnit.SECONDS);
        } else {
            System.out.println("Grabber stopped working because camera or the configuration is not ready: " + new Date().toString());
            isRunning.setValue(false);
        }
    }

    @Override
    public void stop() throws TimelapseException {
        if (!isRunning.getValue() || cameraTask == null) {
            throw new TimelapseException("Grabber cannot get stopped because it isn't running.");
        }
        cameraTask.cancel(true);
        isRunning.setValue(false);
        System.out.println("Grabber stops working.");
        camera.shouldListenForWebcams(true);
    }

    @Override
    public void shutdown() {
        if (isRunning.getValue()) {
            try {
                stop();
            } catch (TimelapseException ignore) {
            }
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                executorService.awaitTermination(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException ignore) {
            }
        }
    }

    private boolean makeCameraReady() throws TimelapseException {
        try {
            return camera.makeItReady();
        } catch (CameraException e) {
            throw new TimelapseException(e.getMessage());
        }
    }

    @Override
    public BooleanProperty isRunning() {
        return isRunning;
    }

}
