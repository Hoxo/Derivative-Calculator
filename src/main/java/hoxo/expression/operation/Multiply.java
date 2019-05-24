package hoxo.expression.operation;

import hoxo.expression.AbstractFunction;
import hoxo.expression.Constant;
import hoxo.expression.Expression;

import static hoxo.expression.Functions.*;

public class Multiply extends BinaryOperation {

    private Multiply(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Expression derivative() {
        return sum(multiply(getLeft().derivative(), getRight()), multiply(getLeft(), getRight().derivative()));
    }

    @Override
    public boolean isCommutative() {
        return true;
    }

    @Override
    public double evaluate(double x) {
        return getLeft().evaluate(x) * getRight().evaluate(x);
    }

    public static Expression cons(Expression left, Expression right) {
        if (Constant.ZERO.equals(left) || Constant.ZERO.equals(right)) {
            return Constant.ZERO;
        }
        if (Constant.ONE.equals(left)) {
            return right;
        } else {
            if (Constant.ONE.equals(right)) {
                return left;
            }
        }
        boolean isNegative = false;
        if (left instanceof AbstractFunction.Negative) {
            left = ((AbstractFunction.Negative) left).getArg();
            isNegative = !isNegative;
        }
        if (right instanceof AbstractFunction.Negative) {
            right = ((AbstractFunction.Negative) right).getArg();
            isNegative = !isNegative;
        }

        if (left.equals(right)) {
            return wrapIfIsNegative(pow(left, c(2)), isNegative);
        }
        return wrapIfIsNegative(new Multiply(left, right), isNegative);
    }

    private static Expression wrapIfIsNegative(Expression expression, boolean isNegative) {
        return isNegative ? neg(expression) : expression;
    }

    @Override
    protected String operator() {
        return "*";
    }
}
