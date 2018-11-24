package hoxo.expression.operation;

import com.google.common.collect.Lists;
import hoxo.expression.Constant;
import hoxo.expression.Expression;

import java.util.List;
import java.util.stream.Collectors;

public class MultiSum extends MultiOperation {
    private MultiSum(List<Expression> args) {
        super(args);
    }

    public static Expression cons(List<Expression> args) {
        //TODO Add validation and simlplification
        if (args.stream().allMatch(func -> func.equals(Constant.ZERO))) {
            return Constant.ZERO;
        }
        return new MultiSum(args);
    }

    public static Expression cons(Expression first, Expression second, Expression... rest) {
        return cons(Lists.asList(first, second, rest));
    }

    @Override
    public Expression derivative() {
        return new MultiSum(getArgs().stream().map(Expression::derivative).collect(Collectors.toList()));
    }

    @Override
    public double evaluate(double x) {
        return getArgs().stream().mapToDouble(function -> function.evaluate(x)).reduce(0, (a, b) -> a + b);
    }
}
