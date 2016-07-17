package gui.controller;

import appl.grabber.Grabber;
import appl.grabber.exceptions.GrabberException;
import appl.grabber.impl.GrabberImpl;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by ca on 16/07/16.
 */
public class ConfigController {

    private Grabber grabber;

    public ConfigController(Grabber grabber) {
        if (grabber == null) {
            throw new IllegalArgumentException("The passed grabber is null.");
        }
        this.grabber = grabber;
    }

    public List<String> getAvailableWebcamNames() {
        return grabber.getCamera().getAvailableWebcams().stream().map(wc -> wc.getName()).collect(Collectors.toList());
    }

    public void setWebcam(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("The passed name is null or empty.");
        }
        try {
            grabber.setCamera(name);
        } catch (GrabberException e) {
            // TODO e.printStackTrace();
        }
    }

    public void chooseSaveDirectory(Window window) {
        if (window == null) {
            throw new IllegalArgumentException("Stage is null.");
        }
        DirectoryChooser dirChooser = new DirectoryChooser();
        Stage dirStage = new Stage();
        dirStage.initModality(Modality.APPLICATION_MODAL);
        dirChooser.setTitle("Speicherort für Bilder wählen");
        Optional<File> file = Optional.ofNullable(dirChooser.showDialog(window));
    }
}
