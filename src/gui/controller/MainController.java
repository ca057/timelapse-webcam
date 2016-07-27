package gui.controller;

import appl.grabber.Grabber;
import gui.view.mainstage.MainStage;

/**
 * Created by ca on 16/07/16.
 */
public class MainController {

    private Grabber grabber;
    private MainStage mainStage;

    private ControlsController controlsController;
    private ConfigController configController;
    private DebugController debugController;
    private ImageController imageController;

    public MainController(Grabber grabber) {
        if (grabber == null) {
            throw new IllegalArgumentException("Passed grabber is null.");
        }
        this.grabber = grabber;
        this.controlsController = new ControlsController(grabber);
        this.configController = new ConfigController(grabber);
        this.debugController = new DebugController();
        this.imageController = new ImageController();
        this.mainStage = new MainStage(this, imageController, configController, debugController, controlsController);
    }

    public void showView() {
        mainStage.show();
    }

    public void stopApplication() {
        // TODO make it with futures
        grabber.shutdown();
        mainStage.close();
        System.exit(0);
    }
}
