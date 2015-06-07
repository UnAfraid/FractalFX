package com.github.unafraid.fractals.type;

import com.github.unafraid.fractals.model.Complex;
import javafx.scene.paint.Color;

/**
 * Created by UnAfraid on 7.6.2015 г..
 */
public class Mandelbrot extends AbstractFractal {
    public Mandelbrot() {
        _offsetX = -2.5;
        _offsetY = -1;
        _ratioWidth = 3.5;
        _ratioHeight = 2;
    }

    @Override
    public Complex getIterationResult(Complex z, Complex c) {
        return z.mul(z).add(c);
    }

    @Override
    public Color getColor(int i) {
        return new Color(0, 0, (double) i / 255, 1);
    }
}
