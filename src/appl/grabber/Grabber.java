package appl.grabber;

import appl.camera.Camera;
import appl.grabber.exceptions.GrabberException;
import javafx.beans.property.BooleanProperty;

/**
 * Created by ca on 16/07/16.
 */
public interface Grabber {

    Camera getCamera();

    void setCamera(String name) throws GrabberException;

    BooleanProperty isRunning();

    void start() throws GrabberException;

    void stop() throws GrabberException;

    void shutdown();
}
