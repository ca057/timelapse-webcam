package gui.view.applicationpane.controlview;

import gui.controller.ControlsController;
import gui.controller.exceptions.ControllerException;
import gui.view.applicationpane.ApplicationPane;
import gui.view.applicationpane.SubViews;
import javafx.beans.binding.Bindings;
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
public class ControlView implements SubViews {

    private final BooleanProperty isRunning;

    private final ApplicationPane applicationPane;

    private VBox elementContainer;
    private ControlsController controlsController;

    public ControlView(ControlsController controlsController, ApplicationPane pane) {
        if (controlsController == null || pane == null) {
            throw new IllegalArgumentException("Passed ControlsController or ApplicationPane is null.");
        }
        this.controlsController = controlsController;
        this.isRunning = new SimpleBooleanProperty(false);
        this.applicationPane = pane;
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

        startButton.disableProperty().bind(Bindings.and(isRunning, applicationPane.getAllConfigDoneProperty().not()));
        startButton.setOnAction((ActionEvent event) -> {
            try {
                event.consume();
                controlsController.startGrabbing();
            } catch (ControllerException e) {
                // TODO show error
            }
        });
        return startButton;
    }

    private Node createStopButton() {
        Button stopButton = new Button();
        stopButton.setText("STOP");
        stopButton.disableProperty().bind(isRunning.not());
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
