package gui.controller;

import appl.timelapse.Timelapse;
import appl.timelapse.exceptions.TimelapseException;
import com.github.sarxos.webcam.Webcam;
import gui.controller.exceptions.ControllerException;
import gui.view.mainstage.MainStage;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;

/**
 * Created by ca on 16/07/16.
 */
public class MainController {

    private Timelapse timelapse;
    private MainStage mainStage;

    private ControlsController controlsController;
    private ConfigController configController;
    private ImageController imageController;

    public MainController(Timelapse timelapse) {
        if (timelapse == null) {
            throw new IllegalArgumentException("Passed timelapse module is null.");
        }
        this.timelapse = timelapse;
        this.controlsController = new ControlsController(this);
        this.configController = new ConfigController(this);
        this.imageController = new ImageController();
        this.mainStage = new MainStage(this, imageController, configController, controlsController);
    }

    void startTimelapse() throws ControllerException {
        try {
            timelapse.start(configController.getRepetitionRate(), configController.getTargetDirectory());
        } catch (TimelapseException ex) {
            throw new ControllerException("Starting the timelapse failed.");
        }
    }

    void stopTimelapse() throws ControllerException {
        try {
            timelapse.stop();
        } catch (TimelapseException ex) {
            throw new ControllerException("Stopping the timelapse failed.");
        }
    }

    BooleanProperty isRunningProperty() {
        return timelapse.isRunning();
    }

    BooleanProperty isReadyProperty() {
        return configController.getAllConfigDone();
    }

    ObservableList<Webcam> getAvailableWebcams() {
        return timelapse.getCameraModule().getAvailableWebcams();
    }

    Webcam getCurrentWebcam() {
        return timelapse.getCameraModule().getCurrentWebcam();
    }

    public void showView() {
        mainStage.show();
    }

    public void stopApplication() {
        timelapse.shutdown();
        mainStage.close();
        System.exit(0);
    }
}
