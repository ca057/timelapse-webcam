package gui.controller;

import gui.controller.exceptions.ControllerException;
import gui.view.applicationpane.controlview.ControlView;

/**
 * Created by ca on 16/07/16.
 */
public class ControlsController {

    private final MainController mainController;

    private final ControlView controlView;

    public ControlsController(MainController mainController) {
        if (mainController == null) {
            throw new IllegalArgumentException("The passed MainController is null.");
        }
        this.mainController = mainController;
        this.controlView = new ControlView(this);
        controlView.isRunningProperty().bind(mainController.isRunningProperty());
        controlView.isReadyProperty().bind(mainController.isReadyProperty());
    }

    public void startTimelapse() throws ControllerException {
        mainController.startTimelapse();
    }

    public void stopTimelapse() throws ControllerException {
        mainController.stopTimelapse();
    }

    public ControlView getControlView() {
        return controlView;
    }
}
