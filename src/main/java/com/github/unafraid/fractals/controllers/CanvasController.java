package com.github.unafraid.fractals.controllers;

import com.github.unafraid.fractals.utils.Dialogs;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by UnAfraid on 7.6.2015 г..
 */
public class CanvasController {
    @FXML
    private Canvas canvas;

    @FXML
    public void onSaveRequest(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        fileChooser.setTitle("Save Map");
        final File file = fileChooser.showSaveDialog(null);
        if(file != null) {
            final SnapshotParameters snapshotParameters = new SnapshotParameters();
            snapshotParameters.setFill(Color.TRANSPARENT);
            try {
                final WritableImage writableImage = new WritableImage((int)canvas.getWidth(),(int)canvas.getHeight());
                ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(snapshotParameters, writableImage), null), "png", file);
                Dialogs.showDialog(Alert.AlertType.INFORMATION, "Success!", "The canvas has been saved!", "Your canvas was successfully saved as image on your hard drive!");
            } catch (IOException e) {
                Dialogs.showExceptionDialog(Alert.AlertType.ERROR, "Error!", "Error while saving CanvasView to file!", e);
            }
        }
    }

    @FXML
    private void onCloseRequest(ActionEvent event) {
        canvas.getScene().getWindow().hide();
    }
}
