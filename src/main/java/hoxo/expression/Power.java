package hoxo.expression;

import static hoxo.expression.Functions.*;

public class Power extends AbstractFunction {
    private final double power;

    private Power(Expression arg, double power) {
        super(arg);
        this.power = power;
    }

    public static Expression cons(Expression arg, double power) {
        if (arg.equals(Constant.ZERO))
            return arg;
        if (power == 1)
            return arg;
        if (power == 0)
            return Constant.ONE;
        return new Power(arg, power);
    }

    @Override
    public double evaluate(double x) {
        return Math.pow(arg.evaluate(x), power);
    }

    @Override
    public Expression derivative() {
        return multiply(multiply(c(power), arg.derivative()), pow(arg, power - 1));
    }

    @Override
    public String toString() {
        return "(" + arg + ")^" + power;
    }
}
