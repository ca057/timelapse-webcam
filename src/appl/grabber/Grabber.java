package appl.grabber;

import appl.camera.Camera;
import appl.config.Config;
import appl.grabber.exceptions.GrabberException;

/**
 * Created by ca on 16/07/16.
 */
public interface Grabber {

    Camera getCamera();

    void setCamera(String name) throws GrabberException;

    boolean isReady();

    void start() throws GrabberException;

    void stop() throws GrabberException;

    void shutdown();
}
