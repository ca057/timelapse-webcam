/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.view.applicationpane.configview;

import com.github.sarxos.webcam.Webcam;
import javafx.scene.control.ListCell;

/**
 *
 * @author ca
 */
class CameraCell extends ListCell<Webcam> {

    @Override
    protected void updateItem(Webcam camera, boolean empty) {
        super.updateItem(camera, empty);
        if (!empty) {
            if (camera != null) {
                setText(camera.getName());
            }
        }
    }
}
