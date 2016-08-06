package appl.grabber.impl;

import appl.camera.Camera;
import appl.camera.exceptions.CameraException;
import appl.camera.impl.CameraImpl;
import appl.grabber.Grabber;
import appl.grabber.exceptions.GrabberException;
import appl.util.Checker;
import com.github.sarxos.webcam.Webcam;
import java.nio.file.Path;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by ca on 16/07/16.
 */
public class GrabberImpl implements Grabber {

    private final Camera camera;
    private final ScheduledExecutorService executorService;
    private ScheduledFuture cameraTask;

    private final BooleanProperty isReady = new SimpleBooleanProperty();
    private final BooleanProperty isRunning = new SimpleBooleanProperty(false);

    private final IntegerProperty repetitionRate = new SimpleIntegerProperty(1);

    private Path directory;

    public GrabberImpl() {
        camera = new CameraImpl();
        executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public Camera getCamera() {
        return camera;
    }

    @Override
    public void setCamera(Webcam camera) throws GrabberException {
        if (camera == null) {
            throw new IllegalArgumentException("The passed camera is null.");
        }
        this.camera.setWebcam(camera);
    }

    @Override
    public BooleanProperty isRunning() {
        return isRunning;
    }

    @Override
    public void start() throws GrabberException {
        isRunning.setValue(true);
        System.out.println("Grabber is started: " + new Date().toString());
        if (makeCameraReady() && checkConfiguration()) {
            System.out.println("Grabber starts working: " + new Date().toString());
            camera.shouldListenForWebcams(false);
            camera.saveTo(directory);
            cameraTask = executorService.scheduleWithFixedDelay(camera, 0, Integer.toUnsignedLong(repetitionRate.getValue()), TimeUnit.SECONDS);
        } else {
            // TODO show an prompt to the user if something is not set
            System.out.println("Grabber stopped working because camera or the configuration is not ready: " + new Date().toString());
            isRunning.setValue(false);
        }
    }

    @Override
    public void stop() throws GrabberException {
        if (!isRunning.getValue() || cameraTask == null) {
            throw new GrabberException("Grabber cannot get stopped because it isn't running.");
        }
        cameraTask.cancel(true);
        isRunning.setValue(false);
        System.out.println("Grabber stopps working.");
        camera.shouldListenForWebcams(true);
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                executorService.awaitTermination(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException ignore) {
            }
        }
    }

    private boolean makeCameraReady() throws GrabberException {
        try {
            return camera.makeItReady();
        } catch (CameraException e) {
            throw new GrabberException(e.getMessage());
        }
    }

    private boolean checkConfiguration() {
        return Checker.isCorrectDirectory.test(directory);
    }

    @Override
    public void setDirectoryToSave(Path directory) {
        if (Checker.isCorrectDirectory.negate().test(directory)) {
            throw new IllegalArgumentException("The passed directory is null or does not lead to an directory.");
        }
        this.directory = directory;
    }

    @Override
    public IntegerProperty getRepetitionRate() {
        return repetitionRate;
    }

}
