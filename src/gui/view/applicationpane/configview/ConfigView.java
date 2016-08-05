package gui.view.applicationpane.configview;

import gui.controller.ConfigController;
import gui.view.applicationpane.SubViews;
import java.util.function.UnaryOperator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by ca on 16/07/16.
 */
public class ConfigView implements SubViews {

    private final String chooseDirectoryUiText = "Speicherort wählen";

    private final BooleanProperty isRunning;
    private final StringProperty directoryName;
    private final IntegerProperty repetitionRate;

    private final BooleanProperty allConfigDone;

    private ConfigController configController;
    private GridPane configInputs;

    public ConfigView(ConfigController configController) {
        if (configController == null) {
            throw new IllegalArgumentException("No ConfigController passed.");
        }
        this.configController = configController;

        this.isRunning = new SimpleBooleanProperty(false);
        this.directoryName = new SimpleStringProperty(chooseDirectoryUiText);
        this.allConfigDone = new SimpleBooleanProperty(false);
        this.repetitionRate = new SimpleIntegerProperty(30);

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

    private void setupBindings() {
        // TODO bind cam selection
        allConfigDone.bind(directoryName.isNotEqualTo(chooseDirectoryUiText));
    }

    private Node createWebcamSelection() {
        ObservableList<String> cams = FXCollections.observableList(configController.getAvailableWebcamNames());
        ComboBox<String> camSelection = new ComboBox<>(cams);

        camSelection.valueProperty().setValue("Webcam auswählen");
        camSelection.promptTextProperty().bind(camSelection.valueProperty());

        camSelection.disableProperty().bind(isRunning);
        camSelection.setOnAction((ActionEvent event) -> {
            event.consume();
            configController.setWebcam(camSelection.getValue());
        });
        return camSelection;
    }

    private Node createSaveDirectoryInput() {
        Button directorySelection = new Button();

        directorySelection.textProperty().bind(directoryName);
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
        TextField input = new TextField();
        UnaryOperator<Change> filter = change -> {
            if (change.getText().matches("\\d*")) {
                return change;
            }
            return null;
        };
        input.setTextFormatter(new TextFormatter<>(filter));
        input.disableProperty().bind(isRunning);
        input.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                repetitionRate.setValue(Integer.parseInt(newValue));
            } catch (NumberFormatException ignore) {
            }
        });
        return input;
    }

    public BooleanProperty isRunningProperty() {
        return isRunning;
    }

    public StringProperty directoryTextProperty() {
        return directoryName;
    }

    public BooleanProperty allConfigDoneProperty() {
        return allConfigDone;
    }

    public IntegerProperty repetitionRateProperty() {
        return repetitionRate;
    }

    @Override
    public Node getNode() {
        return configInputs;
    }
}
