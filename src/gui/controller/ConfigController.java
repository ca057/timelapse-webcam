package gui.controller;

import com.github.sarxos.webcam.Webcam;
import gui.controller.exceptions.ControllerException;
import gui.view.applicationpane.configview.ConfigView;
import java.io.File;
import java.nio.file.Path;
import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Created by ca on 16/07/16.
 */
public class ConfigController {

    private final MainController mainController;

    private Path targetPath;

    private ConfigView configView;

    public ConfigController(MainController mainController) {
        if (mainController == null) {
            throw new IllegalArgumentException("The passed MainController is null.");
        }
        this.mainController = mainController;
        this.configView = new ConfigView(this);
        configView.isRunningProperty().bind(mainController.isRunningProperty());
    }

    public ObservableList<Webcam> getAvailableWebcams() {
        return mainController.getAvailableWebcams();
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
            this.targetPath = directory.get().toPath();
            configView.directoryTextProperty().setValue(directory.get().toString());
        } else {
            // TODO show error window
        }
    }

    public void setWebcam(Webcam webcam) throws ControllerException {
        mainController.setWebcam(webcam);
    }

    public ConfigView getConfigView() {
        return configView;
    }

    public BooleanProperty getAllConfigDone() {
        return configView.allConfigDoneProperty();
    }

    public BooleanProperty getTurnsOffBetweenCapturesProperty() {
        return mainController.turnsOffBetweenCapturesProperty();
    }

    public Webcam getCurrentWebcam() {
        return mainController.getCurrentWebcam();
    }

    public long getRepetitionRate() {
        return configView.repetitionRateProperty().longValue();
    }

    public Path getTargetDirectory() {
        return targetPath;
    }
}
