package hoxo.function.operation;

import hoxo.function.Function;

public abstract class BinaryOperation implements Function {
    private final Function left;
    private final Function right;

    public BinaryOperation(Function left, Function right) {
        this.left = left;
        this.right = right;
    }

    public Function getLeft() {
        return left;
    }

    public Function getRight() {
        return right;
    }

    public abstract boolean isCommutative();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinaryOperation that = (BinaryOperation) o;

        return left.equals(that.left) && right.equals(that.right) ||
                isCommutative() && left.equals(that.right) && right.equals(that.left);
    }

}
