package com.github.unafraid.fractals.type;

import com.github.unafraid.fractals.model.Complex;
import javafx.scene.paint.Color;

/**
 * Created by UnAfraid on 7.6.2015 �..
 */
public class Mandelbrot2 extends AbstractFractal {
    public Mandelbrot2() {
        _offsetX = -1.5;
        _offsetY = -1.5;
        _ratioWidth = 3;
        _ratioHeight = 3;
    }

    @Override
    public Complex getIterationResult(Complex z, Complex c) {
        return z.pow(16).add(c);
    }

    @Override
    public Color getColor(int i) {
        return new Color(0.4, 0, (double) i / 255, 1);
    }
}