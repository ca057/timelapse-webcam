package gui.view.applicationpane.configview;

import gui.controller.ConfigController;
import gui.view.applicationpane.SubViews;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by ca on 16/07/16.
 */
public class ConfigView implements SubViews {

    private BooleanProperty isRunning = new SimpleBooleanProperty(false);

    private ConfigController configController;
    private GridPane configInputs;

    public ConfigView(ConfigController configController) {
        if (configController == null) {
            throw new IllegalArgumentException("No ConfigController passed.");
        }
        this.configController = configController;
        configInputs = new GridPane();
        configInputs.add(new Text("Webcam"), 0, 0);
        configInputs.add(createWebcamSelection(), 1, 0);
        configInputs.add(new Text("Speicherort"), 0, 1);
        configInputs.add(createSaveDirectoryInput(), 1, 1);
        configInputs.add(new Text("Bildformat"), 0, 2);
        configInputs.add(createFileEndingInput(), 1, 2);
        configInputs.add(new Text("Wiederholrate (s)"), 0, 3);
        configInputs.add(createDelayInput(), 1, 3);
    }

    private Node createWebcamSelection() {
        ObservableList<String> cams = FXCollections.observableList(configController.getAvailableWebcamNames());
        ComboBox<String> camSelection = new ComboBox<>(cams);

        camSelection.disableProperty().bind(isRunning);
        camSelection.setOnAction((ActionEvent event) -> {
            event.consume();
            configController.setWebcam(camSelection.getValue());
        });
        return camSelection;
    }

    private Node createSaveDirectoryInput() {
        Button directorySelection = new Button("Speicherort wählen");

        directorySelection.disableProperty().bind(isRunning);
        directorySelection.setOnAction((ActionEvent event) -> {
            event.consume();
            configController.chooseSaveDirectory(configInputs.getScene().getWindow());
        });
        return directorySelection;
    }

    private Node createFileEndingInput() {
        return new HBox();
    }

    private Node createDelayInput() {
        return new HBox();
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

    @Override
    public Node getNode() {
        return configInputs;
    }
}
