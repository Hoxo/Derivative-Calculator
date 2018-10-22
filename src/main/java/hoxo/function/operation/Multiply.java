package hoxo.function.operation;

import hoxo.function.Constant;
import hoxo.function.Function;

import static hoxo.function.Functions.*;

public class Multiply extends BinaryOperation {

    private Multiply(Function left, Function right) {
        super(left, right);
    }

    @Override
    public Function derivative() {
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

    public static Function cons(Function left, Function right) {
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
        if (left.equals(right)) {
            return pow(left, 2);
        }
        return new Multiply(left, right);
    }

    @Override
    public String toString() {
        return "(" + getLeft() + ") * (" + getRight() + ")";
    }
}
