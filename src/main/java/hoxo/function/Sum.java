package hoxo.function;

import static hoxo.function.Functions.*;

public class Sum extends BinaryOperation {
    private Sum(Function left, Function right) {
        super(left, right);
    }

    @Override
    public Function derivative() {
        return sum(getLeft().derivative(), getRight().derivative());
    }

    @Override
    public double evaluate(double x) {
        return getLeft().evaluate(x) + getRight().evaluate(x);
    }

    public static Function cons(Function left, Function right) {
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
    public String toString() {
        return getLeft() + " + " + getRight();
    }
}
