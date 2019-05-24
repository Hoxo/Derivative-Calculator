package hoxo.math.expression.operation;

import hoxo.math.expression.function.AbstractFunction;
import hoxo.math.expression.function.Constant;
import hoxo.math.expression.Expression;
import static hoxo.math.expression.function.Functions.*;

public class Minus extends BinaryOperation {
    private Minus(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Expression derivative() {
        return cons(getLeft().derivative(), getRight().derivative());
    }

    public static Expression cons(Expression left, Expression right) {
        if (right.equals(Constant.ZERO)) {
            return left;
        }
        if (left.equals(Constant.ZERO)) {
            return neg(right);
        }
        if (right instanceof AbstractFunction.Negative) {
            return sum(left, ((AbstractFunction.Negative) right).getArg());
        }
        return new Minus(left, right);
    }

    @Override
    public boolean isCommutative() {
        return false;
    }

    @Override
    public double evaluate(double x) {
        return getLeft().evaluate(x) - getRight().evaluate(x);
    }

    @Override
    protected String operator() {
        return "-";
    }
}
