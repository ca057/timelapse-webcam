package appl.timelapse;

import appl.camera.Camera;
import appl.timelapse.exceptions.TimelapseException;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import java.nio.file.Path;
import javafx.beans.property.BooleanProperty;

/**
 * @author Christian Ost
 */
public interface Timelapse {

    Camera getCameraModule();

    BooleanProperty isRunning();

    void setCamera(Webcam camera) throws TimelapseException;

    public boolean setWebcamDiscoveryListener(WebcamDiscoveryListener listener);

    public boolean removeWebcamDiscoveryListener(WebcamDiscoveryListener listener);

    void start(long repetitionRate, Path targetDirectory) throws TimelapseException;

    void stop() throws TimelapseException;

    void shutdown();

}
