package hoxo.expression;

import static hoxo.expression.Functions.*;

public class Log extends AbstractFunction {
    private final double base;

    private Log(double base, Expression arg) {
        super(arg);
        this.base = base;
    }

    public static Expression cons(double base, Expression arg) {
        if (base <= 0) {
            throw new ArithmeticException("Logarithm base is less or equals zero");
        }
        if (base == 1) {
            throw new ArithmeticException("Logarithm base is equals one");
        }
        if (arg instanceof Constant) {
            Constant c = ((Constant) arg);
            if (base == c.getValue()) {
                return Constant.cons(1);
            }
            if (c.getValue() == 1) {
                return Constant.ZERO;
            }
            if (c.getValue() <= 0) {
                throw new ArithmeticException("Logarithm arg is less or equals zero");
            }
        }
        return new Log(base, arg);
    }

    @Override
    public double evaluate(double x) {
        return Math.log(arg.evaluate(x)) / Math.log(base);
    }

    @Override
    public Expression derivative() {
        return divide(arg.derivative(), multiply(ln(c(base)), arg));
    }

    public double getBase() {
        return base;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && Double.compare(base, ((Log) o).getBase()) == 0;
    }

    @Override
    public String toString() {
        String name = "log" + Double.toString(base);
        if (Double.compare(Math.E, base) == 0) {
            name = "ln";
        }
        return name + "(" + arg + ")";
    }
}
