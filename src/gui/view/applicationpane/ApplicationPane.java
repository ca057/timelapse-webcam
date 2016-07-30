package gui.view.applicationpane;

import gui.controller.ConfigController;
import gui.controller.ControlsController;
import gui.controller.MainController;
import gui.view.applicationpane.configview.ConfigView;
import gui.view.applicationpane.controlview.ControlView;
import gui.view.applicationpane.debugview.DebugView;
import gui.view.applicationpane.imageviewer.ImageViewer;
import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by ca on 16/07/16.
 */
public class ApplicationPane extends GridPane {

    private final VBox pane;

    private ConfigView configView;

    public ApplicationPane(MainController mainController, ControlsController controlsController, ConfigController configController) {
        if (mainController == null || controlsController == null || configController == null) {
            // TODO
        }
        ImageViewer imageViewer = new ImageViewer();

        configView = new ConfigView(configController);
        configController.setConfigView(configView);

        ControlView controlView = new ControlView(controlsController, this);
        controlsController.setControlView(controlView);

        DebugView debugView = new DebugView();
        HBox controlElements = new HBox(configView.getNode(), debugView.getNode(), controlView.getNode());

        pane = new VBox(imageViewer.getNode(), controlElements);
    }

    public BooleanProperty getAllConfigDoneProperty() {
        return configView.allConfigDoneProperty();
    }

    public VBox getPane() {
        return pane;
    }
}
