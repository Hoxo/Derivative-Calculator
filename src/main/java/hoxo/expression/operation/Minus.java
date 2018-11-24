package hoxo.expression.operation;

import hoxo.expression.Expression;

public class Minus extends BinaryOperation {
    private Minus(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Expression derivative() {
        return cons(getLeft().derivative(), getRight().derivative());
    }

    public static Expression cons(Expression left, Expression right) {
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
}
