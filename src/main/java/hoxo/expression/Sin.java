package hoxo.expression;

import static hoxo.expression.Functions.cos;
import static hoxo.expression.Functions.multiply;

public class Sin extends AbstractFunction {

    public Sin(Expression arg) {
        super(arg);
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
