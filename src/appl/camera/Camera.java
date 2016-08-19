package appl.camera;

import appl.camera.exceptions.CameraException;
import com.github.sarxos.webcam.Webcam;
import java.nio.file.Path;
import javafx.collections.ObservableList;

/**
 * Created by ca on 16/07/16.
 */
public interface Camera extends Runnable {

    ObservableList<Webcam> getAvailableWebcams();

    Webcam getCurrentWebcam();

    void setWebcam(Webcam webcam);

    boolean makeItReady() throws CameraException;

    boolean turnItOff();

    void shouldListenForWebcams(boolean value);

    void saveTo(Path path);
}
