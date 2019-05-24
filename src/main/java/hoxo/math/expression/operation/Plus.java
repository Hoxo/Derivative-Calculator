package hoxo.math.expression.operation;

import hoxo.math.expression.Constant;
import hoxo.math.expression.Expression;
import hoxo.math.expression.ExpressionVisitor;

import static hoxo.math.expression.function.Functions.*;

public class Plus extends BinaryOperation {
    private Plus(Expression left, Expression right) {
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

    @Override
    public <T> T visit(ExpressionVisitor<T> visitor) {
        return visitor.visitPlus(this);
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
        return new Plus(left, right);
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
