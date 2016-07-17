package gui.view.applicationpane.controlview;

import gui.controller.ControlsController;
import gui.view.applicationpane.SubViews;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by ca on 16/07/16.
 */
public class ControlView implements SubViews{

    private VBox elementContainer;
    private ControlsController controlsController;

    public ControlView(ControlsController controlsController) {
        if (controlsController == null) {
            // TODO
        }
        this.controlsController = controlsController;
        elementContainer = new VBox(createStartButton(), createStopButton());
    }

    private Node createStartButton() {
        Button startButton = new Button();
        startButton.setText("START");

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controlsController.startGrabbing();
            }
        });
        return startButton;
    }

    private Node createStopButton() {
        Button stopButton = new Button();
        stopButton.setText("STOP");

        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controlsController.stopGrabbing();
            }
        });
        return stopButton;
    }

    @Override
    public Node getNode() {
        return elementContainer;
    }
}
