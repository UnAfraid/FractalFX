package com.github.unafraid.fractals;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        final Parent root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Fractal FX Drawer");
        primaryStage.getIcons().add(new Image(getClass().getResource("/images/logo.png").toString()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
