package gui.controller;

import appl.grabber.Grabber;
import appl.grabber.exceptions.GrabberException;
import com.github.sarxos.webcam.Webcam;
import gui.controller.exceptions.ControllerException;
import gui.view.applicationpane.configview.ConfigView;
import java.io.File;
import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Created by ca on 16/07/16.
 */
public class ConfigController {

    private Grabber grabber;

    private final BooleanProperty allConfigDone;

    private ConfigView configView;

    public ConfigController(Grabber grabber) {
        this.allConfigDone = new SimpleBooleanProperty(false);
        if (grabber == null) {
            throw new IllegalArgumentException("The passed grabber is null.");
        }
        this.grabber = grabber;
    }

    public ObservableList<Webcam> getAvailableWebcamNames() {
        return grabber.getCamera().getAvailableWebcams();
    }

    public void setWebcam(Webcam camera) throws ControllerException {
        if (camera == null) {
            throw new IllegalArgumentException("The passed camera is null.");
        }
        try {
            grabber.setCamera(camera);
        } catch (GrabberException e) {
            throw new ControllerException("Setting the webcam failed: " + e.getMessage());
        }
    }

    public void chooseSaveDirectory(Window window) {
        if (window == null) {
            throw new IllegalArgumentException("Stage is null.");
        }
        DirectoryChooser dirChooser = new DirectoryChooser();
        Stage dirStage = new Stage();

        dirStage.initModality(Modality.APPLICATION_MODAL);
        dirChooser.setTitle("Speicherort für Bilder wählen");

        Optional<File> directory = Optional.ofNullable(dirChooser.showDialog(window));
        if (directory.isPresent()) {
            grabber.setDirectoryToSave(directory.get().toPath());
            configView.directoryTextProperty().setValue(directory.get().toString());
        } else {
            // TODO show error window
        }
    }

    public void setConfigView(ConfigView configView) {
        if (configView == null) {
            throw new IllegalArgumentException("Passed ConfigView is null.");
        }
        this.configView = configView;
        configView.isRunningProperty().bind(grabber.isRunning());
        grabber.getRepetitionRate().bind(configView.repetitionRateProperty());
    }

    public BooleanProperty getAllConfigDone() {
        return allConfigDone;
    }

    public Webcam getCurrentWebcam() {
        return grabber.getCamera().getCurrentWebcam();
    }

}
