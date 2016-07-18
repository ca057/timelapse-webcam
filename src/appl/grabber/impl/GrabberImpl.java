package appl.grabber.impl;

import appl.camera.Camera;
import appl.camera.impl.CameraImpl;
import appl.config.Config;
import appl.grabber.Grabber;
import appl.grabber.exceptions.GrabberException;
import com.github.sarxos.webcam.Webcam;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by ca on 16/07/16.
 */
public class GrabberImpl implements Grabber {

    private BooleanProperty isReady = new SimpleBooleanProperty();

    private Config config;
    private Camera camera;
    private ScheduledExecutorService executorService;
    private ScheduledFuture cameraTask;

    private BooleanProperty isRunning = new SimpleBooleanProperty(false);

    public GrabberImpl(Config config) {
        if (config == null) {
            throw new IllegalArgumentException("The given config is null.");
        }
        this.config = config;
        camera = new CameraImpl();
        executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public Camera getCamera() {
        return camera;
    }

    @Override
    public void setCamera(String name) throws GrabberException {
        if (camera == null) {
            throw new IllegalArgumentException("The given camera is null.");
        }
        Optional<Webcam> cam = camera.getAvailableWebcams().stream().filter(w -> name.equals(w.getName())).findFirst();
        camera.setWebcam(cam.orElseThrow(() -> new GrabberException("The camera is not found anymore")));
    }

    @Override
    public BooleanProperty isRunning() {
        return isRunning;
    }

    @Override
    public void start() throws GrabberException {
        isRunning.set(true);
        if (makeCameraReady()) {
            System.out.println("Grabber starts working: " + new Date().toString());
            cameraTask = executorService.scheduleWithFixedDelay(camera, 0, Integer.toUnsignedLong(config.getRepetitionTime()), TimeUnit.SECONDS);
        } else {
            isRunning.set(false);
        }
    }

    @Override
    public void stop() throws GrabberException {
        if (!isRunning.getValue() || cameraTask == null) {
            throw new GrabberException("Grabber cannot get stopped because it isn't running.");
        }
        System.out.println("Grabber stopps working.");
        isRunning.set(false);
        cameraTask.cancel(true);
        // executorService.shutdown();
        // no busy wait, perform resets
    }

    @Override
    public void shutdown() {
        // TODO richtig mit futures und warten
        executorService.shutdown();
    }

    private boolean makeCameraReady() {
        return camera.makeItReady();
    }
}
