package hoxo.math.expression.operation;

import hoxo.math.expression.Expression;

public abstract class BinaryOperation implements Expression {
    private final Expression left;
    private final Expression right;

    public BinaryOperation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
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

    @Override
    public String toString() {
        return String.format("%s %s %s", wrapIfIsAnOperation(getLeft()), operator(), wrapIfIsAnOperation(getRight()));
    }

    private String wrapIfIsAnOperation(Expression exp) {
        return exp instanceof BinaryOperation ? "(" + exp + ")" : exp.toString();
    }

    protected abstract String operator();
}
