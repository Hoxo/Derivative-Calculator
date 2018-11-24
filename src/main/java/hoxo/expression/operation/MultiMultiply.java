package hoxo.expression.operation;

import com.google.common.collect.Lists;
import hoxo.expression.Expression;

import java.util.List;
import java.util.stream.Collectors;

public class MultiMultiply extends MultiOperation {
    private MultiMultiply(List<Expression> args) {
        super(args);
    }

    public static Expression cons(List<Expression> args) {
        return new MultiMultiply(args);
    }

    public static Expression cons(Expression first, Expression second, Expression... rest) {
        return cons(Lists.asList(first, second, rest));
    }

    @Override
    public Expression derivative() {
        List<Expression> sum = Lists.newArrayList();
        for (Expression expression : getArgs()) {
            Expression deriv = expression.derivative();
            List<Expression> withDeriv = getArgs().stream().filter(f -> !f.equals(expression)).collect(Collectors.toList());
            withDeriv.add(deriv);
            sum.add(MultiMultiply.cons(withDeriv));
        }
        return MultiSum.cons(sum);
    }

    @Override
    public double evaluate(double x) {
        return getArgs().stream().mapToDouble(function -> function.evaluate(x)).reduce(1, (a, b) -> a * b);
    }
}
