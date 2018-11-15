package hoxo.function;

import static hoxo.function.Functions.*;

public class Log extends AbstractFunction {
    public static final Log LN_X = new Log(Math.E, Functions.X);
    public static final Log LOG2_X = new Log(2, Functions.X);
    public static final Log LOG10_X = new Log(10, Functions.X);

    private final double base;

    private Log(double base, Function arg) {
        super(arg);
        this.base = base;
    }

    public static Function cons(double base, Function arg) {
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
    public Function derivative() {
        return divide(arg.derivative(), multiply(c(Math.log(base)), arg));
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
        return "log" + Double.toString(base) + "(" + arg + ")";
    }
}