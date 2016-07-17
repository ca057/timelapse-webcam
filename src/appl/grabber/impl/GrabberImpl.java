package appl.grabber.impl;

import appl.camera.Camera;
import appl.camera.impl.CameraImpl;
import appl.config.Config;
import appl.grabber.Grabber;
import appl.grabber.exceptions.GrabberException;
import com.github.sarxos.webcam.Webcam;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Created by ca on 16/07/16.
 */
public class GrabberImpl implements Grabber {

    private Config config;
    private Camera camera;
    private ScheduledExecutorService executorService;
    private ScheduledFuture cameraTask;

    private boolean isRunning = false;

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
    public void start() throws GrabberException {
        if (!isReady()) {
            throw new GrabberException("Grabber was not initialized.");
        }
        boolean isReady = camera.makeItReady();

        if (isReady) {
            System.out.println("Grabber starts working: " + new Date().toString());
            isRunning = true;
            cameraTask = executorService.scheduleWithFixedDelay(camera, 0, Integer.toUnsignedLong(config.getRepetitionTime()), TimeUnit.SECONDS);
        } else {
            // TODO
        }
    }

    @Override
    public void stop() throws GrabberException {
        if (!isRunning || cameraTask == null) {
            throw new GrabberException("Grabber cannot get stopped because it isn't running.");
        }
        System.out.println("Grabber stopps working.");
        isRunning = false;
        cameraTask.cancel(true);
        // executorService.shutdown();
        // no busy wait, perform resets
    }

    @Override
    public void shutdown() {
        // TODO richtig mit futures und warten
        executorService.shutdown();
    }

    public boolean isReady() {
        return config != null;
    }
}
