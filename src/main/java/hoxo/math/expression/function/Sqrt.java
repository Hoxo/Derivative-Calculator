package hoxo.math.expression.function;

import hoxo.math.expression.Expression;

public class Sqrt extends AbstractFunction {

    public Sqrt(Expression arg) {
        super("sqrt", arg);
    }

    @Override
    public String toString() {
        return "sqrt(" + arg + ")";
    }

    @Override
    public Expression derivative() {
        return null;
    }

    @Override
    public double evaluate(double x) {
        return Math.sqrt(arg.evaluate(x));
    }
}
