package appl.grabber.impl;

import appl.camera.Camera;
import appl.camera.impl.CameraImpl;
import appl.grabber.Grabber;
import appl.grabber.exceptions.GrabberException;
import com.github.sarxos.webcam.Webcam;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Created by ca on 16/07/16.
 */
public class GrabberImpl implements Grabber {

    private Camera camera;
    private ScheduledExecutorService executorService;
    private ScheduledFuture cameraTask;

    private BooleanProperty isReady = new SimpleBooleanProperty();
    private BooleanProperty isRunning = new SimpleBooleanProperty(false);

    public GrabberImpl() {
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
        isRunning.setValue(true);
        System.out.println("Grabber is started: " + new Date().toString());
        if (makeCameraReady()) {
            System.out.println("Grabber starts working: " + new Date().toString());
            camera.shouldListenForWebcams(false);
            cameraTask = executorService.scheduleWithFixedDelay(camera, 0, Integer.toUnsignedLong(1), TimeUnit.SECONDS);
        } else {
            System.out.println("Grabber stopped working because camera is not ready." + new Date().toString());
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
