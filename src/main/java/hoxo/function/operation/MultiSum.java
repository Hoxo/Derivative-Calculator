package hoxo.function.operation;

import com.google.common.collect.Lists;
import hoxo.function.Constant;
import hoxo.function.Function;

import java.util.List;
import java.util.stream.Collectors;

public class MultiSum extends MultiOperation {
    private MultiSum(List<Function> args) {
        super(args);
    }

    public static Function cons(List<Function> args) {
        //TODO Add validation and simlplification
        if (args.stream().allMatch(func -> func.equals(Constant.ZERO))) {
            return Constant.ZERO;
        }
        return new MultiSum(args);
    }

    public static Function cons(Function first, Function second, Function... rest) {
        return cons(Lists.asList(first, second, rest));
    }

    @Override
    public Function derivative() {
        return new MultiSum(getArgs().stream().map(Function::derivative).collect(Collectors.toList()));
    }

    @Override
    public double evaluate(double x) {
        return getArgs().stream().mapToDouble(function -> function.evaluate(x)).reduce(0, (a, b) -> a + b);
    }
}
