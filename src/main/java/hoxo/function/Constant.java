package hoxo.function;

import java.util.Objects;

public class Constant implements Function {
    private final double value;

    public static final Constant ZERO = new Constant(0);
    public static final Constant ONE = new Constant(1);

    private Constant(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public double evaluate(double x) {
        return value;
    }

    @Override
    public Function derivative() {
        return cons(0);
    }

    public static Constant cons(double value) {
        if (value == Constant.ZERO.getValue()) {
            return ZERO;
        }
        if (value == ONE.getValue()) {
            return ONE;
        }
        return new Constant(value);
    }

    public Constant abs() {
        if (value < 0) return cons(-value);
        else return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Constant constant = (Constant) o;

        return Double.compare(constant.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
