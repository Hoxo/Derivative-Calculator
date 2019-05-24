package hoxo.math.expression.operation;

import hoxo.math.expression.Constant;
import hoxo.math.expression.Expression;
import hoxo.math.expression.ExpressionVisitor;

import static hoxo.math.expression.function.Functions.*;

public class Power extends BinaryOperation {

    private Power(Expression u, Expression v) {
        super(u, v);
    }

    public static Expression cons(Expression u, Expression v) {
        if (u.equals(Constant.ZERO))
            return u;
        if (v.equals(Constant.ONE))
            return u;
        if (v.equals(Constant.ZERO))
            return Constant.ONE;
        return new Power(u, v);
    }

    @Override
    public boolean isCommutative() {
        return false;
    }

    @Override
    public double evaluate(double x) {
        return Math.pow(getLeft().evaluate(x), getRight().evaluate(x));
    }

    @Override
    public <T> T visit(ExpressionVisitor<T> visitor) {
        return visitor.visitPower(this);
    }

    @Override
    public Expression derivative() {
        if (getLeft() instanceof Constant && getRight() instanceof Constant) {
            return Constant.ZERO;
        }
        if (getRight() instanceof Constant) {
            return multiply(
                    multiply(
                            getRight(),
                            getLeft().derivative()
                    ),
                    pow(
                            getLeft(),
                            ((Constant) getRight()).dec()
                    )
            );
        }
        if (getLeft() instanceof Constant) {
            return multiply(
                    multiply(
                            ln(getLeft()),
                            getRight().derivative()
                    ),
                    this
            );
        }
        return multiply(
                this,
                sum(
                        multiply(
                                getRight().derivative(),
                                ln(getLeft())),
                        multiply(
                                divide(
                                        getLeft().derivative(),
                                        getLeft()
                                ),
                                getRight()
                        )
                )
        );
    }

    @Override
    protected String operator() {
        return "^";
    }
}
