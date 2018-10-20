package hoxo.function;

public abstract class AbstractFunction implements Function {
    protected final Function arg;

    public AbstractFunction(Function arg) {
        this.arg = arg;
    }

    public Function getArgument() {
        return arg;
    }

    public abstract Function derivative();

    public boolean isNegative() {
        return getClass().equals(Negative.class);
    }

    public abstract String toString();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractFunction that = (AbstractFunction) o;

        return arg.equals(that.arg);
    }

    public static class Negative implements Function {
        private final Function arg;

        private Negative(Function arg) {
            this.arg = arg;
        }

        @Override
        public Function derivative() {
            return Negative.wrap(arg.derivative());
        }

        @Override
        public double evaluate(double x) {
            return -arg.evaluate(x);
        }

        public Function getArg() {
            return arg;
        }

        public static Function wrap(Function function) {
            if (function instanceof Constant) {
                return Constant.cons(-((Constant) function).getValue());
            }
            if (function instanceof Negative) {
                return ((Negative) function).getArg();
            }
            return new Negative(function);
        }

        @Override
        public String toString() {
            return "-" + arg;
        }
    }

}
