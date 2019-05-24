package hoxo.math.expression.operation;

import hoxo.math.expression.function.Constant;
import hoxo.math.expression.Expression;

import static hoxo.math.expression.function.Functions.*;

public class Sum extends BinaryOperation {
    private Sum(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Expression derivative() {
        return sum(getLeft().derivative(), getRight().derivative());
    }

    @Override
    public double evaluate(double x) {
        return getLeft().evaluate(x) + getRight().evaluate(x);
    }

    public static Expression cons(Expression left, Expression right) {
        if (left instanceof Constant && right instanceof Constant) {
            return c(((Constant) left).getValue() + ((Constant) right).getValue());
        }
        if (left.equals(right)) {
            return multiply(c(2), left);
        }
        if (Constant.ZERO.equals(left)) {
            if (Constant.ZERO.equals(right)) {
                return Constant.ZERO;
            } else {
                return right;
            }
        } else {
            if (Constant.ZERO.equals(right)) {
                return left;
            }
        }
        return new Sum(left, right);
    }

    @Override
    public boolean isCommutative() {
        return true;
    }

    @Override
    protected String operator() {
        return "+";
    }
}
