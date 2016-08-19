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
            ImageViewerController imageViewerController,
            ConfigController configController,
            ControlsController controlsController) {
        if (mainController == null
                || imageViewerController == null
                || configController == null
                || controlsController == null) {
            throw new IllegalArgumentException("One of the passed controllers is null.");
        }
        ApplicationPane rootPane = new ApplicationPane(mainController, controlsController, configController, imageViewerController);
        Scene scene = new Scene(rootPane.getPane());

        this.setTitle("TIMELAPSE WEBCAM");
        this.setMinWidth(500);
        this.setMinHeight(500);
        this.setScene(scene);
        this.setOnCloseRequest((WindowEvent we) -> {
            we.consume();
            mainController.stopApplication();
        });
    }
}
