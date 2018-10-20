package hoxo.function;

public class Identity implements Function {

    private static Identity INSTANCE;

    private Identity() {}

    @Override
    public Function derivative() {
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
