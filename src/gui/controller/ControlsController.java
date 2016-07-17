package gui.controller;

import appl.grabber.Grabber;
import appl.grabber.exceptions.GrabberException;

/**
 * Created by ca on 16/07/16.
 */
public class ControlsController {

    private Grabber grabber;

    public ControlsController(Grabber grabber) {
        if (grabber == null) {
            throw new IllegalArgumentException("No grabber passed.");
        }
        this.grabber = grabber;
    }
    public void startGrabbing() {
        try {
            if (grabber.isReady()) {
                grabber.start();
            }
        } catch (GrabberException e) {
            // TODO
            e.printStackTrace();
        }
    }

    public void stopGrabbing() {
        try {
            grabber.stop();
        } catch (GrabberException e) {
            e.printStackTrace();
        }
    }
}
