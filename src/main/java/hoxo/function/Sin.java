package hoxo.function;

import static hoxo.function.Functions.cos;
import static hoxo.function.Functions.multiply;

public class Sin extends AbstractFunction {

    public Sin(Function arg) {
        super(arg);
    }

    @Override
    public Function derivative() {
        return multiply(arg.derivative(), cos(arg));
    }

    @Override
    public double evaluate(double x) {
        return Math.sin(arg.evaluate(x));
    }

    @Override
    public String toString() {
        return "sin(" + arg + ")";
    }
}
