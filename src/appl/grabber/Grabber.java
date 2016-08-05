package appl.grabber;

import appl.camera.Camera;
import appl.grabber.exceptions.GrabberException;
import java.nio.file.Path;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;

/**
 * Created by ca on 16/07/16.
 */
public interface Grabber {

    Camera getCamera();

    BooleanProperty isRunning();

    void setCamera(String name) throws GrabberException;

    void setDirectoryToSave(Path directory);

    void start() throws GrabberException;

    void stop() throws GrabberException;

    void shutdown();

    IntegerProperty getRepetitionRate();
}
