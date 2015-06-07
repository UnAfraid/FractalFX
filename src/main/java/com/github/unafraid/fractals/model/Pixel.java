package com.github.unafraid.fractals.model;

import com.github.unafraid.fractals.CanvasRender;
import com.github.unafraid.fractals.type.AbstractFractal;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by UnAfraid on 7.6.2015 г..
 */
public class Pixel {
    private CanvasRender canvasRender;
    private int _x;
    private int _y;
    private Color _color;

    public Pixel(CanvasRender canvasRender, int x, int y) {
        this.canvasRender = canvasRender;
        _x = x;
        _y = y;
    }

    public void compute(double scale, Color[] manyShadesOfGrey) {
        final Complex input = new Complex((double) _x * scale + canvasRender.getFractal().getOffsetX(), (double) _y * scale + canvasRender.getFractal().getOffsetY());
        final int iterations = getIterations(input, canvasRender.getFractal(), manyShadesOfGrey);
        _color = iterations > 0 ? manyShadesOfGrey[iterations * 2] : Color.BLACK;
    }

    public void apply(GraphicsContext gc) {
        if (gc.getFill() != _color) {
            gc.setFill(_color);
        }

        gc.fillRect(_x, _y, 1, 1);
    }


    private int getIterations(Complex c, AbstractFractal fractal, Color[] colors) {
        Complex z = new Complex(0, 0);

        for (int i = 0; i < colors.length / 2; ++i) {
            z = fractal.getIterationResult(z, c);

            if (z.getReal() > 2 || z.getImag() > 2) {
                return i;
            }
        }

        return 0;
    }
}
