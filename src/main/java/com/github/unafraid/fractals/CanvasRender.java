package com.github.unafraid.fractals;

import com.github.unafraid.fractals.model.Pixel;
import com.github.unafraid.fractals.type.AbstractFractal;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Created by UnAfraid on 7.6.2015 г..
 */
public class CanvasRender extends Task<Void> {
    private final AbstractFractal _fractal;
    private final Canvas _canvas;
    private final boolean _isParalel;
    private final Runnable _callback;

    public CanvasRender(AbstractFractal fractal, Canvas canvas, boolean isParalel, Runnable callback) {
        _fractal = fractal;
        _canvas = canvas;
        _isParalel = isParalel;
        _callback = callback;
    }

    @Override
    protected Void call() throws Exception {
        // Get the graphic context
        final GraphicsContext gc = _canvas.getGraphicsContext2D();

        // Clear the context
        gc.clearRect(0, 0, _canvas.getWidth(), _canvas.getHeight());

        // The color array cache
        final Color[] manyShadesOfGrey = new Color[127];
        for (int i = 0; i < manyShadesOfGrey.length; i++) {
            manyShadesOfGrey[i] = _fractal.getColor(i);
        }

        // Calculate the scale
        final double scale = _fractal.getRatioWidth() / _canvas.getWidth();
        final int width = (int)_canvas.getWidth();
        final int height = (int)_canvas.getHeight();

        final List<Pixel> pixels = new ArrayList<>(width * height);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                pixels.add(new Pixel(this, x, y));
            }
        }

        final AtomicInteger processed = new AtomicInteger(0);
        final Stream<Pixel> stream = _isParalel ? pixels.parallelStream() : pixels.stream();
        stream.forEach(pixel -> {
            pixel.compute(scale, manyShadesOfGrey);
            updateProgress(processed.getAndIncrement(), height * width);
        });

        // Execute stuff into main FX thread
        Platform.runLater(() -> {
            // Render all the pixels
            pixels.forEach(pixel -> pixel.apply(gc));

            // Trigger the callback
            _callback.run();
        });

        // Clear progress bar upon complication
        updateProgress(0, _canvas.getHeight());
        return null;
    }

    public AbstractFractal getFractal() {
        return _fractal;
    }
}
