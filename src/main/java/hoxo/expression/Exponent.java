package hoxo.expression;

import static hoxo.expression.Functions.*;

public class Exponent extends AbstractFunction {

    private final double base;

    private Exponent(double base, Expression arg) {
        super(arg);
        this.base = base;
    }

    @Override
    public double evaluate(double x) {
        return Math.pow(base, arg.evaluate(x));
    }

    public static Exponent cons(double base, Expression arg) {
        return new Exponent(base, arg);
    }

    public double getBase() {
        return base;
    }

    @Override
    public Expression derivative() {
        return multiply(arg.derivative(), multiply(c(Math.log(base)), this));
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && Double.compare(base, ((Exponent) o).getBase()) == 0;
    }

    @Override
    public String toString() {
        return Double.toString(base) + "^(" + arg + ")";
    }
}
