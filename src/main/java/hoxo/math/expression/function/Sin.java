package hoxo.math.expression.function;

import hoxo.math.expression.Expression;

import static hoxo.math.expression.function.Functions.cos;
import static hoxo.math.expression.function.Functions.multiply;

public class Sin extends AbstractFunction {

    public Sin(Expression arg) {
        super("sin", arg);
    }

    @Override
    public Expression derivative() {
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
