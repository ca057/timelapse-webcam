package appl.camera;

import appl.camera.exceptions.CameraException;
import com.github.sarxos.webcam.Webcam;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by ca on 16/07/16.
 */
public interface Camera extends Runnable {

    List<Webcam> getAvailableWebcams();

    void setWebcam(Webcam webcam);

    boolean makeItReady() throws CameraException;

    void shouldListenForWebcams(boolean value);

    void saveTo(Path path);
}
