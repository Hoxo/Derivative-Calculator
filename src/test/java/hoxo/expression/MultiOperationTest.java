package hoxo.expression;

import hoxo.expression.operation.MultiMultiply;
import hoxo.expression.operation.MultiSum;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static hoxo.expression.Functions.*;

public class MultiOperationTest {

    public static final double DELTA = 0.0000000001;

    @Test
    public void multiSumWithConstantsTest() {
        int num = 2;
        int limit = 10;
        List<Expression> constants = Stream.generate(() -> c(num)).limit(limit).collect(Collectors.toList());
        Expression multiSum = MultiSum.cons(constants);
        Assert.assertEquals(num * limit, multiSum.evaluate(12345), DELTA);
        Assert.assertEquals(0, multiSum.derivative().evaluate(12345), DELTA);
    }

    @Test
    public void multiSumWithXTest() {
        int limit = 10;
        int x = 5;
        List<Expression> expressions = Stream.generate(Identity::instance).limit(limit).collect(Collectors.toList());
        Expression sum = MultiSum.cons(expressions);
        Assert.assertEquals(x * limit, sum.evaluate(x), DELTA);
        Assert.assertEquals(limit, sum.derivative().evaluate(x), DELTA);
    }

    @Test
    public void multiMultiplyWithConstantsTest() {
        int limit = 10;
        int num  = 2;
        List<Expression> constants = Stream.generate(() -> c(num)).limit(limit).collect(Collectors.toList());
        Expression multiMultiply = MultiMultiply.cons(constants);
        Assert.assertEquals(Math.pow(num, limit), multiMultiply.evaluate(12345), DELTA);
        Assert.assertEquals(0, multiMultiply.derivative().evaluate(12345), DELTA);
    }
}
