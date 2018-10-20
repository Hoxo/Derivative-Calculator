package hoxo.function;

public class Minus extends BinaryOperation {
    private Minus(Function left, Function right) {
        super(left, right);
    }

    @Override
    public Function derivative() {
        return cons(getLeft().derivative(), getRight().derivative());
    }

    public static Function cons(Function left, Function right) {
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
