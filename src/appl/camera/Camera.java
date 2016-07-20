package appl.camera;

import com.github.sarxos.webcam.Webcam;
import java.util.List;

/**
 * Created by ca on 16/07/16.
 */
public interface Camera extends Runnable {

    List<Webcam> getAvailableWebcams();

    void setWebcam(Webcam webcam);

    boolean makeItReady();

    void shouldListenForWebcams(boolean value);
}
