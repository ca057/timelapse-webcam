package main;

import appl.timelapse.Timelapse;
import appl.timelapse.impl.TimelapseImpl;
import gui.controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Timelapse timelapse = new TimelapseImpl();
        new MainController(timelapse).showView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
