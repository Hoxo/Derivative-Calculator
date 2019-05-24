package hoxo.math.expression.function;

import hoxo.math.expression.Expression;

public abstract class AbstractFunction implements Expression {
    protected final Expression arg;

    public AbstractFunction(Expression arg) {
        this.arg = arg;
    }

    public Expression getArgument() {
        return arg;
    }

    public abstract Expression derivative();

    public boolean isNegative() {
        return arg.getClass().equals(Negative.class);
    }

    public abstract String toString();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractFunction that = (AbstractFunction) o;

        return arg.equals(that.arg);
    }

    public static class Negative implements Expression {
        private final Expression arg;

        private Negative(Expression arg) {
            this.arg = arg;
        }

        @Override
        public Expression derivative() {
            return Negative.wrap(arg.derivative());
        }

        @Override
        public double evaluate(double x) {
            return -arg.evaluate(x);
        }

        public Expression getArg() {
            return arg;
        }

        public static Expression wrap(Expression expression) {
            if (expression instanceof Constant) {
                return Constant.cons(-((Constant) expression).getValue());
            }
            if (expression instanceof Negative) {
                return ((Negative) expression).getArg();
            }
            return new Negative(expression);
        }

        @Override
        public String toString() {
            return "-(" + arg + ")";
        }
    }

}
