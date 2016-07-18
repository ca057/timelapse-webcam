package gui.view.applicationpane.controlview;

import gui.controller.ControlsController;
import gui.controller.exceptions.ControllerException;
import gui.view.applicationpane.SubViews;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Created by ca on 16/07/16.
 */
public class ControlView implements SubViews{

    private BooleanProperty isRunning = new SimpleBooleanProperty(false);

    private VBox elementContainer;
    private ControlsController controlsController;

    public ControlView(ControlsController controlsController) {
        if (controlsController == null) {
            throw new IllegalArgumentException("Passed ControlsController is null.");
        }
        this.controlsController = controlsController;
        elementContainer = new VBox(createStartButton(), createStopButton());
    }

    public boolean isIsRunning() {
        return isRunning.get();
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning.set(isRunning);
    }

    public BooleanProperty isRunningProperty() {
        return isRunning;
    }

    private Node createStartButton() {
        Button startButton = new Button();
        startButton.setText("START");
        startButton.disableProperty().bind(isRunning);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    event.consume();
                    controlsController.startGrabbing();
                } catch (ControllerException e) {
                    // TODO show error
                }
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
                try {
                    controlsController.stopGrabbing();
                } catch (ControllerException e) {
                    // TODO show error view
                }
            }
        });
        return stopButton;
    }

    @Override
    public Node getNode() {
        return elementContainer;
    }
}
