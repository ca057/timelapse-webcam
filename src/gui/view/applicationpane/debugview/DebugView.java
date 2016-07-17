package gui.view.applicationpane.debugview;

import gui.view.applicationpane.SubViews;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.*;
import javafx.scene.text.Font;

import java.awt.*;

/**
 * Created by ca on 16/07/16.
 */
public class DebugView implements SubViews {

    private TextArea output;

    public DebugView() {
        output = new TextArea();
        output.setDisable(true);
        output.setFont(Font.font("monospaced"));
    }

    @Override
    public Node getNode() {
        return output;
    }

    public void concatDebugInformation(String info) {
        if (!info.isEmpty()) {
            // TODO bindings?!
            output.textProperty().set(output.textProperty().getValue().concat(info));
        }
    }
}
