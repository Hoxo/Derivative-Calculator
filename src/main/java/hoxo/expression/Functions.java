package hoxo.expression;

import hoxo.expression.operation.Divide;
import hoxo.expression.operation.Minus;
import hoxo.expression.operation.Multiply;
import hoxo.expression.operation.Sum;

public final class Functions {
    public static final Identity X = Identity.instance();

    public static Expression multiply(Expression a, Expression b) {
        return Multiply.cons(a, b);
    }

    public static Expression sum(Expression a, Expression b) {
        return Sum.cons(a, b);
    }

    public static Expression divide(Expression a, Expression b) {
        return Divide.cons(a, b);
    }

    public static Expression minus(Expression a, Expression b) {
        return Minus.cons(a, b);
    }

    public static Expression neg(Expression x) {
        return AbstractFunction.Negative.wrap(x);
    }

    public static Expression pow(Expression arg, double pow) {
        return Power.cons(arg, pow);
    }

    public static Expression sqr(Expression arg) {
        return Power.cons(arg, 2);
    }

    public static Expression exponent(double base, Expression arg) {
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

    public static Expression ln(Expression arg) {
        return Log.cons(Math.E, arg);
    }

    public static Expression log(double base, Expression arg) {
        return Log.cons(base, arg);
    }

    public static Expression log2(Expression arg) {
        return Log.cons(2, arg);
    }

    public static Expression log10(Expression arg) {
        return Log.cons(10, arg);
    }

    public static Sin sinX() {
        return new Sin(X);
    }

    public static Expression sin(Expression arg) {
        return new Sin(arg);
    }

    public static Cos cosX() {
        return new Cos(X);
    }

    public static Expression cos(Expression arg) {
        return new Cos(arg);
    }

    public static Constant c(double c) {
        return Constant.cons(c);
    }
}
