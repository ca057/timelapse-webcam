package gui.controller;

import appl.timelapse.Timelapse;
import appl.timelapse.exceptions.TimelapseException;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import gui.controller.exceptions.ControllerException;
import gui.view.mainstage.MainStage;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;

/**
 * Created by ca on 16/07/16.
 */
public class MainController {

    private Timelapse timelapse;
    private WebcamDiscoveryListener webcamListener;
    private MainStage mainStage;

    private ControlsController controlsController;
    private ConfigController configController;
    private ImageViewerController imageViewerController;

    public MainController(Timelapse timelapse) {
        if (timelapse == null) {
            throw new IllegalArgumentException("Passed timelapse module is null.");
        }
        this.timelapse = timelapse;
        this.webcamListener = createWebcamDiscoveryListener(timelapse.getCameraModule().getAvailableWebcams());
        this.timelapse.setWebcamDiscoveryListener(webcamListener);

        this.configController = new ConfigController(this);
        this.controlsController = new ControlsController(this);
        this.imageViewerController = new ImageViewerController();
        this.mainStage = new MainStage(this, imageViewerController, configController, controlsController);
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

    BooleanProperty turnsOffBetweenCapturesProperty() {
        return timelapse.turnsCameraOffBetweenCaptures();
    }

    ObservableList<Webcam> getAvailableWebcams() {
        return timelapse.getCameraModule().getAvailableWebcams();
    }

    Webcam getCurrentWebcam() {
        return timelapse.getCameraModule().getCurrentWebcam();
    }

    void setWebcam(Webcam webcam) throws ControllerException {
        try {
            timelapse.setCamera(webcam);
        } catch (TimelapseException e) {
            throw new ControllerException("Setting the webcam throwed an error: " + e.getMessage());
        }
    }

    public void showView() {
        mainStage.show();
    }

    public void stopApplication() {
        timelapse.removeWebcamDiscoveryListener(webcamListener);
        timelapse.shutdown();
        mainStage.close();
        System.exit(0);
    }

    private WebcamDiscoveryListener createWebcamDiscoveryListener(ObservableList<Webcam> cameras) {
        return new WebcamDiscoveryListener() {
            @Override
            public void webcamFound(WebcamDiscoveryEvent event) {
                Platform.runLater(() -> {
                    cameras.add(event.getWebcam());
                });
            }

            @Override
            public void webcamGone(WebcamDiscoveryEvent event) {
                Platform.runLater(() -> {
                    cameras.remove(event.getWebcam());
                });
            }
        };
    }
}
