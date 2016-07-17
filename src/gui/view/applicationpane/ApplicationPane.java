package gui.view.applicationpane;

import gui.controller.ConfigController;
import gui.controller.ControlsController;
import gui.controller.MainController;
import gui.view.applicationpane.configview.ConfigView;
import gui.view.applicationpane.controlview.ControlView;
import gui.view.applicationpane.debugview.DebugView;
import gui.view.applicationpane.imageviewer.ImageViewer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by ca on 16/07/16.
 */
public class ApplicationPane extends GridPane{

    private VBox pane;

    public ApplicationPane(MainController mainController, ControlsController controlsController, ConfigController configController) {
        if (mainController == null || controlsController == null || configController == null) {
            // TODO
        }
        ImageViewer imageViewer = new ImageViewer();

        ConfigView configView = new ConfigView(configController);
        ControlView controlView = new ControlView(controlsController);
        DebugView debugView = new DebugView();
        HBox controlElements = new HBox(configView.getNode(), debugView.getNode(), controlView.getNode());

        pane = new VBox(imageViewer.getNode(), controlElements);
        /*

        Button stopButton = new Button();
        stopButton.setCancelButton(true);
        stopButton.setText("STOP");

        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainController.stopGrabbing();
            }
        });

        pane.add(startButton, 0, 0);
        pane.add(stopButton, 0, 1);
        */
    }

    public VBox getPane() {
        return pane;
    }
}
