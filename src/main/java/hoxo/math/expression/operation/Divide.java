package hoxo.math.expression.operation;

import hoxo.math.expression.function.Constant;
import hoxo.math.expression.Expression;

import static hoxo.math.expression.function.Functions.*;

public class Divide extends BinaryOperation {

    private Divide(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Expression derivative() {
        return cons(
                minus(
                        multiply(
                                getLeft().derivative(),
                                getRight()
                        ),
                        multiply(
                                getLeft(),
                                getRight().derivative()
                        )
                ),
                pow(
                        getRight(),
                        c(2)
                )
        );
    }

    public static Expression cons(Expression up, Expression down) {
        if (up.equals(Constant.ZERO))
            return up;
        if (down.equals(Constant.ZERO))
            throw new ArithmeticException("Divided by zero");
        if (down.equals(Constant.ONE))
            return up;
        if (up.equals(down))
            return Constant.ONE;
        if (up instanceof Constant && down instanceof Constant)
            return c(((Constant) up).getValue()/ ((Constant) down).getValue());
        return new Divide(up, down);
    }

    @Override
    public boolean isCommutative() {
        return false;
    }

    @Override
    public double evaluate(double x) {
        return getLeft().evaluate(x) / getRight().evaluate(x);
    }

    @Override
    protected String operator() {
        return "/";
    }
}
