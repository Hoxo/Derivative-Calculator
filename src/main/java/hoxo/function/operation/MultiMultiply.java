package hoxo.function.operation;

import com.google.common.collect.Lists;
import hoxo.function.Function;

import java.util.List;
import java.util.stream.Collectors;

public class MultiMultiply extends MultiOperation {
    private MultiMultiply(List<Function> args) {
        super(args);
    }

    public static Function cons(List<Function> args) {
        return new MultiMultiply(args);
    }

    public static Function cons(Function first, Function second, Function... rest) {
        return cons(Lists.asList(first, second, rest));
    }

    @Override
    public Function derivative() {
        List<Function> sum = Lists.newArrayList();
        for (Function function : getArgs()) {
            Function deriv = function.derivative();
            List<Function> withDeriv = getArgs().stream().filter(f -> !f.equals(function)).collect(Collectors.toList());
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
