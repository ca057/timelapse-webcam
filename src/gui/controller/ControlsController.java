package gui.controller;

import gui.controller.exceptions.ControllerException;
import gui.view.applicationpane.controlview.ControlView;

/**
 * Created by ca on 16/07/16.
 */
public class ControlsController {

    private MainController mainController;

    private ControlView controlView;

    public ControlsController(MainController mainController) {
        if (mainController == null) {
            throw new IllegalArgumentException("The passed MainController is null.");
        }
        this.mainController = mainController;
    }

    public void startTimelapse() throws ControllerException {
        mainController.startTimelapse();
    }

    public void stopTimelapse() throws ControllerException {
        mainController.stopTimelapse();
    }

    public void setControlView(ControlView controlView) {
        if (controlView == null) {
            throw new IllegalArgumentException("Passed ConfigView is null.");
        }
        this.controlView = controlView;
        controlView.isRunningProperty().bind(mainController.isRunningProperty());
        controlView.isReadyProperty().bind(mainController.isReadyProperty());
    }

}
