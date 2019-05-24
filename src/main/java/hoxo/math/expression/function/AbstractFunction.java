package hoxo.math.expression.function;

import hoxo.math.expression.Constant;
import hoxo.math.expression.Expression;
import hoxo.math.expression.ExpressionVisitor;

public abstract class AbstractFunction implements Expression {
    protected final Expression arg;
    private String name;

    public AbstractFunction(String name, Expression arg) {
        this.arg = arg;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Expression getArgument() {
        return arg;
    }

    @Override
    public <T> T visit(ExpressionVisitor<T> visitor) {
        return visitor.visitFunction(this);
    }

    public boolean isNegative() {
        return arg.getClass().equals(Negative.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractFunction that = (AbstractFunction) o;

        return arg.equals(that.arg);
    }

    public abstract String toString();

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

        @Override
        public <T> T visit(ExpressionVisitor<T> visitor) {
            return visitor.visitNegative(this);
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
