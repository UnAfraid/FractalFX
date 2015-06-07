package com.github.unafraid.fractals.controllers;

import com.github.unafraid.fractals.CanvasRender;
import com.github.unafraid.fractals.type.AbstractFractal;
import com.github.unafraid.fractals.type.BurningShip;
import com.github.unafraid.fractals.type.Mandelbrot;
import com.github.unafraid.fractals.type.Mandelbrot2;
import com.github.unafraid.fractals.utils.Dialogs;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by UnAfraid on 7.6.2015 г..
 */
public class MainController implements Initializable {
    private volatile Task<Void> _task = null;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TextField width;

    @FXML
    private TextField height;

    @FXML
    private Button renderButton;

    @FXML
    private CheckBox parallelism;

    @FXML
    private ComboBox<AbstractFractal> fractalSelector;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fractalSelector.getItems().addAll(FXCollections.observableArrayList(new BurningShip(), new Mandelbrot(), new Mandelbrot2()));
    }

    @FXML
    public void onRenderRequest(ActionEvent event) {
        int w, h;
        try {
            w = Integer.parseInt(width.getText());
            h = Integer.parseInt(height.getText());
        }
        catch (NumberFormatException e) {
            Dialogs.showDialog(Alert.AlertType.ERROR, "Wrong input", "Invalid width", "The specified width is invalid, it should be numeric!");
            return;
        }

        if (w < 1) {
            Dialogs.showDialog(Alert.AlertType.ERROR, "Wrong input", "Invalid width", "The specified width is invalid, it should be numeric!");
            return;
        }

        final AbstractFractal fractal = fractalSelector.getSelectionModel().getSelectedItem();
        if (fractal == null) {
            Dialogs.showDialog(Alert.AlertType.ERROR, "Wrong input", "Invalid fractal", "You must specify fractal type!");
            return;
        }
        if (h < 1) {
            h = (int) (w * fractal.getRatioHeight() / fractal.getRatioWidth());
        }

        final Stage canvasStage = new Stage();
        final Scene scene;
        try {
            final Parent CanvasRoot = FXMLLoader.load(getClass().getResource("/views/CanvasView.fxml"));
            scene = new Scene(CanvasRoot);
        }
        catch (IOException e) {
            Dialogs.showExceptionDialog(Alert.AlertType.ERROR, "Error!", "Error while loading CanvasView.fxml", e);
            return;
        }

        canvasStage.setScene(scene);
        canvasStage.setTitle("Canvas - " + fractal);
        canvasStage.getIcons().add(new Image(getClass().getResource("/images/logo.png").toString()));
        canvasStage.show();

        if (_task == null) {
            final Canvas canvas = (Canvas) scene.lookup("#canvas");
            _task = new CanvasRender(fractal, canvas, parallelism.isSelected(), this::onRenderFinish);
            canvas.setWidth(w);
            canvas.setHeight(h);
            progressBar.progressProperty().bind(_task.progressProperty());
            renderButton.setText("Working...");

            new Thread(_task).start();
        }
    }

    @FXML
    private void onApplicationExitRequest(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void onAboutRequest(ActionEvent event) {
        try
        {
            final Stage stage = new Stage();
            final Parent root = FXMLLoader.load(getClass().getResource("/views/About.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("FractionsFX - About");
            stage.initStyle(StageStyle.UTILITY);
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e)
        {
            Dialogs.showExceptionDialog(Alert.AlertType.ERROR, "Error!", "Exception during help form creation:", e);
        }
    }

    private void onRenderFinish() {
        renderButton.setText("Render");
        _task = null;
    }
}
