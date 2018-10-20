package hoxo.function;

import hoxo.util.HasName;

public class Sin extends AbstractFunction implements HasName {

    public Sin(Function arg) {
        super(arg);
    }

    @Override
    public Function derivative() {
        return Multiply.cons(arg.derivative(), new Cos(arg));
    }

    @Override
    public double evaluate(double x) {
        return Math.sin(arg.evaluate(x));
    }

    @Override
    public String getName() {
        return "sin";
    }

    @Override
    public String toString() {
        return "sin(" + arg + ")";
    }
}
