package hoxo.function;

import hoxo.util.HasName;

import static hoxo.function.Functions.*;

public class Cos extends AbstractFunction implements HasName {
    public Cos(Function arg) {
        super(arg);
    }

    @Override
    public double evaluate(double x) {
        return Math.cos(arg.evaluate(x));
    }

    @Override
    public Function derivative() {
        return multiply(arg.derivative(), neg(sin(arg)));
    }


    @Override
    public String getName() {
        return "cos";
    }

    @Override
    public String toString() {
        return "cos(" + arg + ")";
    }
}
