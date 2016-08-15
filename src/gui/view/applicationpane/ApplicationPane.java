package gui.view.applicationpane;

import gui.controller.ConfigController;
import gui.controller.ControlsController;
import gui.controller.MainController;
import gui.view.applicationpane.controlview.ControlView;
import gui.view.applicationpane.imageviewer.ImageViewer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by ca on 16/07/16.
 */
public class ApplicationPane extends GridPane {

    private final VBox pane;

    public ApplicationPane(MainController mainController, ControlsController controlsController, ConfigController configController) {
        if (mainController == null || controlsController == null || configController == null) {
            throw new IllegalArgumentException("One of the passed controllers is null.");
        }
        ImageViewer imageViewer = new ImageViewer();

        ControlView controlView = new ControlView(controlsController);
        controlsController.setControlView(controlView);

        HBox controlElements = new HBox(configController.getConfigView().getNode(), controlView.getNode());
        controlElements.setSpacing(5.0);
        controlElements.setAlignment(Pos.CENTER);

        pane = new VBox(imageViewer.getNode(), controlElements);
        pane.setPadding(new Insets(5));
    }

    public VBox getPane() {
        return pane;
    }
}
