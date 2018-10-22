package hoxo.function;

import static hoxo.function.Functions.*;

public class Cos extends AbstractFunction {
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
    public String toString() {
        return "cos(" + arg + ")";
    }
}
