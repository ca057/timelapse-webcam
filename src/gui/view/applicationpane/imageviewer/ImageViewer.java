package gui.view.applicationpane.imageviewer;

import gui.view.applicationpane.SubViews;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 * Created by ca on 16/07/16.
 */
public class ImageViewer implements SubViews{
    @Override
    public Node getNode() {
        return new BorderPane();
    }
}
