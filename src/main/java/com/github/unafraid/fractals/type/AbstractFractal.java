package com.github.unafraid.fractals.type;

import com.github.unafraid.fractals.model.Complex;
import javafx.scene.paint.Color;

/**
 * Created by UnAfraid on 7.6.2015 г..
 */
public abstract class AbstractFractal {
    protected double _offsetX, _offsetY, _ratioWidth, _ratioHeight;

    public abstract Complex getIterationResult(Complex z, Complex c);

    public abstract Color getColor(int i);

    public double getOffsetX() {
        return _offsetX;
    }

    public double getOffsetY() {
        return _offsetY;
    }

    public double getRatioWidth() {
        return _ratioWidth;
    }

    public double getRatioHeight() {
        return _ratioHeight;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
