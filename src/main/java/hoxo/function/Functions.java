package hoxo.function;

public final class Functions {
    public static final Identity X = Identity.instance();

    public static Function multiply(Function a, Function b) {
        return Multiply.cons(a, b);
    }

    public static Function sum(Function a, Function b) {
        return Sum.cons(a, b);
    }

    public static Function divide(Function a, Function b) {
        return Divide.cons(a, b);
    }

    public static Function minus(Function a, Function b) {
        return Minus.cons(a, b);
    }

    public static Function neg(Function x) {
        return AbstractFunction.Negative.wrap(x);
    }

    public static Function pow(Function arg, double pow) {
        return Power.cons(arg, pow);
    }

    public static Function sqr(Function arg) {
        return Power.cons(arg, 2);
    }

    public static Function exponent(double base, Function arg) {
        return Exponent.cons(base, arg);
    }

    public static Log lnX() {
        return Log.LN_X;
    }

    public static Log log2X() {
        return Log.LOG2_X;
    }

    public static Log log10X() {
        return Log.LOG10_X;
    }

    public static Function ln(Function arg) {
        return Log.cons(Math.E, arg);
    }

    public static Function log(double base, Function arg) {
        return Log.cons(base, arg);
    }

    public static Function log2(Function arg) {
        return Log.cons(2, arg);
    }

    public static Function log10(Function arg) {
        return Log.cons(10, arg);
    }

    public static Sin sinX() {
        return new Sin(X);
    }

    public static Function sin(Function arg) {
        return new Sin(arg);
    }

    public static Cos cosX() {
        return new Cos(X);
    }

    public static Function cos(Function arg) {
        return new Cos(arg);
    }

    public static Constant c(double c) {
        return Constant.cons(c);
    }
}
