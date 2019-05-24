package hoxo.math.expression.function;

import hoxo.math.expression.Expression;

import static hoxo.math.expression.function.Functions.*;

public class Cos extends AbstractFunction {
    public Cos(Expression arg) {
        super(arg);
    }

    @Override
    public double evaluate(double x) {
        return Math.cos(arg.evaluate(x));
    }

    @Override
    public Expression derivative() {
        return multiply(arg.derivative(), neg(sin(arg)));
    }

    @Override
    public String toString() {
        return "cos(" + arg + ")";
    }
}
