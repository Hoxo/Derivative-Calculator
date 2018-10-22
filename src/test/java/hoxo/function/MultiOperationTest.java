package hoxo.function;

import hoxo.function.operation.MultiMultiply;
import hoxo.function.operation.MultiSum;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static hoxo.function.Functions.*;

public class MultiOperationTest {

    public static final double DELTA = 0.0000000001;

    @Test
    public void multiSumWithConstantsTest() {
        int num = 2;
        int limit = 10;
        List<Function> constants = Stream.generate(() -> c(num)).limit(limit).collect(Collectors.toList());
        Function multiSum = MultiSum.cons(constants);
        Assert.assertEquals(num * limit, multiSum.evaluate(12345), DELTA);
        Assert.assertEquals(0, multiSum.derivative().evaluate(12345), DELTA);
    }

    @Test
    public void multiSumWithXTest() {
        int limit = 10;
        int x = 5;
        List<Function> functions = Stream.generate(Identity::instance).limit(limit).collect(Collectors.toList());
        Function sum = MultiSum.cons(functions);
        Assert.assertEquals(x * limit, sum.evaluate(x), DELTA);
        Assert.assertEquals(limit, sum.derivative().evaluate(x), DELTA);
    }

    @Test
    public void multiMultiplyWithConstantsTest() {
        int limit = 10;
        int num  = 2;
        List<Function> constants = Stream.generate(() -> c(num)).limit(limit).collect(Collectors.toList());
        Function multiMultiply = MultiMultiply.cons(constants);
        Assert.assertEquals(Math.pow(num, limit), multiMultiply.evaluate(12345), DELTA);
        Assert.assertEquals(0, multiMultiply.derivative().evaluate(12345), DELTA);
    }
}
