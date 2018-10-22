package hoxo.function.operation;

import hoxo.function.Constant;
import hoxo.function.Function;

import static hoxo.function.Functions.*;

public class Divide extends BinaryOperation {

    private Divide(Function left, Function right) {
        super(left, right);
    }

    @Override
    public Function derivative() {
        return cons(
                minus(multiply(getLeft().derivative(), getRight()), multiply(getLeft(), getRight().derivative())),
                pow(getLeft(), 2));
    }

    public static Function cons(Function up, Function down) {
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
