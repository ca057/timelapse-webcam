package main;

import appl.config.Config;
import appl.grabber.Grabber;
import appl.grabber.impl.GrabberImpl;
import gui.controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Config config = new Config();
        Grabber grabber = new GrabberImpl(config);
        new MainController(grabber, config).showView();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
