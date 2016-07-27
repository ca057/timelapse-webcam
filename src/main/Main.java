package main;

import appl.grabber.Grabber;
import appl.grabber.impl.GrabberImpl;
import gui.controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Grabber grabber = new GrabberImpl();
        new MainController(grabber).showView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
