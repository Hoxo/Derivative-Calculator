package hoxo.expression.operation;

import hoxo.expression.Constant;
import hoxo.expression.Expression;

import static hoxo.expression.Functions.*;

public class Divide extends BinaryOperation {

    private Divide(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Expression derivative() {
        return cons(
                minus(multiply(getLeft().derivative(), getRight()), multiply(getLeft(), getRight().derivative())),
                pow(getLeft(), 2));
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
        return 0;
    }

    @Override
    public String toString() {
        return "(" +  getLeft() + ") / (" + getRight() + ")";
    }
}
