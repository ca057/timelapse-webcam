package gui.controller;

import appl.grabber.Grabber;
import appl.grabber.exceptions.GrabberException;
import gui.controller.exceptions.ControllerException;
import gui.view.applicationpane.controlview.ControlView;

/**
 * Created by ca on 16/07/16.
 */
public class ControlsController {

    private Grabber grabber;

    private ControlView controlView;

    public ControlsController(Grabber grabber) {
        if (grabber == null) {
            throw new IllegalArgumentException("No grabber passed.");
        }
        this.grabber = grabber;
    }

    public void startGrabbing() throws ControllerException {
        try {
            grabber.start();
        } catch (GrabberException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    public void stopGrabbing() throws ControllerException {
        try {
            grabber.stop();
        } catch (GrabberException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    public void setControlView(ControlView controlView) {
        if (controlView == null) {
            throw new IllegalArgumentException("Passed ConfigView is null.");
        }
        this.controlView = controlView;
        controlView.isRunningProperty().bind(grabber.isRunning());
    }
}
