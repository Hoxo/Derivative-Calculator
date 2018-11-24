package hoxo.expression;

public class Identity implements Expression {

    private static Identity INSTANCE;

    private Identity() {}

    @Override
    public Expression derivative() {
        return Constant.ONE;
    }

    @Override
    public double evaluate(double x) {
        return x;
    }

    public static Identity instance() {
        if (INSTANCE == null) {
            INSTANCE = new Identity();
        }
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "x";
    }
}
