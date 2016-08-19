package gui.view.applicationpane;

import gui.controller.ConfigController;
import gui.controller.ControlsController;
import gui.controller.ImageViewerController;
import gui.controller.MainController;
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

    public ApplicationPane(MainController mainController, ControlsController controlsController, ConfigController configController, ImageViewerController imageViewerController) {
        if (mainController == null || controlsController == null || configController == null || imageViewerController == null) {
            throw new IllegalArgumentException("One of the passed controllers is null.");
        }

        HBox controlElements = new HBox(configController.getConfigView().getNode(), controlsController.getControlView().getNode());

        controlElements.setSpacing(5.0);
        controlElements.setAlignment(Pos.CENTER);

        pane = new VBox(imageViewerController.getImageViewer().getNode(), controlElements);
        pane.setSpacing(5);
        pane.setFillWidth(true);
        pane.setPadding(new Insets(5));
        pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public VBox getPane() {
        return pane;
    }
}
