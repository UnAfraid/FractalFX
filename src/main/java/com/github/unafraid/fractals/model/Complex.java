package com.github.unafraid.fractals.model;

/**
 * Created by UnAfraid on 7.6.2015 г..
 */
public class Complex {
    private double _real, _imag;

    public Complex(double real, double imag) {
        _real = real;
        _imag = imag;
    }

    public double getReal() {
        return _real;
    }

    public double getImag() {
        return _imag;
    }

    public Complex add(Complex b) {
        return new Complex(_real + b.getReal(), _imag + b.getImag());
    }

    public Complex mul(Complex b) {
        return new Complex(_real * b.getReal() - _imag * b.getImag(), _real * b.getImag() + _imag * b.getReal());
    }

    public Complex pow(int power) {
        Complex result = new Complex(1, 0);
        for (int i = 1; i < power; i++) {
            result = result.mul(this);
        }
        return result;
    }
}
