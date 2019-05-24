package hoxo.expression;

import hoxo.expression.operation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class Functions {
    public static final Identity X = Identity.instance();
    static final Map<String, Function<Expression, Expression>> FUNCTIONS = new HashMap<>();

    static {
        FUNCTIONS.put("sin", Functions::sin);
        FUNCTIONS.put("cos", Functions::cos);
        FUNCTIONS.put("log10", Functions::log10);
        FUNCTIONS.put("log2", Functions::log2);
        FUNCTIONS.put("ln", Functions::ln);
        FUNCTIONS.put("exp", Functions::exp);
        FUNCTIONS.put("sqr", Functions::sqr);
        FUNCTIONS.put("sqrt", Functions::sqrt);
    }

    public static Map<String, Function<Expression, Expression>> functions() {
        return FUNCTIONS;
    }

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

    public static Expression pow(Expression arg, Expression pow) {
        return Power.cons(arg, pow);
    }

    public static Expression sqr(Expression arg) {
        return Power.cons(arg, c(2));
    }

    public static Expression sqrt(Expression arg) {
        return Power.cons(arg, c(1 / 2.));
    }

    public static Expression exp(Expression arg) {
        return Power.cons(Constant.E, arg);
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

    public static Expression sin(Expression arg) {
        return new Sin(arg);
    }

    public static Expression cos(Expression arg) {
        return new Cos(arg);
    }

    public static Constant c(double c) {
        return Constant.cons(c);
    }
}
