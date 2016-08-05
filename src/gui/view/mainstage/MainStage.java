package gui.view.mainstage;

import gui.controller.*;
import gui.view.applicationpane.ApplicationPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Created by ca on 16/07/16.
 */
public class MainStage extends Stage {

    public MainStage(MainController mainController,
            ImageController imageController,
            ConfigController configController,
            ControlsController controlsController) {
        if (mainController == null
                || imageController == null
                || configController == null
                || controlsController == null) {
            throw new IllegalArgumentException("No MainController or ControlsController passed.");
        }
        ApplicationPane rootPane = new ApplicationPane(mainController, controlsController, configController);

        Scene scene = new Scene(rootPane.getPane());

        this.setTitle("TIMELAPSE WEBCAM");
        this.setScene(scene);
        this.setOnCloseRequest((WindowEvent we) -> {
            we.consume();
            mainController.stopApplication();
        });
    }
}
