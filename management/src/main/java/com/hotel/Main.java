package com.hotel;
import com.hotel.view.FirebaseIntializer;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        WelcomePage welcomePage = new WelcomePage(primaryStage);
        welcomePage.show();
    }

    public static void main(String[] args) {
        FirebaseIntializer.initialize();
        launch(args);
    }
}
