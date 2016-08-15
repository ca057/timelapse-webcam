package appl.grabber;

import appl.camera.Camera;
import appl.grabber.exceptions.GrabberException;
import com.github.sarxos.webcam.Webcam;
import java.nio.file.Path;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;

/**
 * Created by ca on 16/07/16.
 */
public interface Grabber {

    Camera getCameraModule();

    BooleanProperty isRunning();

    void setCamera(Webcam camera) throws GrabberException;

    void setDirectoryToSave(Path directory);

    void start() throws GrabberException;

    void stop() throws GrabberException;

    void shutdown();

    IntegerProperty getRepetitionRate();
}
