package com.github.unafraid.fractals.type;

import com.github.unafraid.fractals.model.Complex;
import javafx.scene.paint.Color;

/**
 * Created by UnAfraid on 7.6.2015 г..
 */
public class BurningShip extends AbstractFractal {

    public BurningShip() {
        _offsetX = -2.5;
        _offsetY = -1.5;
        _ratioWidth = 3.5;
        _ratioHeight = 2;
    }

    @Override
    public Complex getIterationResult(Complex z, Complex c) {
        final Complex absZ = new Complex(Math.abs(z.getReal()), Math.abs(z.getImag()));
        return absZ.mul(absZ).add(c);
    }

    @Override
    public Color getColor(int i) {
        return new Color(1, 0.5, (double) i / 255, 1);
    }
}
