package hoxo.math.expression.function;

import hoxo.math.expression.Constant;
import hoxo.math.expression.Expression;
import hoxo.math.expression.Variable;
import hoxo.math.expression.operation.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

public final class Functions {
    static final Map<String, UnaryOperator<Expression>> FUNCTIONS;

    @Target(value = {ElementType.METHOD})
    @Retention(value = RetentionPolicy.RUNTIME)
    private @interface Enabled {
        String name() default "";
    }

    static {
        FUNCTIONS = findAllFunctionsInClass();
    }

    private static Map<String, UnaryOperator<Expression>> findAllFunctionsInClass() {
        Map<String, UnaryOperator<Expression>> functions = new HashMap<>();
        List<Method> methods = findAllFunctionsAsMethods();
        for (Method method : methods) {
            functions.put(getFunctionMethodName(method), convertToUnaryOperator(method));
        }
        return functions;
    }

    private static UnaryOperator<Expression> convertToUnaryOperator(Method method) {
        return x -> {
            try {
                return (Expression) method.invoke(null, x);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getCause());
            }
        };
    }

    private static String getFunctionMethodName(Method method) {
        Enabled annotation = method.getAnnotation(Enabled.class);
        String name = annotation.name();
        if (name.isBlank()) {
            return method.getName();
        } else {
            return name;
        }
    }

    private static List<Method> findAllFunctionsAsMethods() {
        Class<Functions> classObj = Functions.class;
        Method[] methods = classObj.getMethods();
        List<Method> functions = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Enabled.class)) {
                if (method.getReturnType().equals(Expression.class) &&
                        method.getParameterCount() == 1 && method.getParameterTypes()[0].equals(Expression.class))
                {
                    functions.add(method);
                }
            }
        }
        return functions;
    }

    public static Map<String, UnaryOperator<Expression>> functions() {
        return FUNCTIONS;
    }

    public static Expression multiply(Expression a, Expression b) {
        return Multiply.cons(a, b);
    }

    public static Expression sum(Expression a, Expression b) {
        return Plus.cons(a, b);
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

    @Enabled
    public static Expression sqr(Expression arg) {
        return Power.cons(arg, c(2));
    }

    @Enabled
    public static Expression sqrt(Expression arg) {
        return new Sqrt(arg);
    }

    @Enabled
    public static Expression exp(Expression arg) {
        return Power.cons(Constant.E, arg);
    }

    @Enabled
    public static Expression ln(Expression arg) {
        return Log.cons(Math.E, arg);
    }

    public static Expression log(double base, Expression arg) {
        return Log.cons(base, arg);
    }

    @Enabled
    public static Expression log2(Expression arg) {
        return Log.cons(2, arg);
    }

    @Enabled
    public static Expression log10(Expression arg) {
        return Log.cons(10, arg);
    }

    @Enabled
    public static Expression sin(Expression arg) {
        return new Sin(arg);
    }

    @Enabled
    public static Expression cos(Expression arg) {
        return new Cos(arg);
    }

    public static Variable var(String name) {
        return Variable.byName(name);
    }

    public static Constant c(double c) {
        return Constant.cons(c);
    }
}
